package com.example.sportmarket;

import com.example.sportmarket.DAO.ProductDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Item;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;

public class AddProductController {

    @FXML
    private TextField fieldAddProductName;

    @FXML
    private TextField fieldAddProductPrice;

    @FXML
    private TextField fieldAddProductQuantity;

    @FXML
    private ComboBox<String> fieldAddProductCategoryId;

    @FXML
    private TextArea fieldAddProductDescription;

    @FXML
    private Button btnChooseThumbnail;

    @FXML
    private Label lblThumbnailPath;

    @FXML
    private Text viewProductResponse;

    private File thumbnailFile;

    // Save to target/classes/img/products/ for runtime access
    private static final String IMAGE_DIR = "target/classes/img/products/";

    private int getCurrentOwnerId() {
        return 1; // Replace with your actual logic
    }

    @FXML
    private void chooseThumbnail() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Thumbnail Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        Stage stage = (Stage) btnChooseThumbnail.getScene().getWindow();
        thumbnailFile = fileChooser.showOpenDialog(stage);
        if (thumbnailFile != null) {
            lblThumbnailPath.setText(thumbnailFile.getName());
        } else {
            lblThumbnailPath.setText("No file selected");
        }
    }

    @FXML
    private void btnAddProductOnAction() {
        try {
            String name = fieldAddProductName.getText();
            double price = Double.parseDouble(fieldAddProductPrice.getText());
            int quantity = Integer.parseInt(fieldAddProductQuantity.getText());
            String category = fieldAddProductCategoryId.getValue();
            String description = fieldAddProductDescription.getText();
            int ownerId = getCurrentOwnerId();

            if (name.isEmpty() || category == null || description.isEmpty()) {
                viewProductResponse.setText("Please fill all required fields!");
                viewProductResponse.setVisible(true);
                return;
            }

            String thumbnailPath = null;
            if (thumbnailFile != null) {
                File dir = new File(IMAGE_DIR);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                String fileName = System.currentTimeMillis() + "_" + thumbnailFile.getName();
                Path targetPath = Paths.get(IMAGE_DIR, fileName);
                Files.copy(thumbnailFile.toPath(), targetPath);
                thumbnailPath = "/img/products/" + fileName;
                System.out.println("Thumbnail saved to: " + targetPath.toAbsolutePath());
            }

            Item newItem = new Item();
            newItem.setName(name);
            newItem.setPrice(price);
            newItem.setStockQuantity(quantity);
            newItem.setType(category);
            newItem.setDescription(description);
            newItem.setThumbnailImage(thumbnailPath);
            newItem.setOwnerId(ownerId);
            newItem.setStatus("active");

            ProductDAO.addProduct(newItem);

            viewProductResponse.setText("Product added successfully!");
            viewProductResponse.setVisible(true);
            clearFields();
        } catch (NumberFormatException e) {
            viewProductResponse.setText("Price and Quantity must be numbers!");
            viewProductResponse.setVisible(true);
        } catch (IOException e) {
            viewProductResponse.setText("Error copying thumbnail image: " + e.getMessage());
            viewProductResponse.setVisible(true);
            e.printStackTrace();
        } catch (Exception e) {
            viewProductResponse.setText("Error adding product: " + e.getMessage());
            viewProductResponse.setVisible(true);
            e.printStackTrace();
        }
    }

    @FXML
    private void goToStoreView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/sportmarket/store-view.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) fieldAddProductName.getScene().getWindow();
            stage.setScene(new Scene(root, 1100, 700));
            stage.setTitle("SportX - Market");
        } catch (IOException e) {
            e.printStackTrace();
            viewProductResponse.setText("Error loading store view: " + e.getMessage());
            viewProductResponse.setVisible(true);
        }
    }

    private void clearFields() {
        fieldAddProductName.clear();
        fieldAddProductPrice.clear();
        fieldAddProductQuantity.clear();
        fieldAddProductCategoryId.getSelectionModel().clearSelection();
        fieldAddProductDescription.clear();
        lblThumbnailPath.setText("No file selected");
        thumbnailFile = null;
        viewProductResponse.setVisible(false);
    }
}