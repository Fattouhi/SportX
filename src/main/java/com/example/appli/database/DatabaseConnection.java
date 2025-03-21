package com.example.appli.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // Updated URL with the correct host, port, and database name
    private static final String URL = "jdbc:mysql://mysql-196aef4e-esprim-b42e.b.aivencloud.com:21836/defaultdb";
    private static final String USER = "avnadmin"; // Updated username
    private static final String PASSWORD = "AVNS_DIenDKhRpZUhC8F0A1M"; // Replace with the actual password

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}