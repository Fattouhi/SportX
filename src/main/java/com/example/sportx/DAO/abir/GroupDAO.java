package com.example.sportx.DAO.abir;

import com.example.sportx.models.abir.Group;
import com.example.sportx.utils.abir.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GroupDAO {

    private final Connection connection;

    public GroupDAO() {
        this.connection = DatabaseConnection.getConnection();
    }

    public void addGroup(String name, String description, String category, String imageUrl, int createdBy) throws SQLException {
        String sql = "INSERT INTO groups (name, description, category, image_url, created_by, created_at) VALUES (?, ?, ?, ?, ?, NOW())";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, description);
            stmt.setString(3, category);
            stmt.setString(4, imageUrl);
            stmt.setInt(5, createdBy);
            stmt.executeUpdate();
        }
    }

    public List<Group> getAllGroups() throws SQLException {
        List<Group> groups = new ArrayList<>();
        String sql = "SELECT * FROM groups";
        try (PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
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

    public int getLastInsertedGroupId() throws SQLException {
        String sql = "SELECT LAST_INSERT_ID() AS last_id";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("last_id");
            }
        }
        return -1; // Erreur si aucun ID trouvé
    }

    public Group getGroupById(int groupId) throws SQLException {
        String sql = "SELECT * FROM groups WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, groupId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Group(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("description"),
                            rs.getString("category"),
                            rs.getInt("created_by"),
                            rs.getString("created_at"),
                            rs.getString("image_url")
                    );
                }
            }
        }
        return null;
    }
}