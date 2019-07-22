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
import android.widget.Toast;

import java.util.ArrayList;

public class LinkCourseToTermScreen extends AppCompatActivity {

    static DBManager databaseMan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        databaseMan = new DBManager(this, null, null, 1);

        ArrayList textArray = new ArrayList();
        for (int i = 0; i < MainActivity.terms.size(); i++) {
            Term term = MainActivity.terms.get(i);
            textArray.add(term.getTitle());
        }

        ScrollView scrollView = new ScrollView(this);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        final RadioGroup radioGroup = new RadioGroup(this);
        final Button submitButton = new Button(this);

        Intent data = getIntent();
        final String title = data.getStringExtra("title");
        final String startDate = data.getStringExtra("startDate");
        final String endDate = data.getStringExtra("endDate");
        final String status = data.getStringExtra("status");
        final int course_id = data.getIntExtra("ID",99);
        final int course_index = data.getIntExtra("index",99);

        final String[] termDetails = new String[5];
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
                        termDetails[0] = MainActivity.terms.get(radioButton.getId()).getTitle();
                        termDetails[1] = String.valueOf(MainActivity.terms.get(radioButton.getId()).getStartDate());
                        termDetails[2] = String.valueOf(MainActivity.terms.get(radioButton.getId()).getEndDate());
                        termDetails[3] = String.valueOf(MainActivity.terms.get(radioButton.getId()).get_id());
                        termDetails[4] = String.valueOf(radioButton.getId());
                    }
                }
            });
        }

        submitButton.setText("Attach to Selected Term");
        linearLayout.addView(radioGroup);
        linearLayout.addView(submitButton);

        scrollView.addView(linearLayout);
        setContentView(scrollView);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{databaseMan.addCourseToTerm(course_id, Integer.parseInt(termDetails[3]));}
                catch(Exception e) {System.out.println(e.getMessage());}
                Course course = MainActivity.courses.get(course_index);
                if (MainActivity.terms.get(Integer.parseInt(termDetails[4])).getTermCourses()==null) {
                    ArrayList<Course> courses = new ArrayList<>();
                    MainActivity.terms.get(Integer.parseInt(termDetails[4])).setTermCourses(courses);
                }
                MainActivity.terms.get(Integer.parseInt(termDetails[4])).setTermCourse(course);
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                Toast toast = Toast.makeText(getApplicationContext(),"Linked to term successfully", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }
}
