package com.jmc.kmdsekoiacarepath.Controllers.Employer;

import com.jmc.kmdsekoiacarepath.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class OpretJobController implements Initializable {

    public TextField jobtitel_fld;
    public TextArea job_beskrivelse_fld;
    public TextField antal_timer_fld;
    public TextField lokation_fld;
    public TextField yderliger_krav_fld;
    public Button opret_job_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        opret_job_btn.setOnAction(value -> {
            try {

                // Get connection to sqlite db
                Connection connection = DriverManager.getConnection("jdbc:sqlite:sample.db");

                // create sql template
                String insertSQL = "INSERT INTO jobs(title, description, hours, location, demands, employer_id) VALUES(?,?,?,?,?,?)";

                int employerId = Model.getInstance().getCurrentUserId();

                // get the entered text from the fields
                String jobTitle = jobtitel_fld.getText();
                String jobDescription = job_beskrivelse_fld.getText();
                String jobHours = antal_timer_fld.getText();
                String jobLocation = lokation_fld.getText();
                String furtherDemands = yderliger_krav_fld.getText();

                // create a prepared statement with the sql template
                PreparedStatement pstmt = connection.prepareStatement(insertSQL);

                // set the value in the prepared statement
                pstmt.setString(1, jobTitle);
                pstmt.setString(2, jobDescription);
                pstmt.setString(3, jobHours);
                pstmt.setString(4, jobLocation);
                pstmt.setString(5, furtherDemands);
                pstmt.setInt(6, employerId);


                // execute the prepared statement
                pstmt.executeUpdate();

            } catch (SQLException e) {
                throw new RuntimeException(e);
                }
            }
        );
    }

}
