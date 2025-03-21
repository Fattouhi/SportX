package com.example.appli.controller;

import com.example.appli.dao.InscriptionDAO;
import com.example.appli.model.Inscription;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ListInscriptionController {

    @FXML private TableView<Inscription> inscriptionTable;
    @FXML private TextField searchField;
    @FXML private ImageView refrech;
    @FXML private Button submit111;
    @FXML private Button submit1111;
    @FXML private Button submit11111;
    @FXML private Button submit1112;
    @FXML private Button Authentfier1;

    private ObservableList<Inscription> inscriptionData;
    private InscriptionDAO inscriptionDAO = new InscriptionDAO();

    @FXML
    private void initialize() {
        inscriptionData = FXCollections.observableArrayList();
        try {
            inscriptionData.addAll(inscriptionDAO.getAllInscriptions());
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load inscriptions from database.");
        }

        TableColumn<Inscription, String> sportCol = new TableColumn<>("Sport");
        sportCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEvent().getSport()));

        TableColumn<Inscription, String> lieuCol = new TableColumn<>("Lieu");
        lieuCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEvent().getLocation()));

        TableColumn<Inscription, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEvent().getDate().toString()));

        TableColumn<Inscription, String> participantCol = new TableColumn<>("Participant");
        participantCol.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getParticipant().getFirstName() + " " + cellData.getValue().getParticipant().getLastName()
        ));

        inscriptionTable.getColumns().setAll(sportCol, lieuCol, dateCol, participantCol);
        inscriptionTable.setItems(inscriptionData);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> handleSearchInscriptions());
        refrech.setOnMouseClicked(event -> refreshTable());
        submit111.setOnAction(event -> navigateToEvents());
        Authentfier1.setOnAction(event -> navigateToAddEvent());
    }

    @FXML
    private void handleSearchInscriptions() {
        String query = searchField.getText().toLowerCase();
        if (query.isEmpty()) {
            inscriptionTable.setItems(inscriptionData);
            return;
        }

        ObservableList<Inscription> filteredData = FXCollections.observableArrayList();
        for (Inscription inscription : inscriptionData) {
            String participantName = inscription.getParticipant().getFirstName().toLowerCase() + " " +
                    inscription.getParticipant().getLastName().toLowerCase();
            String eventDetails = inscription.getEvent().getSport().toLowerCase() + " " +
                    inscription.getEvent().getLocation().toLowerCase();

            if (participantName.contains(query) || eventDetails.contains(query)) {
                filteredData.add(inscription);
            }
        }
        inscriptionTable.setItems(filteredData);
    }

    private void refreshTable() {
        try {
            inscriptionData.setAll(inscriptionDAO.getAllInscriptions());
            searchField.clear();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to refresh inscriptions.");
        }
    }

    private void navigateToEvents() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/appli/fxml/EvenementInterface.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) submit111.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to navigate to Events page.");
        }
    }

    private void navigateToAddEvent() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/appli/fxml/PerticiperEvent.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Add Event");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to open Add Event page.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void setInscriptions(List<Inscription> inscriptions) {
        inscriptionData.setAll(inscriptions);
        inscriptionTable.setItems(inscriptionData);
    }
}