package com.example.demo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://mysql-196aef4e-esprim-b42e.b.aivencloud.com:21836/defaultdb";
    private static final String USER = "avnadmin"; // Mets ton utilisateur MySQL
    private static final String PASSWORD = "AVNS_DIenDKhRpZUhC8F0A1M"; // Mets ton mot de passe MySQL

    public static Connection connection;

// Ouvre une nouvelle connexion à chaque appel
public static Connection getConnection() throws SQLException {
    return DriverManager.getConnection(URL, USER, PASSWORD);
}

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Connexion fermée.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
