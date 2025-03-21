package com.example.sportmarket;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class sidebar {
    @FXML
    private Button actualitesButton; // Bouton pour les actualités
    @FXML
    private Button homeButton;
    @FXML
    private VBox partenairesContainer; // Conteneur pour afficher les partenaires
    @FXML
    private Button marketButton; // Ajoutez cette ligne pour référencer le bouton Market
    @FXML
    private Button messagesButton; // Ajoutez cette ligne pour référencer le bouton Messages
    @FXML
    private void gotoMessages() {
        try {
            // Charger community-view.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/community-view.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène
            Stage stage = new Stage();
            stage.setTitle("SportX - Messages");
            stage.setScene(new Scene(root, 800, 600)); // Ajustez la taille selon vos besoins
            stage.show();

            // Fermer la fenêtre actuelle (optionnel)
            ((Stage) messagesButton.getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void gotoChatbot() {
        try {
            // Charger chatboot_interface.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/chatboot_interface.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène
            Stage stage = new Stage();
            stage.setTitle("SportX - Chatbot");
            stage.setScene(new Scene(root, 800, 600)); // Ajustez la taille selon vos besoins
            stage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void goToHome() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/hello-view.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) homeButton.getScene().getWindow();
            stage.setScene(new Scene(root, 1100, 700));
            stage.setTitle("SportX - Accueil");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void gotoMarket() {
        try {
            // Charger store-view.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/sportmarket/store-view.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène
            Stage stage = new Stage();
            stage.setTitle("SportX - Market");
            stage.setScene(new Scene(root, 800, 600)); // Ajustez la taille selon vos besoins
            stage.show();

            // Fermer la fenêtre actuelle (optionnel)
            ((Stage) marketButton.getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void gotoActualite() {
        try {
            // Charger MainScreen.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainScreen.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène
            Stage stage = new Stage();
            stage.setTitle("SportX - Actualités");
            stage.setScene(new Scene(root, 800, 600)); // Ajustez la taille selon vos besoins
            stage.show();

            // Fermer la fenêtre actuelle (optionnel)
            ((Stage) actualitesButton.getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
