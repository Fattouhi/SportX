package com.example.sportx.controller.abir;

import com.example.sportx.services.abir.GroupMemberService;
import com.example.sportx.utils.abir.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AdminApprovalController {
    @FXML
    private ListView<String> requestListView;

    private final GroupMemberService groupMemberService;

    public AdminApprovalController() throws SQLException {
        this.groupMemberService = new GroupMemberService();
    }

    @FXML
    public void initialize() {
        System.out.println("AdminApprovalController initialized!");
        requestListView.setCellFactory(param -> new RequestCell(this));
        loadPendingRequests();
    }

    void loadPendingRequests() {
        try {
            List<String> requests = groupMemberService.getPendingRequests();
            requestListView.getItems().setAll(requests);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void approveRequest(String request) {
        try {
            String[] parts = request.split(":");
            if (parts.length < 2) {
                System.err.println("Invalid request format: " + request);
                return;
            }

            int requestId = Integer.parseInt(parts[0].trim());
            String userInfo = parts[1].trim(); // "bob_jones wants to join alicegroup"

            // ✅ Trouver l'index de "wants to join"
            int joinIndex = userInfo.indexOf("wants to join");
            if (joinIndex == -1) {
                System.err.println("Invalid request format (Group not found): " + request);
                return;
            }

            String username = userInfo.substring(0, joinIndex).trim();  // Extraire le username
            String groupName = userInfo.substring(joinIndex + 13).trim(); // Extraire le groupName

            System.out.println("Extracted username: " + username);
            System.out.println("Extracted group name: " + groupName);

            // ✅ Correction de l'erreur SQL : Vérifier la table user et non users
            int groupId = getGroupIdByName(groupName);
            int userId = getUserIdByUsername(username);

            if (groupId == -1 || userId == -1) {
                System.err.println("Group or User not found.");
                return;
            }

            // Approuver la requête et ajouter l'utilisateur au groupe
            groupMemberService.approveRequest(requestId);
            groupMemberService.addMember(userId, groupId);

            // Supprimer la notification
            deleteNotificationForRequest(requestId);

            // Rafraîchir la liste des demandes
            loadPendingRequests();

            System.out.println("Request approved: " + username + " added to " + groupName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }





    public void rejectRequest(String request) {
        try {
            String[] parts = request.split(":");
            if (parts.length < 2) {
                System.err.println("Invalid request format: " + request);
                return;
            }

            int requestId = Integer.parseInt(parts[0].trim());

            // Rejeter la demande
            groupMemberService.rejectRequest(requestId);

            // Supprimer la notification
            deleteNotificationForRequest(requestId);

            // Rafraîchir la liste
            loadPendingRequests();

            System.out.println("Request rejected.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private int getGroupIdByName(String groupName) throws SQLException {
        System.out.println("Searching for group: " + groupName);  // 🔍 Ajoute cette ligne

        String query = "SELECT id FROM groups WHERE name = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, groupName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id"); // Retourne l'ID du groupe
            }
        }
        return -1; // Retourne -1 si le groupe n'est pas trouvé
    }


    private int getRequesterId(int requestId) throws SQLException {
        String query = "SELECT user_id FROM group_requests WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, requestId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("user_id"); // Return the requester's ID
            }
        }
        return -1; // Return -1 if the requester ID is not found
    }
    private int getUserIdByUsername(String username) throws SQLException {
        String query = "SELECT id FROM user WHERE username = ?";  // ✅ Correction ici

        try (Connection conn = DatabaseConnection.getConnection()) {
            assert conn != null;
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, username);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        }
        return -1;
    }

    private void deleteNotificationForRequest(int requestId) throws SQLException {
        String query = "DELETE FROM notification WHERE group_id = (SELECT group_id FROM group_requests WHERE id = ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, requestId);
            stmt.executeUpdate();
        }
    }
}