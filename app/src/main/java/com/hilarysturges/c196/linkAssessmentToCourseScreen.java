package com.hilarysturges.c196;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

public class linkAssessmentToCourseScreen extends AppCompatActivity {

    static DBManager databaseMan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        databaseMan = new DBManager(this, null, null, 1);

        ArrayList textArray = new ArrayList();
        for (int i = 0; i < MainActivity.courses.size(); i++) {
            Course course = MainActivity.courses.get(i);
            textArray.add(course.getTitle());
        }

        ScrollView scrollView = new ScrollView(this);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        RadioGroup radioGroup = new RadioGroup(this);
        Button submitButton = new Button(this);

        Intent data = getIntent();
        final String name = data.getStringExtra("name");
        final String type = data.getStringExtra("type");
        final int assessment_id = Integer.parseInt(data.getStringExtra("ID"));
        final int assessment_index = Integer.parseInt(data.getStringExtra("index"));

        final String[] courseDetails = new String[6];
        for (int i = 0; i < textArray.size(); i++) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(String.valueOf(textArray.get(i)));
            radioButton.setTextSize(30);
            radioButton.setId(i);
            radioButton.setPadding(30, 30, 0, 30);
            linearLayout.addView(radioButton);
            courseDetails[0] = MainActivity.courses.get(i).getTitle();
            courseDetails[1] = String.valueOf(MainActivity.courses.get(i).getStartDate());
            courseDetails[2] = String.valueOf(MainActivity.courses.get(i).getEndDate());
            courseDetails[3] = MainActivity.courses.get(i).getStatus();
            courseDetails[4] = String.valueOf(MainActivity.courses.get(i).get_id());
            courseDetails[5] = String.valueOf(i);

        }

        submitButton.setText("Attach to Selected Course");
        linearLayout.addView(submitButton);

        scrollView.addView(linearLayout);
        setContentView(scrollView);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{databaseMan.linkAssessmentToCourse(Integer.parseInt(courseDetails[4]),assessment_id);}
                catch(Exception e) {System.out.println(e.getMessage());}
                Assessment assessment = MainActivity.assessments.get(assessment_index);
                MainActivity.courses.get(Integer.parseInt(courseDetails[5])).setAssessment(assessment);
                Intent i = new Intent(getApplicationContext(), DetailCourseScreen.class);
                i.putExtra("title", courseDetails[0]);
                i.putExtra("start", courseDetails[1]);
                i.putExtra("end", courseDetails[2]);
                i.putExtra("status", courseDetails[3]);
                startActivity(i);
            }
        });
    }
}
