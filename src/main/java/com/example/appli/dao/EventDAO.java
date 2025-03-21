package com.example.appli.dao;

import com.example.appli.database.DatabaseConnection;
import com.example.appli.model.Event;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EventDAO {

    public void addEvent(Event event) throws SQLException {
        String query = "INSERT INTO events (title, sport, location, event_date, description, status, image_url) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, event.getTitle());
            stmt.setString(2, event.getSport());
            stmt.setString(3, event.getLocation());
            stmt.setDate(4, Date.valueOf(event.getDate()));
            stmt.setString(5, event.getDescription());
            stmt.setString(6, event.getStatus()); // Add status
            stmt.setString(7, event.getImageUrl());
            stmt.executeUpdate();
        }
    }

    public List<Event> getAllEvents() throws SQLException {
        List<Event> events = new ArrayList<>();
        String query = "SELECT * FROM events";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Event event = new Event(
                        rs.getString("title"),
                        rs.getString("sport"),
                        rs.getString("location"),
                        rs.getDate("event_date").toLocalDate(),
                        rs.getString("description"),
                        rs.getString("status"),
                        rs.getString("image_url") // Récupération de l'URL de l'image
                );
                events.add(event);
            }
        }
        return events;
    }

    public Event getEventById(int id) throws SQLException {
        String query = "SELECT * FROM events WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Event(
                            rs.getString("title"),
                            rs.getString("sport"),
                            rs.getString("location"),
                            rs.getDate("event_date").toLocalDate(),
                            rs.getString("description"),
                            rs.getString("status"),
                            rs.getString("image_url") // Récupération de l'URL de l'image
                    );
                }
            }
        }
        return null;
    }
}