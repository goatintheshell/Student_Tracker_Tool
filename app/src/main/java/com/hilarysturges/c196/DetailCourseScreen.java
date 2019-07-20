package com.hilarysturges.c196;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.sql.Date;

public class DetailCourseScreen extends AppCompatActivity {

    DBManager databaseMan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        TextView instructorNameText = new TextView(this);
        TextView instructorPhoneText = new TextView(this);
        TextView instructorEmailText = new TextView(this);
        TextView assessmentNameText = new TextView(this);
        TextView assessmentTypeText = new TextView(this);
        Button editButton = new Button(this);
        Button deleteButton = new Button(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);


        Intent data = getIntent();
        String title = data.getStringExtra("title");
        String start = data.getStringExtra("start");
        String end = data.getStringExtra("end");
        String status = data.getStringExtra("status");
        final int _id = Integer.parseInt(data.getStringExtra("ID"));
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

        linearLayout.addView(titleText);
        linearLayout.addView(titleEdit);
        linearLayout.addView(startText);
        linearLayout.addView(startEdit);
        linearLayout.addView(endText);
        linearLayout.addView(endEdit);
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
    }
}
