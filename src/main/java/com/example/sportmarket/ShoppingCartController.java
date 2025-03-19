package com.example.sportmarket;

import com.example.sportmarket.DAO.CartDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import models.Item;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingCartController {
    @FXML
    private ListView<HBox> cartItemsList;

    @FXML
    private Label totalPriceLabel;

    @FXML
    private Button checkoutButton; // Added to control the button

    private List<Item> cartItems;
    private Map<Integer, Integer> itemQuantities = new HashMap<>(); // productId -> quantity

    @FXML
    public void initialize() {
        cartItems = CartDAO.getCartItems(1); // User ID 1 for testing
        populateCartItems();
        updateTotalPrice();
        updateCheckoutButtonState(); // Update button state on initialization
    }

    private void populateCartItems() {
        cartItemsList.getItems().clear();
        itemQuantities.clear();

        for (Item item : cartItems) {
            int productId = item.getId();
            int quantity = item.getQuantity();
            itemQuantities.put(productId, quantity);

            HBox hbox = new HBox(10);
            Label nameLabel = new Label(item.getName() + " - $" + String.format("%.2f", item.getPrice()));
            Label qtyLabel = new Label("Qty: " + quantity);
            Button increaseBtn = new Button("+");
            Button decreaseBtn = new Button("-");
            Button removeBtn = new Button("Remove");

            // Style buttons
            increaseBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
            decreaseBtn.setStyle("-fx-background-color: #FF6B6B; -fx-text-fill: white;");
            removeBtn.setStyle("-fx-background-color: #FF5252; -fx-text-fill: white;");

            // Event handlers
            increaseBtn.setOnAction(e -> adjustQuantity(productId, 1, qtyLabel));
            decreaseBtn.setOnAction(e -> adjustQuantity(productId, -1, qtyLabel));
            removeBtn.setOnAction(e -> removeItem(productId));

            hbox.getChildren().addAll(nameLabel, qtyLabel, increaseBtn, decreaseBtn, removeBtn);
            cartItemsList.getItems().add(hbox);
        }
        updateCheckoutButtonState(); // Update button state after populating
    }

    private void adjustQuantity(int productId, int change, Label qtyLabel) {
        int currentQty = itemQuantities.get(productId);
        int newQty = Math.max(1, currentQty + change); // Minimum quantity is 1
        itemQuantities.put(productId, newQty);
        CartDAO.updateQuantity(1, productId, newQty); // Update in DB

        // Update UI in real-time
        qtyLabel.setText("Qty: " + newQty);
        updateTotalPrice();

        // Update the quantity in the cartItems list
        cartItems.stream()
                .filter(item -> item.getId() == productId)
                .findFirst()
                .ifPresent(item -> item.setQuantity(newQty));

        updateCheckoutButtonState(); // Update button state after quantity change
    }

    private void removeItem(int productId) {
        CartDAO.removeFromCart(1, productId); // Remove from DB
        cartItems.removeIf(item -> item.getId() == productId); // Remove from list
        itemQuantities.remove(productId); // Remove from map
        populateCartItems(); // Refresh view
        updateTotalPrice();
        updateCheckoutButtonState(); // Update button state after removal
    }

    private void updateTotalPrice() {
        double total = 0;
        for (Item item : cartItems) {
            total += item.getPrice() * itemQuantities.getOrDefault(item.getId(), 0);
        }
        totalPriceLabel.setText("Total: $" + String.format("%.2f", total));
    }

    private void updateCheckoutButtonState() {
        checkoutButton.setDisable(cartItems.isEmpty()); // Disable if cart is empty
    }

    @FXML
    private void checkout() throws IOException {
        if (!cartItems.isEmpty()) { // Double-check to ensure cart isn't empty
            FXMLLoader loader = new FXMLLoader(getClass().getResource("checkout-form-view.fxml"));
            Parent root = loader.load();
            CheckoutFormController controller = loader.getController();
            controller.setCartItems(cartItems);

            // Update the current scene
            cartItemsList.getScene().setRoot(root);
        }
    }
}