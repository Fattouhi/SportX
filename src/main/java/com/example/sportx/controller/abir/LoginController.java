package com.example.sportx.controller.abir;

import com.example.sportx.models.abir.User;
import com.example.sportx.services.abir.UserService;
import com.example.sportx.utils.abir.ApplicationContext;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {
    @FXML
    private TextField emailField; // Email input field
    @FXML
    private PasswordField passwordField; // Password input field
    @FXML
    private Label errorLabel; // Label to display error messages

    private UserService userService = new UserService(); // Service to handle user authentication

    @FXML
    public void handleLogin(ActionEvent event) {
        String email = emailField.getText(); // Use email instead of username
        String password = passwordField.getText();

        try {
            // Authenticate the user
            User user = userService.authenticateUser(email, password);
            if (user != null) {
                // Store the logged-in user in ApplicationContext
                ApplicationContext.setCurrentUser(user);

                // Navigate to the CommunityView after successful login
                navigateToCommunityView();
            } else {
                showError("Invalid email or password.");
            }
        } catch (IllegalArgumentException | SQLException e) {
            showError("Login failed. Please check your credentials.");
        }
    }

    /**
     * Navigate to the CommunityView after successful login.
     */
    private void navigateToCommunityView() {
        try {
            SceneManager.switchToScene("community-view.fxml", null);
        } catch (IOException | SQLException e) {
            showError("Failed to load the application.");
            e.printStackTrace();
        }
    }


    /**
     * Display an error message on the login interface.
     *
     * @param message The error message to display.
     */
    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }
}