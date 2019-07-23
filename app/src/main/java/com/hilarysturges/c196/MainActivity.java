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
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity {

    static DBManager databaseMan;
    static int counterTerms;
    static int counterCourses;
    static int counterAssessments;
    static int counterInstructors;

    TextView welcomeText;
    Button viewTermsButton;
    Button viewCoursesButton;
    Button viewAssessmentsButton;
    Button viewInstructorsButton;

    static ArrayList<Term> terms = new ArrayList<>();
    static ArrayList<Course> courses = new ArrayList<>();
    static ArrayList<Assessment> assessments = new ArrayList<>();
    static ArrayList<Instructor> instructors = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseMan = new DBManager(this, null, null, 1);

        welcomeText=findViewById(R.id.welcomeText);
        viewTermsButton=findViewById(R.id.viewTermsButton);
        viewCoursesButton=findViewById(R.id.viewCoursesButton);
        viewAssessmentsButton=findViewById(R.id.viewAssessmentsButton);
        viewInstructorsButton=findViewById(R.id.viewInstructorsButton);

        viewTermsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), TermScreen.class);
                startActivity(i);
            }
        });

        viewCoursesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), CourseScreen.class);
                startActivity(i);
            }
        });

        viewAssessmentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AssessmentScreen.class);
                startActivity(i);
            }
        });

        viewInstructorsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), InstructorScreen.class);
                startActivity(i);
            }
        });

        initData();
    }

    public static void addNewTerm(String title,String start,String end) {
        if (start != null && end != null) {
                java.sql.Date startDate = java.sql.Date.valueOf(start);
                java.sql.Date endDate = java.sql.Date.valueOf(end);
                Term term = new Term(title, startDate, endDate);
                //terms.add(term);
                databaseMan.addTerm(term);
                Term term2 = databaseMan.getLastTerm();
                terms.add(term2);
        }
    }

    public static void addNewCourse(String title,String start,String end,String status) {
        if (start != null && end != null) {
            java.sql.Date startDate = java.sql.Date.valueOf(start);
            java.sql.Date endDate = java.sql.Date.valueOf(end);
            Course course = new Course(title, startDate, endDate, status);
            databaseMan.addCourse(course);
            Course course2 = databaseMan.getLastCourse();
            courses.add(course2);
        }
    }

    public static void addNewAssessment(String name,String type) {
            Assessment assessment = new Assessment(name,type);
            databaseMan.addAssessment(assessment);
            Assessment assessment2 = databaseMan.getLastAssessment();
            assessments.add(assessment2);
    }

    public static void addNewInstructor(String name,String phone, String email) {
        Instructor instructor = new Instructor(name,phone,email);
        databaseMan.addInstructor(instructor);
        Instructor instructor2 = databaseMan.getLastInstructor();
        instructors.add(instructor2);
    }

    public void initData() {

        if (counterTerms == 0) {
            ArrayList<Term> dBTerms = databaseMan.termsToArray();
            terms.addAll(dBTerms);
            counterTerms++;
        }

        if (counterCourses == 0) {
            ArrayList<Course> dBCourses = databaseMan.coursesToArray();
            courses.addAll(dBCourses);
            counterCourses++;
        }

        if (counterAssessments== 0) {
            ArrayList<Assessment> dBAssessments = databaseMan.assessmentsToArray();
            assessments.addAll(dBAssessments);
            counterAssessments++;
        }

        if (counterInstructors== 0) {
            ArrayList<Instructor> dBInstructors = databaseMan.instructorsToArray();
            instructors.addAll(dBInstructors);
            counterInstructors++;
        }
    }
}
