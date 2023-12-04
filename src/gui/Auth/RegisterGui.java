package gui.Auth;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import user.User;
import user.UserManager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import connection.koneksi;

public class RegisterGui extends Application {

    private TextField usernameField, passwordField, teleponField;
    private DatePicker tanggalLahirPicker;
    private ChoiceBox<String> jenisKelaminChoiceBox;

    // public static void main(String[] args) {
    //     launch(args);
    // }
    @Override
    public void start(Stage primaryStage) {
        koneksi konek = new koneksi();
        konek.getConnection();
        primaryStage.setTitle("Register Form");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.setVgap(18);
        grid.setHgap(70);

        Label userLabel = new Label("Name:");
        userLabel.getStyleClass().add("label-text-white");
        GridPane.setConstraints(userLabel, 0, 0);

        usernameField = new TextField();
        usernameField.getStyleClass().addAll("text-field");
        GridPane.setConstraints(usernameField, 1, 0);

        Label passwordLabel = new Label("Password:");
        passwordLabel.getStyleClass().add("label-text-white");
        GridPane.setConstraints(passwordLabel, 0, 1);

        passwordField = new PasswordField(); // Use PasswordField for password input
        passwordField.getStyleClass().addAll("text-field");
        GridPane.setConstraints(passwordField, 1, 1);

        Label teleponLabel = new Label("Telepon:");
        teleponLabel.getStyleClass().add("label-text-white");
        GridPane.setConstraints(teleponLabel, 0, 2);

        teleponField = new TextField();
        teleponField.getStyleClass().addAll("text-field");
        GridPane.setConstraints(teleponField, 1, 2);

        Label tanggalLahirLabel = new Label("Tanggal Lahir:");
        tanggalLahirLabel.getStyleClass().add("label-text-white");
        GridPane.setConstraints(tanggalLahirLabel, 0, 3);

        tanggalLahirPicker = new DatePicker();
        tanggalLahirPicker.getStyleClass().addAll("text-field");
        GridPane.setConstraints(tanggalLahirPicker, 1, 3);

        Label jenisKelaminLabel = new Label("Jenis Kelamin:");
        jenisKelaminLabel.getStyleClass().add("label-text-white");
        GridPane.setConstraints(jenisKelaminLabel, 0, 4);

        jenisKelaminChoiceBox = new ChoiceBox<>();
        jenisKelaminChoiceBox.getItems().addAll("Laki-laki", "Perempuan");
        jenisKelaminChoiceBox.getStyleClass().addAll("text-field");
        GridPane.setConstraints(jenisKelaminChoiceBox, 1, 4);

        Button registerButton = new Button("Register");
        registerButton.getStyleClass().addAll("button");
        GridPane.setConstraints(registerButton, 1, 5);

        grid.getChildren().addAll(
                userLabel, usernameField,
                passwordLabel, passwordField,
                teleponLabel, teleponField,
                tanggalLahirLabel, tanggalLahirPicker,
                jenisKelaminLabel, jenisKelaminChoiceBox,
                registerButton
        );

        registerButton.setOnAction(e -> registerUser());

        Scene scene = new Scene(grid, 800, 400);
        scene.getStylesheets().add(getClass().getResource("/style/style.css").toExternalForm());
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private void registerUser() {
        try {
        String username = usernameField.getText();
        String password = hashPassword(passwordField.getText()); // Hash the password
        String telepon = teleponField.getText();
        String tanggalLahir = tanggalLahirPicker.getValue() != null ? tanggalLahirPicker.getValue().toString() : null;
        String jenisKelamin = jenisKelaminChoiceBox.getValue();
    
        // Validate user input
        if (username.isEmpty() || password.isEmpty() || telepon.isEmpty() || tanggalLahir == null || jenisKelamin == null) {
            // Use Alert for validation error messages
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all fields.");
            alert.showAndWait();
            return; // Exit the method if there are validation errors
        }
    
        // Create an instance of koneksi
        koneksi myKoneksi = new koneksi();
    
        User newUser = new User(username, password, telepon, tanggalLahir, jenisKelamin);
    
        // Assuming you have an instance of UserManager available
        UserManager userManager = new UserManager(myKoneksi.getConnection());
        userManager.addUser(newUser);

        // Use Alert instead of JOptionPane
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Registrasi Berhasil");
        alert.setHeaderText(null);
        alert.setContentText("Registrasi Berhasil");
        alert.showAndWait();
        
        // Show login page after successful registration
        showLoginPage();
        
    } catch (Exception e) {
        e.printStackTrace();

        // Use Alert for error messages as well
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Registration failed: " + e.getMessage());
        alert.showAndWait();
    }

    }

    private void showLoginPage() {
        // Create a new Stage for the login page
        Stage loginStage = new Stage();
        LoginGui loginGui = new LoginGui();
    
        // Start the login page on the new Stage
        loginGui.start(loginStage);
    
        // Close the current registration Stage (assuming the registration form is on a separate Stage)
        Stage registrationStage = (Stage) usernameField.getScene().getWindow();
        registrationStage.close();
    }
    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedHash = digest.digest(password.getBytes());
    
        // Convert byte array to a string
        StringBuilder hexString = new StringBuilder(2 * encodedHash.length);
        for (byte b : encodedHash) {
            hexString.append(String.format("%02x", b & 0xff));
        }
    
        return hexString.toString();
    }
    
}
