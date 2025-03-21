package com.example.demo1; // Adjust to com.example.demo2 if that's your intended package

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class AccController {

    @FXML
    private ScrollPane scrollPane;



    @FXML
    private Button Authentifier; // "Creer compt" button

    @FXML
    private Button login;

    @FXML
    private ImageView refrech;

    @FXML
    private Button Event; // "Evénements" button

    @FXML
    private Button actualiter; // "Actualités" button

    @FXML
    private Button groups;

    @FXML
    private Button market; // "Marketplace" button

    @FXML
    private TextField NomEvent;

    @FXML
    private DatePicker DateEvent;

    @FXML
    private MenuButton menuButton;

    @FXML
    private Button submit12; // "WWW.BEETWIIN.COM" button

    private String loggedInEmail; // Optional: to track the current user

    public AccController() {
        // Constructor can initialize dependencies if needed
    }

    public void setLoggedInEmail(String email) {
        this.loggedInEmail = email;
    }

    @FXML
    public void initialize() {
        // Initialize MenuButton items if needed (e.g., populate departments)
        menuButton.getItems().clear();
        menuButton.getItems().addAll(
                new MenuItem("Paris"),
                new MenuItem("Lyon"),
                new MenuItem("Marseille")
        );

        // Optional: Add listener for search functionality
        NomEvent.textProperty().addListener((obs, oldVal, newVal) -> searchEvents());
        DateEvent.valueProperty().addListener((obs, oldVal, newVal) -> searchEvents());
        menuButton.setOnAction(e -> searchEvents());
    }

    // Header Button Actions
    @FXML
    public void handleAuthentifier() {
        loadScene("/com/example/demo1/authentification.fxml", "Sign Up"); // Placeholder for signup page
    }

    @FXML
    public void handleLogin() {
        loadScene("/com/example/demo1/login.fxml", "Login");
    }

    @FXML
    public void handleRefrech() {
        // Refresh the page (reload Acc.fxml)
        loadScene("/com/example/demo1/Acc.fxml", "Home");
    }

    @FXML
    public void handleEvent() {
        loadScene("/com/example/demo1/evenements.fxml", "Evénements");
    }

    @FXML
    public void handleActualites() {
        loadScene("/com/example/demo1/actualites.fxml", "Actualités");
    }

    @FXML
    public void handleGroups() {
        loadScene("/com/example/demo1/groups.fxml", "Groups");
    }

    @FXML
    public void handleMarketplace() {
        loadScene("/com/example/demo1/marketplace.fxml", "Marketplace");
    }

    @FXML
    public void handleBeetwiin() {
        // Open external link (placeholder)
        showAlert("Opening WWW.BEETWIIN.COM...");
        // Optionally use java.awt.Desktop to open browser:
        // try {
        //     java.awt.Desktop.getDesktop().browse(new URI("https://www.beetwiin.com"));
        // } catch (Exception e) {
        //     showAlert("Error opening link: " + e.getMessage());
        // }
    }

    // Helper Methods
    private void loadScene(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            // Pass loggedInEmail to relevant controllers if applicable
            if (fxmlPath.equals("/com/example/demo1/login.fxml")) {
                // Assuming a LoginController exists
            } else if (fxmlPath.equals("/com/example/demo1/Acc.fxml")) {
                AccController controller = loader.getController();
                controller.setLoggedInEmail(loggedInEmail);
            }

            Stage stage = (Stage) Authentifier.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle(title);
            stage.show();
        } catch (IOException e) {
            showAlert("Error loading " + fxmlPath + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void searchEvents() {
        String eventName = NomEvent.getText().trim();
        String eventDate = DateEvent.getValue() != null ? DateEvent.getValue().toString() : "";
        String department = menuButton.getText();

        // Placeholder for search logic
        showAlert("Searching for events:\nName: " + eventName + "\nDate: " + eventDate + "\nLocation: " + department);
        // Implement actual search logic here (e.g., query a database)
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Action");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}