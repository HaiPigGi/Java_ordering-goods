
package gui.Auth;
import user.User;
import user.UserManager;
import user.UserSession;
import connection.koneksi;
import gui.MainGui;
import gui.MainGuiAdmin;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginGui extends Application {
  //  private boolean loggedIn = false; // assuming this is a class variable

    private TextField usernameField;
    private PasswordField passwordField;
    private koneksi konek=new koneksi();

    // public static void main(String[] args) {
    //     launch(args);
    // }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Login Form");
        konek.getConnection(); 
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(30, 30, 30, 30));
        grid.setVgap(18);
        grid.setHgap(70);

        Label userLabel = new Label("Username:");
        userLabel.getStyleClass().add("label-text-white");
        GridPane.setConstraints(userLabel, 0, 0);

        usernameField = new TextField();
        usernameField.getStyleClass().addAll("text-field");
        GridPane.setConstraints(usernameField, 1, 0);

        Label passwordLabel = new Label("Password:");
        passwordLabel.getStyleClass().add("label-text-white");
        GridPane.setConstraints(passwordLabel, 0, 1);

        passwordField = new PasswordField();
        passwordField.getStyleClass().addAll("text-field");
        GridPane.setConstraints(passwordField, 1, 1);

        Button loginButton = new Button("Login");
        loginButton.getStyleClass().addAll("button");
        GridPane.setConstraints(loginButton, 1, 2);

        grid.getChildren().addAll(
                userLabel, usernameField,
                passwordLabel, passwordField,
                loginButton
        );

        loginButton.setOnAction(e -> loginUser(primaryStage));

        Scene scene = new Scene(grid, 800, 400);
        scene.getStylesheets().add(getClass().getResource("/style/style.css").toExternalForm());
        primaryStage.setScene(scene);

        primaryStage.show();
    }
    

    private void loginUser(Stage primaryStage) {
        String username = usernameField.getText();
        String enteredPassword = passwordField.getText();
    
        // Hash the entered password
        String hashedEnteredPassword = hashPassword(enteredPassword);
    
        try {
            // Create an instance of UserManager
            koneksi myKoneksi = new koneksi();
            UserManager userManager = new UserManager(myKoneksi.getConnection());
    
            // Retrieve user data from the database
            User storedUser = userManager.getUserToDatabase(username);
    
            // Validate the username and password
            if (storedUser != null && hashedEnteredPassword.equals(storedUser.getPassword())) {
                UserSession.setLoggedIn(true); // Set login status to true
    
                // Check user status
                String userStatus = checkStatus(username);
    
                // Check user status and open the appropriate GUI
                if ("admin".equals(userStatus)) {
                    // Admin user, open BarangGui
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Login Successful");
                    alert.setHeaderText(null);
                    alert.setContentText("Welcome, Admin " + storedUser.getUsername() + "!");
                    alert.showAndWait();
                    // Open the BarangGui frame
                    startMainGuiAdmin();
                } else if ("user".equals(userStatus)) {
                    // Regular user, open MainGui
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Login Successful");
                    alert.setHeaderText(null);
                    alert.setContentText("Welcome, " + storedUser.getUsername() + "!");
                    alert.showAndWait();
                    // Open the MainGui frame
                    startMainGui();
                } else {
                    System.out.println("Unknown user status");
                }
    
                // Close the login stage
                Stage loginStage = (Stage) usernameField.getScene().getWindow();
                loginStage.close();
            } else {
                // Login failed, show an alert with specific error message
                UserSession.setLoggedIn(false); // Set login status to false
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login Failed");
                alert.setHeaderText(null);
                alert.setContentText("Invalid username or password. Please try again.");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Handle other unexpected exceptions
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("An unexpected error occurred: " + e.getMessage());
            alert.showAndWait();
        }
    }
    
    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(password.getBytes());

            // Convert byte array to a string
            StringBuilder hexString = new StringBuilder(2 * encodedHash.length);
            for (byte b : encodedHash) {
                hexString.append(String.format("%02x", b & 0xff));
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
    private void startMainGuiAdmin() {
    MainGuiAdmin admin = new MainGuiAdmin();
    try {
        admin.start(new Stage());
    } catch (Exception e) {
        e.printStackTrace();
    }
}

private void startMainGui() {
    MainGui mainGui = new MainGui();
    try {
        mainGui.start(new Stage());
    } catch (Exception e) {
        e.printStackTrace();
    }
}
private String checkStatus(String name) {
    String query = "SELECT * FROM users WHERE name=?";

Connection connection = konek.getConnection();
   konek.getConnection();
    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        preparedStatement.setString(1, name);

        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                String storedStatus = resultSet.getString("status");

                // Assuming you have a User class with a constructor that takes the status
                User user = new User(storedStatus);

                // Set the status to the User object
                user.setStatus(storedStatus);

                // Return the status or the User object, depending on your needs
                return storedStatus;
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
    return null; // Return null if no user with the specified name is found
}


}

