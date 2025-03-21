package com.example.appli.model;

import java.time.LocalDate;

public class Event {
    private String title;
    private String sport;
    private String location;
    private LocalDate date;
    private String imageUrl;
    private String description;
    private String status; // Replace organizer with status

    // Constructeur complet
    public Event(String title, String sport, String location, LocalDate date, String imageUrl, String description, String status) {
        this.title = title;
        this.sport = sport;
        this.location = location;
        this.date = date;
        this.imageUrl = imageUrl;
        this.description = description;
        this.status = status;
    }

    // Constructeur alternatif
    public Event(String title, String sport, String location, LocalDate date, String imageUrl, String description) {
        this(title, sport, location, date, imageUrl, description, ""); // status par défaut
    }

    // Getters
    public String getTitle() { return title; }
    public String getSport() { return sport; }
    public String getLocation() { return location; }
    public LocalDate getDate() { return date; }
    public String getImageUrl() { return imageUrl; }
    public String getDescription() { return description; }
    public String getStatus() { return status; }
}