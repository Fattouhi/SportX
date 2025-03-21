package com.example.demo.dao;

import com.example.demo.model.Products;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    private static final String SELECT_ALL_PRODUCTS = "SELECT * FROM products";
    private static final String UPDATE_PRODUCT_REQUEST_STATUS = "UPDATE products SET request_status = ?, updated_at = NOW() WHERE name = ?";

    public List<Products> getAllProductRequests() {
        List<Products> productRequests = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_ALL_PRODUCTS)) {

            while (rs.next()) {
                Products product = new Products(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("owner_id"),
                        rs.getString("type"),
                        rs.getString("thumbnail_image"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getInt("stock_quantity"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime(),
                        rs.getString("status"),
                        rs.getString("request_status") // Ajout de request_status
                );
                productRequests.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productRequests;
    }

    public boolean updateProductStatus(String productName, String requestStatus) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_PRODUCT_REQUEST_STATUS)) {
            stmt.setString(1, requestStatus);
            stmt.setString(2, productName);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Nouvelle méthode pour compter le nombre total de produits
    public static int getProductCount() {
        String query = "SELECT COUNT(*) AS total FROM products";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0; // Retourne 0 en cas d'erreur
    }
}