package com.jmc.kmdsekoiacarepath.Models;

public class ApplicationActionLog {

    private final int applicationID;
    private String action;
    private String performedBy;
    private String timestamp;

    public ApplicationActionLog(int applicationID, String action, String performedBy, String timestamp) {
        this.applicationID = applicationID;
        this.action = action;
        this.performedBy = performedBy;
        this.timestamp = timestamp;
    }

    // Getters
    public int getApplicationID(){
        return applicationID;
    }
    public String getAction() {
        return action;
    }

    public String getPerformedBy() {
        return performedBy;
    }

    public String getTimestamp() {
        return timestamp;
    }

    // Setters
    public void setAction(String action) {
        if (action == null || action.isEmpty()) {
            System.out.println("Error: Action cannot be null or empty.");
            return;
        }
        this.action = action;
    }

    public void setPerformedBy(String performedBy) {
        if (performedBy == null || performedBy.isEmpty()) {
            System.out.println("Error: PerformedBy cannot be null or empty.");
            return;
        }
        this.performedBy = performedBy;
    }

    public void setTimestamp(String timestamp) {
        if (timestamp == null || timestamp.isEmpty()) {
            System.out.println("Error: Timestamp cannot be null or empty.");
            return;
        }
        this.timestamp = timestamp;
    }

    // toString() method for a readable representation of the log
    @Override
    public String toString() {
        return "ApplicationActionLog{" +
                "applicationID=" + applicationID +
                ", action='" + action + '\'' +
                ", performedBy='" + performedBy + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }

    // This method will retrive the detail of the action performed, including the action type, person who performed it, and the timestamp.
    public String getActionDetails(){
        return "Action" + action + "performed by: " + performedBy + "Timestamp : " + timestamp;
    }
}


