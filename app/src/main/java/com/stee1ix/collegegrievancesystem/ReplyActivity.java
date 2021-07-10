package com.stee1ix.collegegrievancesystem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ReplyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);

        TextView tvCompleteMsg = findViewById(R.id.tvCompleteMsg);
        EditText etReply = findViewById(R.id.etReply);
        Button btnSubmitReply = findViewById(R.id.btnSubmitReply);


    }
}