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
        RadioGroup radioGroup = new RadioGroup(this);
        Button submitButton = new Button(this);

        Intent data = getIntent();
        final String title = data.getStringExtra("title");
        final String startDate = data.getStringExtra("startDate");
        final String endDate = data.getStringExtra("endDate");
        final String status = data.getStringExtra("status");
        final int course_id = data.getIntExtra("ID",99);
        final int course_index = data.getIntExtra("index",99);

        final String[] termDetails = new String[5];
        for (int i = 0; i < textArray.size(); i++) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(String.valueOf(textArray.get(i)));
            radioButton.setTextSize(30);
            radioButton.setId(i);
            radioButton.setPadding(30, 30, 0, 30);
            linearLayout.addView(radioButton);
            termDetails[0] = MainActivity.terms.get(i).getTitle();
            termDetails[1] = String.valueOf(MainActivity.terms.get(i).getStartDate());
            termDetails[2] = String.valueOf(MainActivity.terms.get(i).getEndDate());
            termDetails[3] = String.valueOf(MainActivity.terms.get(i).get_id());
            termDetails[4] = String.valueOf(i);

        }

        submitButton.setText("Attach to Selected Term");
        linearLayout.addView(submitButton);

        scrollView.addView(linearLayout);
        setContentView(scrollView);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{databaseMan.addCourseToTerm(course_id, Integer.parseInt(termDetails[3]));}
                catch(Exception e) {System.out.println(e.getMessage());}
                Course course = MainActivity.courses.get(course_index);
                System.out.println(termDetails[0]);
                MainActivity.terms.get(Integer.parseInt(termDetails[4])).setTermCourse(course);
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                Toast toast = Toast.makeText(getApplicationContext(),"Linked to term successfully", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }
}
