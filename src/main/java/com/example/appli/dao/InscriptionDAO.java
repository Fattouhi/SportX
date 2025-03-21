package com.example.appli.dao;

import com.example.appli.database.DatabaseConnection;
import com.example.appli.model.Event;
import com.example.appli.model.Inscription;
import com.example.appli.model.Participant;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InscriptionDAO {

    private final EventDAO eventDAO = new EventDAO();
    private final ParticipantDAO participantDAO = new ParticipantDAO();

    public void addInscription(Inscription inscription) throws SQLException {
        String query = "INSERT INTO inscriptions (participant_id, event_id) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            int participantId = participantDAO.addParticipant(inscription.getParticipant());
            stmt.setInt(1, participantId);
            stmt.setInt(2, getEventId(inscription.getEvent()));
            stmt.executeUpdate();
        }
    }

    public List<Inscription> getAllInscriptions() throws SQLException {
        List<Inscription> inscriptions = new ArrayList<>();
        String query = "SELECT * FROM inscriptions i JOIN participants p ON i.participant_id = p.id JOIN events e ON i.event_id = e.id";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Participant participant = new Participant(
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("gender"),
                        rs.getDate("birth_date").toLocalDate(),
                        rs.getString("email"),
                        rs.getString("phone_number")
                );
                Event event = new Event(
                        rs.getString("title"),
                        rs.getString("sport"),
                        rs.getString("location"),
                        rs.getDate("event_date").toLocalDate(),
                        rs.getString("description"),
                        rs.getString("status"),
                        rs.getString("image_url")
                );
                inscriptions.add(new Inscription(participant, event));
            }
        }
        return inscriptions;
    }

    private int getEventId(Event event) throws SQLException {
        String query = "SELECT id FROM events WHERE title = ? AND event_date = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, event.getTitle());
            stmt.setDate(2, Date.valueOf(event.getDate()));
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        }
        return -1;
    }
}