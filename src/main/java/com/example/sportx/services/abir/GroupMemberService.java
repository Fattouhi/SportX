package com.example.sportx.services.abir;

import com.example.sportx.DAO.abir.GroupMemberDAO;
import com.example.sportx.models.abir.Group;
import com.example.sportx.models.abir.GroupMember;
import com.example.sportx.utils.abir.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GroupMemberService {
    private final GroupMemberDAO groupMemberDAO;
    private NotificationService notificationService;

    public GroupMemberService() throws SQLException {
        this.groupMemberDAO = new GroupMemberDAO();
        this.notificationService = new NotificationService(this);
    }

    // Lazy initialization of NotificationService
    private NotificationService getNotificationService() throws SQLException {
        if (notificationService == null) {
            notificationService = new NotificationService(this);
        }
        return notificationService;
    }

    public void addMember(int userId, int groupId) throws SQLException {
        groupMemberDAO.addMember(userId, groupId);

        // ✅ Vérification avant d'utiliser notificationService
        if (notificationService == null) {
            System.err.println("⚠️ notificationService is null! Initializing it...");
            notificationService = new NotificationService(this);
        }

        notificationService.createNotificationForGroup(groupId,
                "A new member has joined the group", "NEW_MEMBER", userId);
    }


    public void removeMember(int userId, int groupId) throws SQLException {
        groupMemberDAO.removeMember(userId, groupId);
    }

    public List<GroupMember> getMembers(int groupId) throws SQLException {
        List<GroupMember> members = groupMemberDAO.getMembers(groupId);
        System.out.println("Fetched members from service for group ID " + groupId + ": " + members.size()); // Debugging
        return members;
    }

    public List<Group> getGroupsByUserId(int userId) throws SQLException {
        return groupMemberDAO.getGroupsByUserId(userId);
    }
    public List<Integer> getMemberIds(int groupId) throws SQLException {
        List<Integer> memberIds = new ArrayList<>();
        String query = "SELECT user_id FROM group_members WHERE group_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, groupId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                memberIds.add(rs.getInt("user_id"));
            }
        }
        return memberIds;
    }
    public void approveRequest(int requestId) throws SQLException {
        String query = "UPDATE group_requests SET status = 'approved' WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, requestId);
            stmt.executeUpdate();
        }
    }

    public void rejectRequest(int requestId) throws SQLException {
        String query = "UPDATE group_requests SET status = 'rejected' WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, requestId);
            stmt.executeUpdate();
        }
    }

    public List<String> getPendingRequests() throws SQLException {
        List<String> requests = new ArrayList<>();
        String query = "SELECT gr.id, u.username, g.name FROM group_requests gr " +
                "JOIN user u ON gr.user_id = u.id " +  // 🔥 Correction ici : 'user' au lieu de 'users'
                "JOIN groups g ON gr.group_id = g.id " +
                "WHERE gr.status = 'PENDING'";


        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int requestId = rs.getInt("id");
                String username = rs.getString("username");
                String groupName = rs.getString("name");

                // ✅ Formater la requête avec le username et le groupName
                requests.add(requestId + ": " + username + " wants to join " + groupName);
            }
        }
        return requests;
    }

    public boolean isMember(int userId, int groupId) throws SQLException {
        return GroupMemberDAO.isMember(userId,groupId);
    }

}