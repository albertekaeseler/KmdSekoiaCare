package com.jmc.kmdsekoiacarepath.Controllers.Employee;

import com.jmc.kmdsekoiacarepath.Models.Model;
import com.jmc.kmdsekoiacarepath.Views.EmployeeMenuOptions;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;


public class EmployeeMenuController implements Initializable {
    public Button mineans_btn;
    public Button searchJob_btn;
    public Button minprofil_btn;
    public Button logud_btn;
    public Button rapporter_btn;
    public Button e_Learning_btn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addListeners();

    }

    private void addListeners(){
        mineans_btn.setOnAction(event -> onMineAnsøgninger());
        searchJob_btn.setOnAction(event -> onSearchJob());
    }

    private void onMineAnsøgninger(){
        Model.getInstance().getViewFactory().getEmployeeSelectedMenuItem().set(EmployeeMenuOptions.MINE_ANSØGNIGER);
    }

    private void onSearchJob(){
        Model.getInstance().getViewFactory().getEmployeeSelectedMenuItem().set(EmployeeMenuOptions.SØG_JOB);
    }

    @FXML
    public void onLogout(){
        // Get the current stage from the logout button
        Stage currentStage = (Stage) logud_btn.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(currentStage);

        // Show the Login window
        Model.getInstance().getViewFactory().showLoginWindow();
    }
}

