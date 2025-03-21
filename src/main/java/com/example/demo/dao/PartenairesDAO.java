package com.example.demo.dao;

import com.example.demo.model.Partenaires;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PartenairesDAO {

    private static final String SELECT_ALL_PARTENAIRES = "SELECT * FROM partenaires";
    private static final String UPDATE_PARTENAIRE_REQUEST_STATUS = "UPDATE partenaires SET request_status = ? WHERE id = ?";

    public List<Partenaires> getAllPartenaireRequests() {
        List<Partenaires> partenaireRequests = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_ALL_PARTENAIRES)) {

            while (rs.next()) {
                Partenaires partenaire = new Partenaires(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("description"),
                        rs.getString("logo_url"),
                        rs.getString("site_web"),
                        rs.getString("request_status")
                );
                partenaireRequests.add(partenaire);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return partenaireRequests;
    }

    public boolean updatePartenaireRequestStatus(int partenaireId, String requestStatus) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_PARTENAIRE_REQUEST_STATUS)) {
            stmt.setString(1, requestStatus);
            stmt.setInt(2, partenaireId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isPartenaireApproved(int partenaireId) {
        String query = "SELECT request_status FROM partenaires WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, partenaireId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return "approved".equalsIgnoreCase(rs.getString("request_status"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}