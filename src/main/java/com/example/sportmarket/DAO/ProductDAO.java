package com.example.sportmarket.DAO;

import models.Item;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    public static List<Item> getAllProducts() {
        List<Item> products = new ArrayList<>();
        String query = "SELECT * FROM products";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Item item = new Item();
                item.setId(resultSet.getInt("id"));
                item.setName(resultSet.getString("name"));
                item.setOwnerId(resultSet.getInt("owner_id"));
                item.setType(resultSet.getString("type"));
                item.setThumbnailImage(resultSet.getString("thumbnail_image"));
                item.setDescription(resultSet.getString("description"));
                item.setPrice(resultSet.getDouble("price"));
                item.setStockQuantity(resultSet.getInt("stock_quantity"));
                item.setStatus(resultSet.getString("status"));
                products.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public static void addProduct(Item item) {
        String query = "INSERT INTO products (name, owner_id, type, thumbnail_image, description, price, stock_quantity, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, item.getName());
            statement.setInt(2, item.getOwnerId());
            statement.setString(3, item.getType());
            statement.setString(4, item.getThumbnailImage()); // Save thumbnail path
            statement.setString(5, item.getDescription());
            statement.setDouble(6, item.getPrice());
            statement.setInt(7, item.getStockQuantity());
            statement.setString(8, item.getStatus());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}