package com.example.sportx.services.abir;

import com.example.sportx.DAO.abir.UserDAO;
import com.example.sportx.models.abir.User;

import java.sql.SQLException;

public class UserService {
    private static final UserDAO userDAO = new UserDAO();


    public static String getUsernameById(int userId) throws SQLException {
        return userDAO.getUsernameById(userId);
    }
    public int getUserIdByUsername(String username) throws SQLException {
        return userDAO.getUserIdByUsername(username);
    }
    public void registerUser(String username, String email, String password) throws SQLException {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username is required.");
        }
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email is required.");
        }
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password is required.");
        }

        // Check if the email is already registered
        if (userDAO.getUserByEmail(email) != null) {
            throw new IllegalArgumentException("Email already exists.");
        }
        // Add the user to the database
        userDAO.addUser(username, email, password);
    }
    /**
     * Authenticate a user using their email and password.
     *
     * @param email    The user's email.
     * @param password The user's password.
     * @return The authenticated user, or null if authentication fails.
     * @throws SQLException If a database error occurs.
     */

    public User authenticateUser(String email, String password) throws SQLException {
        User user = userDAO.getUserByEmail(email);
        if (user != null && user.getPassword().equals(password)) { // Replace with password hashing in production
            return user;
        }
        return null;
    }
}