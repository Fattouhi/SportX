package com.example.sportx.services.abir;

import com.example.sportx.DAO.abir.GroupDAO;
import com.example.sportx.models.abir.Group;

import java.sql.SQLException;
import java.util.List;

public class GroupService {

    private static final GroupDAO groupDAO = new GroupDAO();

    // Add this method to get the group by group ID
    public Group getGroupById(int groupId) throws SQLException {
        return groupDAO.getGroupById(groupId);
    }
    public void createGroup(String name, String description, String category, String imageUrl, int createdBy) throws SQLException {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Group name is required!");
        }
        groupDAO.addGroup(name, description, category, imageUrl, createdBy);

        // ✅ Récupérer l'ID du groupe créé
        int groupId = groupDAO.getLastInsertedGroupId();

        // ✅ Ajouter l'admin comme membre du groupe
        GroupMemberService groupMemberService = new GroupMemberService();
        groupMemberService.addMember(createdBy, groupId);
    }


    public List<Group> getAllGroups() throws SQLException {
        return groupDAO.getAllGroups();
    }

    public static boolean checkAdminPermission(int userId, int groupId) throws SQLException {
        Group group = groupDAO.getGroupById(groupId);
        return group != null && group.getCreatedby() == userId;
    }
}