package gui.admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import connection.koneksi;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import user.User;

public class TampilUserGui extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private koneksi konek = new koneksi();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Tampil User");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(30, 30, 30, 30));
        grid.setVgap(18);
        grid.setHgap(70);

        // Table to display items
        TableView<User> itemTable = createItemTable();
        GridPane.setConstraints(itemTable, 0, 0);

        grid.getChildren().addAll(itemTable);

        Scene scene = new Scene(grid, 800, 400);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private TableView<User> createItemTable() {
        TableView<User> table = new TableView<>();

        TableColumn<User, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));


        TableColumn<User, String> teleponColumn = new TableColumn<>("Telepon");
        teleponColumn.setCellValueFactory(new PropertyValueFactory<>("telepon"));

        TableColumn<User, String> tanggalLahirColumn = new TableColumn<>("Tanggal Lahir");
        tanggalLahirColumn.setCellValueFactory(new PropertyValueFactory<>("tanggalLahir"));

        TableColumn<User, String> jenisKelaminColumn = new TableColumn<>("Jenis Kelamin");
        jenisKelaminColumn.setCellValueFactory(new PropertyValueFactory<>("jenisKelamin"));

        // Add the columns to the table
        table.getColumns().addAll(nameColumn, teleponColumn, tanggalLahirColumn, jenisKelaminColumn);

        // Populate the table with data from the database
        ObservableList<User> items = fetchDataFromDatabase();
        table.setItems(items);

        // Set preferred width for the TableView
        table.setPrefWidth(800);

        return table;
    }

    private ObservableList<User> fetchDataFromDatabase() {
        ObservableList<User> items = FXCollections.observableArrayList();

        String query = "SELECT * FROM users";

        try (Connection connection = konek.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (resultSet.next()) {
                String nama_user = resultSet.getString("name");
                String telepon = resultSet.getString("telepon");
                String tanggal_lahir = resultSet.getString("tanggal_lahir");
                String jenis_kelamin = resultSet.getString("jenis_kelamin");

                // Create a User object and add it to the list
                User item = new User(nama_user,telepon, tanggal_lahir, jenis_kelamin);
                items.add(item);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions appropriately
        }

        return items;
    }
}
