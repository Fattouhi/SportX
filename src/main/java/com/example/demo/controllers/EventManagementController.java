package com.example.demo.controllers;

import com.example.demo.AdminApplication;
import com.example.demo.dao.EventDAO;
import com.example.demo.model.EventRequest;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
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

    @FXML
    private TableColumn<EventRequest, Number> colIndex;

    @FXML
    private TableView<EventRequest> eventRequestTable;

    @FXML
    private TableColumn<EventRequest, String> colEventName;

    @FXML
    private TableColumn<EventRequest, String> colDescription;
    @FXML
    private TableColumn<EventRequest, String> addressField;


    @FXML
    private TableColumn<EventRequest, String> colDate;

    @FXML
    private TableColumn<EventRequest, String> colOrganizer;

    @FXML
    private TextArea eventDetails;

    @FXML
    private Button btnAccept;

    @FXML
    private Button btnReject;

    @FXML
    private TextField searchField; // Assurez-vous que ce champ est bien lié dans le FXML

    // Liste complète des événements (on la garde en mémoire)
    private final ObservableList<EventRequest> fullEventList = FXCollections.observableArrayList();

    @FXML
    private ChoiceBox<String> filterChoiceBox;

    private final ObservableList<EventRequest> allEventRequests = FXCollections.observableArrayList();
    private final ObservableList<EventRequest> eventRequests = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Initialisation des colonnes
        colEventName.setCellValueFactory(cellData -> cellData.getValue().eventNameProperty());
        colDescription.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
        colDate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEventDate().toString()));
        colOrganizer.setCellValueFactory(cellData -> cellData.getValue().organizerProperty());
        colIndex.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(eventRequestTable.getItems().indexOf(cellData.getValue()) + 1));
        addressField.setCellValueFactory(cellData -> cellData.getValue().addressProperty());



        // Chargement des données réelles depuis la base de données
        EventDAO eventDAO = new EventDAO();
        List<EventRequest> events = eventDAO.getAllEventRequests();
        eventRequestTable.getItems().setAll(events);
        loadEventRequests();


        // Listener pour filtrer dynamiquement
        searchField.textProperty().addListener((observable, oldValue, newValue) -> searchEvent());
        searchField.setOnKeyReleased(event -> filterEvents());
        filterChoiceBox.setOnAction(event -> filterEvents());
        filterChoiceBox.setValue("Tous");
        fullEventList.setAll(eventRequestTable.getItems());



        // Affichage des détails au clic sur une ligne
        eventRequestTable.setOnMouseClicked(this::displayEventDetails);
    }

    private void loadEventRequests() {
        EventDAO eventDAO = new EventDAO();
        List<EventRequest> events = eventDAO.getAllEventRequests();
        allEventRequests.setAll(events);
        eventRequestTable.setItems(allEventRequests);
    }


    private void displayEventDetails(MouseEvent event) {
        EventRequest selectedRequest = eventRequestTable.getSelectionModel().getSelectedItem();
        if (selectedRequest != null) {
            eventDetails.setText(
                    "Nom: " + selectedRequest.getEventName() + "\n" +
                            "Description: " + selectedRequest.getDescription() + "\n" +
                            "Date: " + selectedRequest.getEventDate() + "\n" +
                            "Organisateur: " + selectedRequest.getOrganizer() + "\n" +
                            "Adresse: " + selectedRequest.getAddress() // Ajout de l'adresse
            );
        }
    }

    @FXML
    private void filterEvents() {
        String selectedFilter = filterChoiceBox.getValue();

        // Vérifier si selectedFilter est null
        if (selectedFilter == null) {
            selectedFilter = "Tous"; // Valeur par défaut
        }

        // Recharger la liste complète avant d'appliquer le filtre
        EventDAO eventDAO = new EventDAO();
        List<EventRequest> allEvents = eventDAO.getAllEventRequests();

        ObservableList<EventRequest> filteredList = FXCollections.observableArrayList();

        if (selectedFilter.equals("Tous")) {
            filteredList.setAll(allEvents);  // Afficher tous les événements
        } else {
            for (EventRequest event : allEvents) {
                if (event.getStatus().equalsIgnoreCase(selectedFilter)) {
                    filteredList.add(event);
                }
            }
        }

        eventRequestTable.setItems(filteredList);
        eventRequestTable.refresh();  // Mettre à jour l'affichage
    }


    @FXML
    private void acceptEvent() {
        EventRequest selectedRequest = eventRequestTable.getSelectionModel().getSelectedItem();
        if (selectedRequest != null) {
            EventDAO eventDAO = new EventDAO();

            // Mettre à jour le statut de l'événement en 'approved'
            boolean success = eventDAO.updateEventStatus(selectedRequest.getEventName(), "approved");

            if (success) {
                // Afficher un message de confirmation
                Alert alert = new Alert(Alert.AlertType.INFORMATION,
                        "Demande d'événement acceptée : " + selectedRequest.getEventName(), ButtonType.OK);
                alert.showAndWait();

            } else {
                // Afficher un message d'erreur si la mise à jour échoue
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "Échec de l'acceptation de l'événement : " + selectedRequest.getEventName(), ButtonType.OK);
                alert.showAndWait();
            }
        } else {
            // Si aucun événement n'est sélectionné
            Alert alert = new Alert(Alert.AlertType.WARNING,
                    "Veuillez sélectionner un événement à accepter.", ButtonType.OK);
            alert.showAndWait();
        }
    }

    @FXML
    private void rejectEvent() {
        EventRequest selectedRequest = eventRequestTable.getSelectionModel().getSelectedItem();
        if (selectedRequest != null) {
            // Mise à jour du statut dans la base de données
            EventDAO eventDAO = new EventDAO();
            boolean success = eventDAO.updateEventStatus(selectedRequest.getEventName(), "rejected");

            if (success) {
                eventRequests.remove(selectedRequest); // Retirer de l'affichage
                eventDetails.clear();
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Demande d'événement refusée et masquée.", ButtonType.OK);
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Échec de la mise à jour du statut.", ButtonType.OK);
                alert.showAndWait();
            }
        }
    }


    @FXML
    private void searchEvent() {
        String searchText = searchField.getText().trim().toLowerCase();

        // Vérifier si la liste complète est bien chargée
        if (fullEventList.isEmpty()) {
            fullEventList.setAll(eventRequestTable.getItems());
        }

        ObservableList<EventRequest> filteredList = FXCollections.observableArrayList();

        if (searchText.isEmpty()) {
            eventRequestTable.setItems(fullEventList);
            return;
        }

        for (EventRequest eventRequest : fullEventList) {
            // Recherche insensible à la casse pour le nom, la description, l'organisateur, le statut, la date et l'adresse
            if ((eventRequest.getEventName() != null && eventRequest.getEventName().toLowerCase().contains(searchText)) ||
                    (eventRequest.getDescription() != null && eventRequest.getDescription().toLowerCase().contains(searchText)) ||
                    (eventRequest.getOrganizer() != null && eventRequest.getOrganizer().toLowerCase().contains(searchText)) ||
                    (eventRequest.getStatus() != null && eventRequest.getStatus().toLowerCase().contains(searchText)) ||
                    (eventRequest.getAddress() != null && eventRequest.getAddress().toLowerCase().contains(searchText)) || // Ajout de la recherche par adresse
                    (eventRequest.getEventDate() != null && eventRequest.getEventDate().toString().contains(searchText))) { // Ajout de la recherche par date

                filteredList.add(eventRequest);
            }
        }
        eventRequestTable.setItems(filteredList);
    }


    @FXML
    private void openCalendar() {
        try {
            // Ouvre un lien externe vers Google Calendar
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
            System.err.println("Erreur lors du chargement du dashboard : " + e.getMessage());
        }
    }


}