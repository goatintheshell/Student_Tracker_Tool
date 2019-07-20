package com.hilarysturges.c196;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

public class CourseScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayList textArray = new ArrayList();
        for (int i = 0; i < MainActivity.courses.size(); i++) {
            Course course = MainActivity.courses.get(i);
            textArray.add(course.getTitle());
        }
        ScrollView scrollView = new ScrollView(this);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        final String[] instructor = new String[3];
        final String[] assessment = new String[2];

        for (int i = 0; i < textArray.size(); i++) {
            TextView textView = new TextView(this);
            textView.setText(String.valueOf(textArray.get(i)));
            textView.setTextSize(30);
            textView.setId(i);
            textView.setPadding(30,30,0,30);
            final String title = MainActivity.courses.get(i).getTitle();
            final String start = String.valueOf(MainActivity.courses.get(i).getStartDate());
            final String end = String.valueOf(MainActivity.courses.get(i).getEndDate());
            final String status = MainActivity.courses.get(i).getStatus();
            final String id = String.valueOf(MainActivity.courses.get(i).get_id());
            final String index = String.valueOf(i);
            if (MainActivity.courses.get(i).getInstructor() != null) {
                instructor[0] = MainActivity.courses.get(i).getInstructor().getName();
                instructor[1] = MainActivity.courses.get(i).getInstructor().getPhoneNumber();
                instructor[2] = MainActivity.courses.get(i).getInstructor().getEmail();
            }
            if (MainActivity.courses.get(i).getAssessment() != null) {
                assessment[0] = MainActivity.courses.get(i).getAssessment().getName();
                assessment[1] = MainActivity.courses.get(i).getAssessment().getType();
            }

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(getApplicationContext(), DetailCourseScreen.class);
                    intent.putExtra("title", title);
                    intent.putExtra("start", start);
                    intent.putExtra("end", end);
                    intent.putExtra("status", status);
                    intent.putExtra("ID", id);
                    intent.putExtra("index", index);
                    if (instructor[0] != null) {
                        intent.putExtra("instructorName", instructor[0]);
                        intent.putExtra("instructorPhone", instructor[1]);
                        intent.putExtra("instructorEmail", instructor[2]);
                    }
                    if (assessment[0] != null) {
                        intent.putExtra("assessmentName", assessment[0]);
                        intent.putExtra("assessmentType", assessment[1]);
                    }
                    startActivity(intent);
                }
            });
            linearLayout.addView(textView);


        }
        Button addCourseButton = new Button(this);
        addCourseButton.setText("Add Course");
        Button homeScreenButton = new Button(this);
        homeScreenButton.setText("Return to home");

        addCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AddCourseScreen.class);
                startActivity(i);
            }
        });

        homeScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });

        linearLayout.addView(addCourseButton);
        linearLayout.addView(homeScreenButton);
        scrollView.addView(linearLayout);
        setContentView(scrollView);
    }
}
