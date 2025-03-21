package com.example.demo.model;

import java.time.LocalDateTime;

public class GroupRequests {
    private final int id;
    private final String name;
    private final String description;
    private final String category;
    private final String imageUrl;
    private final int createdBy;
    private final LocalDateTime createdAt;
    private final String status;

    public GroupRequests(int id, String name, String description, String category, String imageUrl, int createdBy, LocalDateTime createdAt, String status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.imageUrl = imageUrl;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getStatus() {
        return status;
    }
}