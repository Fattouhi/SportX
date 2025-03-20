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
    @FXML private TableColumn<GroupRequests, String> creatorColumn;
    @FXML private TextArea descriptionArea;
    @FXML private ImageView groupImageView;
    @FXML private Button approveButton;
    @FXML private Button rejectButton;

    private GroupDAO groupDAO;
    @FXML
    private TextField searchField;

    @FXML
    private ChoiceBox<String> filterChoiceBox;

    public GroupRequestController() {
        // Constructeur par défaut obligatoire pour JavaFX
    }

    @FXML
    private void goToDashboard() {
        try {
            Stage stage = (Stage) groupRequestTable.getScene().getWindow(); // Utilisez userTable pour obtenir la scène
            stage.setScene(new javafx.scene.Scene(
                    javafx.fxml.FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/demo/View/dashboard-view.fxml")))
            ));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Recherche d'un groupe
    @FXML
    private void searchGroup() {
        String searchText = searchField.getText().toLowerCase();
        List<GroupRequests> filteredList = groupDAO.getAllGroupRequests().stream() // Utilisez getAllGroupRequests()
                .filter(g -> g.getGroupName().toLowerCase().contains(searchText) ||
                        g.getCreator().toLowerCase().contains(searchText) ||
                        g.getCategory().toLowerCase().contains(searchText))
                .toList();
        groupRequestTable.getItems().setAll(filteredList);
    }

    // Filtrer les groupes par statut
    @FXML
    private void filterGroups() {
        String filter = filterChoiceBox.getValue();
        List<GroupRequests> filteredList;

        if ("Tous".equals(filter)) {
            filteredList = groupDAO.getAllGroupRequests(); // Utilisez getAllGroupRequests()
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
            e.printStackTrace(); // Affiche l'erreur dans la console
            showAlert();
        }

        groupNameColumn.setCellValueFactory(new PropertyValueFactory<>("groupName"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        creatorColumn.setCellValueFactory(new PropertyValueFactory<>("creator"));

        loadAllRequests(); // Charge toutes les demandes au lieu de seulement celles en attente

        groupRequestTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                showDetails(newSelection);
            }
        });

        approveButton.setOnAction(event -> updateGroupStatus("approved"));
        rejectButton.setOnAction(event -> updateGroupStatus("rejected"));
    }

    // Fonction pour afficher une alerte en cas d'erreur
    private void showAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur de connexion");
        alert.setHeaderText(null);
        alert.setContentText("Impossible de se connecter à la base de données.");
        alert.showAndWait();
    }

    private void loadAllRequests() {
        List<GroupRequests> requests = groupDAO.getAllGroupRequests(); // Charge toutes les demandes
        groupRequestTable.getItems().setAll(requests);
    }

    private void showDetails(GroupRequests request) {
        descriptionArea.setText(request.getDescription());

        File file = new File(request.getImagePath());
        if (file.exists()) {
            groupImageView.setImage(new Image(file.toURI().toString()));
        } else {
            groupImageView.setImage(null);
        }
    }

    private void updateGroupStatus(String status) {
        GroupRequests selectedRequest = groupRequestTable.getSelectionModel().getSelectedItem();
        if (selectedRequest != null) {
            boolean success = groupDAO.updateGroupStatus(selectedRequest.getId(), status);
            if (success) {
                groupRequestTable.getItems().remove(selectedRequest);
                descriptionArea.clear();
                groupImageView.setImage(null);
            }
        }
    }
}