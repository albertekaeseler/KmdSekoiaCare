package com.jmc.kmdsekoiacarepath.Controllers.Employer;

import com.jmc.kmdsekoiacarepath.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class EmployerController implements Initializable {
    public BorderPane employer_parent;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model.getInstance().getViewFactory().getEmployerSelectedMenuItem().addListener((observableValue, oldVal, newVal) -> {
            switch (newVal){
                case OPRET_NYT_JOB -> employer_parent.setCenter(Model.getInstance().getViewFactory().getOpretJobView());
                default -> employer_parent.setCenter(Model.getInstance().getViewFactory().getSeeApplicationView());
            }
        });

    }
}
