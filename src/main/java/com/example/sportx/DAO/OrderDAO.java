package com.example.sportx.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderDAO {
    public static void createOrder(int userId, int productId, int quantity, String city, String address, String phoneNumber) {
        String query = "INSERT INTO orders (user_id, product_id, quantity, city, address, phone_number) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            statement.setInt(2, productId);
            statement.setInt(3, quantity);
            statement.setString(4, city);
            statement.setString(5, address);
            statement.setString(6, phoneNumber);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}