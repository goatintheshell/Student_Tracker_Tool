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


public class TermScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayList textArray = new ArrayList();
        for (int i = 0; i < MainActivity.terms.size() ; i++) {
            Term term = MainActivity.terms.get(i);
            textArray.add(term.getTitle());
        }

        ScrollView scrollView = new ScrollView(this);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);


        for (int i = 0; i < textArray.size(); i++) {
            TextView textView = new TextView(this);
            textView.setText(String.valueOf(textArray.get(i)));
            textView.setTextSize(30);
            textView.setId(i);
            textView.setPadding(30,30,0,30);
                        final String[] term = new String[5];
                term[0] = MainActivity.terms.get(i).getTitle();
                term[1] = String.valueOf(MainActivity.terms.get(i).getStartDate());
                term[2] = String.valueOf(MainActivity.terms.get(i).getEndDate());
                term[3] = String.valueOf(MainActivity.terms.get(i).get_id());
                term[4] = String.valueOf(i);


            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), DetailTermScreen.class);
                    intent.putExtra("title", term[0]);
                    intent.putExtra("start", term[1]);
                    intent.putExtra("end", term[2]);
                    intent.putExtra("ID", term[3]);
                    intent.putExtra("index", term[4]);
                    startActivity(intent);
                }
            });
            linearLayout.addView(textView);


            }
        Button addTermButton = new Button(this);
        addTermButton.setText("Add Term");
        Button homeScreenButton = new Button(this);
        homeScreenButton.setText("Return to home");

        addTermButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AddTermScreen.class);
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

        linearLayout.addView(addTermButton);
        linearLayout.addView(homeScreenButton);
        scrollView.addView(linearLayout);
        setContentView(scrollView);
        }

    }


