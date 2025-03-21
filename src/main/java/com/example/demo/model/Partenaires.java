package com.example.demo.model;

import javafx.beans.property.*;

public class Partenaires {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty nom = new SimpleStringProperty();
    private final StringProperty description = new SimpleStringProperty();
    private final StringProperty logoUrl = new SimpleStringProperty();
    private final StringProperty siteWeb = new SimpleStringProperty();
    private final StringProperty requestStatus = new SimpleStringProperty();

    public Partenaires(int id, String nom, String description, String logoUrl, String siteWeb, String requestStatus) {
        this.id.set(id);
        this.nom.set(nom);
        this.description.set(description);
        this.logoUrl.set(logoUrl);
        this.siteWeb.set(siteWeb);
        this.requestStatus.set(requestStatus);
    }

    // Getters pour les propriétés
    public IntegerProperty idProperty() { return id; }
    public StringProperty nomProperty() { return nom; }
    public StringProperty descriptionProperty() { return description; }
    public StringProperty logoUrlProperty() { return logoUrl; }
    public StringProperty siteWebProperty() { return siteWeb; }
    public StringProperty requestStatusProperty() { return requestStatus; }

    // Getters pour les valeurs
    public int getId() { return id.get(); }
    public String getNom() { return nom.get(); }
    public String getDescription() { return description.get(); }
    public String getLogoUrl() { return logoUrl.get(); }
    public String getSiteWeb() { return siteWeb.get(); }
    public String getRequestStatus() { return requestStatus.get(); }

    // Setters
    public void setId(int id) { this.id.set(id); }
    public void setNom(String nom) { this.nom.set(nom); }
    public void setDescription(String description) { this.description.set(description); }
    public void setLogoUrl(String logoUrl) { this.logoUrl.set(logoUrl); }
    public void setSiteWeb(String siteWeb) { this.siteWeb.set(siteWeb); }
    public void setRequestStatus(String requestStatus) { this.requestStatus.set(requestStatus); }
}