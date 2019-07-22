package com.hilarysturges.c196;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
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

import java.sql.Date;
import java.util.Calendar;

import static com.hilarysturges.c196.Channels.CHANNEL_1_ID;

public class DetailCourseScreen extends AppCompatActivity {

    DBManager databaseMan;
    private NotificationManagerCompat notificationManager;
    private AlarmManager alarmManager;
    public int ID;
    public int startYear;
    public int startMonth;
    public int startDay;
    public int endYear;
    public int endMonth;
    public int endDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        notificationManager = NotificationManagerCompat.from(this);
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        databaseMan = new DBManager(this, null, null, 1);

        ScrollView scrollView = new ScrollView(this);
        LinearLayout linearLayout = new LinearLayout(this);
        TextView titleText = new TextView(this);
        final EditText titleEdit = new EditText(this);
        TextView startText = new TextView(this);
        final EditText startEdit = new EditText(this);
        Switch startSwitch = new Switch(this);
        TextView endText = new TextView(this);
        final EditText endEdit = new EditText(this);
        Switch endSwitch = new Switch(this);
        TextView statusText = new TextView(this);
        final EditText statusEdit = new EditText(this);
        TextView instructorNameText = new TextView(this);
        TextView instructorPhoneText = new TextView(this);
        TextView instructorEmailText = new TextView(this);
        TextView assessmentNameText = new TextView(this);
        TextView assessmentTypeText = new TextView(this);
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

        instructorNameText.setText("Instructor Name: " + instructorName);
        instructorNameText.setTextSize(30);
        instructorNameText.setPadding(30,30,0,30);
        instructorPhoneText.setText("Instructor Phone: " + instructorPhone);
        instructorPhoneText.setTextSize(30);
        instructorPhoneText.setPadding(30,30,0,30);
        instructorEmailText.setText("Instructor Email: " + instructorEmail);
        instructorEmailText.setTextSize(30);
        instructorEmailText.setPadding(30,30,0,30);
        assessmentNameText.setText("Assessment Name: " + assessmentName);
        assessmentNameText.setTextSize(30);
        assessmentNameText.setPadding(30,30,0,30);
        assessmentTypeText.setText("Assessment Type: " + assessmentType);
        assessmentTypeText.setTextSize(30);
        assessmentTypeText.setPadding(30,30,0,30);

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
                if (instructorName != null) {
            linearLayout.addView(instructorNameText);
            linearLayout.addView(instructorPhoneText);
            linearLayout.addView(instructorEmailText);
        }
        if (assessmentName != null) {
            linearLayout.addView(assessmentNameText);
            linearLayout.addView(assessmentTypeText);
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
                try {databaseMan.removeCourse(_id);}
                catch (Exception e) {System.out.println(e.getMessage());}
                MainActivity.courses.remove(index);
                Intent i = new Intent(getApplicationContext(), CourseScreen.class);
                startActivity(i);
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
    }

    public void startNotificationOn(Boolean b) {
        PackageManager startPM = this.getPackageManager();
        ComponentName receiver = new ComponentName(this, DeviceBootReceiver.class);
        Intent startIntent = new Intent(getApplicationContext(), AlarmReceiver.class);
        PendingIntent startPendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, startIntent, 0);
        AlarmManager startManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (b) {
            //turn notification on
            System.out.println("turned on");
            databaseMan.turnCourseStartOn(ID);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, 19);
            calendar.set(Calendar.YEAR, startYear);
            calendar.set(Calendar.MONTH, startMonth);
            calendar.set(Calendar.DAY_OF_MONTH, startDay);
            startPM.setComponentEnabledSetting(receiver, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
            if (startManager!=null) {
                startManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), startPendingIntent);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    startManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), startPendingIntent);
                }
            }

        } else {
            //turn notification off
            databaseMan.turnCourseStartOff(ID);
            if (startManager!=null) {
                startManager.cancel(startPendingIntent); }
            System.out.println("turned off");
            startPM.setComponentEnabledSetting(receiver, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
        }
    }

    public void endNotificationOn(Boolean b) {
        PackageManager endPM = this.getPackageManager();
        ComponentName receiver = new ComponentName(this, DeviceBootReceiver.class);
        Intent endIntent = new Intent(getApplicationContext(), AlarmReceiver.class);
        PendingIntent endPendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, endIntent, 0);
        AlarmManager endManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (b) {
            //turn notification on
            System.out.println("turned on");
            databaseMan.turnCourseEndOn(ID);

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            Calendar startDate = Calendar.getInstance();
            startDate.set(startYear, startMonth-1, startDay);
            if (calendar.getTimeInMillis() == startDate.getTimeInMillis()) {
//                Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
//                        .setSmallIcon(R.mipmap.ic_launcher)
//                        .setContentTitle("WGU Student Tracker Tool")
//                        .setContentText("Course started!")
//                        .setPriority(NotificationCompat.PRIORITY_HIGH)
//                        .build();
//                notificationManager.notify(1, notification);

                if (alarmManager!=null) {
                    alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), endPendingIntent);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), endPendingIntent);
                    }
                } endPM.setComponentEnabledSetting(receiver, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);

            }




            calendar.set(Calendar.HOUR_OF_DAY, 21);
            calendar.set(Calendar.MINUTE, 45);
            calendar.set(Calendar.YEAR, endYear);
            calendar.set(Calendar.MONTH, endMonth);
            calendar.set(Calendar.DAY_OF_MONTH, endDay);
            if (endManager!=null) {
                endManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), endPendingIntent);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    endManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), endPendingIntent);
                }
            }
            endPM.setComponentEnabledSetting(receiver, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);

        } else {
            //turn notification off
            databaseMan.turnCourseEndOff(ID);
            if (endManager!=null) {
            endManager.cancel(endPendingIntent); }
            System.out.println("turned off");
            endPM.setComponentEnabledSetting(receiver, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
        }
    }


}
