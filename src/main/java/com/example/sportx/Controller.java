package com.example.sportx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.Item;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private HBox cardLayout;
    @FXML
    private GridPane itemContainer;
    private List<Item> recentlyAdded;
    private List<Item> products;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        recentlyAdded = new ArrayList<>(recentlyAdded());
        products = new ArrayList<>(items());

        itemContainer.getChildren().clear();
        itemContainer.getRowConstraints().clear();
        itemContainer.getColumnConstraints().clear();

        int columns = 0;
        int rows = 0;
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
            for (Item item : products) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("product.fxml"));
                VBox productBox = fxmlLoader.load();
                ProductController productController = fxmlLoader.getController();
                productController.setData(item);

                GridPane.setConstraints(productBox, columns, rows);
                GridPane.setMargin(productBox, new Insets(10));
                itemContainer.getChildren().add(productBox);

                columns++;
                if (columns == 6) {
                    columns = 0;
                    rows++;
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

    private List<Item> items() {
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
