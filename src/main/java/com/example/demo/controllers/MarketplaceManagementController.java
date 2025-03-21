package com.example.demo.controllers;

import com.example.demo.AdminApplication;
import com.example.demo.dao.ProductDAO;
import com.example.demo.model.Products;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.List;

public class MarketplaceManagementController {

    @FXML private ListView<Products> productList;
    @FXML private TextField searchField;
    @FXML private ChoiceBox<String> filterChoiceBox;
    @FXML private Button btnAccept;
    @FXML private Button btnReject;

    private ObservableList<Products> allProductRequests = FXCollections.observableArrayList();
    private Products selectedProduct;

    @FXML
    public void initialize() {
        productList.setCellFactory(param -> new ProductListCell());
        refreshList();
        searchField.textProperty().addListener((obs, oldValue, newValue) -> searchProduct());
        filterChoiceBox.setOnAction(event -> filterProducts());
        filterChoiceBox.setValue("Tous");
        productList.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> selectedProduct = newValue);
    }

    private static class ProductListCell extends ListCell<Products> {
        private final HBox hbox = new HBox(10);
        private final ImageView imageView = new ImageView();
        private final VBox infoBox = new VBox(5);
        private final Text nameText = new Text();
        private final Text descriptionText = new Text();
        private final Text priceText = new Text();
        private final Text sellerText = new Text();
        private final Text requestStatusText = new Text(); // Changé de statusText à requestStatusText

        public ProductListCell() {
            imageView.setFitHeight(80);
            imageView.setFitWidth(80);
            imageView.setPreserveRatio(true);
            nameText.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
            descriptionText.setStyle("-fx-font-size: 12px;");
            priceText.setStyle("-fx-font-size: 12px;");
            sellerText.setStyle("-fx-font-size: 12px;");
            requestStatusText.setStyle("-fx-font-size: 12px;");
            infoBox.getChildren().addAll(nameText, descriptionText, priceText, sellerText, requestStatusText);
            hbox.getChildren().addAll(imageView, infoBox);
            hbox.setStyle("-fx-padding: 10; -fx-background-color: white; -fx-border-color: #ddd; -fx-border-width: 1;");
        }

        @Override
        protected void updateItem(Products product, boolean empty) {
            super.updateItem(product, empty);
            if (empty || product == null) {
                setGraphic(null);
            } else {
                nameText.setText("Nom: " + product.getName());
                descriptionText.setText("Description: " + product.getDescription());
                priceText.setText("Prix: " + product.getPrice() + " €");
                sellerText.setText("Vendeur: " + product.getOwnerId());
                requestStatusText.setText("Statut de la demande: " + product.getRequestStatus()); // Utilise requestStatus
                try {
                    imageView.setImage(new Image(product.getThumbnailImage()));
                } catch (Exception e) {
                    imageView.setImage(null);
                }
                setGraphic(hbox);
            }
        }
    }

    @FXML
    private void searchProduct() {
        String searchText = searchField.getText().trim().toLowerCase();
        if (searchText.isEmpty()) {
            productList.setItems(allProductRequests);
            return;
        }
        ObservableList<Products> filteredList = FXCollections.observableArrayList();
        for (Products product : allProductRequests) {
            if ((product.getName() != null && product.getName().toLowerCase().contains(searchText)) ||
                    (product.getDescription() != null && product.getDescription().toLowerCase().contains(searchText)) ||
                    (String.valueOf(product.getOwnerId()).contains(searchText)) ||
                    (product.getRequestStatus() != null && product.getRequestStatus().toLowerCase().contains(searchText))) { // Utilise requestStatus
                filteredList.add(product);
            }
        }
        productList.setItems(filteredList);
    }

    @FXML
    private void filterProducts() {
        String selectedFilter = filterChoiceBox.getValue();
        ObservableList<Products> filteredList = FXCollections.observableArrayList();
        if ("Tous".equals(selectedFilter)) {
            filteredList.setAll(allProductRequests);
        } else {
            filteredList.setAll(allProductRequests.stream()
                    .filter(p -> p.getRequestStatus().equalsIgnoreCase(selectedFilter)) // Utilise requestStatus
                    .toList());
        }
        productList.setItems(filteredList);
    }

    @FXML
    private void acceptProduct() {
        if (selectedProduct != null) {
            ProductDAO productDAO = new ProductDAO();
            boolean success = productDAO.updateProductStatus(selectedProduct.getName(), "approved"); // Minuscule pour cohérence
            showAlert(success ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR,
                    success ? "Succès" : "Erreur",
                    success ? "Produit accepté : " + selectedProduct.getName() : "Échec de l'acceptation");
            if (success) refreshList();
        } else {
            showAlert(Alert.AlertType.WARNING, "Attention", "Sélectionnez un produit d'abord.");
        }
    }

    @FXML
    private void rejectProduct() {
        if (selectedProduct != null) {
            ProductDAO productDAO = new ProductDAO();
            boolean success = productDAO.updateProductStatus(selectedProduct.getName(), "rejected"); // Minuscule pour cohérence
            showAlert(success ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR,
                    success ? "Succès" : "Erreur",
                    success ? "Produit refusé : " + selectedProduct.getName() : "Échec du refus");
            if (success) refreshList();
        } else {
            showAlert(Alert.AlertType.WARNING, "Attention", "Sélectionnez un produit d'abord.");
        }
    }

    @FXML
    private void refreshList() {
        ProductDAO productDAO = new ProductDAO();
        List<Products> products = productDAO.getAllProductRequests();
        allProductRequests.setAll(products);
        productList.setItems(allProductRequests);
    }

    @FXML
    private void goToDashboard() {
        AdminApplication.changeScene("/com/example/demo/View/dashboard-view.fxml");
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type, content, ButtonType.OK);
        alert.setTitle(title);
        alert.showAndWait();
    }
}