package com.jmc.kmdsekoiacarepath.Models;

import java.util.ArrayList;
import java.util.List;

    public class Employee extends User {
        private String profilePicture;    // This stores the file path of the employee's profile picture and can only get accssed or modified through getters and setters
        private String gender;
        private String cv;
        private int phone;
        private String criminalRecord;
        private String educationStatus;
        private int weeklyHoursAvailable;
        private List<Application> appliedJobs; // This keeps track of all the jobs the employee has applied to.

        // Constructor
        public Employee(String fullName, String dateOfBirth, String email, String password, String municipality, String profilePicture, String gender, String cv, List<Application> appliedJobs) {

            super(fullName, dateOfBirth, email, password, municipality);
            this.profilePicture = profilePicture;
            this.gender = gender;
            this.cv = cv;
            this.phone = phone;
            this.criminalRecord = criminalRecord;
            this.educationStatus = educationStatus;
            this.weeklyHoursAvailable = weeklyHoursAvailable;


            //How the appliedJobs attribute is initialized when we create a new Employee instance
            // and checks if the value of appliedJobs(the list of jobs the employee has already applied for) is not null.
            if (appliedJobs != null) {
                this.appliedJobs = appliedJobs;       // Use the provided list
            } else {
                this.appliedJobs = new ArrayList<>(); // Create a new empty list
            }
        }

        // Getters
        public String getProfilePicture() {
            return profilePicture;
        }

        public String getCv() {
            return cv;
        }

        public String getCriminalRecord() {
            return criminalRecord;
        }

        public String getGender() {
            return gender;
        }

        public int getPhone() {
            return phone;
        }

        public String getEducationStatus() {
            return educationStatus;
        }

        public int getWeeklyHoursAvailable() {
            return weeklyHoursAvailable;
        }

        public List<Application> getAppliedJobs() {
            return appliedJobs;
        }

        // Getter for all applications
        public List<Application> getAllApplications() {
            return appliedJobs;
        }


        // Setters
        public void setProfilePicture(String profilePicture) {
            this.profilePicture = profilePicture;
        }

        public void setCv(String cv) {
            this.cv = cv;
        }

        public void setCriminalRecord(String criminalRecord) {
            this.criminalRecord = criminalRecord;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public void setEducationStatus(String educationStatus) {
            this.educationStatus = educationStatus;
        }

        public void setWeeklyHoursAvailable(int weeklyHoursAvailable) {
            this.weeklyHoursAvailable = weeklyHoursAvailable;
        }

        public void setAppliedJobs(List<Application> appliedJobs) {
            this.appliedJobs = appliedJobs;
        }


        public void applyForJob(Job job) {
            System.out.println("Applying for job: " + job.getJobTitle());
        }


    }



