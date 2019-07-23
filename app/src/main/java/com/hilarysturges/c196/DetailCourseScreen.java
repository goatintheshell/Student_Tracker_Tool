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
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;





public class DetailCourseScreen extends AppCompatActivity {

    DBManager databaseMan;
    public int ID;
    public int startYear;
    public int startMonth;
    public int startDay;
    public int endYear;
    public int endMonth;
    public int endDay;
    private Switch startSwitch;
    private Switch endSwitch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startSwitch = new Switch(this);
        endSwitch = new Switch(this);

        databaseMan = new DBManager(this, null, null, 1);

        ScrollView scrollView = new ScrollView(this);
        LinearLayout linearLayout = new LinearLayout(this);
        TextView titleText = new TextView(this);
        final EditText titleEdit = new EditText(this);
        TextView startText = new TextView(this);
        final EditText startEdit = new EditText(this);
        TextView endText = new TextView(this);
        final EditText endEdit = new EditText(this);
        TextView statusText = new TextView(this);
        final EditText statusEdit = new EditText(this);
        TextView instructorsText = new TextView(this);
        TextView instructorNameText = new TextView(this);
        TextView instructorPhoneText = new TextView(this);
        TextView instructorEmailText = new TextView(this);
        TextView assessmentsText = new TextView(this);
        Button editButton = new Button(this);
        Button deleteButton = new Button(this);
        Button addNotesButton = new Button(this);
        Button linkToTermButton = new Button(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);


        Intent data = getIntent();
        final String title = data.getStringExtra("title");
        final String start = data.getStringExtra("start");
        startYear = Integer.parseInt(start.substring(0,4));
        startMonth = Integer.parseInt(start.substring(5,7));
        startDay = Integer.parseInt(start.substring(8,10));
        final String end = data.getStringExtra("end");
        endYear = Integer.parseInt(end.substring(0,4));
        endMonth = Integer.parseInt(end.substring(5,7));
        endDay = Integer.parseInt(end.substring(8,10));
        final String status = data.getStringExtra("status");
        final int _id = Integer.parseInt(data.getStringExtra("ID"));
        ID = _id;
        final int index = Integer.parseInt(data.getStringExtra("index"));
        String instructorName = data.getStringExtra("instructorName");
        String instructorPhone = data.getStringExtra("instructorPhone");
        String instructorEmail = data.getStringExtra("instructorEmail");
        String assessmentName = data.getStringExtra("assessmentName");
        String assessmentType = data.getStringExtra("assessmentType");

        titleText.setText("Course Title: ");
        titleText.setTextSize(30);
        titleText.setPadding(30,30,0,0);
        titleEdit.setText(title);
        titleEdit.setTextSize(30);
        titleEdit.setPadding(30,0,0,30);
        startText.setText("Course Start: ");
        startText.setTextSize(30);
        startText.setPadding(30,30,0,0);
        startEdit.setText(start);
        startEdit.setTextSize(30);
        startEdit.setPadding(30,0,0,30);
        endText.setText("Course End: ");
        endText.setTextSize(30);
        endText.setPadding(30,30,0,0);
        endEdit.setText(end);
        endEdit.setTextSize(30);
        endEdit.setPadding(30,0,0,30);
        statusText.setText("Status: ");
        statusText.setTextSize(30);
        statusText.setPadding(30,30,0,0);
        statusEdit.setText(status);
        statusEdit.setTextSize(30);
        statusEdit.setPadding(30,0,0,30);

        instructorsText.setText("Instructors:");
        instructorsText.setTextSize(30);
        instructorsText.setPadding(30,30,0,0);
        instructorNameText.setText("Instructor Name: " + instructorName);
        instructorNameText.setTextSize(30);
        instructorNameText.setPadding(30,30,0,30);
        instructorPhoneText.setText("Instructor Phone: " + instructorPhone);
        instructorPhoneText.setTextSize(30);
        instructorPhoneText.setPadding(30,30,0,30);
        instructorEmailText.setText("Instructor Email: " + instructorEmail);
        instructorEmailText.setTextSize(30);
        instructorEmailText.setPadding(30,30,0,30);
        assessmentsText.setText("Assessments:");
        assessmentsText.setTextSize(30);
        assessmentsText.setPadding(30, 30,0,0);

        editButton.setText("Edit");
        deleteButton.setText("Delete");
        addNotesButton.setText("Add/Edit Notes");
        linkToTermButton.setText("Link to Term");
        startSwitch.setText("Start Notification");
        endSwitch.setText("End Notification");

        if (databaseMan.checkCourseStartOn(_id) == 1) {
            startSwitch.setChecked(true);
        }

        if (databaseMan.checkCourseEndOn(_id) == 1) {
            endSwitch.setChecked(true);
        }

