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

public class DetailInstructorScreen extends AppCompatActivity {

    DBManager databaseMan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        databaseMan = new DBManager(this, null, null, 1);

        LinearLayout linearLayout = new LinearLayout(this);
        TextView nameText = new TextView(this);
        final EditText nameEdit = new EditText(this);
        TextView phoneText = new TextView(this);
        final EditText phoneEdit = new EditText(this);
        TextView emailText = new TextView(this);
        final EditText emailEdit = new EditText(this);
        Button editButton = new Button(this);
        Button deleteButton = new Button(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        setContentView(linearLayout);

        Intent data = getIntent();
        String name = data.getStringExtra("name");
        String phoneNumber = data.getStringExtra("phoneNumber");
        String email = data.getStringExtra("email");
        final int _id = Integer.parseInt(data.getStringExtra("ID"));
        final int index = Integer.parseInt(data.getStringExtra("index"));


        nameText.setText("Instructor Name: ");
        nameText.setTextSize(30);
        nameText.setPadding(30,30,0,0);
        nameEdit.setText(name);
        nameEdit.setTextSize(30);
        nameEdit.setPadding(30,0,0,30);
        phoneText.setText("Instructor Phone: ");
        phoneText.setTextSize(30);
        phoneText.setPadding(30,30,0,0);
        phoneEdit.setText(phoneNumber);
        phoneEdit.setTextSize(30);
        phoneEdit.setPadding(30,0,0,30);
        emailText.setText("Instructor Email: ");
        emailText.setTextSize(30);
        emailText.setPadding(30,0,0,30);
        emailEdit.setText(email);
        emailEdit.setTextSize(30);
        emailEdit.setPadding(30,0,0,30);

        editButton.setText("Edit");
        deleteButton.setText("Delete");

        linearLayout.addView(nameText);
        linearLayout.addView(nameEdit);
        linearLayout.addView(phoneText);
        linearLayout.addView(phoneEdit);
        linearLayout.addView(emailText);
        linearLayout.addView(emailEdit);

        linearLayout.addView(editButton);
        linearLayout.addView(deleteButton);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {databaseMan.removeInstructor(_id);}
                catch (Exception e) {System.out.println(e.getMessage());}
                MainActivity.instructors.remove(index);
                Intent i = new Intent(getApplicationContext(), InstructorScreen.class);
                startActivity(i);
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{databaseMan.updateInstructor(_id,nameEdit.getText().toString(),phoneEdit.getText().toString(),emailEdit.getText().toString());}
                catch(Exception e) {System.out.println(e.getMessage());}
                MainActivity.instructors.get(index).setName(nameEdit.getText().toString());
                MainActivity.instructors.get(index).setPhoneNumber(phoneEdit.getText().toString());
                MainActivity.instructors.get(index).setEmail(emailEdit.getText().toString());
                Intent i = new Intent(getApplicationContext(), InstructorScreen.class);
                startActivity(i);
            }
        });
    }
}
