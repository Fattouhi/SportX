package com.example.appli.dao;

import com.example.appli.database.DatabaseConnection;
import com.example.appli.model.Participant;

import java.sql.*;
import java.time.LocalDate;

public class ParticipantDAO {

    public int addParticipant(Participant participant) throws SQLException {
        String query = "INSERT INTO participants (first_name, last_name, gender, birth_date, email, phone_number) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, participant.getFirstName());
            stmt.setString(2, participant.getLastName());
            stmt.setString(3, participant.getGender());
            stmt.setDate(4, Date.valueOf(participant.getBirthDate()));
            stmt.setString(5, participant.getEmail());
            stmt.setString(6, participant.getPhoneNumber());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1); // Retourne l'ID généré
                }
            }
        }
        return -1;
    }
}