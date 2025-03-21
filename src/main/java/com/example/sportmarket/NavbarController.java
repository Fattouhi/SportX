package com.example.sportmarket;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class NavbarController {

    @FXML
    private void handleNotificationButtonClick() {
        // Handle notification button click
        System.out.println("Notification button clicked");
    }

    @FXML
    private void handleMessagesButtonClick() {
        // Handle messages button click
        System.out.println("Messages button clicked");
    }

    @FXML
    private void openCart() throws IOException {
        // Load the shopping cart view
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo1/shopping-cart-view.fxml"));
        Parent root = loader.load();

        // Create a new stage for the shopping cart view
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Shopping Cart");
        stage.show();
    }

    private void loadFXML(String fxmlPath, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = loader.load();

        // Create a new stage for the new view
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle(title);
        stage.show();
    }
}