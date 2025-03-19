package com.example.sportx.DAO.abir;

import com.example.sportx.models.abir.Notification;
import com.example.sportx.utils.abir.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NotificationDAO {
    private Connection connection;

    public NotificationDAO() throws SQLException {
        this.connection = DatabaseConnection.getConnection();
    }

    public void addNotification(int userId, String message, String type, int groupId) throws SQLException {
        String sql = "INSERT INTO notification (user_id, message, is_read, created_at, type, group_id) VALUES (?, ?, false, NOW(), ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId); // This should be the admin's ID
            stmt.setString(2, message); // The notification message
            stmt.setString(3, type);
            stmt.setInt(4, groupId);
            stmt.executeUpdate();
        }
    }

    public List<Notification> getUnreadNotifications(int userId) throws SQLException {
        List<Notification> notification = new ArrayList<>();
        String sql = "SELECT id, user_id, message, is_read, created_at, type, group_id FROM notification WHERE user_id = ? AND is_read = false";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                notification.add(new Notification(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("message"),
                        rs.getBoolean("is_read"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getString("type"),
                        rs.getInt("group_id")
                ));
            }
        }
        return notification;
    }

    public void markAsRead(int notificationId) throws SQLException {
        String sql = "UPDATE notification SET is_read = true WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, notificationId);
            stmt.executeUpdate();
        }
    }
}