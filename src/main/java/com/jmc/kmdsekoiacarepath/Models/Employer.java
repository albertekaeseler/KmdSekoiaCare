package com.jmc.kmdsekoiacarepath.Models;

import java.util.ArrayList;
import java.util.List;

public class Employer extends User {
    private String employerName;
    private String email;
    private String password;
    private List<Job> postJobs;
    private List<ApplicationActionLog>actionLogs;

    // Constructor
    public Employer(String fullName, String dateOfBirth, String email, String password, String municipality ) {
        super(fullName, dateOfBirth,email,password,municipality); // Call the User constructor
        this.employerName = employerName;
        this.email = email;
        this.password = password;
        this.postJobs = new ArrayList<>();
        this.actionLogs = new ArrayList<>();
    }


    // Log an action
    private void logAction(int applicationID, String action) {
        String timestamp = java.time.LocalDateTime.now().toString();     // This creates a variable named timestamp and assigns it to the current date and time as a string.
        ApplicationActionLog log = new ApplicationActionLog(applicationID, action, this.employerName, timestamp);  // This creates a new ApplicationActionLog object named log and passes these 4 values
        actionLogs.add(log);   // This adds the newly created log object to the actionLogs list (a collection of all actions performed). This help us  to keeps a history of all actions performed by this employer.
        System.out.println("Action logged: " + action + " for Application ID: " + applicationID);
    }
}




