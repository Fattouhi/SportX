package com.example.demo.controllers;

import com.example.demo.dao.ContentDAO;
import com.example.demo.dao.DatabaseConnection;
import com.example.demo.model.ContentRequest;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Objects;

public class ContenuManagementController {

    @FXML private TableView<ContentRequest> contentTable;
    @FXML private TableColumn<ContentRequest, Integer> colId;
    @FXML private TableColumn<ContentRequest, String> colTitle;
    @FXML private TableColumn<ContentRequest, String> colType;
    @FXML private TableColumn<ContentRequest, String> colAuthor;
    @FXML private TableColumn<ContentRequest, String> colStatus;
    @FXML private TextField searchField;
    @FXML private ChoiceBox<String> filterChoiceBox;
    @FXML private TextArea contentDetails;
    @FXML private ImageView contentImage;
    @FXML private Button btnAccept;
    @FXML private Button btnReject;
    @FXML private TextArea rejectionReason;
    @FXML private Button submitRejection;
    private Hyperlink linkElement; // Champ pour le lien (URL ou PDF)
    private ObservableList<ContentRequest> allContentRequests = FXCollections.observableArrayList();
    private ContentRequest selectedContent;

    @FXML
    public void initialize() {
        // Configurer les colonnes de la TableView
        colId.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        colTitle.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        colType.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType()));
        colAuthor.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAuthor()));
        colStatus.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus()));

        refreshList();
        searchField.textProperty().addListener((obs, oldValue, newValue) -> searchContent());
        filterChoiceBox.setOnAction(event -> filterContents());
        filterChoiceBox.setValue("Tous");

        contentTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                showDetails(newSelection);
            } else {
                contentDetails.clear();
                contentImage.setImage(null);
                if (linkElement != null) linkElement.setVisible(false); // Masquer le lien si aucun contenu n'est sélectionné
                rejectionReason.setVisible(false);
                submitRejection.setVisible(false);
            }
        });

        // Gérer les actions des boutons
        btnAccept.setOnAction(event -> acceptContent());
        btnReject.setOnAction(event -> showRejectionDialog());
    }

    @FXML
    private void refreshList() {
        try {
            ContentDAO contentDAO = new ContentDAO(DatabaseConnection.getConnection());
            allContentRequests.setAll(contentDAO.getAllContentRequests());
            contentTable.setItems(allContentRequests);
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Erreur", "Impossible de charger les publications : " + e.getMessage());
        }
    }

    @FXML
    private void searchContent() {
        String searchText = searchField.getText().trim().toLowerCase();
        if (searchText.isEmpty()) {
            contentTable.setItems(allContentRequests);
            return;
        }
        ObservableList<ContentRequest> filteredList = FXCollections.observableArrayList();
        for (ContentRequest content : allContentRequests) {
            if ((content.getTitle() != null && content.getTitle().toLowerCase().contains(searchText)) ||
                    (content.getAuthor() != null && content.getAuthor().toLowerCase().contains(searchText)) ||
                    (content.getType() != null && content.getType().toLowerCase().contains(searchText)) ||
                    (content.getStatus() != null && content.getStatus().toLowerCase().contains(searchText))) {
                filteredList.add(content);
            }
        }
        contentTable.setItems(filteredList);
    }

    @FXML
    private void filterContents() {
        String selectedFilter = filterChoiceBox.getValue();
        try {
            ContentDAO contentDAO = new ContentDAO(DatabaseConnection.getConnection());
            ObservableList<ContentRequest> filteredList = FXCollections.observableArrayList();
            if ("Tous".equals(selectedFilter)) {
                filteredList.setAll(contentDAO.getAllContentRequests());
            } else {
                filteredList.setAll(contentDAO.getContentRequestsByStatus(selectedFilter));
            }
            contentTable.setItems(filteredList);
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Erreur", "Impossible de filtrer les publications : " + e.getMessage());
        }
    }

    private void showDetails(ContentRequest content) {
        selectedContent = content;
        StringBuilder details = new StringBuilder();
        details.append("Titre: ").append(content.getTitle()).append("\n")
                .append("Type: ").append(content.getType()).append("\n")
                .append("Auteur: ").append(content.getAuthor()).append("\n")
                .append("Statut: ").append(content.getStatus()).append("\n")
                .append("Créé le: ").append(content.getCreatedAt()).append("\n")
                .append("Contenu: ");

        // Gérer le contenu en fonction du type
        if ("image".equalsIgnoreCase(content.getType())) {
            File file = new File(content.getContent());
            if (file.exists()) {
                contentImage.setImage(new Image(file.toURI().toString()));
                details.append("Image affichée ci-dessous.");
            } else {
                contentImage.setImage(null);
                details.append("Image non trouvée : ").append(content.getContent());
            }
            if (linkElement != null) linkElement.setVisible(false); // Masquer le lien pour les images
        } else if ("link".equalsIgnoreCase(content.getType())) {
            contentImage.setImage(null); // Masquer l'image pour les liens
            String url = content.getContent();
            if (linkElement == null) {
                linkElement = new Hyperlink("Ouvrir le lien");
                // Ajouter le lien après contentDetails dans le parent (VBox)
                VBox parent = (VBox) contentDetails.getParent();
                if (parent != null && !parent.getChildren().contains(linkElement)) {
                    parent.getChildren().add(linkElement); // Ajouter le lien au parent
                }
            }
            // Mettre à jour l’action du lien avec l’URL actuel
            linkElement.setOnAction(event -> openLink(url));
            linkElement.setVisible(true); // Afficher le lien
            linkElement.setManaged(true); // Assurer que le lien prend de l'espace
            details.append("Lien disponible via le bouton ci-dessous.");
        } else { // text
            contentImage.setImage(null); // Masquer l'image pour le texte
            if (linkElement != null) linkElement.setVisible(false); // Masquer le lien pour le texte
            details.append(content.getContent());
        }

        contentDetails.setText(details.toString());
        contentDetails.setPrefHeight(350); // Garder une hauteur fixe pour le TextArea

        // Réinitialiser la visibilité des éléments de refus
        rejectionReason.setVisible(false);
        submitRejection.setVisible(false);
    }

    // Méthode pour ouvrir un lien (URL) dans le navigateur par défaut
    private void openLink(String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (URISyntaxException e) {
            e.printStackTrace();
            showAlert("Erreur", "URL invalide : " + url);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Erreur", "Impossible d’ouvrir le lien : " + e.getMessage());
        }
    }

    @FXML
    private void acceptContent() {
        if (selectedContent != null) {
            try {
                ContentDAO contentDAO = new ContentDAO(DatabaseConnection.getConnection());
                boolean success = contentDAO.updateContentStatus(selectedContent.getId(), "Approved", null);
                showAlert("Succès", success ? "Publication acceptée : " + selectedContent.getTitle() : "Échec de l'acceptation");
                if (success) refreshList();
            } catch (SQLException e) {
                showAlert("Erreur", "Impossible de mettre à jour le statut : " + e.getMessage());
            }
        } else {
            showAlert("Attention", "Sélectionnez une publication d'abord.");
        }
    }

    private void showRejectionDialog() {
        if (selectedContent != null) {
            rejectionReason.setVisible(true);
            submitRejection.setVisible(true);
            submitRejection.setOnAction(event -> rejectContent());
        } else {
            showAlert("Attention", "Sélectionnez une publication d'abord.");
        }
    }

    @FXML
    private void rejectContent() {
        if (selectedContent != null) {
            String reason = rejectionReason.getText().trim();
            try {
                ContentDAO contentDAO = new ContentDAO(DatabaseConnection.getConnection());
                boolean success = contentDAO.updateContentStatus(selectedContent.getId(), "Rejected", reason.isEmpty() ? "Aucune raison spécifiée" : reason);
                showAlert("Succès", success ? "Publication refusée : " + selectedContent.getTitle() : "Échec du refus");
                if (success) {
                    refreshList();
                    rejectionReason.setVisible(false);
                    submitRejection.setVisible(false);
                    rejectionReason.clear();
                }
            } catch (SQLException e) {
                showAlert("Erreur", "Impossible de mettre à jour le statut : " + e.getMessage());
            }
        } else {
            showAlert("Attention", "Sélectionnez une publication d'abord.");
        }
    }

    @FXML
    private void submitRejection() {
        if (selectedContent != null) {
            String reason = rejectionReason.getText().trim();
            try {
                ContentDAO contentDAO = new ContentDAO(DatabaseConnection.getConnection());
                boolean success = contentDAO.updateContentStatus(selectedContent.getId(), "Rejected", reason.isEmpty() ? "Aucune raison spécifiée" : reason);
                showAlert("Succès", success ? "Publication refusée : " + selectedContent.getTitle() : "Échec du refus");
                if (success) {
                    refreshList();
                    rejectionReason.setVisible(false);
                    submitRejection.setVisible(false);
                    rejectionReason.clear();
                }
            } catch (SQLException e) {
                showAlert("Erreur", "Impossible de mettre à jour le statut : " + e.getMessage());
            }
        } else {
            showAlert("Attention", "Sélectionnez une publication d'abord.");
        }
    }

    @FXML
    private void goToDashboard() {
        try {
            Stage stage = (Stage)contentTable.getScene().getWindow();
            stage.setScene(new javafx.scene.Scene(
                    javafx.fxml.FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/demo/View/dashboard-view.fxml")))
            ));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, content, ButtonType.OK);
        alert.setTitle(title);
        alert.showAndWait();
    }
}