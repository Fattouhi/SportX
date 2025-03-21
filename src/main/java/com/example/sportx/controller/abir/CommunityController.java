package com.example.sportx.controller.abir;

import com.example.model.User;
import com.example.sportx.models.abir.Group;
import com.example.sportx.services.abir.GroupService;
import com.example.sportx.services.abir.NotificationService;
import com.example.sportx.services.abir.ServiceFactory;
import com.example.sportx.utils.abir.ApplicationContext;
import com.example.sportx.utils.abir.DatabaseConnection;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CommunityController {

    @FXML
    private TextField searchBar;

    @FXML
    private TilePane communityContainer;
    @FXML
    private Label welcomeLabel; // Label to display a welcome message
    @FXML
    private ComboBox<String> categoryComboBox;

    private final GroupService groupService;
    private final NotificationService notificationService;


    public CommunityController() throws SQLException {
        this.groupService = ServiceFactory.getInstance().getGroupService();
        this.notificationService = ServiceFactory.getInstance().getNotificationService();
    }

    @FXML
    public void initialize() {
        // Display a welcome message with the logged-in user's username
        User currentUser = ApplicationContext.getCurrentUser();
        if (currentUser != null) {
            welcomeLabel.setText("Welcome, " + currentUser.getUsername() + "!");
        }
        categoryComboBox.setValue("All"); // Set default value to "All"
        loadGroups();
        // Add a listener to the search bar for letter-by-letter search
        searchBar.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                handleSearchLetterByLetter(newValue);
            }
        });
    }

    // Method to handle search as the user types
    private void handleSearchLetterByLetter(String searchText) {
        // Clear the current groups displayed
        communityContainer.getChildren().clear();

        // Filter groups based on the search text
        String query = "SELECT id, name, description, image_url FROM groups WHERE LOWER(name) LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, "%" + searchText.toLowerCase() + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                VBox groupCard = createGroupCard(rs.getInt("id"), rs.getString("name"), rs.getString("description"), rs.getString("image_url"));
                communityContainer.getChildren().add(groupCard);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to search groups.");
        }
    }
    @FXML
    public void handleSearch(ActionEvent event) {
        String searchText = searchBar.getText().trim().toLowerCase();

        communityContainer.getChildren().clear();

        if (searchText.isEmpty()) {
            loadGroups();
        } else {
            searchGroups(searchText);
        }
    }


    @FXML
    public void handleCategoryFilter(ActionEvent event) {
        String category = categoryComboBox.getValue();

        communityContainer.getChildren().clear();

        if (category.equals("All")) {
            loadGroups();
        } else {
            filterGroupsByCategory(category);
        }
    }

    private void loadGroups() {
        try {
            List<Group> groups = groupService.getAllGroups();
            for (Group group : groups) {
                VBox groupCard = createGroupCard(group.getId(), group.getName(), group.getDescription(), group.getImageUrl());
                communityContainer.getChildren().add(groupCard);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load groups.");
        }
    }

    private void searchGroups(String searchText) {
        String query = "SELECT id, name, description, category, image_url FROM groups WHERE LOWER(name) LIKE ? OR LOWER(description) LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, "%" + searchText + "%");
            stmt.setString(2, "%" + searchText + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                VBox groupCard = createGroupCard(rs.getInt("id"), rs.getString("name"), rs.getString("description"), rs.getString("image_url"));
                communityContainer.getChildren().add(groupCard);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void filterGroupsByCategory(String category) {
        String query = "SELECT id, name, description, category, image_url FROM groups WHERE category = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, category);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                VBox groupCard = createGroupCard(rs.getInt("id"), rs.getString("name"), rs.getString("description"), rs.getString("image_url"));
                communityContainer.getChildren().add(groupCard);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private VBox createGroupCard(int id, String name, String description, String imageUrl) {
        VBox card = new VBox();
        card.getStyleClass().add("card");

        ImageView imageView = new ImageView();
        imageView.setFitWidth(100); // Set the desired width
        imageView.setFitHeight(100); // Set the desired height
        imageView.setPreserveRatio(false);
        imageView.getStyleClass().add("community-image");

        // Load the image from the imageUrl
        if (imageUrl != null && !imageUrl.isEmpty()) {
            try {
                // Check if the file exists at the specified path
                File file = new File(imageUrl);
                if (file.exists()) {
                    // Load the image from the file path
                    Image image = new Image(file.toURI().toString());
                    imageView.setImage(image);
                } else {
                    // If the file doesn't exist, use a default image
                    System.err.println("Image not found: " + imageUrl);
                    imageView.setImage(loadDefaultImage());
                }
            } catch (Exception e) {
                System.err.println("Failed to load image: " + imageUrl);
                e.printStackTrace();
                // Use a default image if loading fails
                imageView.setImage(loadDefaultImage());
            }
        } else {
            // Use a default image if no imageUrl is provided
            imageView.setImage(loadDefaultImage());
        }

        Label titleLabel = new Label(name);
        titleLabel.getStyleClass().add("card-title");

        Label descriptionLabel = new Label(description);
        descriptionLabel.getStyleClass().add("card-description");

        Button joinButton = new Button("Join");
        joinButton.setOnAction(event -> handleJoinGroup(id));

        card.getChildren().addAll(imageView, titleLabel, descriptionLabel, joinButton);
        return card;
    }

    // Helper method to load a default image
    private Image loadDefaultImage() {
        InputStream inputStream = getClass().getResourceAsStream("/images/default_image.png");
        if (inputStream == null) {
            System.err.println("Default image not found!");
            return null;
        }
        return new Image(inputStream);
    }

    @FXML
    private void handleJoinGroup(int groupId) {
        int requesterId = ApplicationContext.getCurrentUser().getId(); // Get the ID of the user requesting to join
        String query = "INSERT INTO group_requests (user_id, group_id, status) VALUES (?, ?, 'pending')";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, requesterId);
            stmt.setInt(2, groupId);
            stmt.executeUpdate();

            // Notify the admin
            int adminId = getGroupAdminId(groupId); // Get the admin's ID
            if (adminId != -1) {
                // Pass the admin's ID (requesterId) to the notification
                notificationService.createNotification(adminId, "User " + requesterId + " has requested to join your group.", "JOIN_REQUEST", groupId);

            }

            showAlert("Success", "Join request sent successfully!");
        } catch (SQLException e) {
            showAlert("Error", "Failed to send join request.");
        }
    }

    private int getGroupAdminId(int groupId) {
        String query = "SELECT created_by FROM groups WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, groupId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("created_by"); // Return the admin's ID
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Return -1 if the admin ID is not found
    }

    @FXML
    public void handleStartCommunityClick(ActionEvent event) {
        try {
            SceneManager.switchToScene("AddCommunity.fxml", null);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        } 
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}