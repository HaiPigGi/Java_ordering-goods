package item;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import connection.koneksi;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import user.User;

import java.util.ArrayList;
import java.util.Stack;

public class Pemesanan {
    protected ArrayList<barang> daftarBarang = new ArrayList<>();
    protected Stack<barang> keranjang = new Stack<>();
    private Connection connection;

    koneksi connect=new koneksi(); //memanggil constructor untuk koneksi

    public Pemesanan (Connection connection) {
        this.connection = connection;
    }

    public void tambahBarang(barang item) {
    try {
        connection.setAutoCommit(false); // Start a transaction

        daftarBarang.add(item);

        // Show Alert instead of JOptionPane
        saveBarangToDatabase(item);
        // Save to both ArrayList and Database
        connection.commit(); // Commit the transaction if both operations succeed
    } catch (SQLException e) {
        e.printStackTrace();
        // Show Error Alert
        showAlert(AlertType.ERROR, "Error", "Error: " + e.getMessage());
        try {
            connection.rollback(); // Rollback the transaction if an error occurs
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    } finally {
        try {
            connection.setAutoCommit(true); // Reset auto-commit to true for future operations
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}


public void tampilDataBarang() {
    try {
        // Fetch data from the database
        String query = "SELECT * FROM barang";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String nama_barang = resultSet.getString("nama_barang");
                    int jumlah_barang = resultSet.getInt("jumlah_barang");
                    double discount = resultSet.getDouble("discount");
                    int harga = resultSet.getInt("harga");
                    String jenis = resultSet.getString("jenis");
                    
                    // Create a new barang object using data from the database
                    barang item = new barang(nama_barang, jumlah_barang, harga, discount,jenis);
                    item.setDiscount(discount);
                    
                    // Display the details
                    item.printDetail();
                }
            }
        }
        
        // Display data from the ArrayList
        JOptionPane.showMessageDialog(null, "Tampil Data Barang ");
        for (barang item : daftarBarang) {
            item.printDetail();
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error fetching and displaying data: " + e.getMessage());
    }
}

protected void pesanBarang(barang item, User user) {
    keranjang.push(item);
    JOptionPane.showMessageDialog(null, "Data Telah Ditambahkan Kedalam Keranjang" + item.getNama_barang());
    savePemesananToDatabase(item, user);
}

protected void tampilDataKeranjang() {
    JOptionPane.showMessageDialog(null, "Tampil Data Barang");
    for (barang basket : keranjang) {
        basket.printDetail();
        }
    }
     private void savePemesananToDatabase(barang barang, User user) {
        try {
            String query = "INSERT INTO orders (username, barang_nama, harga) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, user.getUsername());
                preparedStatement.setString(2, barang.getNama_barang());
                preparedStatement.setDouble(3, barang.getHarga());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void saveBarangToDatabase(barang item) {
        try {
            String query = "INSERT INTO barang (nama_barang, jumlah_barang,harga, discount, jenis) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, item.getNama_barang());
                preparedStatement.setInt(2, item.getJumlah_barang());
                preparedStatement.setInt(3, item.getHarga());
                preparedStatement.setDouble(4, item.getDiscount());
                preparedStatement.setString(5, item.getJenis());
                
                preparedStatement.executeUpdate();
            }
           showAlert(AlertType.INFORMATION, "Data Telah Ditambahkan", "Data Telah Ditambahkan" + item);
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(AlertType.INFORMATION, "\"Error saving barang to database: ", "Err : " + e.getMessage());
        }
    }
    // Helper method to show alerts
    private void showAlert(AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
    
}
