package com.example.sportx.controller.abir;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class NavbarController {

    @FXML
    private VBox profileDropdown; // Correspond à l'ID dans navbar.fxml

    @FXML
    private void openProfile() throws IOException {
        loadFXML("/com/example/demo1/historique.fxml", "Profile");
    }

    @FXML
    private void openSettings() throws IOException {
        loadFXML("/com/example/demo1/ParametreProfile.fxml", "Settings");
    }

    @FXML
    private void openLogout() throws IOException {
        // Fermer la fenêtre actuelle
        Stage currentStage = (Stage) profileDropdown.getScene().getWindow();
        currentStage.close();

        // Charger la nouvelle fenêtre (acc.fxml)
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo1/Acc.fxml"));
        Parent root = loader.load();

        // Créer une nouvelle scène et l'afficher
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Accueil");
        stage.show();
    }

    @FXML
    private void handleNotificationButtonClick() {
        System.out.println("Notification button clicked");
    }

    @FXML
    private void handleMessagesButtonClick() {
        System.out.println("Messages button clicked");
    }

    @FXML
    private void openCart() throws IOException {
        loadFXML("/com/example/demo1/shopping-cart-view.fxml", "Shopping Cart");
    }

    private void loadFXML(String fxmlPath, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle(title);
        stage.show();
    }
}