package com.jmc.kmdsekoiacarepath.Controllers;

import com.jmc.kmdsekoiacarepath.Models.Model;
import com.jmc.kmdsekoiacarepath.Views.AccountType;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    public ChoiceBox<AccountType> acc_selector;
    public Label email_adress_lbl;
    public TextField email_adrdress_fld;
    public TextField password_text_fld; // Changed to PasswordField for security
    public Button login_btn;
    public Label error_lbl;
    public Button opret_bruger_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        acc_selector.setItems(FXCollections.observableArrayList(AccountType.EMPLOYEE, AccountType.EMPLOYER));
        acc_selector.setValue(AccountType.EMPLOYEE); // Default selection
        login_btn.setOnAction(event -> onLogin());
        opret_bruger_btn.setOnAction(this::onOpretBruger);
    }

    private void onLogin() {
        String email = email_adrdress_fld.getText().trim();
        String password = password_text_fld.getText().trim();
        AccountType selectedAccountType = acc_selector.getValue();

        if (email.isEmpty() || password.isEmpty()) {
            error_lbl.setText("Please enter both email and password.");
            return;
        }

        // Authenticate user
        boolean isAuthenticated = authenticateUser(email, password, selectedAccountType);
        if (isAuthenticated) {
            // Close the login window
            Stage stage = (Stage) login_btn.getScene().getWindow();
            Model.getInstance().getViewFactory().closeStage(stage);



            // Show the appropriate dashboard
            if (selectedAccountType == AccountType.EMPLOYEE) {
                Model.getInstance().getViewFactory().showEmployeeWindow();
            } else {
                Model.getInstance().getViewFactory().showEmployerWindow();
            }
        } else {
            error_lbl.setText("Invalid email or password.");
        }
    }

    private boolean authenticateUser(String email, String password, AccountType accountType) {
        String query = "SELECT id, account_type FROM application_user WHERE email = ? AND password = ?";
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setString(1, email);
            pstmt.setString(2, password); // Consider hashing the password

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String dbAccountType = rs.getString("account_type");
                    int userId = rs.getInt("id");
                    Model.getInstance().setCurrentUserId(userId);
                    // Verify if the account type matches
                    if (accountType.toString().equals(dbAccountType)) {
                        // Optionally, verify existence in employee/employer table
                        if (accountType == AccountType.EMPLOYEE) {
                            return isEmployee(userId);
                        } else if (accountType == AccountType.EMPLOYER) {
                            return isEmployer(userId);
                        }
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            error_lbl.setText("Database error.");
        }
        return false;
    }

    private boolean isEmployee(int userId) {
        String query = "SELECT * FROM employee WHERE user_id = ?";
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next(); // Returns true if employee record exists
            }

        } catch (SQLException e) {
            e.printStackTrace();
            error_lbl.setText("Database error.");
        }
        return false;
    }

    private boolean isEmployer(int userId) {
        String query = "SELECT * FROM employer WHERE user_id = ?";
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next(); // Returns true if employer record exists
            }

        } catch (SQLException e) {
            e.printStackTrace();
            error_lbl.setText("Database error.");
        }
        return false;
    }

    public void onOpretBruger(ActionEvent actionEvent) {
        // Close the current login stage
        Stage stage = (Stage) opret_bruger_btn.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);

        Model.getInstance().getViewFactory().getOpretBrugerWindow();
    }
}
