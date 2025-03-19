package com.example.sportx.models.abir;

public class GroupMember {
    private int userId; // ID of the user who is a member of the group
    private int groupId; // ID of the group the user has joined
    private String joinedAt; // Timestamp when the user joined the group

    // Constructor
    public GroupMember(int userId, int groupId, String joinedAt) {
        this.userId = userId;
        this.groupId = groupId;
        this.joinedAt = joinedAt;
    }

    // Getters and Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(String joinedAt) {
        this.joinedAt = joinedAt;
    }

    @Override
    public String toString() {
        return "GroupMember{userId=" + userId + ", groupId=" + groupId + ", joinedAt='" + joinedAt + "'}";
    }
}
