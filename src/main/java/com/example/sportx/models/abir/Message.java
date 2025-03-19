package com.example.sportx.models.abir;

import java.time.LocalDateTime;

public class Message {
    private int id;
    private int groupId;
    private LocalDateTime timestamp;
    private int senderId; // Sender's user ID
    private String content;
    private String voiceFilePath;

    public Message(int id, int groupId, LocalDateTime timestamp, int senderId, String content, String voiceFilePath) {
        this.id = id;
        this.groupId = groupId;
        this.timestamp = timestamp;
        this.senderId = senderId;
        this.content = content;
        this.voiceFilePath = voiceFilePath;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getVoiceFilePath() {
        return voiceFilePath;
    }

    public void setVoiceFilePath(String voiceFilePath) {
        this.voiceFilePath = voiceFilePath;
    }

    public boolean isVoiceMessage() {
        return voiceFilePath != null;
    }

    @Override
    public String toString() {
        return "User " + senderId + (isVoiceMessage() ? " [Voice Message]" : ": " + content);
    }
}