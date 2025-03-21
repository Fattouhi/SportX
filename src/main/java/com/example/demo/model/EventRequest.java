package com.example.demo.model;

import javafx.beans.property.*;
import java.time.LocalDate;

public class EventRequest {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty title = new SimpleStringProperty();
    private final StringProperty sport = new SimpleStringProperty();
    private final StringProperty location = new SimpleStringProperty();
    private final ObjectProperty<LocalDate> eventDate = new SimpleObjectProperty<>();
    private final StringProperty description = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();

    // Constructeur
    public EventRequest(int id, String title, String sport, String location, LocalDate eventDate, String description, String status) {
        this.id.set(id);
        this.title.set(title);
        this.sport.set(sport);
        this.location.set(location);
        this.eventDate.set(eventDate);
        this.description.set(description);
        this.status.set(status);
    }

    // Getters des propriétés
    public IntegerProperty idProperty() { return id; }
    public StringProperty titleProperty() { return title; }
    public StringProperty sportProperty() { return sport; }
    public StringProperty locationProperty() { return location; }
    public ObjectProperty<LocalDate> eventDateProperty() { return eventDate; }
    public StringProperty descriptionProperty() { return description; }
    public StringProperty statusProperty() { return status; }

    // Getters pour les valeurs
    public int getId() { return id.get(); }
    public String getTitle() { return title.get(); }
    public String getSport() { return sport.get(); }
    public String getLocation() { return location.get(); }
    public LocalDate getEventDate() { return eventDate.get(); }
    public String getDescription() { return description.get(); }
    public String getStatus() { return status.get(); }

    // Setters
    public void setId(int id) { this.id.set(id); }
    public void setTitle(String title) { this.title.set(title); }
    public void setSport(String sport) { this.sport.set(sport); }
    public void setLocation(String location) { this.location.set(location); }
    public void setEventDate(LocalDate eventDate) { this.eventDate.set(eventDate); }
    public void setDescription(String description) { this.description.set(description); }
    public void setStatus(String status) { this.status.set(status); }
}