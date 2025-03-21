package com.example.demo.controllers;

import com.example.demo.AdminApplication;
import com.example.demo.dao.DatabaseConnection;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import javafx.scene.input.MouseEvent;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Objects;

public class DashboardController {

    public Button btnContentManagement;
    public Button btnUserManagement;
    public Button btnMarketplaceManagement;
    public Button btnStatistics;
    public Button btnGroupManagement;
    public Button btnEventManagement;
    public StackPane mainContent;
    public ImageView profileIcon;

    @FXML
    private VBox profileMenu;
    @FXML
    private StackPane contentPane; // Zone où charger les interfaces

    @FXML
    public void initialize() {
        System.out.println("✅ Interface graphique chargée !");

        // Test de la connexion à la base de données
        try {
            if (DatabaseConnection.getConnection() != null) {
                System.out.println("✅ Connexion BD opérationnelle !");
            } else {
                System.out.println("⚠️ Impossible de se connecter à la BD !");
            }
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la connexion à la BD : " + e.getMessage());
            e.printStackTrace(); // Affiche les détails pour le debug
        }

    }


    // Méthode générique pour charger du contenu
    private void loadPage(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            AnchorPane newPage = loader.load();
            contentPane.getChildren().setAll(newPage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void toggleProfileMenu(MouseEvent event) {
        if (!profileMenu.isVisible()) {
            // Positionner le menu sous l'icône de profil
            profileMenu.setLayoutX(event.getSceneX() - profileMenu.getPrefWidth() / 2);
            profileMenu.setLayoutY(event.getSceneY() + 30); // 34 pour le placer juste en dessous

            profileMenu.setVisible(!profileMenu.isVisible());
            profileMenu.setManaged(profileMenu.isVisible());

            // Animation d'apparition
            FadeTransition fadeIn = new FadeTransition(Duration.millis(200), profileMenu);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.play();
        } else {
            // Animation de disparition
            FadeTransition fadeOut = new FadeTransition(Duration.millis(200), profileMenu);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
            fadeOut.setOnFinished(_ -> {
                profileMenu.setVisible(false);
                profileMenu.setManaged(false);
            });
            fadeOut.play();
        }
    }


    @FXML
    private void goToUserManagement() {
        AdminApplication.changeScene("/com/example/demo/View/user_management.fxml");
    }

    @FXML
    private void goToGroupManagement() {
        AdminApplication.changeScene("/com/example/demo/View/group_requests.fxml");
    }

    @FXML
    private void goToEventManagement() {
        AdminApplication.changeScene("/com/example/demo/View/event_management.fxml");
    }

    @FXML
    private void goToMarketplaceManagement() {
        AdminApplication.changeScene("/com/example/demo/View/marketplace_management.fxml");
    }

    @FXML
    private void goToReportManagement() {
        AdminApplication.changeScene("/com/example/demo/View/contenu_management.fxml");
    }

    @FXML
    private void goToStatistics() {
        AdminApplication.changeScene("/com/example/demo/View/statistics.fxml");
    }

    @FXML
    private void goToPartenairesManagement() {
        AdminApplication.changeScene("/com/example/demo/View/partenaires-management.fxml");
    }

    @FXML
    private void openWeather() {
        try {
            // Lien vers AccuWeather pour Tunis, Tunisie, prévisions du 24/02/2025
            String weatherUrl = "https://www.meteo.tn/";
            Desktop.getDesktop().browse(new URI(weatherUrl));
        } catch (URISyntaxException e) {
            e.printStackTrace();
            showAlert("Erreur", "URL de la météo invalide.");
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Erreur", "Impossible d’ouvrir le lien météo : " + e.getMessage());
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, content, ButtonType.OK);
        alert.setTitle(title);
        alert.showAndWait();
    }

    @FXML
    private void openCalendar() {
        try {
            // Lien vers Google Calendar pour les événements à Tunis (exemple)
            String calendarUrl = "https://calendar.google.com/";
            Desktop.getDesktop().browse(new URI(calendarUrl));
        } catch (URISyntaxException e) {
            e.printStackTrace();
            showAlert("Erreur", "URL du calendrier invalide.");
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Erreur", "Impossible d’ouvrir le lien du calendrier : " + e.getMessage());
        }
    }



}