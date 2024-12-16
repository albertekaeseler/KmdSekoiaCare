//Denne klasse bruges som hovedindgangen til applikationen.
//Når applikationen starter, bruger den Model og ViewFactory til at vise login-vinduet.

package com.jmc.kmdsekoiacarepath;
//Klassen er en del af pakken com.jmc.kmdsekoiacarepath, som bruges til at organisere projektets filer.
//dette sikrer, at koden er struktureret og undgår navnekonflikter.

import com.jmc.kmdsekoiacarepath.Models.Model;
import javafx.application.Application;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) {
        Model.getInstance().getViewFactory().showLoginWindow();
        setupDatabase();
    }

    // Setup all the necessary tables in the database
    private void setupDatabase() {
        String sqlPragma = "PRAGMA foreign_keys = ON;";

        String sqlUser = "CREATE TABLE IF NOT EXISTS application_user (\n" +
                "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    full_name TEXT NOT NULL,\n" +
                "    date_of_birth TEXT NOT NULL,\n" +
                "    email TEXT NOT NULL UNIQUE,\n" +
                "    password TEXT NOT NULL,\n" +
                "    municipality TEXT NOT NULL,\n" +
                "    account_type TEXT NOT NULL CHECK(account_type IN ('EMPLOYEE', 'EMPLOYER'))\n" +
                ");";

        String sqlJob = "CREATE TABLE IF NOT EXISTS jobs (" +
                "id INTEGER PRIMARY KEY," +
                "title TEXT NOT NULL," +
                "description TEXT NOT NULL," +
                "hours TEXT NOT NULL," +
                "location TEXT NOT NULL," +
                "demands TEXT NOT NULL," +
                "employer_id INTEGER NOT NULL," +
                "FOREIGN KEY (employer_id) REFERENCES application_user(id) ON DELETE CASCADE" +
                ");";

        String sqlApplication = "CREATE TABLE IF NOT EXISTS application (" +
                "id INTEGER PRIMARY KEY," +
                "job_id INTEGER NOT NULL," +
                "job TEXT NOT NULL," +
                "applicant_id INTEGER NOT NULL," +
                "status TEXT NOT NULL," +
                "FOREIGN KEY (job_id) REFERENCES jobs(id)," +
                "FOREIGN KEY (applicant_id) REFERENCES application_user(id)" +
                ");";

        String sqlApplicationActionLog = "CREATE TABLE IF NOT EXISTS application_action_log (" +
                "id INTEGER PRIMARY KEY," +
                "application_id INTEGER NOT NULL," +
                "action TEXT NOT NULL," +
                "performed_by TEXT NOT NULL," +
                "timestamp TEXT NOT NULL," +
                "FOREIGN KEY (application_id) REFERENCES application(id)" +
                ");";

        String sqlEmployee = "CREATE TABLE IF NOT EXISTS employee (\n" +
                "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    user_id INTEGER NOT NULL,\n" +
                "    gender TEXT,\n" +
                "    education_status TEXT,\n" +
                "    phone INTEGER,\n" +
                "    FOREIGN KEY(user_id) REFERENCES application_user(id) ON DELETE CASCADE\n" +
                ");\n";

        String sqlEmployer = "CREATE TABLE IF NOT EXISTS employer (" +
                "id INTEGER PRIMARY KEY," +
                "user_id INTEGER NOT NULL," +
                "employer_name TEXT," +
                "FOREIGN KEY (user_id) REFERENCES application_user(id)" +
                ");";

        // Put all SQL statements in the correct order
        String[] sqlStatements = {
                sqlPragma,
                sqlUser,
                sqlJob,
                sqlApplication,
                sqlApplicationActionLog,
                sqlEmployee,
                sqlEmployer
        };

        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
             Statement statement = connection.createStatement()) {

            // Execute all statements in order
            for (String sql : sqlStatements) {
                statement.execute(sql);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Database setup failed: " + e.getMessage(), e);
        }
    }
}
/*public class App extends Application:
 Dette betyder, at denne klasse kan bruges til at starte og håndtere en JavaFX-applikation.
Klassen skal implementere metoden start().*/

/** start()-metoden er hovedindgangspunktet for JavaFX-applikationens livscyklus.**/
/* Parameteren primaryStage:
 - Dette er hovedvinduet (eller scenen) i JavaFX, hvor applikationens indhold vises.

 Hvad sker der i start()?
 - Model.getInstance():
    * Kalder en metode på Model-klassen.

 - getViewFactory():
    * Henter en ViewFactory-instans, som er ansvarlig for at oprette og håndtere JavaFX-vinduer.

 - showLoginWindow():
   *  Kalder en metode på ViewFactory-instansen, som åbner og viser login-vinduet.
   * Dette er startpunktet, hvor brugeren ser login-skærmen, når applikationen starter.
 */