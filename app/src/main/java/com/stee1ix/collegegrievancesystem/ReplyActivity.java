package com.stee1ix.collegegrievancesystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ReplyActivity extends AppCompatActivity {

    private FirebaseFirestore db;

    String name, subject, message, studentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);

        name = getIntent().getStringExtra("name");
        subject = getIntent().getStringExtra("subject");
        message = getIntent().getStringExtra("message");
        studentId = getIntent().getStringExtra("studentId");

        db = FirebaseFirestore.getInstance();
    }

    public void submitReply(View view) {
        EditText etReply = findViewById(R.id.etReply);

        String replyString = etReply.getText().toString();


        Map<String, String> reply = new HashMap<>();
        reply.put("reply", replyString);


        //update db with complaint
        DocumentReference documentReference = db.collection("complaints").document(studentId);
        documentReference
                .update(subject + ".reply", replyString)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d("reply", "Reply added");
                        etReply.setText("");

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("reply", "Error adding reply", e);
                    }
                });
    }
}