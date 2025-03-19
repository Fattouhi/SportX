package com.example.sportx.controller.abir;

import com.example.sportx.models.abir.Group;
import com.example.sportx.services.abir.GroupService;
import com.example.sportx.utils.abir.ApplicationContext;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class GroupController {

    @FXML
    private TextField groupNameField;

    @FXML
    private TextArea groupDescriptionField;

    @FXML
    private ComboBox<String> groupCategoryField;

    @FXML
    private ImageView groupImageView;

    @FXML
    private File selectedImageFile;

    private final GroupService groupService = new GroupService();

    @FXML
    public void initialize() {
        // Add categories to the ComboBox
        List<String> categories = List.of(
                "Game Analysis and Strategies", "Training and Performance", "Sports News and Updates",
                "Fan Communities", "Health and Recovery", "Esports", "Equipment"
        );
        groupCategoryField.setItems(FXCollections.observableArrayList(categories));
    }

    @FXML
    public void handleUploadImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        selectedImageFile = fileChooser.showOpenDialog(null);

        if (selectedImageFile != null) {
            groupImageView.setImage(new Image(selectedImageFile.toURI().toString()));
        }
    }

    @FXML
    public void handleCreateGroup(ActionEvent event) {
        String name = groupNameField.getText();
        String description = groupDescriptionField.getText();
        String category = groupCategoryField.getValue();
        String imageUrl = (selectedImageFile != null) ? selectedImageFile.getAbsolutePath() : null;

        if (name.isEmpty() || description.isEmpty() || category == null) {
            showAlert("Error", "Please fill all fields.");
            return;
        }

        try {
            int userId = ApplicationContext.getCurrentUser().getId(); // Get current user ID
            groupService.createGroup(name, description, category, imageUrl, userId);
            showAlert("Success", "Group created successfully!");

            // Redirect to the community view using SceneManager
            SceneManager.switchToScene("community-view.fxml", null);
        } catch (SQLException | IOException e) {
            showAlert("Error", "Failed to create group.");
            e.printStackTrace();
        }
    }


    @FXML
    public void handleCancel(ActionEvent event) {
        // Clear the form fields
        groupNameField.clear();
        groupDescriptionField.clear();
        groupCategoryField.getSelectionModel().clearSelection();
        groupImageView.setImage(null); // Clear the image
        selectedImageFile = null; // Reset the selected image file
    }

    private int getCurrentUserId() {
        // Implement this method to return the current user's ID
        return 1; // Placeholder value
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}