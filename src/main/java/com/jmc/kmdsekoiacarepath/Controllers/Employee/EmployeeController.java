package com.jmc.kmdsekoiacarepath.Controllers.Employee;

import com.jmc.kmdsekoiacarepath.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class EmployeeController implements Initializable {
    public BorderPane employee_parent;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /* VI TILFØJER EN LISTENER:
        *
        * En listener lytter efter en hændelse (som et museklik, et tastaturtryk eller en handling)
        * og udfører kode som svar på den hændelse. Den "venter" på, at en hændelse indtræffer,
        * og når først hændelsen sker, kaldes lytterens metode til at håndtere hændelsen.
        *
        * Brugen af vores Switch statement, gør at vi kan ændre vores center vindue til noget andet
        *
        * Vinduet bliver ændret gennem EmployeeMenuController
        *
        *
        * */
        Model.getInstance().getViewFactory().getEmployeeSelectedMenuItem().addListener((observable, oldValue, newValue)  -> {
            switch (newValue){
                case SØG_JOB -> employee_parent.setCenter(Model.getInstance().getViewFactory().getSearchJobView());
                default -> employee_parent.setCenter(Model.getInstance().getViewFactory().GetMineAnsøgningerView());
            }
        });

    }
}
