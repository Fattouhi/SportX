package com.example.sportx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class NavbarController {
    @FXML
    private VBox profileDropdown;

    @FXML
    private void toggleDropdown() {
        profileDropdown.setVisible(!profileDropdown.isVisible());
    }

    @FXML
    private void openCart() throws IOException {
        // Load the shopping cart view
        FXMLLoader loader = new FXMLLoader(getClass().getResource("shopping-cart-view.fxml"));
        Parent root = loader.load();

        // Create a new stage for the shopping cart view
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Shopping Cart");
        stage.show();
    }
}