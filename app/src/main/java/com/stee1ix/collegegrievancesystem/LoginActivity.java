package com.stee1ix.collegegrievancesystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    private String getUserName() {
        EditText userName = findViewById(R.id.user_name);
        String usName = userName.getText().toString();
        return usName;
    }


    public void loginBtn(View view) {
        Button loginButton = findViewById(R.id.login_btn);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = getUserName();
                Intent intent = new Intent(LoginActivity.this, StudentActivity.class);
                intent.putExtra("Username", userName);
                startActivity(intent);
            }
        });
    }
}