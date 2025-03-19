package com.example.sportx.models.abir;

import java.time.LocalDateTime;

public class Notification {
    private int id;
    private int userId; // This should be the requester's ID
    private String message;
    private boolean isRead;
    private LocalDateTime createdAt;
    private String type; // "MESSAGE", "NEW_MEMBER", "JOIN_REQUEST"
    private int groupId; // ID of the group related to the notification

    public Notification(int id, int userId, String message, boolean isRead, LocalDateTime createdAt, String type, int groupId) {
        this.id = id;
        this.userId = userId;
        this.message = message;
        this.isRead = isRead;
        this.createdAt = createdAt;
        this.type = type;
        this.groupId = groupId;
    }
    // Constructor with all fields
    public Notification(int id, int userId, String message, boolean isRead, String type, int groupId) {
        this.id = id;
        this.userId = userId;
        this.message = message;
        this.isRead = isRead;
        this.type = type;
        this.groupId = groupId;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public boolean isRead() { return isRead; }
    public void setRead(boolean isRead) { this.isRead = isRead; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public int getGroupId() { return groupId; }
    public void setGroupId(int groupId) { this.groupId = groupId; }


    @Override
    public String toString() {
        return this.message; // Return the notification message directly
    }

}