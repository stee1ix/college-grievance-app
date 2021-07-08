package com.stee1ix.collegegrievancesystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.stee1ix.collegegrievancesystem.Complaint.getListOfComplaints;


public class StudentHistoryActivity extends AppCompatActivity {

    ArrayList<Complaint> complaints = new ArrayList<>();
    //    ArrayList<Complaint> complaints2;
    ListView lvHistory;

    Map<String, Object> complaintMap;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_history);

        db = FirebaseFirestore.getInstance();
        String userId = FirebaseAuth.getInstance().getUid();

        DocumentReference documentReference = db.document("complaints/" + userId);

        documentReference.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            complaintMap = Objects.requireNonNull(task.getResult()).getData();
                            Log.d("Complaints", String.valueOf(complaintMap));
                            for (Map.Entry<String, Object> entry : complaintMap.entrySet()) {
                                Map<String, String> map = (Map<String, String>) entry.getValue();
                                Complaint complaint = new Complaint(map.get("subject"), map.get("message"));
                                complaint.setSubject(map.get("subject"));
                                complaint.setMessage(map.get("message"));
                                complaints.add(complaint);

                                lvHistory = findViewById(R.id.lvHistory);
                                ComplaintAdapter complaintAdapter = new ComplaintAdapter();
                                lvHistory.setAdapter(complaintAdapter);
                            }
                        } else {
                            Log.w("Complaints", "Error getting documents.", task.getException());
                        }
                    }
                });

        for (int i = 0; i < complaints.size(); i++) {
            Log.d("Complaints", complaints.get(i).getSubject() + " " + complaints.get(i).getMessage());
        }


    }

    class ComplaintAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return complaints.size();
        }

        @Override
        public Complaint getItem(int position) {
            return complaints.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = getLayoutInflater().inflate(R.layout.complaint_view, parent, false);

            TextView tvSubject = itemView.findViewById(R.id.tvSubject);
            TextView tvMessage = itemView.findViewById(R.id.tvMessage);
            tvSubject.setText(getItem(position).getSubject());
            tvMessage.setText(getItem(position).getMessage());

            return itemView;
        }
    }
}