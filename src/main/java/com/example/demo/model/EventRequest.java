package com.example.demo.model;

import javafx.beans.property.*;

import java.time.LocalDate;

public class EventRequest {
    // Déclaration des propriétés
    private final StringProperty eventName = new SimpleStringProperty();
    private final StringProperty description = new SimpleStringProperty();
    private final ObjectProperty<LocalDate> eventDate = new SimpleObjectProperty<>();
    private final StringProperty organizer = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty(); // 🆕 Ajout du statut
    private final StringProperty address = new SimpleStringProperty(); // 🆕 Ajout de l'adresse


    // Constructeur
    public EventRequest(String eventName, String description, LocalDate eventDate, String organizer, String status ,String address) {
        this.eventName.set(eventName);
        this.description.set(description);
        this.eventDate.set(eventDate);
        this.organizer.set(organizer);
        this.status.set(status);
        this.address.set(address); // 🆕 Initialisation de l'adresse

    }

    // Getters des propriétés
    public StringProperty eventNameProperty() { return eventName; }
    public StringProperty descriptionProperty() { return description; }
    public ObjectProperty<LocalDate> eventDateProperty() { return eventDate; }
    public StringProperty organizerProperty() { return organizer; }
    public StringProperty statusProperty() { return status; } // 🆕 Getter status
    public StringProperty addressProperty() { return address; } // 🆕 Getter adresse


    // Getters pour les valeurs
    public String getEventName() { return eventName.get(); }
    public String getDescription() { return description.get(); }
    public LocalDate getEventDate() { return eventDate.get(); }
    public String getOrganizer() { return organizer.get(); }
    public String getStatus() { return status.get(); } // 🆕 Getter status
    public String getAddress() { return address.get(); } // 🆕 Getter adresse


    // Setters
    public void setEventName(String eventName) { this.eventName.set(eventName); }
    public void setDescription(String description) { this.description.set(description); }
    public void setEventDate(LocalDate eventDate) { this.eventDate.set(eventDate); }
    public void setOrganizer(String organizer) { this.organizer.set(organizer); }
    public void setStatus(String status) { this.status.set(status); } // 🆕 Setter status
    public void setAddress(String address) { this.address.set(address); } // 🆕 Setter adresse

}
