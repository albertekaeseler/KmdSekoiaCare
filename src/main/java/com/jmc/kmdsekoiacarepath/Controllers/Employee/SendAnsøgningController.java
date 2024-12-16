package com.jmc.kmdsekoiacarepath.Controllers.Employee;

import com.jmc.kmdsekoiacarepath.Models.Model;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;
import java.util.UUID;

public class SendAnsøgningController implements Initializable {
    @FXML
    private Label jobTitleLabel;

    @FXML
    private TextArea jobDescriptionArea;

    @FXML
    private TextField applicantNameField;

    @FXML
    private TextField applicantEmailField;

    @FXML
    private Button submitButton;

    @FXML
    private Label errorLabel;

    private int jobId; // To keep track of which job the application is for
    private String jobTitle; // To store the job title

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        submitButton.setOnAction(event -> submitApplication());
    }

    /**
     * Sets the job details received from SearchJobController.
     *
     * @param jobId          The ID of the job.
     * @param jobTitle       The title of the job.
     * @param jobDescription The description of the job.
     */
    public void setJobDetails(int jobId, String jobTitle, String jobDescription) {
        this.jobId = jobId;
        this.jobTitle = jobTitle;
        // To store the job description
        jobTitleLabel.setText(jobTitle);
        jobDescriptionArea.setText(jobDescription);
    }

    /**
     * Handles the submission of the job application.
     */
    private void submitApplication() {
        String applicantName = applicantNameField.getText().trim();
        String applicantEmail = applicantEmailField.getText().trim();

        if (applicantName.isEmpty() || applicantEmail.isEmpty()) {
            showError("Please fill in all required fields.");
            return;
        }



        int applicantId = Model.getInstance().getCurrentUserId();

        String status = "Pending";

        // Insert application into the database
        String insertApplicationSQL = "INSERT INTO application (job_id, job, applicant_id, status) VALUES (?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
             PreparedStatement pstmt = connection.prepareStatement(insertApplicationSQL)) {

            pstmt.setInt(1, jobId);
            pstmt.setString(2, jobTitle);
            pstmt.setInt(3, applicantId);
            pstmt.setString(4, status);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Your application has been submitted successfully!");
                // Optionally, close the SendAnsøgning window
                Stage stage = (Stage) submitButton.getScene().getWindow();
                stage.close();
            } else {
                showError("Failed to submit your application. Please try again.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while submitting your application.");
        }
    }

    /**
     * Utility method to show alerts.
     *
     * @param alertType The type of the alert.
     * @param title     The title of the alert window.
     * @param message   The message to display.
     */
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Displays an error message in the errorLabel.
     *
     * @param message The error message to display.
     */
    private void showError(String message) {
        if (errorLabel != null) {
            errorLabel.setText(message);
        } else {
            // Fallback: Use an Alert dialog
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        }
    }
}
