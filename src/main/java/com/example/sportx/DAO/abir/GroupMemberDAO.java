package com.example.sportx.DAO.abir;

import com.example.sportx.models.abir.Group;
import com.example.sportx.models.abir.GroupMember;
import com.example.sportx.utils.abir.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GroupMemberDAO {
    private final Connection connection;

    public GroupMemberDAO() {
        this.connection = DatabaseConnection.getConnection();
    }

    public void addMember(int userId, int groupId) throws SQLException {
        String sql = "INSERT INTO group_members (user_id, group_id, joined_at) VALUES (?, ?, NOW())";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, groupId);
            stmt.executeUpdate();
        }
    }

    public void removeMember(int userId, int groupId) throws SQLException {
        String sql = "DELETE FROM group_members WHERE user_id = ? AND group_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, groupId);
            stmt.executeUpdate();
        }
    }
    public static boolean isMember(int userId, int groupId) throws SQLException {
        String query = "SELECT COUNT(*) FROM group_members WHERE user_id = ? AND group_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, groupId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Returns true if the user is a member
            }
        }
        return false;
    }

    public List<GroupMember> getMembers(int groupId) throws SQLException {
        List<GroupMember> members = new ArrayList<>();
        String sql = "SELECT * FROM group_members WHERE group_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, groupId);
            ResultSet rs = stmt.executeQuery();
            System.out.println("Executing query: " + sql + " with group ID: " + groupId); // Debugging
            while (rs.next()) {
                int userId = rs.getInt("user_id");
                int groupIdFromDB = rs.getInt("group_id");
                String joinedAt = rs.getString("joined_at");
                System.out.println("Found member - User ID: " + userId + ", Group ID: " + groupIdFromDB + ", Joined At: " + joinedAt); // Debugging
                GroupMember member = new GroupMember(userId, groupIdFromDB, joinedAt);
                members.add(member);
            }
        }
        return members;
    }


    public List<Group> getGroupsByUserId(int userId) throws SQLException {
        List<Group> groups = new ArrayList<>();
        String sql = "SELECT g.* FROM groups g JOIN group_members gm ON g.id = gm.group_id WHERE gm.user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Group group = new Group(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("category"),
                        rs.getInt("created_by"),
                        rs.getString("created_at"),
                        rs.getString("image_url")
                );
                groups.add(group);
            }
        }
        return groups;
    }
}