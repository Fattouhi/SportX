package com.example.demo.controllers;

import com.example.demo.AdminApplication;
import com.example.demo.dao.PartenairesDAO;
import com.example.demo.model.Partenaires;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.awt.Desktop;
import java.net.URI;
import java.util.List;

public class PartenairesManagementController {

    @FXML private ListView<Partenaires> partenaireList;
    @FXML private TextField searchField;
    @FXML private ChoiceBox<String> filterChoiceBox;
    @FXML private Button btnAccept;
    @FXML private Button btnReject;
    @FXML private Pane loadingOverlay; // Ajout de l'overlay
    @FXML private Label loadingLabel;   // Ajout du label

    private ObservableList<Partenaires> allPartenaireRequests = FXCollections.observableArrayList();
    private Partenaires selectedPartenaire;

    @FXML
    public void initialize() {
        partenaireList.setCellFactory(param -> new PartenaireListCell());
        refreshList();
        searchField.textProperty().addListener((obs, oldValue, newValue) -> searchPartenaire());
        filterChoiceBox.setOnAction(event -> filterPartenaires());
        filterChoiceBox.setValue("Tous");
        partenaireList.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> selectedPartenaire = newValue);
    }

    private class PartenaireListCell extends ListCell<Partenaires> {
        private final HBox hbox = new HBox(10);
        private final ImageView imageView = new ImageView();
        private final VBox infoBox = new VBox(5);
        private final Text nomText = new Text();
        private final Text descriptionText = new Text();
        private final Hyperlink siteWebLink = new Hyperlink();
        private final Text requestStatusText = new Text();

        public PartenaireListCell() {
            imageView.setFitHeight(80);
            imageView.setFitWidth(80);
            imageView.setPreserveRatio(true);
            nomText.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
            descriptionText.setStyle("-fx-font-size: 12px;");
            siteWebLink.setStyle("-fx-font-size: 12px;");
            requestStatusText.setStyle("-fx-font-size: 12px;");
            infoBox.getChildren().addAll(nomText, descriptionText, siteWebLink, requestStatusText);
            hbox.getChildren().addAll(imageView, infoBox);
            hbox.setStyle("-fx-padding: 10; -fx-background-color: white; -fx-border-color: #ddd; -fx-border-width: 1;");

            siteWebLink.setOnAction(event -> {
                String url = siteWebLink.getText();
                if (url != null && !url.isEmpty()) {
                    try {
                        Desktop.getDesktop().browse(new URI(url));
                    } catch (Exception e) {
                        e.printStackTrace();
                        showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible d'ouvrir le lien : " + e.getMessage());
                    }
                }
            });
        }

        @Override
        protected void updateItem(Partenaires partenaire, boolean empty) {
            super.updateItem(partenaire, empty);
            if (empty || partenaire == null) {
                setGraphic(null);
            } else {
                nomText.setText("Nom: " + partenaire.getNom());
                descriptionText.setText("Description: " + partenaire.getDescription());
                siteWebLink.setText(partenaire.getSiteWeb());
                requestStatusText.setText("Statut de la demande: " + partenaire.getRequestStatus());
                try {
                    String imageUrl = partenaire.getLogoUrl();
                    if (imageUrl != null && !imageUrl.isEmpty()) {
                        imageView.setImage(new Image(imageUrl, true));
                    } else {
                        imageView.setImage(null);
                    }
                } catch (Exception e) {
                    System.err.println("Erreur de chargement de l'image pour " + partenaire.getNom() + ": " + e.getMessage());
                    imageView.setImage(null);
                }
                setGraphic(hbox);
            }
        }
    }

    @FXML
    private void searchPartenaire() {
        String searchText = searchField.getText().trim().toLowerCase();
        if (searchText.isEmpty()) {
            partenaireList.setItems(allPartenaireRequests);
            return;
        }
        ObservableList<Partenaires> filteredList = FXCollections.observableArrayList();
        for (Partenaires partenaire : allPartenaireRequests) {
            if ((partenaire.getNom() != null && partenaire.getNom().toLowerCase().contains(searchText)) ||
                    (partenaire.getDescription() != null && partenaire.getDescription().toLowerCase().contains(searchText)) ||
                    (partenaire.getSiteWeb() != null && partenaire.getSiteWeb().toLowerCase().contains(searchText)) ||
                    (partenaire.getRequestStatus() != null && partenaire.getRequestStatus().toLowerCase().contains(searchText))) {
                filteredList.add(partenaire);
            }
        }
        partenaireList.setItems(filteredList);
    }

    @FXML
    private void filterPartenaires() {
        String selectedFilter = filterChoiceBox.getValue();
        ObservableList<Partenaires> filteredList = FXCollections.observableArrayList();
        if ("Tous".equals(selectedFilter)) {
            filteredList.setAll(allPartenaireRequests);
        } else {
            filteredList.setAll(allPartenaireRequests.stream()
                    .filter(p -> p.getRequestStatus().equalsIgnoreCase(selectedFilter))
                    .toList());
        }
        partenaireList.setItems(filteredList);
    }

    @FXML
    private void acceptPartenaire() {
        if (selectedPartenaire != null) {
            showLoading(true); // Afficher l'overlay
            PartenairesDAO partenairesDAO = new PartenairesDAO();
            boolean success = partenairesDAO.updatePartenaireRequestStatus(selectedPartenaire.getId(), "approved");
            showLoading(false); // Masquer l'overlay
            showAlert(success ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR,
                    success ? "Succès" : "Erreur",
                    success ? "Partenaire accepté : " + selectedPartenaire.getNom() : "Échec de l'acceptation");
            if (success) refreshList();
        } else {
            showAlert(Alert.AlertType.WARNING, "Attention", "Sélectionnez un partenaire d'abord.");
        }
    }

    @FXML
    private void rejectPartenaire() {
        if (selectedPartenaire != null) {
            showLoading(true); // Afficher l'overlay
            PartenairesDAO partenairesDAO = new PartenairesDAO();
            boolean success = partenairesDAO.updatePartenaireRequestStatus(selectedPartenaire.getId(), "rejected");
            showLoading(false); // Masquer l'overlay
            showAlert(success ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR,
                    success ? "Succès" : "Erreur",
                    success ? "Partenaire refusé : " + selectedPartenaire.getNom() : "Échec du refus");
            if (success) refreshList();
        } else {
            showAlert(Alert.AlertType.WARNING, "Attention", "Sélectionnez un partenaire d'abord.");
        }
    }

    @FXML
    private void refreshList() {
        PartenairesDAO partenairesDAO = new PartenairesDAO();
        List<Partenaires> partenaires = partenairesDAO.getAllPartenaireRequests();
        allPartenaireRequests.setAll(partenaires);
        partenaireList.setItems(allPartenaireRequests);
    }

    @FXML
    private void goToDashboard() {
        AdminApplication.changeScene("/com/example/demo/View/dashboard-view.fxml");
    }

    private void showLoading(boolean show) {
        if (loadingOverlay != null) {
            loadingOverlay.setVisible(show);
            loadingOverlay.setDisable(!show); // Désactiver l'interaction pendant le chargement
            if (show) {
                loadingLabel.setLayoutX((loadingOverlay.getPrefWidth() - loadingLabel.getWidth()) / 2); // Centrer horizontalement
                loadingLabel.setLayoutY((loadingOverlay.getPrefHeight() - loadingLabel.getHeight()) / 2); // Centrer verticalement
            }
        }
    }


    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type, content, ButtonType.OK);
        alert.setTitle(title);
        alert.showAndWait();
    }




}