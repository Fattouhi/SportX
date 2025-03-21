package com.example.appli.controller;

import com.example.appli.model.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class EventInformationController {

    @FXML private Text eventTitle;
    @FXML private Text eventDetails;
    @FXML private Button Authentfier;
    @FXML private TitledPane infoPane;
    @FXML private ImageView refrech;
    @FXML private Button submit11;

    private Event event;

    @FXML
    private void initialize() {
        Authentfier.setOnAction(e -> handleParticipation());
        submit11.setOnAction(e -> navigateToEvents());
        refrech.setOnMouseClicked(e -> refreshDetails());

        // Initialisation différée des détails jusqu'à ce que setEvent soit appelé
    }

    public void setEvent(Event event) {
        this.event = event;
        if (event != null && eventTitle != null && eventDetails != null) {
            updateEventDetails();
        }
    }

    private void updateEventDetails() {
        eventTitle.setText(event.getTitle());
        eventDetails.setText(String.format("Sport : %s\nLieu : %s\nDate : %s",
                event.getSport(), event.getLocation(),
                event.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
        Label detailsLabel = new Label(event.getDescription());
        detailsLabel.setWrapText(true);
        infoPane.setContent(detailsLabel);
    }

    @FXML
    private void handleParticipation() {
        try {
            java.net.URL url = getClass().getResource("/com/example/appli/fxml/PerticiperEvent.fxml");
            if (url == null) {
                showAlert("Erreur", "Le fichier PerticiperEvent.fxml est introuvable !");
                return;
            }
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();
            PerticiperEventController controller = loader.getController();
            controller.setEvent(event);
            Stage stage = new Stage();
            stage.setScene(new Scene(root, 600, 400)); // Taille ajustée
            stage.setTitle("Participer à " + event.getTitle());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Erreur", "Échec de l'ouverture du formulaire de participation : " + e.getMessage());
        }
    }

    private void navigateToEvents() {
        try {
            java.net.URL url = getClass().getResource("/com/example/appli/fxml/EvenementInterface.fxml");
            if (url == null) {
                showAlert("Erreur", "Le fichier EvenementInterface.fxml est introuvable !");
                return;
            }
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();
            Stage stage = (Stage) submit11.getScene().getWindow();
            stage.setScene(new Scene(root, 900, 600)); // Taille ajustée
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Erreur", "Échec de la navigation vers les événements : " + e.getMessage());
        }
    }

    private void refreshDetails() {
        if (event != null) {
            updateEventDetails();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}