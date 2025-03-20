package com.example.demo.model;

import java.time.LocalDateTime;

public class ContentRequest {
    private int id;
    private String title;
    private String type; // "text", "image", "link"
    private String author;
    private String content; // Texte, chemin pour image, ou URL pour lien
    private String status; // "Pending", "Approved", "Rejected"
    private String rejectionReason; // Raison du refus (optionnel)
    private LocalDateTime createdAt;

    public ContentRequest(int id, String title, String type, String author, String content, String status, String rejectionReason, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.author = author;
        this.content = content;
        this.status = status;
        this.rejectionReason = rejectionReason;
        this.createdAt = createdAt;
    }

    // Getters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getType() { return type; }
    public String getAuthor() { return author; }
    public String getContent() { return content; }
    public String getStatus() { return status; }
    public String getRejectionReason() { return rejectionReason; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    // Setters (pour les mises à jour)
    public void setStatus(String status) { this.status = status; }
    public void setRejectionReason(String rejectionReason) { this.rejectionReason = rejectionReason; }
}