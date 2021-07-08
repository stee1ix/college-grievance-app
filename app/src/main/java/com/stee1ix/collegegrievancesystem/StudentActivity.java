package com.stee1ix.collegegrievancesystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class StudentActivity extends AppCompatActivity {
    private FirebaseFirestore db;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student);

        db = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        //Dropdown logic
//        Spinner spinnerCatergory = findViewById(R.id.spinner_catergories);
//        ArrayAdapter<CharSequence>adapter = ArrayAdapter.createFromResource(this,
//                R.array.catergory, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerCatergory.setAdapter(adapter);

        //TextView to display the username
        TextView name = findViewById(R.id.display_name);
        String email = getIntent().getStringExtra("email");
        name.setText("Welcome, " + email);
    }

    public void submitComplaint(View view) {
        EditText etSubject = findViewById(R.id.etSubject);
        EditText etMessage = findViewById(R.id.etMessage);

        String subject = etSubject.getText().toString();
        String message = etMessage.getText().toString();


        Map<String, String> complaint = new HashMap<>();
        complaint.put(subject, subject);
        complaint.put("message", message);

        //update db with complaint
        DocumentReference documentReference = db.collection("complaints").document(user.getUid());
        documentReference
                .update(subject, complaint)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d("addUserData", "ComplaintDocumentSnapshot added with ID: " + documentReference.getId());
                        etSubject.setText("");
                        etMessage.setText("");

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("addUserData", "Error adding document", e);
                    }
                });
    }

    public void viewStudentHistory(View view) {
        Intent intent = new Intent(this,StudentHistoryActivity.class);
        startActivity(intent);
    }
}