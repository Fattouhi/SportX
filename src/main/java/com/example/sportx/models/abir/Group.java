package com.example.sportx.models.abir;

public class Group {
    private int id;
    private String name;
    private String description;
    private String category ;
    private int createdby;
    private String imageUrl;
    private String createdAt;

    public Group(int id, String name, String description, String category, int createdby, String createdAt, String imageUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.createdby = createdby;
        this.createdAt = createdAt;
        this.imageUrl = imageUrl;
    }


    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public int getCreatedby() { return createdby; }
    public void setCreatedby(int createdBy) { this.createdby = createdBy; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}
