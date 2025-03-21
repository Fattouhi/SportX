package com.example.login.UserDao;

import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private static final String DB_URL = "jdbc:mysql://mysql-196aef4e-esprim-b42e.b.aivencloud.com:21836/defaultdb";
    private static final String DB_USER = "avnadmin";
    private static final String DB_PASSWORD = "AVNS_DIenDKhRpZUhC8F0A1M";

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    public boolean emailExists(String email) throws SQLException {
        String query = "SELECT COUNT(*) FROM Users WHERE email = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    public void addUser(User user) throws SQLException {
        String query = "INSERT INTO Users (nom, prenom, email, password, telephone, pays, genre, date_naissance, besamee) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, user.getNom());
            stmt.setString(2, user.getPrenom());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPassword());
            stmt.setString(5, user.getTelephone());
            stmt.setString(6, user.getPays());
            stmt.setString(7, user.getGenre());
            stmt.setDate(8, new java.sql.Date(user.getDateNaissance().getTime()));
            stmt.setString(9, user.getBesamee());
            stmt.executeUpdate();
        }
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM Users";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                users.add(new User(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("telephone"),
                        rs.getString("pays"),
                        rs.getString("genre"),
                        rs.getDate("date_naissance"),
                        rs.getString("besamee")
                ));
            }
        }
        return users;
    }

    public boolean authenticate(String email, String password) throws SQLException {
        String query = "SELECT * FROM Users WHERE email = ? AND password = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    public void updateUserProfile(String email, String firstName, String lastName, String password, String phone, String country, Date birthDate, String besamee) throws SQLException {
        String query = "UPDATE Users SET nom = ?, prenom = ?, password = ?, telephone = ?, pays = ?, date_naissance = ?, besamee = ? WHERE email = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, password);
            stmt.setString(4, phone);
            stmt.setString(5, country);
            stmt.setDate(6, birthDate);
            stmt.setString(7, besamee);
            stmt.setString(8, email);
            stmt.executeUpdate();
        }
    }

    public User getUserByEmail(String email) throws SQLException {
        String query = "SELECT * FROM Users WHERE email = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getInt("id"),
                            rs.getString("nom"),
                            rs.getString("prenom"),
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getString("telephone"),
                            rs.getString("pays"),
                            rs.getString("genre"),
                            rs.getDate("date_naissance"),
                            rs.getString("besamee")
                    );
                }
            }
        }
        return null;
    }
}