package connection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class koneksi {
    private Connection connection;

    public koneksi() {
        try {
            // Gantilah parameter koneksi sesuai dengan informasi database Anda
            String url = "jdbc:mysql://localhost:3306/pemesanan_barang";
            String username = "root";
            String password = "";
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Koneksi Berhasil");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Add a getter method to get the connection
    public Connection getConnection() {
        return connection;
    }
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Connection closed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
