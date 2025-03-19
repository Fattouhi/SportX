package com.example.sportx.controller.abir;

import com.example.sportx.services.abir.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.TilePane;

import java.sql.SQLException;

public class UserController {

    private final UserService userService = new UserService();

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField roleField;

    @FXML
    private TilePane communityContainer;

    public void handleRegister(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String email = emailField.getText();

        try {
            userService.registerUser(username, email, password);
            communityContainer.getChildren().add(new Label("User registered successfully!"));
        } catch (SQLException e) {
            showErrorAlert("Error", "Registration failed", "Please try again later.");
        }
    }

    public void handleLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        try {
            userService.authenticateUser(username, password);
            communityContainer.getChildren().add(new Label("Login successful!"));
        } catch (IllegalArgumentException | SQLException e) {
            showErrorAlert("Error", "Login failed", "Invalid username or password.");
        }
    }

    private void showErrorAlert(String title, String header, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
