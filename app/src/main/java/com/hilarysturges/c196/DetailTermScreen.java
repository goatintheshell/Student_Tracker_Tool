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
import android.widget.Toast;

import java.sql.Date;
import java.sql.SQLOutput;
import java.util.ArrayList;

public class DetailTermScreen extends AppCompatActivity {

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
        TextView coursesText = new TextView(this);
        Button editButton = new Button(this);
        Button deleteButton = new Button(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        final Intent data = getIntent();
        String title = data.getStringExtra("title");
        String start = data.getStringExtra("start");
        String end = data.getStringExtra("end");
        final int _id = Integer.parseInt(data.getStringExtra("ID"));
        final int index = Integer.parseInt(data.getStringExtra("index"));

        titleText.setText("Term Title: ");
        titleText.setTextSize(30);
        titleText.setPadding(30,30,0,0);
        titleEdit.setText(title);
        titleEdit.setTextSize(30);
        titleEdit.setPadding(30,0,0,30);
        startText.setText("Term Start: ");
        startText.setTextSize(30);
        startText.setPadding(30,30,0,0);
        startEdit.setText(start);
        startEdit.setTextSize(30);
        startEdit.setPadding(30,0,0,30);
        endText.setText("Term End: ");
        endText.setTextSize(30);
        endText.setPadding(30,30,0,0);
        endEdit.setText(end);
        endEdit.setTextSize(30);
        endEdit.setPadding(30,0,0,30);
        coursesText.setText("Courses:");
        coursesText.setTextSize(30);
        coursesText.setPadding(30,30,0,0);

        editButton.setText("Edit");
        deleteButton.setText("Delete");

        linearLayout.addView(titleText);
        linearLayout.addView(titleEdit);
        linearLayout.addView(startText);
        linearLayout.addView(startEdit);
        linearLayout.addView(endText);
        linearLayout.addView(endEdit);
        linearLayout.addView(coursesText);

        Term term = MainActivity.terms.get(index);
        if (term.getTermCourses()!=null) {
            for (int i = 0; i < term.getTermCourses().size(); i++) {
                Course course = term.getTermCourse(i);
                TextView courseNew = new TextView(this);
                courseNew.setText(course.getTitle());
                courseNew.setTextSize(30);
                courseNew.setPadding(30, 0, 0, 0);
                courseNew.setId(i);
                linearLayout.addView(courseNew);
            }
        }



        linearLayout.addView(editButton);
        linearLayout.addView(deleteButton);
        scrollView.addView(linearLayout);
        setContentView(scrollView);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Course> courses = new ArrayList<>();
                try {courses = databaseMan.getAssocCourses(_id);}
                catch (Exception e) {System.out.println("assocCourse:" + e.getMessage());}
                if (courses.isEmpty()) {
                    try {databaseMan.removeTerm(_id);}
                    catch (Exception e) { System.out.println("deleteTerm: " + e.getMessage());}
                    MainActivity.terms.remove(index);
                    Intent i = new Intent(getApplicationContext(), TermScreen.class);
                    startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext(), "Cannot delete term with linked course(s)", Toast.LENGTH_LONG).show();
                }
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{databaseMan.updateTerm(_id,titleEdit.getText().toString(),startEdit.getText().toString(),endEdit.getText().toString());}
                catch(Exception e) {System.out.println(e.getMessage());}
                MainActivity.terms.get(index).setTitle(titleEdit.getText().toString());
                Date startDate = Date.valueOf(startEdit.getText().toString());
                Date endDate = Date.valueOf(endEdit.getText().toString());
                MainActivity.terms.get(index).setStartDate(startDate);
                MainActivity.terms.get(index).setEndDate(endDate);
                Intent i = new Intent(getApplicationContext(), TermScreen.class);
                startActivity(i);
            }
        });

    }

}
