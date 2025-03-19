package com.example.sportmarket.DAO;

import models.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CartDAO {
    public static void addToCart(int userId, int productId) {
        String checkQuery = "SELECT quantity FROM cart WHERE user_id = ? AND product_id = ?";
        String insertQuery = "INSERT INTO cart (user_id, product_id, quantity) VALUES (?, ?, 1)";
        String updateQuery = "UPDATE cart SET quantity = quantity + 1 WHERE user_id = ? AND product_id = ?";

        try (Connection connection = DatabaseUtil.getConnection()) {
            PreparedStatement checkStmt = connection.prepareStatement(checkQuery);
            checkStmt.setInt(1, userId);
            checkStmt.setInt(2, productId);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                PreparedStatement updateStmt = connection.prepareStatement(updateQuery);
                updateStmt.setInt(1, userId);
                updateStmt.setInt(2, productId);
                updateStmt.executeUpdate();
            } else {
                PreparedStatement insertStmt = connection.prepareStatement(insertQuery);
                insertStmt.setInt(1, userId);
                insertStmt.setInt(2, productId);
                insertStmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateQuantity(int userId, int productId, int quantity) {
        String query = "UPDATE cart SET quantity = ? WHERE user_id = ? AND product_id = ?";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, quantity);
            statement.setInt(2, userId);
            statement.setInt(3, productId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeFromCart(int userId, int productId) {
        String query = "DELETE FROM cart WHERE user_id = ? AND product_id = ?";
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
        String query = "SELECT p.*, c.quantity FROM cart c JOIN products p ON c.product_id = p.id WHERE c.user_id = ?";

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
                item.setQuantity(resultSet.getInt("quantity"));
                cartItems.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cartItems;
    }
}