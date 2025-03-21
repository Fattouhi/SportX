package com.example.demo.controllers;

import com.example.demo.dao.EventDAO;
import com.example.demo.model.EventRequest;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;

public class EventManagementController {

    @FXML private TableColumn<EventRequest, Number> colIndex;
    @FXML private TableView<EventRequest> eventRequestTable;
    @FXML private TableColumn<EventRequest, String> colEventName; // À renommer en colTitle
    @FXML private TableColumn<EventRequest, String> colSport; // Nouvelle colonne pour sport
    @FXML private TableColumn<EventRequest, String> colLocation; // À renommer depuis addressField
    @FXML private TableColumn<EventRequest, String> colDate;
    @FXML private TableColumn<EventRequest, String> colDescription;
    @FXML private TableColumn<EventRequest, String> colStatus; // Nouvelle colonne pour status
    @FXML private TextArea eventDetails;
    @FXML private Button btnAccept;
    @FXML private Button btnReject;
    @FXML private TextField searchField;
    @FXML private ChoiceBox<String> filterChoiceBox;

    private final ObservableList<EventRequest> fullEventList = FXCollections.observableArrayList();
    private final ObservableList<EventRequest> allEvents = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Initialisation des colonnes
        colIndex.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(eventRequestTable.getItems().indexOf(cellData.getValue()) + 1));
        colEventName.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        colSport.setCellValueFactory(cellData -> cellData.getValue().sportProperty());
        colLocation.setCellValueFactory(cellData -> cellData.getValue().locationProperty());
        colDate.setCellValueFactory(cellData -> cellData.getValue().eventDateProperty().asString());
        colDescription.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
        colStatus.setCellValueFactory(cellData -> cellData.getValue().statusProperty());

        // Chargement des données
        loadEvents();

        // Configuration du ChoiceBox pour le filtrage
        filterChoiceBox.getItems().setAll("Tous", "pending", "approved", "rejected");
        filterChoiceBox.setValue("Tous");

        // Listeners
        searchField.textProperty().addListener((observable, oldValue, newValue) -> searchEvent());
        filterChoiceBox.setOnAction(event -> filterEvents());
        eventRequestTable.setOnMouseClicked(this::displayEventDetails);
    }

    private void loadEvents() {
        EventDAO eventDAO = new EventDAO();
        List<EventRequest> events = eventDAO.getAllEvents();
        allEvents.setAll(events);
        eventRequestTable.setItems(allEvents);
        fullEventList.setAll(events);
    }

    private void displayEventDetails(MouseEvent event) {
        EventRequest selectedEvent = eventRequestTable.getSelectionModel().getSelectedItem();
        if (selectedEvent != null) {
            eventDetails.setText(
                    "ID: " + selectedEvent.getId() + "\n" +
                            "Titre: " + selectedEvent.getTitle() + "\n" +
                            "Sport: " + selectedEvent.getSport() + "\n" +
                            "Lieu: " + selectedEvent.getLocation() + "\n" +
                            "Date: " + selectedEvent.getEventDate() + "\n" +
                            "Description: " + selectedEvent.getDescription() + "\n" +
                            "Statut: " + selectedEvent.getStatus()
            );
        } else {
            eventDetails.clear();
        }
    }

    @FXML
    private void filterEvents() {
        String selectedFilter = filterChoiceBox.getValue();
        if (selectedFilter == null) {
            selectedFilter = "Tous";
        }

        EventDAO eventDAO = new EventDAO();
        List<EventRequest> filteredList;
        if (selectedFilter.equals("Tous")) {
            filteredList = eventDAO.getAllEvents();
        } else {
            filteredList = eventDAO.getEventsByStatus(selectedFilter.toLowerCase());
        }

        eventRequestTable.setItems(FXCollections.observableArrayList(filteredList));
        eventRequestTable.refresh();
    }

    @FXML
    private void acceptEvent() {
        EventRequest selectedEvent = eventRequestTable.getSelectionModel().getSelectedItem();
        if (selectedEvent != null) {
            EventDAO eventDAO = new EventDAO();
            boolean success = eventDAO.updateEventStatus(selectedEvent.getTitle(), "approved");
            if (success) {
                showAlert("Succès", "Événement accepté : " + selectedEvent.getTitle());
                loadEvents();
                eventDetails.clear();
            } else {
                showAlert("Erreur", "Échec de l'acceptation de l'événement : " + selectedEvent.getTitle());
            }
        } else {
            showAlert("Avertissement", "Veuillez sélectionner un événement à accepter.");
        }
    }

    @FXML
    private void rejectEvent() {
        EventRequest selectedEvent = eventRequestTable.getSelectionModel().getSelectedItem();
        if (selectedEvent != null) {
            EventDAO eventDAO = new EventDAO();
            boolean success = eventDAO.updateEventStatus(selectedEvent.getTitle(), "rejected");
            if (success) {
                showAlert("Succès", "Événement refusé : " + selectedEvent.getTitle());
                loadEvents();
                eventDetails.clear();
            } else {
                showAlert("Erreur", "Échec du refus de l'événement : " + selectedEvent.getTitle());
            }
        } else {
            showAlert("Avertissement", "Veuillez sélectionner un événement à refuser.");
        }
    }

    @FXML
    private void searchEvent() {
        String searchText = searchField.getText().trim().toLowerCase();
        ObservableList<EventRequest> filteredList = FXCollections.observableArrayList();

        if (searchText.isEmpty()) {
            eventRequestTable.setItems(fullEventList);
            return;
        }

        for (EventRequest eventRequest : fullEventList) {
            if ((eventRequest.getTitle() != null && eventRequest.getTitle().toLowerCase().contains(searchText)) ||
                    (eventRequest.getSport() != null && eventRequest.getSport().toLowerCase().contains(searchText)) ||
                    (eventRequest.getLocation() != null && eventRequest.getLocation().toLowerCase().contains(searchText)) ||
                    (eventRequest.getEventDate() != null && eventRequest.getEventDate().toString().contains(searchText)) ||
                    (eventRequest.getDescription() != null && eventRequest.getDescription().toLowerCase().contains(searchText)) ||
                    (eventRequest.getStatus() != null && eventRequest.getStatus().toLowerCase().contains(searchText))) {
                filteredList.add(eventRequest);
            }
        }
        eventRequestTable.setItems(filteredList);
    }

    @FXML
    private void openCalendar() {
        try {
            Desktop.getDesktop().browse(new URI("https://calendar.google.com/"));
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
            showAlert("Erreur", "Impossible d’ouvrir le lien du calendrier : " + e.getMessage());
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, content, ButtonType.OK);
        alert.setTitle(title);
        alert.showAndWait();
    }

    @FXML
    private void goToDashboard() {
        try {
            Stage stage = (Stage) eventRequestTable.getScene().getWindow();
            javafx.scene.Parent root = javafx.fxml.FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/demo/View/dashboard-view.fxml")));
            stage.setScene(new javafx.scene.Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erreur", "Erreur lors du chargement du dashboard : " + e.getMessage());
        }
    }
}