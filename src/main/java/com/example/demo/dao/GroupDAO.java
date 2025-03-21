package com.example.demo.dao;

import com.example.demo.model.GroupRequests;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;

public class GroupDAO {
    private final Connection connection;

    public GroupDAO(Connection connection) {
        this.connection = connection;
    }

    public ObservableList<GroupRequests> getAllPendingRequests() {
        ObservableList<GroupRequests> requests = FXCollections.observableArrayList();
        String query = "SELECT * FROM `groups` WHERE status = 'pending'";

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                requests.add(mapResultSetToGroupRequest(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return requests;
    }

    public ObservableList<GroupRequests> getAllGroupRequests() {
        ObservableList<GroupRequests> requests = FXCollections.observableArrayList();
        String query = "SELECT * FROM `groups`";

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                requests.add(mapResultSetToGroupRequest(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return requests;
    }

    public ObservableList<GroupRequests> getGroupRequestsByStatus(String status) {
        ObservableList<GroupRequests> requests = FXCollections.observableArrayList();
        String query = "SELECT * FROM `groups` WHERE LOWER(status) = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, status.toLowerCase());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    requests.add(mapResultSetToGroupRequest(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return requests;
    }

    public boolean updateGroupStatus(int groupId, String status) {
        String query = "UPDATE `groups` SET status = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, status);
            stmt.setInt(2, groupId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private GroupRequests mapResultSetToGroupRequest(ResultSet rs) throws SQLException {
        return new GroupRequests(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getString("category"),
                rs.getString("image_url"),
                rs.getInt("created_by"),
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getString("status")
        );
    }
}