package com.hilarysturges.c196;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddNotesScreen extends AppCompatActivity {

    DBManager databaseMan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        databaseMan = new DBManager(this, null, null, 1);

        ScrollView scrollView = new ScrollView(this);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        TextView notesText = new TextView(this);
        final EditText notesEdit = new EditText(this);
        FloatingActionButton shareButton = new FloatingActionButton(this);
        Button addNotesButton = new Button(this);

        Intent data = getIntent();
        final int _id = data.getIntExtra("ID",99);
        final int index = data.getIntExtra("index",99);

        if (MainActivity.courses.get(index).getNotes() != null && (!MainActivity.courses.get(index).getNotes().isEmpty())) {
            notesEdit.setText(MainActivity.courses.get(index).getNotes().toString());
        }

        notesText.setText("Notes:");
        notesText.setTextSize(30);
        notesText.setPadding(30,0,0,0);
        addNotesButton.setText("Save Notes");
        shareButton.setImageResource(R.drawable.ic_share_white_24dp);
        shareButton.setPadding(30,0,0,0);
        shareButton.setClickable(true);
        notesEdit.setHint("Type notes here");
        notesEdit.setTextSize(30);
        notesEdit.setPadding(30,0,0,30);
        notesEdit.setInputType(InputType.TYPE_TEXT_VARIATION_LONG_MESSAGE);
        notesEdit.setSingleLine(false);
        notesEdit.setImeOptions(EditorInfo.IME_FLAG_NO_ENTER_ACTION);

        linearLayout.addView(notesText);
        linearLayout.addView(notesEdit);
        linearLayout.addView(addNotesButton);
        linearLayout.addView(shareButton);

        scrollView.addView(linearLayout);
        setContentView(scrollView);

        addNotesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {databaseMan.addNotes(_id, notesEdit.getText().toString());}
                catch (Exception e) {System.out.println(e.getMessage());}
                MainActivity.courses.get(index).setNotes(notesEdit.getText().toString());
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setAction(Intent.ACTION_SEND);
                i.putExtra(Intent.EXTRA_TEXT, notesEdit.getText().toString());
                i.setType("text/plain");
                startActivity(i);
            }
        });
    }
}
