package com.example.appli.controller;

import com.example.appli.dao.EventDAO;
import com.example.appli.model.Event;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ListEventController {

    @FXML private TreeTableView<Event> eventTable;
    @FXML private TreeTableColumn<Event, String> titleColumn;
    @FXML private TreeTableColumn<Event, String> sportColumn;
    @FXML private TreeTableColumn<Event, String> locationColumn;
    @FXML private TreeTableColumn<Event, String> dateColumn;
    @FXML private Button backButton;
    @FXML private Button refreshButton; // Nouveau bouton

    private List<Event> events;
    private EventDAO eventDAO = new EventDAO(); // Pour recharger les événements

    @FXML
    private void initialize() {
        if (eventTable == null) {
            System.out.println("Erreur : eventTable est null dans ListEventController ! Vérifiez fx:id dans ListEvent.fxml.");
            return;
        }

        // Configuration des colonnes
        titleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getTitle()));
        sportColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getSport()));
        locationColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getLocation()));
        dateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getValue().getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        ));

        // Ajout des événements
        if (events != null) {
            TreeItem<Event> root = new TreeItem<>(new Event("Événements", "", "", null, "", "", ""));
            for (Event event : events) {
                root.getChildren().add(new TreeItem<>(event));
            }
            eventTable.setRoot(root);
            eventTable.setShowRoot(false);
        }
    }

    public void setEvents(List<Event> events) {
        this.events = events;
        if (eventTable != null) {
            TreeItem<Event> root = new TreeItem<>(new Event("Événements", "", "", null, "", "", ""));
            for (Event event : events) {
                root.getChildren().add(new TreeItem<>(event));
            }
            eventTable.setRoot(root);
            eventTable.setShowRoot(false);
        }
    }

    @FXML
    private void handleBack() {
        try {
            java.net.URL url = getClass().getResource("/com/example/appli/fxml/EvenementInterface.fxml");
            if (url == null) {
                showAlert("Erreur", "Le fichier EvenementInterface.fxml est introuvable ! Vérifiez son emplacement dans src/main/resources/com/example/appli/fxml/");
                return;
            }
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(new Scene(root, 600, 600));
            stage.setTitle("Calendrier Événements Sportifs");
            stage.setX(0);
            stage.setY(0);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Erreur", "Échec de la navigation vers le calendrier des événements : " + e.getMessage());
        }
    }

    @FXML
    private void handleRefresh() {
        try {
            // Recharger les événements depuis la base de données
            events = eventDAO.getAllEvents();
            TreeItem<Event> root = new TreeItem<>(new Event("Événements", "", "", null, "", "", ""));
            for (Event event : events) {
                root.getChildren().add(new TreeItem<>(event));
            }
            eventTable.setRoot(root);
            eventTable.setShowRoot(false);
            showAlert("Succès", "La liste des événements a été rafraîchie !");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Erreur", "Échec du rafraîchissement des événements : " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(title.equals("Erreur") ? Alert.AlertType.ERROR : Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}