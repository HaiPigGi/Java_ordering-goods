package gui.admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import connection.koneksi;
import item.barang;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class TampilBarangGui extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private koneksi konek = new koneksi();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Tampil Barang");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(30, 30, 30, 30));
        grid.setVgap(18);
        grid.setHgap(70);

        // Table to display items
        TableView<barang> itemTable = createItemTable();
        GridPane.setConstraints(itemTable, 0, 0);

        grid.getChildren().addAll(itemTable);

        Scene scene = new Scene(grid, 800, 400);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private TableView<barang> createItemTable() {
        TableView<barang> table = new TableView<>();
    
        // Create columns with appropriate names
        TableColumn<barang, String> nameColumn = new TableColumn<>("Nama Barang");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("nama_barang"));
        nameColumn.setMinWidth(200);  // Set the minimum width of the column

        TableColumn<barang, Integer> jumlahBarangColumn = new TableColumn<>("Jumlah Barang");
        jumlahBarangColumn.setCellValueFactory(new PropertyValueFactory<>("jumlah_barang"));
        jumlahBarangColumn.setMinWidth(150);  // Set the minimum width of the column
    
        TableColumn<barang, Double> hargaColumn = new TableColumn<>("Harga");
        hargaColumn.setCellValueFactory(new PropertyValueFactory<>("harga"));
        hargaColumn.setMinWidth(150);  // Set the minimum width of the column
        
        TableColumn<barang, String> jenisColumn = new TableColumn<>("jenis");
        jenisColumn.setCellValueFactory(new PropertyValueFactory<>("jenis"));
        jenisColumn.setMinWidth(200);  // Set the minimum width of the column
    
        // Add the columns to the table
        table.getColumns().addAll(nameColumn, jumlahBarangColumn, hargaColumn,jenisColumn);
    
        // Populate the table with data from the database
        ObservableList<barang> items = fetchDataFromDatabase();
        table.setItems(items);
    
        // Set preferred width for the TableView
        table.setPrefWidth(800);
    
        return table;
    }
    
    
    private ObservableList<barang> fetchDataFromDatabase() {
        ObservableList<barang> items = FXCollections.observableArrayList();
    
        String query = "SELECT * FROM barang";
    
        try (Connection connection = konek.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
    
            while (resultSet.next()) {
                String namaBarang = resultSet.getString("nama_barang");
                int jumlahBarang = resultSet.getInt("jumlah_barang");
                int harga = resultSet.getInt("harga");
                String jenis = resultSet.getString("jenis");
    
                // Create a barang object and add it to the list
                barang item = new barang(namaBarang, jumlahBarang, harga, 0, jenis); // You may need to adjust the parameters
                items.add(item);
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions appropriately
        }
    
        return items;
    }
    
    

    
}
