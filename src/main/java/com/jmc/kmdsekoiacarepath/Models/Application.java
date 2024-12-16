package com.jmc.kmdsekoiacarepath.Models;

public class Application {

    private final Integer id;
    private final String jobTitle;
    private final String applicantName;
    private final String applicantEmail;
    private final String status;

    public Application(Integer id, String jobTitle, String applicantName, String applicantEmail, String status) {
        this.id = id;
        this.jobTitle = jobTitle;
        this.applicantName = applicantName;
        this.applicantEmail = applicantEmail;
        this.status = status;
    }

    public String getApplicantEmail() {
        return applicantEmail;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getStatus() {
        return status;
    }

    public Integer getId() {
        return id;
    }
}



