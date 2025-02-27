package com.example.sportx.DAO;

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
}