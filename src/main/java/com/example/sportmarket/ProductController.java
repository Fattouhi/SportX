package com.example.sportmarket;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.Item;

import java.io.IOException;

public class ProductController {
    @FXML
    private ImageView imageName;
    @FXML
    private Label itemName;
    @FXML
    private Label ownerName;
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
        imageName.setImage(image);
        itemName.setText(item.getName());
        ownerName.setText("Owner: " + item.getOwnerId());
        productPrice.setText("Price: $" + String.format("%.2f", item.getPrice()));
        stockQuantity.setText("Stock: " + item.getStockQuantity());
        productType.setText("Type: " + item.getType());
    }

    @FXML
    private void handleClick(MouseEvent event) throws IOException {
        // Load the detailed product view
        FXMLLoader loader = new FXMLLoader(getClass().getResource("product-detail.fxml"));
        Parent root = loader.load();

        // Pass the selected product to the detailed view controller
        ProductDetailController controller = loader.getController();
        controller.setData(item);

        // Create a new stage for the detailed view
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Product Details");
        stage.show();
    }
}