package com.jmc.kmdsekoiacarepath.Controllers.Employee;

import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class MineAnsøgningerController implements Initializable {
    public Text user_name;
    public ListView mineansøgninger_listview;
    public ListView sendt_listview;
    public ListView accepteret_listview;
    public ListView afvist_listview;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
