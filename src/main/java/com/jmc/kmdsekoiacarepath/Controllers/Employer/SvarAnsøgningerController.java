package com.jmc.kmdsekoiacarepath.Controllers.Employer;

import com.jmc.kmdsekoiacarepath.Models.Application;
import com.jmc.kmdsekoiacarepath.Models.Model;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class SvarAnsøgningerController implements Initializable {

    @FXML
    private TextArea ansøgning_textarea;

    @FXML
    private Button godkend_btn;

    @FXML
    private Button afvis_btn;

    @FXML
    private Button revurder_btn;

    @FXML
    private Label ansøgning_lbl;

    private Application currentApplication;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        godkend_btn.setOnAction(event -> approveApplication());
        afvis_btn.setOnAction(event -> rejectApplication());
        revurder_btn.setOnAction(event -> reconsiderApplication());
    }

    /**
     * Sets the current application and displays its details.
     *
     * @param application The application to display.
     */
    public void setApplication(Application application) {
        this.currentApplication = application;
        displayApplicationDetails();
    }

    /**
     * Displays the application details in the TextArea and Label.
     */
    private void displayApplicationDetails() {
        if (currentApplication != null) {
            ansøgning_lbl.setText("Ansøgning til: " + currentApplication.getJobTitle());
            // Fetch and display the application description or other details as needed
            // For demonstration, we'll display the job description
            String jobDescription = fetchJobDescription(currentApplication.getId());
            ansøgning_textarea.setText(jobDescription);
        }
    }

    /**
     * Fetches the job description based on job ID.
     *
     * @param jobId The ID of the job.
     * @return The job description.
     */
    private String fetchJobDescription(int jobId) {
        String description = "Ingen beskrivelse tilgængelig.";
        String query = "SELECT description FROM jobs WHERE id = ?";

        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setInt(1, jobId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                description = rs.getString("description");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showError("Fejl ved hentning af jobbeskrivelse: " + e.getMessage());
        }

        return description;
    }

    /**
     * Approves the current application by updating its status to "Accepted".
     */
    private void approveApplication() {
        updateApplicationStatus("Accepted");
    }

    /**
     * Rejects the current application by updating its status to "Rejected".
     */
    private void rejectApplication() {
        updateApplicationStatus("Rejected");
    }

    /**
     * Reconsiders the current application by updating its status to "Reconsidered".
     */
    private void reconsiderApplication() {
        updateApplicationStatus("Reconsidered");
    }

    /**
     * Updates the status of the current application in the database.
     *
     * @param newStatus The new status to set.
     */
    private void updateApplicationStatus(String newStatus) {
        if (currentApplication == null) {
            showError("Ingen ansøgning valgt.");
            return;
        }

        String updateSQL = "UPDATE application SET status = ? WHERE id = ?";

        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
             PreparedStatement pstmt = connection.prepareStatement(updateSQL)) {

            pstmt.setString(1, newStatus);
            pstmt.setInt(2, currentApplication.getId());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Ansøgning status opdateret til: " + newStatus);
                // Optionally, close the window after action
                closeWindow();
            } else {
                showError("Kunne ikke opdatere ansøgning status. Prøv igen.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showError("Database fejl: " + e.getMessage());
        }
    }

    /**
     * Closes the current window.
     */
    private void closeWindow() {
        Stage stage = (Stage) godkend_btn.getScene().getWindow();
        stage.close();
    }

    /**
     * Displays an error message using an alert dialog.
     *
     * @param message The error message to display.
     */
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Fejl");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Displays an informational message using an alert dialog.
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
}
