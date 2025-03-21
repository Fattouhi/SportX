package com.example.demo.dao;

import com.example.demo.model.EventRequest;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EventDAO {
    private static final String SELECT_ALL_PENDING_EVENTS = "SELECT * FROM event_requests WHERE status = 'pending'";
    private static final String SELECT_ALL_EVENTS = "SELECT * FROM event_requests";

    public List<EventRequest> getAllEventRequests() {
        List<EventRequest> eventRequests = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_ALL_EVENTS)) {

            while (rs.next()) {
                String eventName = rs.getString("event_name");
                String description = rs.getString("description");
                //LocalDate eventDate = rs.getDate("event_date").toLocalDate();
                LocalDate eventDate = rs.getTimestamp("event_date").toLocalDateTime().toLocalDate();
                String organizer = rs.getString("organizer");
                String status = rs.getString("status");
                String address = rs.getString("address");

                EventRequest event = new EventRequest(eventName, description, eventDate, organizer, status, address);
                eventRequests.add(event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return eventRequests;
    }

    public List<EventRequest> getEventRequestsByStatus(String status) {
        List<EventRequest> eventRequests = new ArrayList<>();
        String query = "SELECT * FROM event_requests WHERE LOWER(status) = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, status.toLowerCase());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String eventName = rs.getString("event_name");
                String description = rs.getString("description");
                LocalDate eventDate = rs.getDate("event_date").toLocalDate();
                String organizer = rs.getString("organizer");
                String eventStatus = rs.getString("status");
                String address = rs.getString("address");

                EventRequest event = new EventRequest(eventName, description, eventDate, organizer, eventStatus, address);
                eventRequests.add(event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return eventRequests;
    }

    public boolean updateEventStatus(String eventName, String status) {
        String query = "UPDATE event_requests SET status = ? WHERE event_name = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, status);
            stmt.setString(2, eventName);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Nouvelle méthode pour compter le nombre total d'événements
    public static int getEventCount() {
        String query = "SELECT COUNT(*) AS total FROM event_requests";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0; // Retourne 0 en cas d'erreur
    }
}