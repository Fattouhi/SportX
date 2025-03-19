package com.example.sportx.controller.abir;

import com.example.sportx.services.abir.NotificationService;
import com.example.sportx.utils.abir.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GroupRequestController {
    @FXML
    private TextField userIdField;
    @FXML
    private TextField groupIdField;

    private final NotificationService notificationService;

    /**
     * Constructor with dependency injection.
     *
     * @param notificationService The NotificationService instance.
     */
    public GroupRequestController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    public void handleRequestToJoinGroup(ActionEvent event) {
        try {
            // Parse user ID and group ID from the input fields
            int userId = Integer.parseInt(userIdField.getText());
            int groupId = Integer.parseInt(groupIdField.getText());
            System.out.println("Requester ID: " + userId); // Debug statement
            System.out.println("Group ID: " + groupId); // Debug statement

            // Create the group request in the database
            createGroupRequest(userId, groupId);

            // Retrieve the admin ID for the group
            int adminId = getAdminId(groupId);
            System.out.println("Admin ID: " + adminId); // Debug statement

            // Send a notification to the admin
            if (adminId != -1) {
                try {
                    System.out.println("Sending notification to admin ID: " + adminId); // Debug statement
                    notificationService.createNotification(
                            adminId, // Send to the admin
                            "User " + userId + " has requested to join your group", // Message
                            "JOIN_REQUEST", // Notification type
                            groupId // Group ID
                    );
                } catch (SQLException e) {
                    e.printStackTrace();
                    showAlert("Error", "Failed to send notification to the admin.");
                }
            } else {
                showAlert("Error", "Admin ID not found for the group.");
            }

            // Show success message
            showAlert("Success", "Join request sent successfully!");
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid user ID or group ID.");
        }
    }

    private void createGroupRequest(int userId, int groupId) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String insertRequest = "INSERT INTO group_requests (user_id, group_id, status) VALUES (?, ?, 'pending')";
            try (PreparedStatement stmt = connection.prepareStatement(insertRequest)) {
                stmt.setInt(1, userId);
                stmt.setInt(2, groupId);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to send join request.");
        }
    }

    private int getAdminId(int groupId) {
        String query = "SELECT created_by FROM groups WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, groupId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("created_by"); // Return the admin's ID
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Return -1 if the admin ID is not found
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}