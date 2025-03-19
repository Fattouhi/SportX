package com.example.sportx.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {
    private static final String URL = "jdbc:mysql://mysql-196aef4e-esprim-b42e.b.aivencloud.com:21836/defaultdb";
    private static final String USER = "avnadmin";
    private static final String PASSWORD = "AVNS_DIenDKhRpZUhC8F0A1M";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Connection connection = null;
        try {
            connection = DatabaseUtil.getConnection();
            if (connection != null) {
                System.out.println("Connection to the database was successful!");
            } else {
                System.out.println("Failed to establish a connection.");
            }
        } catch (SQLException e) {
            System.err.println("An error occurred while connecting to the database:");
            e.printStackTrace();
        } finally {
            DatabaseUtil.closeConnection(connection);
        }
    }
}
