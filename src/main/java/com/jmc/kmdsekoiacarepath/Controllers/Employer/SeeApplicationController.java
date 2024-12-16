package com.jmc.kmdsekoiacarepath.Controllers.Employer;

import com.jmc.kmdsekoiacarepath.Models.Application;
import com.jmc.kmdsekoiacarepath.Models.Model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class SeeApplicationController implements Initializable {
    @FXML
    private TableView<Application> applicationsTable;

    @FXML
    private TableColumn<Application, String> jobTitleColumn;

    @FXML
    private TableColumn<Application, String> applicantNameColumn;

    @FXML
    private TableColumn<Application, String> applicantEmailColumn;

    @FXML
    private TableColumn<Application, String> statusColumn;

    @FXML
    private Button refreshButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Label errorLabel;

    private ObservableList<Application> applicationsList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize table columns
        jobTitleColumn.setCellValueFactory(new PropertyValueFactory<>("jobTitle"));
        applicantNameColumn.setCellValueFactory(new PropertyValueFactory<>("applicantName"));
        applicantEmailColumn.setCellValueFactory(new PropertyValueFactory<>("applicantEmail"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));


        // Load applications
        loadApplications();

        // Set button actions
        refreshButton.setOnAction(event -> loadApplications());
        logoutButton.setOnAction(event -> logout());
    }

    /**
     * Loads applications related to the employer's jobs from the database.
     */
    private void loadApplications() {
        applicationsList.clear();
        errorLabel.setText("");

        // Retrieve the logged-in employer's ID
        int employerId = Model.getInstance().getCurrentUserId();

        // SQL query to fetch applications for employer's jobs
        String query = "SELECT a.id, j.title AS job_title, au.full_name AS applicant_name, au.email AS applicant_email, a.status " +
                "FROM application a " +
                "JOIN jobs j ON a.job_id = j.id " +
                "JOIN application_user au ON a.applicant_id = au.id " +
                "WHERE j.employer_id = " + employerId;

        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Integer id = Integer.parseInt( rs.getString("id"));
                String jobTitle = rs.getString("job_title");
                String applicantName = rs.getString("applicant_name");
                String applicantEmail = rs.getString("applicant_email");
                String status = rs.getString("status");

                Application application = new Application(id, jobTitle, applicantName, applicantEmail, status);
                applicationsList.add(application);
            }

            applicationsTable.setItems(applicationsList);

            if (applicationsList.isEmpty()) {
                showAlert(Alert.AlertType.INFORMATION, "Info", "Ingen ansøgninger fundet.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            showError("Fejl ved indlæsning af ansøgninger: " + e.getMessage());
        }
    }

    /**
     * Logs out the employer and returns to the Login window.
     */
    private void logout() {
        Stage currentStage = (Stage) logoutButton.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(currentStage);

        Model.getInstance().getViewFactory().showLoginWindow();
    }

    /**
     * Displays an error message in the errorLabel.
     *
     * @param message The error message to display.
     */
    private void showError(String message) {
        errorLabel.setText(message);
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
}
