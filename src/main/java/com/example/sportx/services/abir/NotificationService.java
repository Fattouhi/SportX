package com.example.sportx.services.abir;

import com.example.sportx.DAO.abir.NotificationDAO;
import com.example.sportx.models.abir.Notification;
import com.example.sportx.utils.abir.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NotificationService {
    private final NotificationDAO notificationDAO;
    private final GroupMemberService groupMemberService;
    private final UserService userService;
    private final GroupService groupService;

    public NotificationService(GroupMemberService groupMemberService) throws SQLException {
        this.notificationDAO = new NotificationDAO();
        this.groupMemberService = groupMemberService;
        this.userService = ServiceFactory.getInstance().getUserService();
        this.groupService = ServiceFactory.getInstance().getGroupService();
    }

    public void createNotification(int userId, String message, String type, int groupId) throws SQLException {
        // Fetch the username of the user (userId is the sender's or requester's ID)
        String username = userService.getUsernameById(userId);
        // Fetch the group name
        String groupName = groupService.getGroupById(groupId).getName();
        // Construct the notification message
        String notificationMessage;
        if (type.equals("JOIN_REQUEST")) {
            notificationMessage =  " A new person has requested to join your group " + groupName;
        } else if (type.equals("MESSAGE")) {
            notificationMessage =  " a new message in the group " + groupName;
        } else {
            notificationMessage = message; // Fallback to the original message
        }
        // Save the notification to the database
        notificationDAO.addNotification(userId, notificationMessage, type, groupId); // Use notificationMessage instead of message
    }

    /**
     * Create a notification for all members of a group.
     *
     * @param groupId The ID of the group.
     * @param message The content of the notification.
     * @param type    The type of notification.
     * @throws SQLException If a database error occurs.
     */
    public void createNotificationForGroup(int groupId, String message, String type, int senderId) throws SQLException {
        List<Integer> memberIds = groupMemberService.getMemberIds(groupId);
        for (int memberId : memberIds) {
            // Fetch the username of the sender
            String username = userService.getUsernameById(senderId);
            // Construct the notification message
            String notificationMessage = username + ": " + message;
            // Pass the sender's ID (senderId) to the notification
            createNotification(senderId, notificationMessage, type, groupId);
        }
    }


    public List<Notification> getAllNotifications(int userId) {
        List<Notification> notifications = new ArrayList<>();
        String query = "SELECT * FROM notification WHERE user_id = ? ORDER BY created_at DESC"; // ✅ Récupère tout

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Notification notification = new Notification(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("message"),
                        rs.getBoolean("is_read"),
                        rs.getString("type"),
                        rs.getInt("group_id")
                );
                notifications.add(notification);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notifications;
    }

    public void markAsRead(int notificationId) {
        String query = "UPDATE notification SET is_read = true WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, notificationId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteNotificationForRequest(int requestId) throws SQLException {
        String query = "DELETE FROM notification WHERE group_id = (SELECT group_id FROM group_requests WHERE id = ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, requestId);
            stmt.executeUpdate();
        }
    }
}