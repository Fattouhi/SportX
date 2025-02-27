package com.example.sportx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import models.Item;

import java.io.IOException;

public class CartController {
    @FXML
    private HBox box;

    @FXML
    private ImageView imageName;

    @FXML
    private Label itemName;

    @FXML
    private Label ownerName;

    private Item item;

    // Updated color palette: Purple, Red, Blue, Yellow
    private final String[] colors = {"6A4C93", "FF6B6B", "4ECDC4", "FFE66D"};

    public void setItem(Item item) {
        this.item = item;
        Image image = new Image(getClass().getResourceAsStream(item.getThumbnailImage()));
        imageName.setImage(image);
        itemName.setText(item.getName());
        ownerName.setText("By " + item.getOwnerId());
        box.setStyle("-fx-background-color: #" + colors[(int) (Math.random() * colors.length)] + "; -fx-background-radius : 15; -fx-effect: dropShadow(three-pass-box, rgba(0,0,0,0.1),10,0,10,0);");
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



