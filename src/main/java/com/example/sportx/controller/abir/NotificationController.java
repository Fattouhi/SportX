package com.example.sportx.controller.abir;

import com.example.sportx.models.abir.Notification;
import com.example.sportx.services.abir.GroupMemberService;
import com.example.sportx.services.abir.NotificationService;
import com.example.sportx.utils.abir.ApplicationContext;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class NotificationController {
    @FXML
    private ListView<Notification> notificationListView; // Change back to ListView<Notification>

    private NotificationService notificationService;

    // No-argument constructor required by JavaFX
    public NotificationController() {
        try {
            // Initialize the required dependency
            GroupMemberService groupMemberService = new GroupMemberService(); // Example dependency
            this.notificationService = new NotificationService(groupMemberService); // Pass the dependency
        } catch (SQLException e) {
            e.printStackTrace(); // Log the error for debugging
            this.notificationService = null; // Set to null or use a fallback mechanism
            System.err.println("Failed to initialize NotificationService: " + e.getMessage());
        }
    }

    // Constructor with dependency injection (if needed)
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @FXML
    public void initialize() {
        int userId = ApplicationContext.getCurrentUser().getId();
        List<Notification> notifications = notificationService.getAllNotifications(userId);

        if (notifications.isEmpty()) {
            notificationListView.getItems().add(new Notification(-1, userId, "No notifications now.", false, "INFO", -1));
        } else {
            notificationListView.getItems().setAll(notifications);
        }

        // ✅ Ajouter un gestionnaire de clics
        notificationListView.setOnMouseClicked(this::handleNotificationClick);
    }

    @FXML
    private void handleNotificationClick(MouseEvent event) {
        Notification selectedNotification = notificationListView.getSelectionModel().getSelectedItem();
        if (selectedNotification != null) {
            try {
                notificationService.markAsRead(selectedNotification.getId());

                if (selectedNotification.getType().equals("JOIN_REQUEST")) {
                    SceneManager.switchToScene("AdminApprovalView.fxml", null);
                } else if (selectedNotification.getType().equals("MESSAGE")) {
                    SceneManager.switchToScene("ChatView.fxml", selectedNotification.getGroupId());
                }
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        }
    }




    private void showAlert(String error, String s) {
    }


    private void refreshNotificationList() {
        int userId = ApplicationContext.getCurrentUser().getId();
        List<Notification> notifications = notificationService.getAllNotifications(userId);
        notificationListView.getItems().setAll(notifications); // ✅ Charge toutes les notifications
    }



}