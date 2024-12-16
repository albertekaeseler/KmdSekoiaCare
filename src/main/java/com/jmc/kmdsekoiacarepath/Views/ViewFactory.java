package com.jmc.kmdsekoiacarepath.Views;
//Klassen ViewFactory er en del af pakken com.jmc.kmdsekoiacarepath.Views.
//dette sikrer, at koden er struktureret og undgår navnekonflikter.

import com.jmc.kmdsekoiacarepath.Controllers.Employee.EmployeeController;
import com.jmc.kmdsekoiacarepath.Controllers.Employee.OpretBrugerController;
import com.jmc.kmdsekoiacarepath.Controllers.Employer.EmployerController;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/*Importer bruges til at tilføje funktionalitet fra andre klasser og biblioteker:
* EmployeeController: En controller, som bruges til at styre UI-logik for en "Employee"-relateret visning.
* FXMLLoader: Anvendes til at indlæse FXML-filer, som definerer JavaFX-grænsefladen.
* Scene og Stage: Grundlæggende JavaFX-klasser til at håndtere visninger og vinduer.*/

/**
 * (User Interface-logik) refererer til den del af softwareudviklingen,
 * der fokuserer på de operationer og funktioner,
 * som direkte understøtter og styrer brugerens interaktion med en applikations brugerflade.
 * UI-logik binder hvad brugeren ser
 * ,sammen med applikationens forretningslogik (de underliggende systemprocesser og regler).
 *
 *Det er dog også vigtig at disse ting kan holdes adskilt, for at koden er mere vedligeholdigsvenlig,
 * hvilket det vi bruger MVC til
 * **/

public class ViewFactory {

    private AccountType loginAccountType;
    private AnchorPane opretBrugerView;

   /*Employee views */

    private final ObjectProperty <EmployeeMenuOptions> employeeSelectedMenuItem; /*Vi skal have denne for at opdatere
     vores view i vores employee controller. sådan at når vi eksempelvis trykker på "Mine Ansøgninger",
     så åbner den det vindue.*/
    private AnchorPane mineAnsøgningerView;
    private AnchorPane searchJobView;




    //Employer Views

    private final ObjectProperty <EmployerMenuOptions> employerSelectedMenuItem;
    private AnchorPane seeApplicationView;
    private AnchorPane opretJobView;



    public ViewFactory(){
        this.loginAccountType = AccountType.EMPLOYEE;
        this.employeeSelectedMenuItem = new SimpleObjectProperty<>();
        this.employerSelectedMenuItem = new SimpleObjectProperty<>();
    }

    public AccountType getLoginAccointType() {
        return loginAccountType;
    }

    public void setLoginAccountType(AccountType loginAccountType) {
        this.loginAccountType = loginAccountType;
    }

    public ObjectProperty<EmployeeMenuOptions> getEmployeeSelectedMenuItem() {
        return employeeSelectedMenuItem;
    }

    /*
    * Employee views Section
    * */

    public AnchorPane GetMineAnsøgningerView() {
        if (mineAnsøgningerView == null) {
            try {
                mineAnsøgningerView = new FXMLLoader(getClass().getResource("/Fxml/Employee/MineAnsøgninger.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
       return mineAnsøgningerView;

    }

    public AnchorPane getSearchJobView() {
        if(searchJobView == null){
            try {
                searchJobView = new FXMLLoader(getClass().getResource("/Fxml/Employee/SearchJob.fxml")).load();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return searchJobView;
    }


    public void showEmployeeWindow(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Employee/Employee.fxml"));

        EmployeeController employeeController = new EmployeeController();

        loader.setController(employeeController);

        creatStage(loader);
    }


    /*
    * Employer Views Section
    * */

    public ObjectProperty<EmployerMenuOptions> getEmployerSelectedMenuItem(){
        return employerSelectedMenuItem;
    }

    public AnchorPane getSeeApplicationView(){
        if (seeApplicationView == null){
            try{
                seeApplicationView = new FXMLLoader(getClass().getResource("/Fxml/Employer/SeeApplication .fxml")).load();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return seeApplicationView;
    }

    public AnchorPane getOpretJobView(){
        if (opretJobView == null){
            try {
                opretJobView = new FXMLLoader(getClass().getResource("/Fxml/Employer/OpretJob.fxml")).load();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return opretJobView;
    }

    public void getOpretBrugerWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Employee/OpretBruger.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Opret Ny Bruger");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showEmployerWindow(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Employer/Employer.fxml"));
        EmployerController controller = new EmployerController();
        loader.setController(controller);
        creatStage(loader);
    }

    public void showLoginWindow(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Login.fxml"));
        creatStage(loader);
    }

    private void creatStage(FXMLLoader loader) {
        Scene scene = null;
        try{
            scene = new Scene(loader.load());
        } catch (Exception e){
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("KMD/sekoia Care Path");
        stage.show();
    }

    //Utility metode
    public void closeStage(Stage stage){
        stage.close();
    }
}
