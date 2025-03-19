package com.example.sportxnews.models;

public class Partenaire {
    private int id;
    private String nom;
    private String description;
    private String logoUrl;
    private String siteWeb;

    // Constructeurs, getters et setters
    public Partenaire() {}

    public Partenaire(int id, String nom, String description, String logoUrl, String siteWeb) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.logoUrl = logoUrl;
        this.siteWeb = siteWeb;
    }

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getLogoUrl() { return logoUrl; }
    public void setLogoUrl(String logoUrl) { this.logoUrl = logoUrl; }

    public String getSiteWeb() { return siteWeb; }
    public void setSiteWeb(String siteWeb) { this.siteWeb = siteWeb; }
}