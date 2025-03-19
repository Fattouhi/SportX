package com.example.sportxnews.dao;

import com.example.sportxnews.models.Partenaire;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PartenaireDAO {
    private Connection connection;

    public PartenaireDAO(Connection connection) {
        this.connection = connection;
    }

    // Ajouter un partenaire
    public void addPartenaire(Partenaire partenaire) throws SQLException {
        String query = "INSERT INTO partenaires (nom, description, logo_url, site_web) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, partenaire.getNom());
            statement.setString(2, partenaire.getDescription());
            statement.setString(3, partenaire.getLogoUrl());
            statement.setString(4, partenaire.getSiteWeb());
            statement.executeUpdate();
        }
    }

    // Récupérer tous les partenaires
    public List<Partenaire> getAllPartenaires() throws SQLException {
        List<Partenaire> partenaires = new ArrayList<>();
        String query = "SELECT * FROM partenaires";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Partenaire partenaire = new Partenaire(
                        resultSet.getInt("id"),
                        resultSet.getString("nom"),
                        resultSet.getString("description"),
                        resultSet.getString("logo_url"),
                        resultSet.getString("site_web")
                );
                partenaires.add(partenaire);
            }
        }
        return partenaires;
    }

    // Mettre à jour un partenaire
    public void updatePartenaire(Partenaire partenaire) throws SQLException {
        String query = "UPDATE partenaires SET nom = ?, description = ?, logo_url = ?, site_web = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, partenaire.getNom());
            statement.setString(2, partenaire.getDescription());
            statement.setString(3, partenaire.getLogoUrl());
            statement.setString(4, partenaire.getSiteWeb());
            statement.setInt(5, partenaire.getId());
            statement.executeUpdate();
        }
    }

    // Supprimer un partenaire
    public void deletePartenaire(int id) throws SQLException {
        String query = "DELETE FROM partenaires WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }
}