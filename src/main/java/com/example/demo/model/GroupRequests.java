package com.example.demo.model;

public class GroupRequests { // Correction du nom de la classe
    private final int id;
    private final String groupName;
    private final String category;
    private final String creator;
    private final String description;
    private final String imagePath;
    private final String status;
    //private LocalDateTime createdAt;

    // Constructeur corrigé
    public GroupRequests(int id, String groupName, String category, String creator, String description, String imagePath, String status) {
        this.id = id;
        this.groupName = groupName;
        this.category = category;
        this.creator = creator;
        this.description = description;
        this.imagePath = imagePath;
        this.status = status;
    }

    // Getters
    public int getId() { return id; }
    public String getGroupName() { return groupName; }
    public String getCategory() { return category; }
    public String getCreator() { return creator; }
    public String getDescription() { return description; }
    public String getImagePath() { return imagePath; }
    public String getStatus() { return status; }
}
