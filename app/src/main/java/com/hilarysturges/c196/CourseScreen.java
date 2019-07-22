package com.hilarysturges.c196;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
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

        final ArrayList textArray = new ArrayList();
        for (int i = 0; i < MainActivity.courses.size(); i++) {
            Course course = MainActivity.courses.get(i);
            textArray.add(course.getTitle());
        }
        ScrollView scrollView = new ScrollView(this);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        for (int i = 0; i < textArray.size(); i++) {
            final TextView textView = new TextView(this);
            textView.setText(String.valueOf(textArray.get(i)));
            textView.setTextSize(30);
            textView.setId(i);
            textView.setPadding(30, 30, 0, 30);
            final String title = MainActivity.courses.get(i).getTitle();
            final String start = String.valueOf(MainActivity.courses.get(i).getStartDate());
            final String end = String.valueOf(MainActivity.courses.get(i).getEndDate());
            final String status = MainActivity.courses.get(i).getStatus();
            final String id = String.valueOf(MainActivity.courses.get(i).get_id());
            final String index = String.valueOf(i);
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
                    if (MainActivity.courses.get(textView.getId()).getAssessment() != null) {
                        intent.putExtra("assessmentName", MainActivity.courses.get(textView.getId()).getAssessment().getName());
                        intent.putExtra("assessmentType", MainActivity.courses.get(textView.getId()).getAssessment().getType());
                    }
                    if (MainActivity.courses.get(textView.getId()).getInstructor() != null) {
                        intent.putExtra("instructorName", MainActivity.courses.get(textView.getId()).getInstructor().getName());
                        intent.putExtra("instructorPhone", MainActivity.courses.get(textView.getId()).getInstructor().getPhoneNumber());
                        intent.putExtra("instructorEmail", MainActivity.courses.get(textView.getId()).getInstructor().getEmail());
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
