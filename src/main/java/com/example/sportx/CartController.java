package com.example.sportx;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import models.Item;

public class CartController {
    @FXML
    private HBox box;

    @FXML
    private ImageView imageName;

    @FXML
    private Label itemName;

    @FXML
    private Label ownerName;

    // Updated color palette: Purple, Red, Blue, Yellow
    private final String[] colors = {"6A4C93", "FF6B6B", "4ECDC4", "FFE66D"};

    public void setItem(Item item) {
        // Set the image for the item
        Image image = new Image(getClass().getResourceAsStream(item.getImageSrc()));
        imageName.setImage(image);

        // Set the item name and owner
        itemName.setText(item.getName());
        ownerName.setText(item.getOwner());

        // Set a random background color from the palette
        box.setStyle("-fx-background-color: #" + colors[(int) (Math.random() * colors.length)] + ";");
    }
}