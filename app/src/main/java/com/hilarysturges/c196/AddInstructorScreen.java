package com.hilarysturges.c196;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class AddInstructorScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        final EditText name = new EditText(this);
        name.setHint("Enter Instructor Name");
        linearLayout.addView(name);

        final EditText phone = new EditText(this);
        phone.setHint("Enter Instructor Phone Number");
        linearLayout.addView(phone);

        final EditText email = new EditText(this);
        email.setHint("Enter Instructor Email Address");
        linearLayout.addView(email);

        Button saveButton = new Button(this);
        saveButton.setText("Save");
        linearLayout.addView(saveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.addNewInstructor(name.getText().toString(),phone.getText().toString(),email.getText().toString());
                Intent intent = new Intent(getApplicationContext(), InstructorScreen.class);
                startActivity(intent);
            }
        });
        setContentView(linearLayout);
    }
}
