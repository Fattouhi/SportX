package com.example.appli.controller;

import com.example.appli.dao.EventDAO;
import com.example.appli.model.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class EvenementInterfaceController {

    @FXML private TextField searchField;
    @FXML private SplitMenuButton sportMenu;
    @FXML private SplitMenuButton lieuMenu;
    @FXML private SplitMenuButton monEspaceMenu;
    @FXML private DatePicker datePicker;
    @FXML private Button submit112;
    @FXML private Button groupsButton;
    @FXML private Button marketplaceButton;
    @FXML private Button actualitesButton;
    @FXML private Button Authentfier1;
    @FXML private Button submitButton;
    @FXML private GridPane eventGrid;

    private EventDAO eventDAO = new EventDAO();
    private List<Event> events;

    @FXML
    private void initialize() {
        try {
            events = eventDAO.getAllEvents();
            populateEventGrid(events);

            sportMenu.getItems().clear();
            lieuMenu.getItems().clear();
            events.forEach(event -> {
                if (!sportMenu.getItems().stream().anyMatch(item -> item.getText().equals(event.getSport())))
                    sportMenu.getItems().add(new MenuItem(event.getSport()));
                if (!lieuMenu.getItems().stream().anyMatch(item -> item.getText().equals(event.getLocation())))
                    lieuMenu.getItems().add(new MenuItem(event.getLocation()));
            });
            sportMenu.setText("Sport");
            lieuMenu.setText("Lieu");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Erreur", "Échec du chargement des événements depuis la base de données.");
        }
    }

    private void populateEventGrid(List<Event> eventList) {
        eventGrid.getChildren().clear();
        int row = 0;
        int col = 0;

        for (Event event : eventList) {
            AnchorPane eventPane = new AnchorPane();
            eventPane.setPrefHeight(140.0);
            eventPane.setPrefWidth(200.0);
            eventPane.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 15; -fx-border-radius: 15;");

            ImageView imageView;
            try {
                imageView = new ImageView(new Image(event.getImageUrl()));
            } catch (Exception e) {
                imageView = new ImageView(new Image("file:src/main/resources/com/example/appli/images/default.png"));
            }
            imageView.setFitHeight(110.0);
            imageView.setFitWidth(200.0);
            imageView.setPickOnBounds(true);
            imageView.setOnMouseClicked(e -> handleEventClick(event));

            Text eventText = new Text(event.getTitle());
            eventText.setLayoutX(10.0);
            eventText.setLayoutY(131.0);

            eventPane.getChildren().addAll(imageView, eventText);
            eventGrid.add(eventPane, col, row);
            col++;
            if (col > 2) {
                col = 0;
                row++;
            }
        }
    }

    @FXML
    private void handleSearch() {
        String query = searchField.getText().toLowerCase();
        LocalDate selectedDate = datePicker.getValue();
        String selectedSport = sportMenu.getText().equals("Sport") ? null : sportMenu.getText();
        String selectedLocation = lieuMenu.getText().equals("Lieu") ? null : lieuMenu.getText();

        List<Event> filteredEvents = events.stream()
                .filter(event -> event.getTitle().toLowerCase().contains(query) || query.isEmpty())
                .filter(event -> selectedDate == null || event.getDate().equals(selectedDate))
                .filter(event -> selectedSport == null || event.getSport().equals(selectedSport))
                .filter(event -> selectedLocation == null || event.getLocation().equals(selectedLocation))
                .collect(Collectors.toList());

        populateEventGrid(filteredEvents);
    }

    @FXML
    private void handleAddEvent() {
        try {
            java.net.URL url = getClass().getResource("/com/example/appli/fxml/AddEvent.fxml");
            if (url == null) {
                showAlert("Erreur", "Le fichier AddEvent.fxml est introuvable ! Vérifiez le chemin.");
                return;
            }
            System.out.println("Chargement de AddEvent.fxml depuis : " + url); // Débogage
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root, 400, 600)); // Taille ajustée pour le formulaire
            stage.setTitle("Ajouter un événement");
            stage.setOnHidden(e -> refreshEvents());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Erreur", "Échec de l'ouverture de la fenêtre d'ajout d'événement : " + e.getMessage());
        }
    }

    @FXML
    private void handleShowEvents() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/appli/fxml/ListEvent.fxml"));
            Parent root = loader.load();
            ListEventController controller = loader.getController();
            if (controller != null) {
                controller.setEvents(eventDAO.getAllEvents());
            }
            Stage stage = (Stage) submit112.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            showAlert("Erreur", "Échec de l'ouverture de la liste des événements.");
        }
    }

    @FXML private void handleGroups() { System.out.println("Groups button clicked"); }
    @FXML private void handleMarketplace() { System.out.println("Marketplace button clicked"); }
    @FXML private void handleActualites() { System.out.println("Actualités button clicked"); }

    private void handleEventClick(Event event) {
        try {
            java.net.URL url = getClass().getResource("/com/example/appli/fxml/EventInformation.fxml");
            if (url == null) {
                showAlert("Erreur", "Le fichier EventInformation.fxml est introuvable ! Vérifiez le chemin.");
                return;
            }
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();
            EventInformationController controller = loader.getController();
            controller.setEvent(event);
            Stage stage = new Stage();
            stage.setScene(new Scene(root, 900, 600));
            stage.setTitle("Détails de l'événement : " + event.getTitle());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Erreur", "Échec de l'ouverture des détails de l'événement : " + e.getMessage());
        }
    }

    private void refreshEvents() {
        try {
            events = eventDAO.getAllEvents();
            populateEventGrid(events);
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Erreur", "Échec de la mise à jour des événements.");
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