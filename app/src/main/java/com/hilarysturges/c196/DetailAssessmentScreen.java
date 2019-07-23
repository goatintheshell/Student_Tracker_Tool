package com.hilarysturges.c196;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

public class DetailAssessmentScreen extends AppCompatActivity {

    DBManager databaseMan;
    public int ID;
    public int alarmYear;
    public int alarmMonth;
    public int alarmDay;
    private Switch alarmSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        alarmSwitch = new Switch(this);

        databaseMan = new DBManager(this, null, null, 1);

        LinearLayout linearLayout = new LinearLayout(this);
        TextView nameText = new TextView(this);
        final EditText nameEdit = new EditText(this);
        TextView typeText = new TextView(this);
        final EditText typeEdit = new EditText(this);
        TextView courseDueDateText = new TextView(this);
        TextView notesText = new TextView(this);
        TextView courseNotesText = new TextView(this);
        Button editButton = new Button(this);
        Button deleteButton = new Button(this);
        Button linkToCourseButton = new Button(this);
        FloatingActionButton shareButton = new FloatingActionButton(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        setContentView(linearLayout);

        Intent data = getIntent();
        final String name = data.getStringExtra("name");
        final String type = data.getStringExtra("type");
        final int _id = Integer.parseInt(data.getStringExtra("ID"));
        ID = _id;
        final int index = Integer.parseInt(data.getStringExtra("index"));
        final String courseDueDate = data.getStringExtra("courseDueDate");
        if (courseDueDate!=null) {
            alarmYear = Integer.parseInt(courseDueDate.substring(0, 4));
            alarmMonth = Integer.parseInt(courseDueDate.substring(5, 7));
            alarmDay = Integer.parseInt(courseDueDate.substring(8, 10));
        }
        final String notes = data.getStringExtra("notes");

        nameText.setText("Name: ");
        nameText.setTextSize(30);
        nameText.setPadding(30,30,0,0);
        nameEdit.setText(name);
        nameEdit.setTextSize(30);
        nameEdit.setPadding(30,0,0,30);
        typeText.setText("Type: ");
        typeText.setTextSize(30);
        typeText.setPadding(30,30,0,0);
        typeEdit.setText(type);
        typeEdit.setTextSize(30);
        typeEdit.setPadding(30,0,0,30);
        courseDueDateText.setText("Course Due Date: " + courseDueDate);
        courseDueDateText.setTextSize(30);
        courseDueDateText.setPadding(30,30,0,30);
        notesText.setText("Notes:");
        notesText.setTextSize(30);
        notesText.setPadding(30,30,0,0);

        editButton.setText("Edit");
        deleteButton.setText("Delete");
        linkToCourseButton.setText("Link to Course");
        alarmSwitch.setText("Due Date Notification");
        shareButton.setImageResource(R.drawable.ic_share_white_24dp);
        shareButton.setPadding(30,0,0,0);
        shareButton.setClickable(true);

        if (databaseMan.checkAssessmentAlarmOn(_id) == 1) {
            alarmSwitch.setChecked(true);
        }

        linearLayout.addView(nameText);
        linearLayout.addView(nameEdit);
        linearLayout.addView(typeText);
        linearLayout.addView(typeEdit);
        linearLayout.addView(courseDueDateText);
        if (courseDueDate!=null)
        linearLayout.addView(alarmSwitch);
        linearLayout.addView(notesText);
        linearLayout.addView(courseNotesText);

        if (notes != null) {
            courseNotesText.setText(notes);
            courseNotesText.setTextSize(15);
            courseNotesText.setPadding(30, 0, 0, 0);
            courseNotesText.setSingleLine(false);
        } else {
            TextView noNotes = new TextView(this);
            noNotes.setText("No course notes attached");
            noNotes.setTextSize(15);
            noNotes.setPadding(30,0,0,0);
            linearLayout.addView(noNotes);
        }

        linearLayout.addView(editButton);
        linearLayout.addView(deleteButton);
        linearLayout.addView(linkToCourseButton);
        if (notes!=null)
        linearLayout.addView(shareButton);

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setAction(Intent.ACTION_SEND);
                i.putExtra(Intent.EXTRA_TEXT, notes);
                i.setType("text/plain");
                startActivity(i);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {databaseMan.removeAssessment(_id);}
                catch (Exception e) {System.out.println(e.getMessage());}
                MainActivity.assessments.remove(index);
                Intent i = new Intent(getApplicationContext(), AssessmentScreen.class);
                startActivity(i);
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{databaseMan.updateAssessment(_id,nameEdit.getText().toString(),typeEdit.getText().toString());}
                catch(Exception e) {System.out.println(e.getMessage());}
                MainActivity.assessments.get(index).setName(nameEdit.getText().toString());
                MainActivity.assessments.get(index).setType(typeEdit.getText().toString());
                Intent i = new Intent(getApplicationContext(), AssessmentScreen.class);
                startActivity(i);
            }
        });

        linkToCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), LinkAssessmentToCourseScreen.class);
                i.putExtra("ID", _id);
                i.putExtra("index", index);
                i.putExtra("name", name);
                i.putExtra("type", type);
                startActivity(i);
            }
        });

        alarmSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    alarmNotificationOn(true);
                } else {
                    alarmNotificationOn(false);
                }
            }
        });
        alarm();
    }

    public void alarmNotificationOn(Boolean b) {
        if (b) {
            //turn notification on
            databaseMan.turnAssessmentAlarmOn(ID);
            alarm();

        } else {
            //turn notification off
            databaseMan.turnAssessmentAlarmOff(ID);
        }
    }

    public void alarm() {
        if ((alarmSwitch!=null) && alarmSwitch.isChecked()) {
            Intent alarmIntent = new Intent(getApplicationContext(), AssessmentAlarmReceiver.class);
            PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 3, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, alarmYear);
            calendar.set(Calendar.MONTH, alarmMonth - 1);
            calendar.set(Calendar.DAY_OF_MONTH, alarmDay);
            if (alarmManager != null) {
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmPendingIntent);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmPendingIntent);
                }
            }
        }
    }
}
