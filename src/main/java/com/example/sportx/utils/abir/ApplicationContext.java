package com.example.sportx.utils.abir;

import com.example.model.User;

public class ApplicationContext {
    private static User currentUser; // Store the current logged-in user

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    }
}