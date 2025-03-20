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

    // Récupérer toutes les demandes de groupes en attente
    public ObservableList<GroupRequests> getAllPendingRequests() {
        ObservableList<GroupRequests> requests = FXCollections.observableArrayList();
        String query = "SELECT * FROM group_requests WHERE status = 'pending'";

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

    // Récupérer toutes les demandes de groupes, quelle que soit leur statut
    public ObservableList<GroupRequests> getAllGroupRequests() {
        ObservableList<GroupRequests> requests = FXCollections.observableArrayList();
        String query = "SELECT * FROM group_requests";

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

    // Récupérer les demandes de groupes selon un statut donné
    public ObservableList<GroupRequests> getGroupRequestsByStatus(String status) {
        ObservableList<GroupRequests> requests = FXCollections.observableArrayList();
        String query = "SELECT * FROM group_requests WHERE LOWER(status) = ?";

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

    // Mettre à jour le statut d'une demande
    public boolean updateGroupStatus(int groupId, String status) {
        String query = "UPDATE group_requests SET status = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, status);
            stmt.setInt(2, groupId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Mapper le résultat SQL à un objet GroupRequests
    private GroupRequests mapResultSetToGroupRequest(ResultSet rs) throws SQLException {
        return new GroupRequests(
                rs.getInt("id"),
                rs.getString("group_name"),
                rs.getString("category"),
                rs.getString("creator"),
                rs.getString("description"),
                rs.getString("image_path"),
                rs.getString("status")
        );
    }
}