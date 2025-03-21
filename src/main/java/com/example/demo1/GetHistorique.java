package com.example.demo1;

import javafx.collections.FXCollections;
import com.example.UserDao.UserDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class GetHistorique {

    @FXML
    private ScrollPane mainScrollPane;

    @FXML
    private VBox scrollContent;

    @FXML
    private ChoiceBox<String> filterChoiceBox;

    @FXML
    private ChoiceBox<String> sortChoiceBox;

    @FXML
    private Button facebook;

    @FXML
    private Button instagramme;

    @FXML
    private Button snapchat;

    @FXML
    private Button getgroups;

    @FXML
    private Button getConnection;

    @FXML
    private Button Acheter;

    @FXML
    private Button messages;

    @FXML
    private Button home;

    @FXML
    private Button getgroups1;

    @FXML
    private Button getgroups11;

    @FXML
    private Button submit111; // Evénements

    @FXML
    private Button submit1111; // Groups

    @FXML
    private Button submit1112; // Actualités

    @FXML
    private Button submit11111; // Marketplace

    @FXML
    private Button Authentfier1; // Ajoutez votre évènement

    private String loggedInEmail; // To track the current user

    public GetHistorique() {
        // Constructor can initialize any dependencies if needed (e.g., a DAO)
    }

    public void setLoggedInEmail(String email) {
        this.loggedInEmail = email;
    }

    @FXML
    public void initialize() {
        // Initialize filter and sort ChoiceBoxes
        filterChoiceBox.setItems(FXCollections.observableArrayList("All", "In Transit", "Completed", "Pending"));
        filterChoiceBox.setValue("All");

        sortChoiceBox.setItems(FXCollections.observableArrayList("Date (Newest First)", "Date (Oldest First)", "Total (High to Low)", "Total (Low to High)"));
        sortChoiceBox.setValue("Date (Newest First)");

        // Optional: Add listeners for filtering/sorting
        filterChoiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> filterOrders(newVal));
        sortChoiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> sortOrders(newVal));
    }

    // Sidebar Navigation
    @FXML
    public void goToHome() {
        loadScene("/com/example/demo1/acc.fxml", "Account Page"); // Assuming acc.fxml is the home page
    }

    @FXML
    public void goToMessages() {
        loadScene("/com/example/demo1/messages.fxml", "Messages"); // Placeholder, replace with actual FXML
    }

    @FXML
    public void goToProfile() {
        loadScene("/com/example/demo1/changePassword.fxml", "Profile Settings");
    }

    @FXML
    public void goToCommande() {
        loadScene("/com/example/demo1/inscriptions.fxml", "Mes Inscriptions"); // Placeholder
    }

    @FXML
    public void goToProduct() {
        loadScene("/com/example/demo1/marketplace.fxml", "Marketplace"); // Placeholder
    }

    @FXML
    public void goToReponse() {
        // Already on Historique, no action needed or load a refreshed version
        loadScene("/com/example/demo1/GetHistorique.fxml", "Historique");
    }

    @FXML
    public void logout() {
        loadScene("/com/example/demo1/login.fxml", "Login");
    }

    // Header Navigation
    @FXML
    public void goToEvenements() {
        loadScene("/com/example/demo1/evenements.fxml", "Evénements"); // Placeholder
    }

    @FXML
    public void goToGroups() {
        loadScene("/com/example/demo1/groups.fxml", "Groups"); // Placeholder
    }

    @FXML
    public void goToActualites() {
        loadScene("/com/example/demo1/actualites.fxml", "Actualités"); // Placeholder
    }

    @FXML
    public void goToMarketplace() {
        loadScene("/com/example/demo1/marketplace.fxml", "Marketplace"); // Placeholder
    }

    @FXML
    public void addEvent() {
        loadScene("/com/example/demo1/addEvent.fxml", "Add Event"); // Placeholder
    }

    // Social Media Buttons (Placeholder Actions)
    @FXML
    public void openFacebook() {
        showAlert("Opening Facebook link...");
    }

    @FXML
    public void openInstagram() {
        showAlert("Opening Instagram link...");
    }

    @FXML
    public void openSnapchat() {
        showAlert("Opening Snapchat link...");
    }

    // Helper Methods
    private void loadScene(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            // Pass loggedInEmail to the next controller if applicable
            if (fxmlPath.equals("/com/example/demo1/changePassword.fxml")) {
                changePassword controller = loader.getController();
                controller.setCurrentUser(new UserDAO().getUserByEmail(loggedInEmail));
            } else if (fxmlPath.equals("/com/example/demo1/acc.fxml")) {
                AuthController controller = loader.getController();

            }

            Stage stage = (Stage) home.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle(title);
            stage.show();
        } catch (IOException e) {
            showAlert("Error loading " + fxmlPath + ": " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            showAlert("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void filterOrders(String filter) {
        // Placeholder for filtering logic
        System.out.println("Filtering by: " + filter);
        // Implement logic to filter scrollContent based on filter value
    }

    private void sortOrders(String sort) {
        // Placeholder for sorting logic
        System.out.println("Sorting by: " + sort);
        // Implement logic to sort scrollContent based on sort value
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Action");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Placeholder methods for order-specific actions (to be implemented)
    public void trackOrder() {
        showAlert("Tracking order...");
    }

    public void requestReturn() {
        showAlert("Requesting return...");
    }

    public void showDetails() {
        showAlert("Showing order details...");
    }

    public void buyAgain() {
        showAlert("Buying again...");
    }
}