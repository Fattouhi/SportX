package com.example.sportx;

import com.example.sportx.services.abir.ServiceFactory;
import com.example.sportx.websocket.ChatServer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.CountDownLatch;

public class serverrunning {
    private static final String URL = "jdbc:mysql://localhost:3306/sportx";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static void main(String[] args) {
        // Check database connection
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            System.out.println("Database connection successful!");
        } catch (SQLException e) {
            System.err.println("Failed to connect to the database: " + e.getMessage());
            e.printStackTrace();
            return; // Exit if the database is not reachable
        }

        // Initialize ServiceFactory
        ServiceFactory.getInstance();

        // Start the WebSocket server in a separate thread
        Thread serverThread = new Thread(() -> {
            try {
                ChatServer.main(new String[]{}); // Start the WebSocket server
            } catch (Exception e) {
                System.err.println("Failed to start WebSocket server: " + e.getMessage());
                e.printStackTrace();
            }
        });
        serverThread.setDaemon(true);
        serverThread.start();

        // Keep the main thread alive
        CountDownLatch latch = new CountDownLatch(1);
        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}