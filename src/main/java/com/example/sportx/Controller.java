package com.example.sportx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import models.Item;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private HBox cardLayout;
    private List<Item> recentlyAdded;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        recentlyAdded = new ArrayList<>(recentlyAdded());
        try {
            for (Item item : recentlyAdded) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("cart-view.fxml"));
                HBox cardBox = fxmlLoader.load();
                CartController cartController = fxmlLoader.getController();
                if (cartController == null) {
                    System.err.println("CartController is null!");
                } else {
                    cartController.setItem(item);
                    cardLayout.getChildren().add(cardBox);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Item> recentlyAdded() {
        List<Item> ls = new ArrayList<>();
        Item item = new Item();
        item.setName("Messi T shirt");
        item.setImageSrc("/img/products/p1.png");
        item.setOwner("Messi");
        ls.add(item);
        item = new Item();
        item.setName("Sport shirt");
        item.setImageSrc("/img/products/p2.png");
        item.setOwner("Decatlon");
        ls.add(item);
        item = new Item();
        item.setName("Messi T shirt");
        item.setImageSrc("/img/products/p1.png");
        item.setOwner("Messi");
        ls.add(item);
        item = new Item();
        item.setName("Sport shirt");
        item.setImageSrc("/img/products/p2.png");
        item.setOwner("Decatlon");
        ls.add(item);
        return ls;
    }
}