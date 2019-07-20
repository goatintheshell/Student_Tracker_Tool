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

public class InstructorScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayList textArray = new ArrayList();
        for (int i = 0; i < MainActivity.instructors.size(); i++) {
            Instructor instructor = MainActivity.instructors.get(i);
            textArray.add(instructor.getName());
        }
        ScrollView scrollView = new ScrollView(this);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        for (int i = 0; i < textArray.size(); i++) {
            TextView textView = new TextView(this);
            textView.setText(textArray.get(i).toString());
            textView.setTextSize(30);
            textView.setId(i);
            textView.setPadding(30,30,0,30);
            final String name = MainActivity.instructors.get(i).getName();
            final String phoneNumber = String.valueOf(MainActivity.instructors.get(i).getPhoneNumber());
            final String email = String.valueOf(MainActivity.instructors.get(i).getEmail());
            final String _id = String.valueOf(MainActivity.instructors.get(i).get_id());
            final String index = String.valueOf(i);

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(getApplicationContext(), DetailInstructorScreen.class);
                    intent.putExtra("name", name);
                    intent.putExtra("phoneNumber", phoneNumber);
                    intent.putExtra("email", email);
                    intent.putExtra("ID", _id);
                    intent.putExtra("index", index);
                    startActivity(intent);
                }
            });
            linearLayout.addView(textView);


        }
        Button addInstructorButton = new Button(this);
        addInstructorButton.setText("Add Instructor");
        Button homeScreenButton = new Button(this);
        homeScreenButton.setText("Return to home");

        addInstructorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AddInstructorScreen.class);
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

        linearLayout.addView(addInstructorButton);
        linearLayout.addView(homeScreenButton);
        scrollView.addView(linearLayout);
        setContentView(scrollView);
    }
}
