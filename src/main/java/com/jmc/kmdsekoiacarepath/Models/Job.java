package com.jmc.kmdsekoiacarepath.Models;

import java.util.ArrayList;
import java.util.List;

    public class Job {
        //This class shows job postings created by an employer and vied by employees
        //where it will be possible to read details such as title, description and requirements.

        //   attributes:
        private int jobId;          // The unique ID for the job
        private String jobTitle;   // The title of the job
        private String municipality;   // Where the job is located
        private String additionalRequirements;
        private String description;
        private boolean isActive;      // whether the job is active (by default is true when created)

        public Job(int jobId, String jobTitle, String municipality, String additionalRequirements, boolean isActive) {
            this.jobId = jobId;
            this.jobTitle = jobTitle;
            this.municipality = municipality;
            this.additionalRequirements = additionalRequirements;
            this.description=description;
            this.isActive = true; // Default to active when it is created;
        }

        // Setters;

        public void setJobId(int jobId) {
            this.jobId = jobId;
        }

        public void setJobTitle(String jobTitle) {
            this.jobTitle = jobTitle;
        }

        public void setMunicipality(String municipality) {
            this.municipality = municipality;
        }

        public void setAdditionalRequirements(String additionalRequirements) {
            this.additionalRequirements = additionalRequirements;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setActive(boolean active) {
            isActive = active;
        }

        // Getters:


        public int getJobId() {
            return jobId;
        }

        public String getJobTitle() {

            return jobTitle;
        }

        public String getMunicipality() {
            return municipality;
        }

        public String getAdditionalRequirements() {
            return additionalRequirements;
        }
        public String getTitle(){
            return jobTitle;
        }

        public String getDescription(){
            return description;
        }
        public boolean isActive() {
            return isActive; // return the current status
        }



        public static List<Job> getActiveJobs(List<Job> jobs) {
            List<Job> activeJobs = new ArrayList<>(); // Create a new list to store active jobs
            for (Job job : jobs) { // Loop through each job in the provided list
                if (job.isActive()) { // Check if the job is active
                    activeJobs.add(job); // Add active jobs to the new list
                }
            }
            return activeJobs; // Return the list of active jobs
        }



        //methods to deactivate the job can be called from the outside of the Job class ( for example by JobController or Employer)
        public void deactivateJob() {
            this.isActive = false;
        }




        // we are overriding the toString method from the Object class.
// it ensures us this toString method will be used whenever we try to print a Job object or convert it to string
        @Override
        public String toString() {
            if (!isActive) {
                return "This job is no longer available.";
            }
            return "Job ID: " + jobId +
                    "\nTitle: " + jobTitle +
                    "\nMunicipality: " + municipality +
                    "\nAdditional Requirements: " + additionalRequirements +
                    "\nStatus: Active";
        }

    }