        linearLayout.addView(titleText);
        linearLayout.addView(titleEdit);
        linearLayout.addView(startText);
        linearLayout.addView(startEdit);
        linearLayout.addView(startSwitch);
        linearLayout.addView(endText);
        linearLayout.addView(endEdit);
        linearLayout.addView(endSwitch);
        linearLayout.addView(statusText);
        linearLayout.addView(statusEdit);
        linearLayout.addView(instructorsText);
                if (instructorName != null) {
            linearLayout.addView(instructorNameText);
            linearLayout.addView(instructorPhoneText);
            linearLayout.addView(instructorEmailText);
        } else {
                    TextView noIns = new TextView(this);
                    noIns.setTextSize(15);
                    noIns.setText("No Instructors Linked");
                    noIns.setPadding(30,30,0,30);
                    linearLayout.addView(noIns);
                }
                linearLayout.addView(assessmentsText);
        Course course = MainActivity.courses.get(index);
        if (course.getCourseAssessments()!=null) {
            for (int i = 0; i < course.getCourseAssessments().size(); i++) {
                Assessment assessment = course.getCourseAssessment(i);
                TextView assessmentNew = new TextView(this);
                assessmentNew.setText(assessment.getName());
                assessmentNew.setTextSize(30);
                assessmentNew.setPadding(30, 0, 0, 0);
                assessmentNew.setId(i);
                linearLayout.addView(assessmentNew);
            }
        } else {
            TextView noAss = new TextView(this);
            noAss.setText("No Assessments Linked");
            noAss.setTextSize(15);
            noAss.setPadding(30,30,0,30);
            linearLayout.addView(noAss);
        }

        linearLayout.addView(editButton);
        linearLayout.addView(deleteButton);
        linearLayout.addView(addNotesButton);
        linearLayout.addView(linkToTermButton);
        scrollView.addView(linearLayout);
        setContentView(scrollView);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Assessment> assessments = new ArrayList<>();
                ArrayList<Instructor> instructors = new ArrayList<>();
                try {assessments = databaseMan.getAssocAssessments(_id);}
                catch (Exception e) {System.out.println("Assessments: " + e.getMessage());}
                try {instructors= databaseMan.getAssocInstructors(_id);}
                catch (Exception e) {System.out.println("Instructors: " + e.getMessage());}
                if (assessments.isEmpty() && instructors.isEmpty()) {
                    try {databaseMan.removeCourse(_id);}
                    catch (Exception e) {System.out.println("Delete: " + e.getMessage());}
                    MainActivity.courses.remove(index);
                    Intent i = new Intent(getApplicationContext(), CourseScreen.class);
                    startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext(), "Cannot delete course with linked assessments or instructors", Toast.LENGTH_LONG).show();
                }
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{databaseMan.updateCourse(_id,titleEdit.getText().toString(),startEdit.getText().toString(),endEdit.getText().toString(),statusEdit.getText().toString());}
                catch(Exception e) {System.out.println(e.getMessage());}
                MainActivity.courses.get(index).setTitle(titleEdit.getText().toString());
                Date startDate = Date.valueOf(startEdit.getText().toString());
                Date endDate = Date.valueOf(endEdit.getText().toString());
                MainActivity.courses.get(index).setStartDate(startDate);
                MainActivity.courses.get(index).setEndDate(endDate);
                MainActivity.courses.get(index).setStatus(statusEdit.getText().toString());
                Intent i = new Intent(getApplicationContext(), CourseScreen.class);
                startActivity(i);
            }
        });

        addNotesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AddNotesScreen.class);
                i.putExtra("ID",_id);
                i.putExtra("index",index);
                startActivity(i);
            }
        });

        linkToTermButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), LinkCourseToTermScreen.class);
                i.putExtra("ID", _id);
                i.putExtra("index", index);
                i.putExtra("title", title);
                i.putExtra("startDate", start);
                i.putExtra("endDate", end);
                i.putExtra("status", status);
                startActivity(i);
            }
        });

        startSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    startNotificationOn(true);
                } else {
                    startNotificationOn(false);
                }
            }
        });

        endSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    endNotificationOn(true);
                } else {
                    endNotificationOn(false);
                }
            }
        });
        startAlarm();
        endAlarm();
    }

    public void startNotificationOn(Boolean b) {
        if (b) {
            //turn notification on
            databaseMan.turnCourseStartOn(ID);
            startAlarm();

        } else {
            //turn notification off
            databaseMan.turnCourseStartOff(ID);
        }
    }

    public void endNotificationOn(Boolean b) {
        if (b) {
            //turn notification on
            databaseMan.turnCourseEndOn(ID);
            endAlarm();
        } else {
            //turn notification off
            databaseMan.turnCourseEndOff(ID);
        }
    }

    public void endAlarm() {
        if ((endSwitch!=null) && endSwitch.isChecked()) {
            Intent endIntent = new Intent(getApplicationContext(), EndAlarmReceiver.class);
            PendingIntent endPendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 2, endIntent, PendingIntent.FLAG_CANCEL_CURRENT);
            AlarmManager endManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, endYear);
            calendar.set(Calendar.MONTH, endMonth - 1);
            calendar.set(Calendar.DAY_OF_MONTH, endDay);
            if (endManager != null) {
                endManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), endPendingIntent);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    endManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), endPendingIntent);
                }
            }
        }
    }

    public void startAlarm() {
         if ((startSwitch!=null) && startSwitch.isChecked()) {
            Intent startIntent = new Intent(getApplicationContext(), StartAlarmReceiver.class);
            PendingIntent startPendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 1, startIntent, PendingIntent.FLAG_CANCEL_CURRENT);
            AlarmManager startManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, startYear);
            calendar.set(Calendar.MONTH, startMonth - 1);
            calendar.set(Calendar.DAY_OF_MONTH, startDay);
            if (startManager != null) {
                startManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), startPendingIntent);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    startManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), startPendingIntent);
                }
            }
        }
    }


}
