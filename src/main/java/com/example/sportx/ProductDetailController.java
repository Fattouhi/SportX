package com.example.sportx;

import com.example.sportx.DAO.CartDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    }
}