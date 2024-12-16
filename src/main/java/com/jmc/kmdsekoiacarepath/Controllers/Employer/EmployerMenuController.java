package com.jmc.kmdsekoiacarepath.Controllers.Employer;

import com.jmc.kmdsekoiacarepath.Models.Model;
import com.jmc.kmdsekoiacarepath.Views.EmployerMenuOptions;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class EmployerMenuController implements Initializable {
    public Button se_ansøgninger_btn;
    public Button opret_nyt_job_btn;
    public Button log_ud_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListeners();

    }

    private void addListeners(){
        se_ansøgninger_btn.setOnAction(event -> onSeeApplication());
        opret_nyt_job_btn.setOnAction(event -> onOpretJob());
    }

    private void onSeeApplication(){
        Model.getInstance().getViewFactory().getEmployerSelectedMenuItem().set(EmployerMenuOptions.SE_ANSØGNINGER);
    }

    private void onOpretJob(){
        Model.getInstance().getViewFactory().getEmployerSelectedMenuItem().set(EmployerMenuOptions.OPRET_NYT_JOB);
    }

    @FXML
    public void onLogout(){
        // Get the current stage from the logout button
        Stage currentStage = (Stage) log_ud_btn.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(currentStage);

        // Show the Login window
        Model.getInstance().getViewFactory().showLoginWindow();
    }
}
