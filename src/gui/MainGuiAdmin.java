package gui;

import gui.admin.BarangGui;
import gui.admin.TampilBarangGui;
import gui.admin.TampilUserGui;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class MainGuiAdmin extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private Button keluarButton;

    @Override
public void start(Stage primaryStage) {
    primaryStage.setTitle("Admin Menu");

    GridPane grid = new GridPane();
    grid.setPadding(new Insets(30, 30, 30, 30));
    grid.setVgap(18);
    grid.setHgap(70);

    // Button for Input Barang
    Button inputBarangButton = new Button("Input Barang");
    setButtonWidth(inputBarangButton);
    GridPane.setConstraints(inputBarangButton, 0, 0);
    inputBarangButton.setOnAction(e -> openBarangGui());

    // Button for Tampil Barang
    Button tampilBarangButton = new Button("Tampil Barang");
    setButtonWidth(tampilBarangButton);
    GridPane.setConstraints(tampilBarangButton, 0, 1);
    tampilBarangButton.setOnAction(e -> showBarang());

    // Button for Tampil User
    Button tampilUserButton = new Button("Tampil User");
    setButtonWidth(tampilUserButton);
    GridPane.setConstraints(tampilUserButton, 0, 2);
    tampilUserButton.setOnAction(e -> showUser());

    keluarButton = new Button("Keluar");
    setButtonWidth(keluarButton);
    GridPane.setConstraints(keluarButton, 0, 3);
    keluarButton.setOnAction(e -> handleKeluar());

    grid.getChildren().addAll(
            inputBarangButton,
            tampilBarangButton,
            tampilUserButton,
            keluarButton
    );
    // Add ColumnConstraints to ensure equal-width buttons
    ColumnConstraints columnConstraints = new ColumnConstraints();
    columnConstraints.setPercentWidth(100 / grid.getColumnCount());
    grid.getColumnConstraints().add(columnConstraints);

    Scene scene = new Scene(grid, 400, 300);
    primaryStage.setScene(scene);

    primaryStage.show();
}

private void setButtonWidth(Button button) {
    // Set a preferred width for the buttons
    button.setMinWidth(150);
    button.setMaxWidth(Double.MAX_VALUE);
}


    private void openBarangGui() {
        // Code to open BarangGui
        BarangGui barangGui = new BarangGui();
        try {
            barangGui.start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showBarang() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Show Barang");
        alert.setHeaderText(null);
        alert.setContentText("Tampil Barang");
        alert.showAndWait();
        startTampilGuiAdmin();
    }

    private void showUser() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Show User");
        alert.setHeaderText(null);
        alert.setContentText("Tampil User");
        alert.showAndWait();
        System.out.println("Tampil User");

        startTampilGuiAdminUser();
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

    private void startTampilGuiAdmin() {
        TampilBarangGui admin = new TampilBarangGui();
        try {
            admin.start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void startTampilGuiAdminUser() {
        TampilUserGui admin = new TampilUserGui();
        try {
            admin.start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
