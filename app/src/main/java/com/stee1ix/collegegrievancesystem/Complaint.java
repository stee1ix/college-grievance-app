package com.stee1ix.collegegrievancesystem;

import java.util.ArrayList;

public class Complaint {
    String subject, message;

    public Complaint(String subject, String message) {
        this.subject = subject;
        this.message = message;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static ArrayList<Complaint> getListOfComplaints(String subject, String message) {
        ArrayList<Complaint> complaints = new ArrayList<>();
        complaints.add(new Complaint(subject, message));

        return complaints;
    }
}
