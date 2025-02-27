package com.example.sportx.DAO;

import models.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CartDAO {
    public static void addToCart(int userId, int productId) {
        String query = "INSERT INTO cart (user_id, product_id) VALUES (?, ?)";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            statement.setInt(2, productId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void clearCart(int userId) {
        String query = "DELETE FROM cart WHERE user_id = ?";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Item> getCartItems(int userId) {
        List<Item> cartItems = new ArrayList<>();
        String query = "SELECT p.* FROM cart c JOIN products p ON c.product_id = p.id WHERE c.user_id = ?";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

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
                cartItems.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cartItems;
    }
}