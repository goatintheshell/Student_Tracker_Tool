package com.hilarysturges.c196;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;

public class AddTermScreen extends AppCompatActivity {

    private DatePickerDialog.OnDateSetListener startSetListener;
    private DatePickerDialog.OnDateSetListener endSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        final EditText termName = new EditText(this);
        termName.setHint("Enter Term Name");
        linearLayout.addView(termName);

        final TextView selectStart = new TextView(this);
        selectStart.setText("Select start of term");
        selectStart.setTextSize(30);
        linearLayout.addView(selectStart);

        final TextView selectEnd = new TextView(this);
        selectEnd.setText("Select end of term");
        selectEnd.setTextSize(30);
        linearLayout.addView(selectEnd);

        Button saveButton = new Button(this);
        saveButton.setText("Save");
        linearLayout.addView(saveButton);


        final String[] dates = new String[2];

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = termName.getText().toString();
                MainActivity.addNewTerm(name,dates[0],dates[1]);
                Intent intent = new Intent(getApplicationContext(), TermScreen.class);
//                intent.putExtra("termName",name);
//                intent.putExtra("startDate", dates[0]);
//                intent.putExtra("endDate", dates[1]);
                startActivity(intent);
            }
        });


        selectStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(AddTermScreen.this,
                        android.R.style.Theme_DeviceDefault,
                        startSetListener,
                        year,month,day);
                dialog.show();
            }
        });

        startSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = month + "/" + day + "/" + year;
                selectStart.setText(date);
                String stringNew = year + "-" +  month + "-" + day;
                dates[0] = stringNew;

            }
        };

        selectEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(AddTermScreen.this,
                        android.R.style.Theme_DeviceDefault,
                        endSetListener,
                        year,month,day);
                dialog.show();
            }
        });

        endSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = month + "/" + day + "/" + year;
                selectEnd.setText(date);
                String stringNew = year + "-" +  month + "-" + day;
                dates[1] = stringNew;
            }
        };

        setContentView(linearLayout);
    }
}
