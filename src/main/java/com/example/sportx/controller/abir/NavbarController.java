package com.example.sportx.controller.abir;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
    @FXML
    private void openCart() throws IOException {
        // Load the shopping cart view
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/sportmarket/shopping-cart-view.fxml"));
        Parent root = loader.load();

        // Create a new stage for the shopping cart view
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Shopping Cart");
        stage.show();
    }
}