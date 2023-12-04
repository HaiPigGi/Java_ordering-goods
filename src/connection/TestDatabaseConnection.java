package connection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestDatabaseConnection {
    public static void main(String[] args) {
        Connection connection = null;

        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Replace these parameters with your database information
            String url = "jdbc:mysql://localhost:3306/pemesanan_barang";
            String username = "root";
            String password = "";

            // Try to establish a connection to the database
            connection = DriverManager.getConnection(url, username, password);

            if (connection != null) {
                System.out.println("Connection to the database established successfully!");
            } else {
                System.out.println("Failed to establish a connection to the database!");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the connection when done
            if (connection != null) {
                try {
                    connection.close();
                    System.out.println("Connection closed.");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
