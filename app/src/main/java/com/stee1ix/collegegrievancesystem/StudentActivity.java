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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class StudentActivity extends AppCompatActivity {
    private FirebaseFirestore db;
    private boolean complaintExists;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student);

        complaintExists = false;

        db = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

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
        complaint.put("subject", subject);
        complaint.put("message", message);
        complaint.put("reply", "");
        complaint.put("studentId", user.getUid());

        Map<String, Map<String,String>> complaintMap = new HashMap<>();
        complaintMap.put(complaint.get("subject"), complaint);

        //update db with complaint
        DocumentReference documentReference = db.collection("complaints").document(user.getUid());

        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if (documentSnapshot.exists()) {
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
                    } else {
                        documentReference
                                .set(complaintMap)
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
                }
            }
        });


    }

    public void viewStudentHistory(View view) {


        Intent intent = new Intent(this, StudentHistoryActivity.class);
        startActivity(intent);
    }
}