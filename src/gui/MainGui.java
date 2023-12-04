package gui;
import user.UserSession;
import gui.Auth.LoginGui;
import gui.Auth.RegisterGui;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.control.Alert;

public class MainGui extends Application {
    private UserSession session = new UserSession();
    private boolean isLoggedIn = session.isLoggedIn();
    private HBox buttonBox; // Initialize as a class variable

    private Button loginButton;
    private Button registerButton;
    private Button orderButton;
    private Button viewBarangButton;
    private Button keluarButton;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Welcome");

        Image image = new Image("./image/anya.png");
        // Resize the image to 800x600
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(1280);
        imageView.setFitHeight(680);

        // Initialize buttons for login, register, order, view barang, and keluar
        loginButton = new Button("Login");
        registerButton = new Button("Register");
        orderButton = new Button("Order");
        viewBarangButton = new Button("View Barang");
        keluarButton = new Button("Keluar");

        // Set actions for the buttons
        loginButton.setOnAction(e -> handleLogin(primaryStage));
        registerButton.setOnAction(e -> handleRegister(primaryStage));
        orderButton.setOnAction(e -> handleOrder());
        viewBarangButton.setOnAction(e -> handleViewBarang());
        keluarButton.setOnAction(e -> handleKeluar());

        // Create an HBox to hold the buttons
        buttonBox = new HBox(10);
        buttonBox.setPadding(new Insets(10));

        // Add buttons based on the login state
        updateButtonVisibility();

        // Create a BorderPane to arrange the image and buttons
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(imageView);
        borderPane.setBottom(buttonBox);

        Scene scene = new Scene(borderPane, 1280, 720);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private void handleLogin(Stage primaryStage) {
        // Assume that showLoginPage() returns true if login is successful
        if (showLoginPage(primaryStage)) {
            // Login successful, update the button visibility
            updateButtonVisibility();
        }
    }

    private boolean showLoginPage(Stage primaryStage) {
        LoginGui loginGui = new LoginGui();
        loginGui.start(primaryStage);
        return true;  // You may return a boolean indicating login success/failure
    }
    
    private void handleRegister(Stage primaryStage) {
        // Implement registration logic here
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Register");
        alert.setHeaderText(null);
        alert.setContentText("Registration!");
        alert.showAndWait();
       // Close the current window using primaryStage
          primaryStage.close();
        // Open the RegisterGui
        startRegisterGui();
    }
    private void startRegisterGui() {
        RegisterGui register = new RegisterGui();
        Stage stage = new Stage();
        register.start(stage);
    }
    
    private void handleOrder() {
        // Implement order logic here
        System.out.println("Order!");
    }

    private void handleViewBarang() {
        // Implement view barang logic here
        System.out.println("View Barang!");
    }

    private void handleKeluar() {
        // Implement keluar logic here
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Keluar");
        alert.setHeaderText(null);
        alert.setContentText("Terima Kasih!");
        alert.showAndWait();
        // Exit the application
         Platform.exit();
    }

    private void updateButtonVisibility() {
        buttonBox.getChildren().clear(); // Clear existing buttons

        if (!isLoggedIn) {
            buttonBox.getChildren().addAll(loginButton, registerButton);
        } else {
            buttonBox.getChildren().addAll(orderButton, viewBarangButton, keluarButton);
        }
    }
}
