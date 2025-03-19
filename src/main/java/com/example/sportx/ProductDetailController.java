package com.example.sportx;

import com.example.sportx.DAO.CartDAO;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;
import models.Item;

public class ProductDetailController {
    @FXML
    private ImageView productImage;
    @FXML
    private Label productName;
    @FXML
    private Label productDescription;
    @FXML
    private Label productPrice;
    @FXML
    private Label stockQuantity;
    @FXML
    private Label productType;
    @FXML
    private VBox productDetailBox;
    @FXML
    private Button addToCartButton; // Added to reference the button

    private Item item;

    public void setData(Item item) {
        this.item = item;
        Image image = new Image(getClass().getResourceAsStream(item.getThumbnailImage()));
        productImage.setImage(image);
        productName.setText(item.getName());
        productDescription.setText(item.getDescription());
        productPrice.setText("Price: $" + String.format("%.2f", item.getPrice()));
        stockQuantity.setText("Stock: " + item.getStockQuantity());
        productType.setText("Type: " + item.getType());
    }

    @FXML
    private void addToCart() {
        // Add the product to the cart (user ID is 1 for testing)
        CartDAO.addToCart(1, item.getId());
        System.out.println("Added to cart: " + item.getName());

        // Show toast and disable button
        showToast("Added successfully!");
    }

    private void showToast(String message) {
        Text toast = new Text(message);
        toast.getStyleClass().add("toast");
        productDetailBox.getChildren().add(toast);

        // Disable the Add to Cart button
        addToCartButton.setDisable(true);

        // Remove the toast and re-enable the button after 2 seconds
        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished(event -> {
            productDetailBox.getChildren().remove(toast);
            addToCartButton.setDisable(false); // Re-enable the button
        });
        delay.play();
    }
}