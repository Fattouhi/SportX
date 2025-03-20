package com.example.demo.dao;

import com.example.demo.model.Users;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsersDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/sportx_bd";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static final String UPDATE_PASSWORD = "UPDATE Users SET Password = NULL WHERE UserID = ?";

    // Récupérer tous les utilisateurs
    public static List<Users> getAllUsers() {
        List<Users> usersList = new ArrayList<>();
        String query = "SELECT UserID, Phone, FirstName, LastName, Email, Password, Contry FROM Users";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                usersList.add(new Users(
                        rs.getInt("UserID"),
                        rs.getInt("Phone"),
                        rs.getString("FirstName"),
                        rs.getString("LastName"),
                        rs.getString("Email"),
                        rs.getString("Password"),
                        rs.getInt("Contry")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usersList;
    }

    // Ajouter un utilisateur
    public static void addUser(Users user) {
        String query = "INSERT INTO Users (Phone, FirstName, LastName, Email, Password, Contry) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, user.getPhone());
            stmt.setString(2, user.getFirstName());
            stmt.setString(3, user.getLastName());
            stmt.setString(4, user.getEmail());
            stmt.setString(5, user.getPassword());
            stmt.setInt(6, user.getCountry());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("✅ Utilisateur ajouté avec succès !");
            } else {
                System.out.println("⚠️ Échec de l'ajout de l'utilisateur.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Mettre à jour un utilisateur
    public static void updateUser(Users user) {
        String query = "UPDATE Users SET Phone = ?, FirstName = ?, LastName = ?, Password = ? WHERE Email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, user.getPhone());
            stmt.setString(2, user.getFirstName());
            stmt.setString(3, user.getLastName());
            stmt.setString(4, user.getPassword());
            stmt.setString(5, user.getEmail());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Supprimer un utilisateur (optionnel, mais pas recommandé si l'utilisateur est banni)
    public static void deleteUser(int UserID) {
        String query = "DELETE FROM Users WHERE UserID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, UserID);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("✅ Utilisateur supprimé avec succès !");
            } else {
                System.out.println("⚠️ Échec de la suppression de l'utilisateur.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Vérifier si un utilisateur est banni (utilise BannedUsersDAO)
    public static boolean isUserBanned(int userId) throws SQLException {
        BannedUsersDAO bannedUsersDAO = new BannedUsersDAO(DatabaseConnection.getConnection());
        return bannedUsersDAO.isUserBanned(userId);
    }

    // Récupérer la raison du bannissement (optionnel)
    public static String getBanReason(int userId) throws SQLException {
        BannedUsersDAO bannedUsersDAO = new BannedUsersDAO(DatabaseConnection.getConnection());
        return bannedUsersDAO.getBanReason(userId);
    }

    // Réinitialiser/supprimer le mot de passe d’un utilisateur banni
    public static void resetPasswordOnBan(int userId) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_PASSWORD)) {
            stmt.setInt(1, userId);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("✅ Mot de passe supprimé pour l'utilisateur banni (ID: " + userId + ").");
            } else {
                System.out.println("⚠️ Échec de la suppression du mot de passe pour l'utilisateur banni.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}