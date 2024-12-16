package com.jmc.kmdsekoiacarepath.Controllers.Employee;

import com.jmc.kmdsekoiacarepath.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class OpretBrugerController implements Initializable {
    public TextField Fulde_navn_textfld;
    public TextField fødselsdag_textfl;
    public CheckBox kvinde_checkbox;
    public CheckBox mand_checkbox;
    public CheckBox andet_checkbox;
    public TextField kommune_textfld;
    public ChoiceBox<String> uddannelsesstatus_choicebox;
    public TextField e_mail_adress_textfld;
    public PasswordField password_textfld;
    public TextField telefonnummmer_textfld;
    public Hyperlink indsæt_cv_hyberlink;
    public Button opret_bruger_btn;

    // New RadioButtons for Account Type
    public RadioButton employee_radio;
    public RadioButton employer_radio;
    private ToggleGroup accountTypeGroup;

    public Label error_lbl; // Label for displaying errors

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize the ToggleGroup for Account Type RadioButtons
        accountTypeGroup = new ToggleGroup();
        employee_radio.setToggleGroup(accountTypeGroup);
        employer_radio.setToggleGroup(accountTypeGroup);
        employee_radio.setSelected(true); // Default selection

        // Populate uddannelsesstatus_choicebox with some values
        uddannelsesstatus_choicebox.getItems().addAll("Studerende", "Færdiguddannet", "Andet");

        // Set action for the create user button
        opret_bruger_btn.setOnAction(event -> createUser());
    }

    private void createUser() {
        // Gather all user input
        String fullName = Fulde_navn_textfld.getText().trim();
        String dateOfBirth = fødselsdag_textfl.getText().trim();
        String email = e_mail_adress_textfld.getText().trim();
        String password = password_textfld.getText().trim();
        String municipality = kommune_textfld.getText().trim();

        // Determine selected gender
        String gender = null;
        if (kvinde_checkbox.isSelected()) {
            gender = "Kvinde";
        } else if (mand_checkbox.isSelected()) {
            gender = "Mand";
        } else if (andet_checkbox.isSelected()) {
            gender = "Andet";
        }

        // Determine account type
        String accountType = null;
        if (employee_radio.isSelected()) {
            accountType = "EMPLOYEE";
        } else if (employer_radio.isSelected()) {
            accountType = "EMPLOYER";
        }

        // Education status
        String educationStatus = uddannelsesstatus_choicebox.getValue();

        // Phone number
        String phoneText = telefonnummmer_textfld.getText().trim();
        Integer phone = null;
        if (!phoneText.isEmpty()) {
            try {
                phone = Integer.parseInt(phoneText);
            } catch (NumberFormatException e) {
                showError("Invalid phone number format.");
                return;
            }
        }

        // Validate input fields
        if (fullName.isEmpty() || dateOfBirth.isEmpty() || email.isEmpty() || password.isEmpty() || municipality.isEmpty() || accountType == null) {
            showError("Please fill in all required fields.");
            return;
        }

        // Insert into the database
        String insertUserSQL = "INSERT INTO application_user (full_name, date_of_birth, email, password, municipality, account_type) VALUES(?,?,?,?,?,?)";

        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
             PreparedStatement pstmt = connection.prepareStatement(insertUserSQL, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, fullName);
            pstmt.setString(2, dateOfBirth);
            pstmt.setString(3, email);
            pstmt.setString(4, password);
            pstmt.setString(5, municipality);
            pstmt.setString(6, accountType);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                // Retrieve the generated user ID
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int newUserId = generatedKeys.getInt(1);
                        if (accountType.equals("EMPLOYEE")) {
                            insertEmployeeInfo(newUserId, gender, educationStatus, phone);
                        } else {
                            insertEmployerInfo(newUserId);
                        }
                    }
                }

                // Clear fields after successful creation
                clearFields();

                // Navigate back to Login
                Stage currentStage = (Stage) opret_bruger_btn.getScene().getWindow();
                Model.getInstance().getViewFactory().closeStage(currentStage);
                Model.getInstance().getViewFactory().showLoginWindow();

            } else {
                showError("Failed to create user.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showError("Database error: " + e.getMessage());
        }
    }

    private void insertEmployeeInfo(int userId, String gender, String educationStatus, Integer phone) {
        String insertEmployeeSQL = "INSERT INTO employee (user_id, gender, education_status, phone) VALUES(?,?,?,?)";

        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
             PreparedStatement pstmt = connection.prepareStatement(insertEmployeeSQL)) {

            pstmt.setInt(1, userId);
            pstmt.setString(2, gender != null ? gender : "");
            pstmt.setString(3, educationStatus != null ? educationStatus : "");
            if (phone != null) {
                pstmt.setInt(4, phone);
            } else {
                pstmt.setNull(4, Types.INTEGER);
            }

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            showError("Database error while inserting employee info.");
        }
    }

    private void insertEmployerInfo(int userId) {
        String insertEmployerSQL = "INSERT INTO employer (user_id) VALUES(?)";

        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
             PreparedStatement pstmt = connection.prepareStatement(insertEmployerSQL)) {

            pstmt.setInt(1, userId);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            showError("Database error while inserting employer info.");
        }
    }

    private void clearFields() {
        Fulde_navn_textfld.clear();
        fødselsdag_textfl.clear();
        e_mail_adress_textfld.clear();
        password_textfld.clear();
        kommune_textfld.clear();
        telefonnummmer_textfld.clear();
        kvinde_checkbox.setSelected(false);
        mand_checkbox.setSelected(false);
        andet_checkbox.setSelected(false);
        uddannelsesstatus_choicebox.getSelectionModel().clearSelection();
        employee_radio.setSelected(true); // Reset to default
    }

    private void showError(String message) {
        // Display error message using the error_lbl
        if (error_lbl != null) {
            error_lbl.setText(message);
        } else {
            // Fallback: Use an Alert dialog
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        }
    }
}
