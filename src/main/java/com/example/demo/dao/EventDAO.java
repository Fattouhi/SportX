package com.example.demo.dao;

import com.example.demo.model.EventRequest;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EventDAO {
    private static final String SELECT_ALL_EVENTS = "SELECT * FROM events";
    private static final String SELECT_EVENTS_BY_STATUS = "SELECT * FROM events WHERE status = 'pending'";
    private static final String SELECT_EVENTS_BY_STATUS_FILTER = "SELECT * FROM events WHERE LOWER(status) = ?";

    public List<EventRequest> getAllEvents() {
        List<EventRequest> events = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_ALL_EVENTS)) {

            while (rs.next()) {
                EventRequest event = mapResultSetToEventRequest(rs);
                events.add(event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return events;
    }

    public List<EventRequest> getPendingEvents() {
        List<EventRequest> events = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_EVENTS_BY_STATUS)) {

            while (rs.next()) {
                EventRequest event = mapResultSetToEventRequest(rs);
                events.add(event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return events;
    }

    public List<EventRequest> getEventsByStatus(String status) {
        List<EventRequest> events = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_EVENTS_BY_STATUS_FILTER)) {

            stmt.setString(1, status.toLowerCase());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    EventRequest event = mapResultSetToEventRequest(rs);
                    events.add(event);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return events;
    }

    public boolean updateEventStatus(String title, String status) {
        String query = "UPDATE events SET status = ? WHERE title = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, status);
            stmt.setString(2, title);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static int getEventCount() {
        String query = "SELECT COUNT(*) AS total FROM events";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private EventRequest mapResultSetToEventRequest(ResultSet rs) throws SQLException {
        return new EventRequest(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("sport"),
                rs.getString("location"),
                rs.getTimestamp("event_date").toLocalDateTime().toLocalDate(),
                rs.getString("description"),
                rs.getString("status")
        );
    }
}