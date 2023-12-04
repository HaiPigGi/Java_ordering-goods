package user;
import java.sql.*;
import java.util.ArrayList;

import connection.koneksi;

public class UserManager {
    private ArrayList<User> users = new ArrayList<>();
    private Connection connection;

    koneksi connect=new koneksi(); //memanggil constructor untuk koneksi
    
    public UserManager(Connection connection) {
        this.connection = connection;
    }
    public void addUser(User user) {
        users.add(user);
        // Simpan informasi pengguna ke database
        saveUserToDatabase(user);
    }

    public User getUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    private void saveUserToDatabase(User user) {
        try {
            String query = "INSERT INTO users (name, password, telepon, tanggal_lahir, jenis_kelamin) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, user.getUsername());
                preparedStatement.setString(2, user.getPassword());
                preparedStatement.setString(3, user.getTelepon());
                preparedStatement.setString(4, user.getTanggalLahir());
                preparedStatement.setString(5, user.getJenisKelamin());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public User getUserToDatabase(String username) {
        try {
            String query = "SELECT * FROM users WHERE name=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
    
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        String storedUsername = resultSet.getString("name");
                        String storedPassword = resultSet.getString("password");
                        String storedTelepon = resultSet.getString("telepon");
                        String storedTanggalLahir = resultSet.getString("tanggal_lahir");
                        String storedJenisKelamin = resultSet.getString("jenis_kelamin");
                         //String storedStatus = resultSet.getString("status");
    
                        return new User(storedUsername, storedPassword, storedTelepon, storedTanggalLahir, storedJenisKelamin);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if no user is found
    }
    
    
}
