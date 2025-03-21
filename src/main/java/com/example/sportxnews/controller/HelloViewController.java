package com.example.sportxnews.controller;

import com.example.sportxnews.models.Partenaire;
import com.example.sportxnews.dao.PartenaireDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class HelloViewController {

    @FXML
    private Button actualitesButton; // Bouton pour les actualités

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
    private Button chatbotButton; // Ajoutez cette ligne pour référencer le bouton chatbot

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

            // Fermer la fenêtre actuelle (optionnel)
            ((Stage) chatbotButton.getScene().getWindow()).close();
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
    private void initialize() {
        // Ajouter un gestionnaire d'événements pour le bouton des actualités
        if (actualitesButton != null) {
            actualitesButton.setOnAction(event -> gotoActualite());
        } else {
            System.out.println("actualitesButton is null in initialize()");
        }

        // Ajouter un gestionnaire d'événements pour le bouton Market
        if (marketButton != null) {
            marketButton.setOnAction(event -> gotoMarket());
        } else {
            System.out.println("marketButton is null in initialize()");
        }

        // Ajouter un gestionnaire d'événements pour le bouton Messages
        if (messagesButton != null) {
            messagesButton.setOnAction(event -> gotoMessages());
        } else {
            System.out.println("messagesButton is null in initialize()");
        }

        // Ajouter un gestionnaire d'événements pour le bouton Chatbot
        if (chatbotButton != null) {
            chatbotButton.setOnAction(event -> gotoChatbot());
        } else {
            System.out.println("chatbotButton is null in initialize()");
        }
    }
    /**
     * Charge les partenaires depuis la base de données et les affiche dans le VBox.
     */
    private void loadPartenaires() {
        try {
            // Établir la connexion à la base de données
            Connection connection = DriverManager.getConnection("jdbc:mysql://mysql-196aef4e-esprim-b42e.b.aivencloud.com:21836/defaultdb", "avnadmin", "AVNS_DIenDKhRpZUhC8F0A1M");
            PartenaireDAO partenaireDAO = new PartenaireDAO(connection);

            // Récupérer tous les partenaires
            List<Partenaire> partenaires = partenaireDAO.getAllPartenaires();

            // Afficher les partenaires dans le VBox
            for (Partenaire partenaire : partenaires) {
                // Créer un Label pour chaque partenaire
                Label partenaireLabel = new Label(partenaire.getNom() + " - " + partenaire.getDescription());
                partenaireLabel.setStyle("-fx-font-size: 14px; -fx-padding: 5;");

                // Ajouter le Label au conteneur
                partenairesContainer.getChildren().add(partenaireLabel);
            }

            // Fermer la connexion à la base de données
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            // Afficher un message d'erreur en cas de problème
            Label errorLabel = new Label("Erreur lors du chargement des partenaires.");
            errorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
//            partenairesContainer.getChildren().add(errorLabel);
        }
    }

    /**
     * Ouvre l'écran principal (MainScreen).
     */
    private void openMainScreen() {
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
        } catch (Exception e) {
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