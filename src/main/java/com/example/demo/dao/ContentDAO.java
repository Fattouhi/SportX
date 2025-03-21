package com.example.demo.dao;

import com.example.demo.model.ContentRequest;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;


public class ContentDAO {
    private final Connection connection;

    public ContentDAO(Connection connection) {
        this.connection = connection;
    }

    // Récupérer toutes les publications
    public ObservableList<ContentRequest> getAllContentRequests() {
        ObservableList<ContentRequest> requests = FXCollections.observableArrayList();
        String query = "SELECT id, title, type, author, content, status, rejection_reason, created_at FROM content_requests";

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                requests.add(mapResultSetToContentRequest(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return requests;
    }

    // Récupérer les publications par statut
    public ObservableList<ContentRequest> getContentRequestsByStatus(String status) {
        ObservableList<ContentRequest> requests = FXCollections.observableArrayList();
        String query = "SELECT id, title, type, author, content, status, rejection_reason, created_at FROM content_requests WHERE LOWER(status) = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, status.toLowerCase());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    requests.add(mapResultSetToContentRequest(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return requests;
    }

    // Mettre à jour le statut d’une publication avec une raison de refus (si applicable)
    public boolean updateContentStatus(int contentId, String status, String rejectionReason) {
        String query = "UPDATE content_requests SET status = ?, rejection_reason = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, status);
            stmt.setString(2, rejectionReason != null ? rejectionReason : ""); // Raison vide si null
            stmt.setInt(3, contentId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Mapper le ResultSet à un ContentRequest
    private ContentRequest mapResultSetToContentRequest(ResultSet rs) throws SQLException {
        return new ContentRequest(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("type"),
                rs.getString("author"),
                rs.getString("content"),
                rs.getString("status"),
                rs.getString("rejection_reason"),
                rs.getTimestamp("created_at").toLocalDateTime()
        );
    }
}