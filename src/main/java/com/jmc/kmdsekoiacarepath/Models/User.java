package com.jmc.kmdsekoiacarepath.Models;

public class User {
        private String fullName;
        private String dateOfBirth;  // Format: YYYY-MM-DD
        private String email;
        private String password;
        private String municipality;
        private boolean isLoggedIn;

        // Constructor
        public User(String fullName, String dateOfBirth, String email, String password, String municipality) {
            this.fullName = fullName;
            this.dateOfBirth = dateOfBirth;
            this.email = email;
            this.password = password;
            this.municipality = municipality;
            this.isLoggedIn = false;           // By default: user is not logged in
        }

        // Getter for fullName
        public String getFullName() {
            return fullName;
        }

        // Setter for fullName
        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        // Getter for email
        public String getEmail() {
            return email;
        }

        // Setter for email
        public void setEmail(String email) {
            if (email != null && email.contains("@")) {
                this.email = email;
            } else {
                System.out.println("Invalid email format.");
            }
        }

        // Getter for municipality
        public String getMunicipality() {
            return municipality;
        }

        // Setter for municipality
        public void setMunicipality(String municipality) {
            this.municipality = municipality;
        }
        // Getter fo DateOfBirth
        public String getDateOfBirth() {
            return dateOfBirth;
        }
        // Setter for DateOfBirth
        public void setDateOfBirth(String dateOfBirth) {
            // Validate date format (basic example, adjust as needed)
            if (dateOfBirth != null && dateOfBirth.matches("\\d{4}-\\d{2}-\\d{2}")) {  //   \\d: This means "a digit" (0–9).     {4}: This means "exactly 4 digits" (e.g., the year 2024).
                //    - This matches the hyphen - that separates the year, month, and day.(e.g, 20124-12-9)
            } else {
                System.out.println("Invalid date format. Use YYYY-MM-DD.");
            }
        }



        // This logIn method allows a user to log in to the system by verfying the email and password they enter against the email and password that is stored for this user
        public boolean logIn(String enteredEmail, String enteredPassword) {       // these parameters(inputs) are provided by the user that is trying to log in
            if (this.email.equals(enteredEmail) && this.password.equals(enteredPassword)) {   // (this.email) and (this.password) refers to the email stored in the User object. we compare it to the email and password that enter by user.
                this.isLoggedIn = true;
                System.out.println("Login successful - Inloggningen lyckades.");
                return true;         // Login successful
            } else {
                System.out.println("Invalid email or password. Ogiltig e-postadress eller lösenord.");
                return false;        // Login failed
            }
        }



        // This logOut method checks whether the user is currently logged in. if they are,it logs them out and changes their status to logged out and if they are not logged in, it shows an error message.
        public boolean logOut() {
            if (this.isLoggedIn) {   // This refers to user's current login status
                this.isLoggedIn = false; // Set login status to false
                System.out.println("Logged out successfully. Loggade ut framgångsrikt.");
                return true;  // Logout successful
            } else {
                System.out.println("User is not logged in. Användaren är inte inloggad.");
                return false;    // Cannot log out if not logged in
            }
        }

        // This resetPassword method allow the user to reset their password
        public boolean resetPassword(String enteredEmail, String newPassword, String confirmPassword) {
            // This ensures that the email provided matches the one stored in the system (by comparing them)
            if (!this.email.equals(enteredEmail)) {
                System.out.println("Email does not match our records. E-post stämmer inte överens med våra uppgifter.");
                return false;
            }

            //This ensures that the new password meets the criteria we decided on
            if (newPassword == null || newPassword.length() < 8) {   // no password was entered or shorter than 8 characters
                System.out.println("Password must be at least 8 characters long. Lösenordet måste vara minst 8 tecken långt.");
                return false;
            }

            // Confirming the new password
            if (!newPassword.equals(confirmPassword)) {   // Here we check if (newPassword) matches the (confirmPassword)
                System.out.println("Passwords do not match. Lösenord stämmer inte överens.");
                return false;
            }

            // Updating the password
            this.password = newPassword;  // (this.password) is refering to the password attribute. So password in attributes gets a new value.
            System.out.println("Password has been reset successfully. Lösenordet har återställts.");
            return true;
        }
    }



