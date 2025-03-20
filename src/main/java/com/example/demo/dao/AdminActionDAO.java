package com.example.demo.dao;

import java.sql.*;

public class AdminActionDAO {
    private final Connection connection;

    public AdminActionDAO(Connection connection) {
        this.connection = connection;
    }

    // Enregistrer une action d’administration (par exemple, bannir un utilisateur)
    public boolean logAdminAction(int adminId, String actionType, int targetId) {
        String query = "INSERT INTO admin_actions (admin_id, action_type, target_id) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, adminId);
            stmt.setString(2, actionType);
            stmt.setInt(3, targetId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}