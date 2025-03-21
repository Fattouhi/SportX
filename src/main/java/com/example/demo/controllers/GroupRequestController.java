package com.example.demo.controllers;

import com.example.demo.dao.DatabaseConnection;
import com.example.demo.dao.GroupDAO;
import com.example.demo.model.GroupRequests;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.File;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class GroupRequestController {
    @FXML private TableView<GroupRequests> groupRequestTable;
    @FXML private TableColumn<GroupRequests, String> groupNameColumn;
    @FXML private TableColumn<GroupRequests, String> categoryColumn;
    @FXML private TableColumn<GroupRequests, Integer> creatorColumn;
    @FXML private TableColumn<GroupRequests, String> statusColumn;
    @FXML private TextArea descriptionArea; // Changé de detailsTextArea à descriptionArea pour correspondre au FXML
    @FXML private ImageView groupImageView;
    @FXML private Button approveButton;
    @FXML private Button rejectButton;
    @FXML private TextField searchField;
    @FXML private ChoiceBox<String> filterChoiceBox;

    private GroupDAO groupDAO;

    public GroupRequestController() {
        // Constructeur par défaut obligatoire pour JavaFX
    }

    @FXML
    private void goToDashboard() {
        try {
            Stage stage = (Stage) groupRequestTable.getScene().getWindow();
            stage.setScene(new javafx.scene.Scene(
                    javafx.fxml.FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/demo/View/dashboard-view.fxml")))
            ));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void searchGroup() {
        String searchText = searchField.getText().toLowerCase();
        List<GroupRequests> filteredList = groupDAO.getAllGroupRequests().stream()
                .filter(g -> g.getName().toLowerCase().contains(searchText) ||
                        String.valueOf(g.getCreatedBy()).contains(searchText) ||
                        g.getCategory().toLowerCase().contains(searchText) ||
                        g.getStatus().toLowerCase().contains(searchText))
                .toList();
        groupRequestTable.getItems().setAll(filteredList);
    }

    @FXML
    private void filterGroups() {
        String filter = filterChoiceBox.getValue();
        List<GroupRequests> filteredList;

        if ("Tous".equals(filter)) {
            filteredList = groupDAO.getAllGroupRequests();
        } else {
            filteredList = groupDAO.getGroupRequestsByStatus(filter.toLowerCase());
        }
        groupRequestTable.getItems().setAll(filteredList);
    }

    @FXML
    public void initialize() {
        try {
            if (groupDAO == null) {
                groupDAO = new GroupDAO(DatabaseConnection.getConnection());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Erreur de connexion", "Impossible de se connecter à la base de données.");
        }

        // Configuration des colonnes
        groupNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        creatorColumn.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Configuration du ChoiceBox pour le filtrage
        filterChoiceBox.getItems().clear();
        filterChoiceBox.getItems().addAll("Tous", "pending", "approved", "rejected");
        filterChoiceBox.setValue("Tous");
        filterChoiceBox.setOnAction(event -> filterGroups());

        // Ajout des listeners
        searchField.textProperty().addListener((obs, oldValue, newValue) -> searchGroup());
        groupRequestTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                showDetails(newSelection);
            }
        });

        approveButton.setOnAction(event -> updateGroupStatus("approved"));
        rejectButton.setOnAction(event -> updateGroupStatus("rejected"));

        // Chargement initial des données
        loadAllRequests();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void loadAllRequests() {
        List<GroupRequests> requests = groupDAO.getAllGroupRequests();
        groupRequestTable.getItems().setAll(requests);
    }

    @FXML
    private ImageView groupImage;

    private void showDetails(GroupRequests group) {
        if (group != null) {
            // Afficher les détails du groupe, y compris la description
            String description = group.getDescription() != null ? group.getDescription() : "Aucune description disponible";
            descriptionArea.setText("Nom : " + group.getName() + "\n" +
                    "Catégorie : " + group.getCategory() + "\n" +
                    "Créateur : " + group.getCreatedBy() + "\n" +
                    "Statut : " + group.getStatus() + "\n" +
                    "Description : " + description);

            // Charger l'image
            String imageUrl = group.getImageUrl();
            if (imageUrl != null && !imageUrl.isEmpty()) {
                try {
                    File file = new File(imageUrl);
                    if (file.exists()) {
                        Image image = new Image(file.toURI().toString());
                        groupImage.setImage(image);
                    } else {
                        System.out.println("Image introuvable : " + imageUrl);
                        groupImage.setImage(null);
                    }
                } catch (Exception e) {
                    System.out.println("Erreur lors du chargement de l'image : " + imageUrl);
                    e.printStackTrace();
                    groupImage.setImage(null);
                }
            } else {
                groupImage.setImage(null);
            }
        } else {
            descriptionArea.setText("");
            groupImage.setImage(null);
        }
    }

    private void updateGroupStatus(String status) {
        GroupRequests selectedRequest = groupRequestTable.getSelectionModel().getSelectedItem();
        if (selectedRequest == null) {
            showAlert("Aucune sélection", "Veuillez sélectionner une demande de groupe à " + (status.equals("approved") ? "approuver" : "rejeter") + ".");
            return;
        }

        boolean success = groupDAO.updateGroupStatus(selectedRequest.getId(), status);
        if (success) {
            showAlert("Succès", "La demande de groupe a été " + (status.equals("approved") ? "approuvée" : "rejetée") + " avec succès.");
            loadAllRequests();
            descriptionArea.clear();
            groupImageView.setImage(null);
        } else {
            showAlert("Erreur", "Échec de la mise à jour du statut de la demande.");
        }
    }
}