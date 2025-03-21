package com.example.appli.controller;

import com.example.appli.dao.InscriptionDAO;
import com.example.appli.model.Event;
import com.example.appli.model.Inscription;
import com.example.appli.model.Participant;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class PerticiperEventController {

    @FXML private TextField nomField;
    @FXML private TextField prenomField;
    @FXML private SplitMenuButton genreMenu;
    @FXML private DatePicker dateNaissance;
    @FXML private CheckBox checkAutorisation;
    @FXML private Button Authentfier;
    @FXML private Button Authentfier1;

    private Event event;
    private InscriptionDAO inscriptionDAO = new InscriptionDAO();

    @FXML
    private void initialize() {
        if (genreMenu == null) {
            System.out.println("Erreur : genreMenu est null ! Vérifiez fx:id dans PerticiperEvent.fxml.");
            return;
        }
        genreMenu.getItems().clear();
        genreMenu.getItems().addAll(new MenuItem("Homme"), new MenuItem("Femme"));
        genreMenu.setText("Homme");
        genreMenu.getItems().forEach(item -> item.setOnAction(e -> genreMenu.setText(item.getText())));

        if (Authentfier == null) {
            System.out.println("Erreur : Authentfier est null dans PerticiperEventController !");
        } else {
            Authentfier.setOnAction(e -> handleSubmitParticipation());
        }

        if (Authentfier1 == null) {
            System.out.println("Erreur : Authentfier1 est null dans PerticiperEventController !");
        } else {
            Authentfier1.setOnAction(e -> handleAddEvent());
        }
    }

    @FXML
    private void handleSubmitParticipation() {
        if (nomField.getText().isEmpty() || prenomField.getText().isEmpty() || dateNaissance.getValue() == null) {
            showAlert("Erreur", "Veuillez remplir tous les champs requis !");
            return;
        }

        Participant participant = new Participant(
                nomField.getText(),
                prenomField.getText(),
                genreMenu.getText(),
                dateNaissance.getValue(),
                "example@email.com",
                "123456789"
        );

        try {
            if (event == null) {
                showAlert("Erreur", "Aucun événement sélectionné !");
                return;
            }
            inscriptionDAO.addInscription(new Inscription(participant, event));
            showAlert("Succès", "Participation enregistrée avec succès !");
            Stage stage = (Stage) Authentfier.getScene().getWindow();
            stage.close();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Erreur", "Échec de l'enregistrement : " + e.getMessage());
        }
    }

    @FXML
    private void handleAddEvent() {
        System.out.println("Bouton 'Ajouter un événement' cliqué dans PerticiperEventController.");
        try {
            java.net.URL url = getClass().getResource("/com/example/appli/fxml/AddEvent.fxml");
            if (url == null) {
                System.out.println("Erreur : AddEvent.fxml introuvable dans /com/example/appli/fxml/");
                showAlert("Erreur", "AddEvent.fxml est introuvable !");
                return;
            }
            System.out.println("Chargement de AddEvent.fxml depuis : " + url);
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root, 400, 600));
            stage.setTitle("Ajouter un événement");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Erreur", "Échec de l'ouverture de AddEvent.fxml : " + e.getMessage());
        }
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(title.equals("Erreur") ? Alert.AlertType.ERROR : Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}