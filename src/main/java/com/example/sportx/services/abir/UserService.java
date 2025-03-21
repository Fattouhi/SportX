package com.example.sportx.services.abir;

import com.example.UserDao.UserDAO;
import com.example.model.User;

import java.sql.SQLException;

public class UserService {
    private static final UserDAO userDAO= new UserDAO();


    public static String getUsernameById(int userId) throws SQLException {
        return userDAO.getUsernameById(userId);
    }
    public int getUserIdByUsername(String username) throws SQLException {
        return userDAO.getUserIdByUsername(username);
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
        if (user != null && ((com.example.model.User) user).getPassword().equals(password)) { // Replace with password hashing in production
            return user;
        }
        return null;
    }
}