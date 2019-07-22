package com.hilarysturges.c196;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class LinkAssessmentToCourseScreen extends AppCompatActivity {

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
        final RadioGroup radioGroup = new RadioGroup(this);
        final Button submitButton = new Button(this);

        Intent data = getIntent();
        final String name = data.getStringExtra("name");
        final String type = data.getStringExtra("type");
        final int assessment_id = data.getIntExtra("ID",99);
        final int assessment_index = data.getIntExtra("index",99);

        final String[] courseDetails = new String[6];
        for (int i = 0; i < textArray.size(); i++) {
            final RadioButton radioButton = new RadioButton(this);
            radioButton.setText(String.valueOf(textArray.get(i)));
            radioButton.setTextSize(30);
            radioButton.setId(i);
            radioButton.setPadding(30, 30, 0, 30);
            radioGroup.addView(radioButton);
            radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        courseDetails[0] = MainActivity.courses.get(radioButton.getId()).getTitle();
                        courseDetails[1] = String.valueOf(MainActivity.courses.get(radioButton.getId()).getStartDate());
                        courseDetails[2] = String.valueOf(MainActivity.courses.get(radioButton.getId()).getEndDate());
                        courseDetails[3] = MainActivity.courses.get(radioButton.getId()).getStatus();
                        courseDetails[4] = String.valueOf(MainActivity.courses.get(radioButton.getId()).get_id());
                        courseDetails[5] = String.valueOf(radioButton.getId());
                    }
                }
            });
        }

        submitButton.setText("Attach to Selected Course");
        linearLayout.addView(radioGroup);
        linearLayout.addView(submitButton);

        scrollView.addView(linearLayout);
        setContentView(scrollView);


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View view) {
                    try {databaseMan.linkAssessmentToCourse(Integer.parseInt(courseDetails[4]), assessment_id);}
                    catch (Exception e) {System.out.println(e.getMessage());}
                    Assessment assessment = MainActivity.assessments.get(assessment_index);
                    if (MainActivity.courses.get(Integer.parseInt(courseDetails[5])).getCourseAssessments()==null) {
                        ArrayList<Assessment> assessments = new ArrayList<>();
                        MainActivity.courses.get(Integer.parseInt(courseDetails[5])).setCourseAssessments(assessments);
                    }
                    MainActivity.courses.get(Integer.parseInt(courseDetails[5])).setCourseAssessment(assessment);
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                    Toast toast = Toast.makeText(getApplicationContext(), "Linked to course successfully", Toast.LENGTH_SHORT);
                    toast.show();
                }
            });



    }
}
