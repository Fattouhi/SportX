package com.example.sportx.websocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.example.sportx.services.abir.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChatServer {
    private static final Logger logger = LoggerFactory.getLogger(ChatServer.class);
    private static final int PORT = 5000;
    private static final int MAX_THREADS = 100;
    private static final ExecutorService threadPool = Executors.newFixedThreadPool(MAX_THREADS);
    private static List<ClientHandler> clients = new ArrayList<>();

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            logger.info("WebSocket server started on port {}", PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                logger.info("New client connected: {}", clientSocket.getInetAddress());

                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clients.add(clientHandler);
                threadPool.execute(clientHandler);
            }
        } catch (IOException e) {
            logger.error("Server error: {}", e.getMessage(), e);
        } finally {
            threadPool.shutdown();
        }
    }

    static class ClientHandler implements Runnable {
        private Socket socket;
        private BufferedReader in;
        private PrintWriter out;
        private int userId;
        private int groupId = -1;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                // Authenticate the user
                String loginMessage = in.readLine();
                if (loginMessage != null && loginMessage.startsWith("LOGIN:")) {
                    userId = Integer.parseInt(loginMessage.split(":")[1]);
                    logger.info("User {} connected", userId);
                }

                // Handle incoming messages
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    if (inputLine.startsWith("JOIN_GROUP:")) {
                        groupId = Integer.parseInt(inputLine.split(":")[1]);
                        logger.info("User {} joined group {}", userId, groupId);
                    } else if (groupId != -1) { // Only process messages if the user has joined a group
                        logger.info("Received message: {}", inputLine);
                        forwardMessage(userId, inputLine);
                    } else {
                        logger.warn("User {} tried to send a message without joining a group", userId);
                    }
                }
            } catch (IOException e) {
                logger.error("Client error: {}", e.getMessage(), e);
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    logger.error("Error closing socket: {}", e.getMessage(), e);
                }
                clients.remove(this); // Remove the client when they disconnect
                logger.info("User {} disconnected", userId);
            }
        }
    }

    private static void forwardMessage(int senderId, String message) {
        String[] parts = message.split(":", 3); // Split into userId:groupId:content
        if (parts.length == 3) {
            int groupId = Integer.parseInt(parts[1]);
            String content = parts[2];

            // Fetch the username for the senderId
            String username = getUserNameById(senderId);

            // Forward the message to all group members (including the sender)
            for (ClientHandler client : clients) {
                if (client.groupId == groupId) { // Send to all clients in the group
                    client.out.println(username + ":" + content); // Send username instead of userId
                }
            }
        }
    }

    // Method to fetch the username by user ID
    private static String getUserNameById(int userId) {
        try {
            UserService userService = new UserService();
            return userService.getUsernameById(userId);
        } catch (SQLException e) {
            logger.error("Error fetching username for user ID {}: {}", userId, e.getMessage());
            return "Unknown User"; // Fallback if username cannot be fetched
        }
    }
}