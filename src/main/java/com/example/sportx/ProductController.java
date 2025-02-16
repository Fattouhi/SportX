package com.example.sportx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import models.Item;

public class ProductController {
    @FXML
    private ImageView imageName;

    @FXML
    private Label itemName;

    @FXML
    private Label ownerName;

    public void setData(Item item) {
        // Load the image from the resource path
        Image image = new Image(getClass().getResourceAsStream(item.getImageSrc()));
        imageName.setImage(image);

        // Set the text for the labels
        itemName.setText(item.getName());
        ownerName.setText(item.getOwner());
    }
}