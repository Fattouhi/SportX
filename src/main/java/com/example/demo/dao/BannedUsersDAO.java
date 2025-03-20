package com.example.demo.dao;

import java.sql.*;

public class BannedUsersDAO {
    private final Connection connection;

    public BannedUsersDAO(Connection connection) {
        this.connection = connection;
    }

    // Bannir un utilisateur
    public boolean banUser(int userId, int adminId, String banReason) {
        String query = "INSERT INTO banned_users (user_id, banned_by, ban_reason) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, adminId);
            stmt.setString(3, banReason);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Vérifier si un utilisateur est banni
    public boolean isUserBanned(int userId) {
        String query = "SELECT COUNT(*) AS count FROM banned_users WHERE user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("count") > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Récupérer la raison du bannissement (optionnel)
    public String getBanReason(int userId) {
        String query = "SELECT ban_reason FROM banned_users WHERE user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("ban_reason");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Aucune raison spécifiée";
    }
}