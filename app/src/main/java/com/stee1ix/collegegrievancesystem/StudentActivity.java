package com.stee1ix.collegegrievancesystem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class StudentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student);

        //Dropdown logic
        Spinner spinnerCatergory = findViewById(R.id.spinner_catergories);
        ArrayAdapter<CharSequence>adapter = ArrayAdapter.createFromResource(this,
                R.array.catergory, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCatergory.setAdapter(adapter);

        //TextView to display the username
        TextView name = findViewById(R.id.display_name);
        String userName = getIntent().getStringExtra("Username");
        name.setText("Welcome, " + userName);

    }
}