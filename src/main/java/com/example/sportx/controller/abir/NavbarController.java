package com.example.sportx.controller.abir;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;

public class NavbarController {

    @FXML
    private TextField searchBar;

    @FXML
    private void handleMessagesButtonClick(ActionEvent event) {
        try {
            SceneManager.switchToScene("MyGroups.fxml", null);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load the chat view.");
        }
    }

    @FXML
    private void handleNotificationButtonClick(ActionEvent event) {
        try {
            SceneManager.switchToScene("NotificationView.fxml", null);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load the notification view.");
        }
    }



    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}