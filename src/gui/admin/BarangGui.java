package gui.admin;

import connection.koneksi;
import item.Pemesanan;
import item.barang;
import item.barangElektronik;
import item.barangPakaian;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class BarangGui extends Application {

    // public static void main(String[] args) {
    //     launch(args);
    // }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("BarangGui");
    
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
    
        // ComboBox for selecting jenis (type)
        grid.add(new Label("Pilih Jenis Barang:"), 0, 0);
        ObservableList<String> jenisOptions =
                FXCollections.observableArrayList("Elektronik", "Pakaian");
        ComboBox<String> jenisComboBox = new ComboBox<>(jenisOptions);
        grid.add(jenisComboBox, 1, 0);
    
        // Add an event handler for jenisComboBox
        jenisComboBox.setOnAction(e -> {
            String selectedJenis = jenisComboBox.getValue();
            if (selectedJenis != null) {
                if (selectedJenis.equals("Elektronik")) {
                    showElektronikForm(grid);
                } else if (selectedJenis.equals("Pakaian")) {
                    showPakaianForm(grid);
                }
            }
        });
    
        Scene scene = new Scene(grid, 400, 400);
        scene.getStylesheets().add(getClass().getResource("/style/style.css").toExternalForm());
        primaryStage.setScene(scene);
    
        primaryStage.show();
    }
    

    private void showElektronikForm(GridPane grid) {
        clearGrid(grid);
    
        grid.add(new Label("Nama Barang Elektronik:"), 0, 1);
        TextField namaElektronikField = new TextField();
        grid.add(namaElektronikField, 1, 1);
    
        // Add form fields for Elektronik
        grid.add(new Label("Jumlah Barang Elektronik:"), 0, 2);
        TextField jumlahElektronikField = new TextField();
        grid.add(jumlahElektronikField, 1, 2);
    
        // Add form field for harga
        grid.add(new Label("Harga Elektronik:"), 0, 3);
        TextField hargaElektronikField = new TextField();
        grid.add(hargaElektronikField, 1, 3);
    
        // Add a ComboBox for selecting jenis (type) of Elektronik
        grid.add(new Label("Jenis Barang Elektronik:"), 0, 4);
        ObservableList<String> jenisOptions =
                FXCollections.observableArrayList("Laptop", "Smartphone", "Camera");
        ComboBox<String> jenisElektronikComboBox = new ComboBox<>(jenisOptions);
        grid.add(jenisElektronikComboBox, 1, 4);
    
        // Add form field for discount
        grid.add(new Label("Discount Elektronik:"), 0, 5);
        TextField discountElektronikField = new TextField();
        grid.add(discountElektronikField, 1, 5);
    
        // Add a Print Detail button
        Button printButton = new Button("Print Detail");
        printButton.setOnAction(e -> {
            String jumlahElektronikText = jumlahElektronikField.getText();
            String discountElektronikText = discountElektronikField.getText();
    
            // Check if jumlahElektronikText is not empty before parsing
            if (!jumlahElektronikText.isEmpty()) {
                printDetailElektronik(
                    namaElektronikField.getText(),
                    Integer.parseInt(jumlahElektronikText),
                    Integer.parseInt(hargaElektronikField.getText()),  // Added harga field
                    jenisElektronikComboBox.getValue(),
                    "Elektronik",
                    discountElektronikText.isEmpty() ? 0 : Integer.parseInt(discountElektronikText)
                );
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Jumlah Barang Elektronik tidak boleh kosong.");
                alert.showAndWait();
            }
        });
    
        // Add an Add button
        Button addButton = new Button("Add to Pemesanan");
        addButton.setOnAction(e -> {
            String jumlahElektronikText = jumlahElektronikField.getText();
            String discountElektronikText = discountElektronikField.getText();
    
            // Check if jumlahElektronikText is not empty before parsing
            if (!jumlahElektronikText.isEmpty()) {
                addPemesanan(
                    namaElektronikField.getText(),
                    Integer.parseInt(jumlahElektronikText),
                    Integer.parseInt(hargaElektronikField.getText()),  // Added harga field
                    jenisElektronikComboBox.getValue(),
                    discountElektronikText.isEmpty() ? 0 : Integer.parseInt(discountElektronikText),
                    "Elektronik"
                );
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Jumlah Barang Elektronik tidak boleh kosong.");
                alert.showAndWait();
            }
        });
    
        grid.add(printButton, 1, 8);
        grid.add(addButton, 2, 8);
    }
    
    private void showPakaianForm(GridPane grid) {
        clearGrid(grid);
    
        grid.add(new Label("Nama Barang Pakaian:"), 0, 1);
        TextField namaPakaianField = new TextField();
        grid.add(namaPakaianField, 1, 1);
    
        // Add form field for jumlah barang
        grid.add(new Label("Jumlah Barang Pakaian:"), 0, 2);
        TextField jumlahPakaianField = new TextField();
        grid.add(jumlahPakaianField, 1, 2);
    
        // Add form field for harga
        grid.add(new Label("Harga Pakaian:"), 0, 3);
        TextField hargaPakaianField = new TextField();
        grid.add(hargaPakaianField, 1, 3);
    
        // Add form field for ukuran
        grid.add(new Label("Ukuran Pakaian:"), 0, 4);
        ComboBox<String> ukuranPakaianComboBox = new ComboBox<>();
        ukuranPakaianComboBox.getItems().addAll("S", "M", "L", "XL", "XXL");
        grid.add(ukuranPakaianComboBox, 1, 4);
    
        // Add form field for discount
        grid.add(new Label("Discount Pakaian:"), 0, 5);
        TextField discountPakaianField = new TextField();
        grid.add(discountPakaianField, 1, 5);
    
        // Add a Print Detail button
        Button printButton = new Button("Print Detail");
        printButton.setOnAction(e -> {
            String jumlahPakaianText = jumlahPakaianField.getText();
            String discountPakaianText = discountPakaianField.getText();
    
            // Check if jumlahPakaianText is not empty before parsing
            if (!jumlahPakaianText.isEmpty()) {
                printDetail(
                    namaPakaianField.getText(),
                    Integer.parseInt(jumlahPakaianText),
                    Integer.parseInt(hargaPakaianField.getText()),
                    ukuranPakaianComboBox.getValue(),
                    "Pakaian",
                    discountPakaianText.isEmpty() ? 0 : Integer.parseInt(discountPakaianText)
                );
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Jumlah Barang Pakaian tidak boleh kosong.");
                alert.showAndWait();
            }
        });
    
        // Add an Add button
        Button addButton = new Button("Add to Pemesanan");
        addButton.setOnAction(e -> {
            String jumlahPakaianText = jumlahPakaianField.getText();
            String discountPakaianText = discountPakaianField.getText();
    
            // Check if jumlahPakaianText is not empty before parsing
            if (!jumlahPakaianText.isEmpty()) {
                addPemesanan(
                    namaPakaianField.getText(),
                    Integer.parseInt(jumlahPakaianText),
                    Integer.parseInt(hargaPakaianField.getText()),
                    ukuranPakaianComboBox.getValue(),
                    discountPakaianText.isEmpty() ? 0 : Integer.parseInt(discountPakaianText),
                    "Pakaian"
                );
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Jumlah Barang Pakaian tidak boleh kosong.");
                alert.showAndWait();
            }
        });
    
        grid.add(printButton, 1, 8);
        grid.add(addButton, 2, 8);
    }
    
      
    private void clearGrid(GridPane grid) {
        grid.getChildren().clear();
        grid.add(new Label("Pilih Jenis Barang:"), 0, 0);
        ObservableList<String> jenisOptions =
                FXCollections.observableArrayList("Elektronik", "Pakaian");
        ComboBox<String> jenisComboBox = new ComboBox<>(jenisOptions);
        grid.add(jenisComboBox, 1, 0);
        jenisComboBox.setOnAction(e -> {
            String selectedJenis = jenisComboBox.getValue();
            if (selectedJenis != null) {
                if (selectedJenis.equals("Elektronik")) {
                    showElektronikForm(grid);
                } else if (selectedJenis.equals("Pakaian")) {
                    showPakaianForm(grid);
                }
            }
        });
    }

            private void printDetail(String nama, int jumlah, int harga, String ukuran, String jenis,double discount) {
            StringBuilder message = new StringBuilder();
            message.append("Nama Barang: ").append(getValueOrEmpty(nama)).append("\n");
            message.append("Jumlah Barang: ").append(jumlah).append("\n");
            message.append("Harga: ").append(harga).append("\n");
            message.append("Discount : ").append(discount).append("\n");

            // Check if ukuran is not null before appending to the message
            if (ukuran != null) {
                message.append("Ukuran: ").append(ukuran).append("\n");
            } else {
                message.append("Ukuran: Data kosong\n");
            }

            message.append("Jenis Barang: ").append(getValueOrEmpty(jenis));

            // Create a new JavaFX Alert
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Detail Barang");
            alert.setHeaderText(null); // No header text
            alert.setContentText(message.toString());

            // Show the alert
            alert.showAndWait();
        }
    
    private String getValueOrEmpty(String value) {
        return value != null ? value : "Data kosong";
    }
    
    private void printDetailElektronik(String nama, int jumlah, int harga, String jenis, String kategori,double discount) {
        StringBuilder message = new StringBuilder();
        message.append("Nama Barang: ").append(getValueOrEmpty(nama)).append("\n");
        message.append("Jumlah Barang: ").append(jumlah).append("\n");
        message.append("Discount : ").append(discount).append("\n");
    
        // Check if harga is not 0 before appending to the message
        if (harga != 0) {
            message.append("Harga: ").append(harga).append("\n");
        } else {
            message.append("Harga: Data kosong\n");
        }
    
    
        message.append("Jenis Barang: ").append(getValueOrEmpty(jenis)).append("\n");
        message.append("Kategori: ").append(getValueOrEmpty(kategori));
    
        // Create a new JavaFX Alert
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Detail Barang");
        alert.setHeaderText(null); // No header text
        alert.setContentText(message.toString());
    
        // Show the alert
        alert.showAndWait();
    }

    // Metode untuk menambahkan barang ke dalam pemesanan
    private void addPemesanan(String nama, int jumlah, int harga, String ukuranJenis,double discount,  String kategori) {
        // Create an instance of koneksi
        koneksi myKoneksi = new koneksi();
        Pemesanan order = new Pemesanan(myKoneksi.getConnection());
    
        barang item;

        try {
            // Check the category and create the item accordingly
            if ("Pakaian".equals(kategori)) {
                // For Pakaian, use ukuran as ukuranJenis
                item = new barangPakaian(nama, jumlah, harga, ukuranJenis,discount, kategori);
            } else if ("Elektronik".equals(kategori)) {
                // For Elektronik, use jenis as ukuranJenis
                item = new barangElektronik(nama, jumlah, harga, ukuranJenis,kategori, discount);
            } else {
                // Handle other categories if needed
                item = new barang(nama, jumlah, harga,discount, kategori);
            }
    
            order.tambahBarang(item);
    
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Barang Ditambahkan");
            alert.setHeaderText(null);
            alert.setContentText(
                "Nama: " + nama + "\n" +
                "Jumlah: " + jumlah + "\n" +
                "Harga: " + harga + "\n" +
                "Ukuran/Jenis: " + ukuranJenis + "\n" +
                "Kategori: " + kategori + "\n" +
                "Discount: " + discount + "\n" +
                "Berhasil ditambahkan ke dalam pemesanan."
                );
                alert.showAndWait();

        } catch (Exception e) {
            // Handle the exception and show an alert
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Gagal menambahkan barang ke dalam pemesanan. Cek kembali data barang.");
            alert.showAndWait();
            e.printStackTrace(); // You can log the exception if needed
        }
    }
    
    
}   
