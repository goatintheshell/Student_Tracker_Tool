package com.hilarysturges.c196;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.sql.Date;

public class DetailAssessmentScreen extends AppCompatActivity {

    DBManager databaseMan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        databaseMan = new DBManager(this, null, null, 1);

        LinearLayout linearLayout = new LinearLayout(this);
        TextView nameText = new TextView(this);
        final EditText nameEdit = new EditText(this);
        TextView typeText = new TextView(this);
        final EditText typeEdit = new EditText(this);
        Button editButton = new Button(this);
        Button deleteButton = new Button(this);
        Button linkToCourseButton = new Button(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        setContentView(linearLayout);

        Intent data = getIntent();
        final String name = data.getStringExtra("name");
        final String type = data.getStringExtra("type");
        final int _id = Integer.parseInt(data.getStringExtra("ID"));
        final int index = Integer.parseInt(data.getStringExtra("index"));

        nameText.setText("Name: ");
        nameText.setTextSize(30);
        nameText.setPadding(30,30,0,0);
        nameEdit.setText(name);
        nameEdit.setTextSize(30);
        nameEdit.setPadding(30,0,0,30);
        typeText.setText("Type: ");
        typeText.setTextSize(30);
        typeText.setPadding(30,30,0,0);
        typeEdit.setText(type);
        typeEdit.setTextSize(30);
        typeEdit.setPadding(30,0,0,30);

        editButton.setText("Edit");
        deleteButton.setText("Delete");
        linkToCourseButton.setText("Link to Course");

        linearLayout.addView(nameText);
        linearLayout.addView(nameEdit);
        linearLayout.addView(typeText);
        linearLayout.addView(typeEdit);

        linearLayout.addView(editButton);
        linearLayout.addView(deleteButton);
        linearLayout.addView(linkToCourseButton);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {databaseMan.removeAssessment(_id);}
                catch (Exception e) {System.out.println(e.getMessage());}
                MainActivity.assessments.remove(index);
                Intent i = new Intent(getApplicationContext(), AssessmentScreen.class);
                startActivity(i);
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{databaseMan.updateAssessment(_id,nameEdit.getText().toString(),typeEdit.getText().toString());}
                catch(Exception e) {System.out.println(e.getMessage());}
                MainActivity.assessments.get(index).setName(nameEdit.getText().toString());
                MainActivity.assessments.get(index).setType(typeEdit.getText().toString());
                Intent i = new Intent(getApplicationContext(), AssessmentScreen.class);
                startActivity(i);
            }
        });

        linkToCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), linkAssessmentToCourseScreen.class);
                i.putExtra("ID", _id);
                i.putExtra("index", index);
                i.putExtra("name", name);
                i.putExtra("type", type);
                startActivity(i);
            }
        });
    }
}
