package com.jmc.kmdsekoiacarepath.Controllers.Employee;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class SearchJobController implements Initializable {
    @FXML
    private AnchorPane anchor_pane;

    @FXML
    private VBox jobsContainer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String query = "SELECT * FROM jobs";

        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int jobId = rs.getInt("id");
                String jobTitle = rs.getString("title");
                String jobDescription = rs.getString("description");


                VBox jobBox = new VBox();
                jobBox.getStyleClass().add("job_view_container_VBox");

                SplitPane splitPane = new SplitPane();
                splitPane.getStyleClass().add("job_view_container_Splitpane");
                splitPane.setOrientation(javafx.geometry.Orientation.VERTICAL);
                splitPane.setPrefHeight(200);
                splitPane.setPrefWidth(160);

                AnchorPane topPane = new AnchorPane();
                TextArea textArea = new TextArea();
                textArea.setText(jobTitle + "\n" + jobDescription);
                textArea.getStyleClass().add("job_view_container_textfield");
                textArea.setWrapText(true);
                textArea.setPrefHeight(126.0);
                textArea.setPrefWidth(160.0);
                textArea.setEditable(false); // Make it read-only
                topPane.getChildren().add(textArea);

                AnchorPane bottomPane = new AnchorPane();
                Button infoBtn = new Button("Læs Info Om Jobbet");
                infoBtn.getStyleClass().addAll("job_view_container_button", "job_view_container_Text");
                infoBtn.setPrefHeight(32.0);
                infoBtn.setPrefWidth(157.0);

                infoBtn.setOnAction(event -> openSendAnsogning(jobId, jobTitle, jobDescription));
                bottomPane.getChildren().add(infoBtn);

                splitPane.getItems().addAll(topPane, bottomPane);
                jobBox.getChildren().add(splitPane);

                jobsContainer.getChildren().add(jobBox);
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Error", "Could not load jobs from the database.");
        }
    }

    /**
     * Opens the SendAnsogning.fxml window and passes the selected job's details.
     *
     * @param jobId          The ID of the selected job.
     * @param jobTitle       The title of the selected job.
     * @param jobDescription The description of the selected job.
     */
    private void openSendAnsogning(int jobId, String jobTitle, String jobDescription) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Employee/SendAnsøgning .fxml"));
            Parent root = loader.load();

            SendAnsøgningController sendAnsogningController = loader.getController();
            sendAnsogningController.setJobDetails(jobId, jobTitle, jobDescription);

            Stage stage = new Stage();
            stage.setTitle("Send Ansøgning");
            stage.setScene(new Scene(root));
            stage.show();


        } catch (Exception e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Error", "Could not open Send Ansøgning window.");
        }
    }

    /**
     * Utility method to show alerts.
     *
     * @param alertType The type of the alert.
     * @param title     The title of the alert window.
     * @param message   The message to display.
     */
    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
