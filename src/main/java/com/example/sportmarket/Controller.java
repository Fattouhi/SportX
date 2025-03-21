package com.example.sportmarket;

import com.example.sportmarket.DAO.ProductDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Item;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private HBox cardLayout;
    @FXML
    private GridPane itemContainer;
    @FXML
    private Button actualitesButton;
    @FXML
    private Button homeButton;
    @FXML
    private VBox partenairesContainer;
    @FXML
    private Button marketButton;
    @FXML
    private Button messagesButton;

    private List<Item> recentlyAdded;
    private List<Item> products;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        recentlyAdded = ProductDAO.getAllProducts();
        products = ProductDAO.getAllProducts();

        itemContainer.getChildren().clear();
        itemContainer.getRowConstraints().clear();
        itemContainer.getColumnConstraints().clear();

        int columns = 0;
        int rows = 0;
        try {
            for (Item item : recentlyAdded) {
                if (item != null) { // Null check
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("cart-view.fxml"));
                    HBox cardBox = fxmlLoader.load();
                    CartController cartController = fxmlLoader.getController();
                    cartController.setItem(item); // Line 58
                    cardLayout.getChildren().add(cardBox);
                }
            }
            for (Item item : products) {
                if (item != null) { // Null check
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
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goToAddProduct() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/sportmarket/add-product.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) cardLayout.getScene().getWindow();
            stage.setScene(new Scene(root, 1280, 800));
            stage.setTitle("SportX - Add Product");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void gotoMessages() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/community-view.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("SportX - Messages");
            stage.setScene(new Scene(root, 800, 600));
            stage.show();
            ((Stage) messagesButton.getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goToHome() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/hello-view.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) homeButton.getScene().getWindow();
            stage.setScene(new Scene(root, 1100, 700));
            stage.setTitle("SportX - Accueil");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void gotoMarket() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/sportmarket/store-view.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("SportX - Market");
            stage.setScene(new Scene(root, 800, 600));
            stage.show();
            ((Stage) marketButton.getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void gotoActualite() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainScreen.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("SportX - Actualités");
            stage.setScene(new Scene(root, 800, 600));
            stage.show();
            ((Stage) actualitesButton.getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openMainScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainScreen.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("SportX - Actualités");
            stage.setScene(new Scene(root, 800, 600));
            stage.show();
            ((Stage) actualitesButton.getScene().getWindow()).close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}