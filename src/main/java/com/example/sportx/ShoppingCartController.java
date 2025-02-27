package com.example.sportx;

import com.example.sportx.DAO.CartDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import models.Item;

import java.io.IOException;
import java.util.List;

public class ShoppingCartController {
    @FXML
    private ListView<String> cartItemsList;

    private List<Item> cartItems;

    @FXML
    public void initialize() {
        // Fetch cart items for user ID 1 (for testing)
        cartItems = CartDAO.getCartItems(1);

        // Display cart items in the ListView
        for (Item item : cartItems) {
            cartItemsList.getItems().add(item.getName() + " - $" + String.format("%.2f", item.getPrice()));
        }
    }

    @FXML
    private void checkout() throws IOException {
        // Redirect to the checkout form
        FXMLLoader loader = new FXMLLoader(getClass().getResource("checkout-form-view.fxml"));
        Parent root = loader.load();

        // Pass the cart items to the checkout form controller
        CheckoutFormController controller = loader.getController();
        controller.setCartItems(cartItems);

        // Create a new stage for the checkout form
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Checkout Form");
        stage.show();
    }
}