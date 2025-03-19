package com.example.sportx.DAO.abir;

import com.example.sportx.models.abir.Message;
import com.example.sportx.utils.abir.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {
    private final Connection connection;

    public MessageDAO() throws SQLException {
        this.connection = DatabaseConnection.getConnection();
    }

    public void addMessage(int senderId, int groupId, String content) throws SQLException {
        String sql = "INSERT INTO messages (sender_id, group_id, content, timestamp) VALUES (?, ?, ?, NOW())";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, senderId);
            stmt.setInt(2, groupId);
            stmt.setString(3, content);
            stmt.executeUpdate();
        }
    }
    public void addVoiceMessage(int senderId, int groupId, String filePath) throws SQLException {
        String sql = "INSERT INTO messages (sender_id, group_id, content, voiceFilePath) VALUES (?, ?, NULL, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, senderId);
            stmt.setInt(2, groupId);
            stmt.setString(3, filePath);
            stmt.executeUpdate();
        }
    }

    public List<Message> getMessages(int groupId) throws SQLException {
        List<Message> messages = new ArrayList<>();
        String sql = "SELECT id, group_id, sender_id, content, voiceFilePath, timestamp " +
                "FROM messages " +
                "WHERE group_id = ? " +
                "ORDER BY timestamp ASC";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, groupId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Message message = new Message(
                        rs.getInt("id"),
                        rs.getInt("group_id"),
                        rs.getTimestamp("timestamp").toLocalDateTime(),
                        rs.getInt("sender_id"), // Retrieve the sender's user ID
                        rs.getString("content"),
                        rs.getString("voiceFilePath")
                );
                messages.add(message);
            }
        }
        return messages;
    }
}