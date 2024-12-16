module KmdSekoiaCarePath {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.jmc.kmdsekoiacarepath to javafx.fxml;
    opens com.jmc.kmdsekoiacarepath.Controllers.Employee to javafx.fxml;
    opens com.jmc.kmdsekoiacarepath.Controllers.Employer to javafx.fxml;
    exports com.jmc.kmdsekoiacarepath;

    /*man bliver nødt til at eksporte alle de packages vi har,
    så de kan kommunikere med hinanden
    Hvis GUI-elementerne er i separate packages,
    skal disse packages eksporteres, så JavaFX runtime kan tilgå dem.*/

    exports com.jmc.kmdsekoiacarepath.Controllers;
    exports com.jmc.kmdsekoiacarepath.Controllers.Employer;
    exports com.jmc.kmdsekoiacarepath.Controllers.Employee;
    exports com.jmc.kmdsekoiacarepath.Views;
    exports com.jmc.kmdsekoiacarepath.Models;
}