package com.example.sportx;

import com.example.sportx.DAO.CartDAO;
import com.example.sportx.DAO.OrderDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Item;

import java.io.IOException;
import java.util.List;

public class CheckoutFormController {
    @FXML
    private ComboBox<String> cityComboBox;
    @FXML
    private TextField addressField;
    @FXML
    private TextField phoneNumberField;

    private List<Item> cartItems;

    // Add this method to set the cart items
    public void setCartItems(List<Item> cartItems) {
        this.cartItems = cartItems;
    }

    @FXML
    public void initialize() {
        // Populate the city ComboBox with Tunisia states
        cityComboBox.getItems().addAll(
                "Ariana", "Béja", "Ben Arous", "Bizerte", "Gabès", "Gafsa", "Jendouba",
                "Kairouan", "Kasserine", "Kébili", "Kef", "Mahdia", "Manouba", "Médenine",
                "Monastir", "Nabeul", "Sfax", "Sidi Bouzid", "Siliana", "Sousse", "Tataouine",
                "Tozeur", "Tunis", "Zaghouan"
        );
    }

    @FXML
    private void placeOrder() {
        String city = cityComboBox.getValue();
        String address = addressField.getText();
        String phoneNumber = phoneNumberField.getText();

        if (city == null || address.isEmpty() || phoneNumber.isEmpty()) {
            System.out.println("Please fill all fields!");
            return;
        }

        // For testing, assume user ID is 1
        int userId = 1;

        // Save each item in the cart as a separate order
        for (Item item : cartItems) {
            OrderDAO.createOrder(userId, item.getId(), 1, city, address, phoneNumber);
        }

        // Clear the shopping cart
        CartDAO.clearCart(userId);

        // Redirect to the confirmation page
        redirectToConfirmationPage();
    }

    private void redirectToConfirmationPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("order-confirmation-view.fxml"));
            Parent root = loader.load();

            // Get the current stage
            Stage stage = (Stage) cityComboBox.getScene().getWindow();

            // Set the new scene
            stage.setScene(new Scene(root));
            stage.setTitle("Order Confirmation");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void disableForm() {
        cityComboBox.setDisable(true);
        addressField.setDisable(true);
        phoneNumberField.setDisable(true);
    }

    private void showToast(String message) {
        // Create a toast notification
        Text toast = new Text(message);
        toast.setStyle("-fx-font-size: 16px; -fx-fill: green;");

        // Add the toast to the VBox (or any other container)
        ((VBox) cityComboBox.getParent()).getChildren().add(toast);
    }
}