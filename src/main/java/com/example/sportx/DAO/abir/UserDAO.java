package com.example.sportx.DAO.abir;

import com.example.sportx.models.abir.User;
import com.example.sportx.utils.abir.DatabaseConnection;
import java.sql.*;

public class UserDAO {
    private final Connection connection;

    public UserDAO() {
        this.connection = DatabaseConnection.getConnection();
    }

    public String getUsernameById(int userId) throws SQLException {
        String query = "SELECT username FROM user WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("username");
            }
        }
        return null; // Return null if no user is found
    }

    public int getUserIdByUsername(String username) throws SQLException {
        String query = "SELECT id FROM user WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        }
        return -1; // Return -1 if no user is found
    }

    public void addUser(String username, String email, String password) throws SQLException {
        String query = "INSERT INTO user (username, email, password, created_at) VALUES (?, ?, ?, NOW())";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, email);
            stmt.setString(3, password);
            stmt.executeUpdate();
        }
    }
    /**
     * Retrieve a user by their email.
     *
     * @param email The user's email.
     * @return The user object, or null if not found.
     * @throws SQLException If a database error occurs.
     */
    public User getUserByEmail(String email) throws SQLException {
        String query = "SELECT * FROM user WHERE email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("created_at"),
                        rs.getString("status")
                );
            }
        }
        return null;
    }
}