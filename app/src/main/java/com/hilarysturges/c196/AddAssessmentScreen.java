package com.hilarysturges.c196;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class AddAssessmentScreen extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        final EditText name = new EditText(this);
        name.setHint("Enter Assessment Name");
        linearLayout.addView(name);

        final EditText type = new EditText(this);
        type.setHint("Enter Assessment Type");
        linearLayout.addView(type);

        Button saveButton = new Button(this);
        saveButton.setText("Save");
        linearLayout.addView(saveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.addNewAssessment(name.getText().toString(),type.getText().toString());
                Intent intent = new Intent(getApplicationContext(), AssessmentScreen.class);
                startActivity(intent);
            }
        });
        setContentView(linearLayout);
    }
}
