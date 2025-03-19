package com.example.sportx.controller.abir;

import com.example.sportx.services.abir.*;
import com.example.sportx.models.abir.Message;
import com.example.sportx.utils.abir.ApplicationContext;
import com.example.sportx.utils.abir.DatabaseConnection;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ChatController {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private int userId;
    private int groupId;
    private boolean isChatActive = false;

    private final NotificationService notificationService;
    private final UserService userService;
    private final MessageService messageService;

    @FXML
    private ListView<String> chatPane;
    @FXML
    private TextArea messageBox;
    @FXML
    private Button manageGroupMembersButton;

    private int lastMessageId = -1;

    public ChatController() throws SQLException {
        this.notificationService = new NotificationService(new GroupMemberService());
        this.userService = new UserService();
        this.messageService = new MessageService();
    }

    public void setGroupId(int groupId) throws SQLException {
        this.groupId = groupId;
        loadChatHistory();
        lastMessageId = getLastMessageId();

        // Check if the current user is the admin and set button visibility
        manageGroupMembersButton.setVisible(isAdmin(groupId));
    }

    @FXML
    private void handleManageGroupMembers(ActionEvent event) {
        System.out.println("Switching to GroupMember.fxml with group ID: " + groupId); // Debugging
        try {
            SceneManager.switchToScene("GroupMember.fxml", groupId);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load group members view.");
        }
    }


    public void initialize() {
        try {
            socket = new Socket("localhost", 5000);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out.println("LOGIN:" + userId);
            out.println("JOIN_GROUP:" + groupId);

            initializeWebSocketListener();

            // Set up a timeline to check for new messages every 2 seconds
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> checkForNewMessages()));
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();

            // Check if the current user is the admin of the group
            boolean isAdmin = GroupService.checkAdminPermission(userId, groupId);
            manageGroupMembersButton.setVisible(isAdmin);

        } catch (IOException e) {
            System.err.println("Error initializing WebSocket connection: " + e.getMessage());
            showAlert("Error", "Failed to connect to the chat server.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void checkForNewMessages() {
        try {
            int newLastMessageId = getLastMessageId();
            if (newLastMessageId > lastMessageId) {
                loadLastMessage(newLastMessageId);
                lastMessageId = newLastMessageId;
            }
        } catch (SQLException e) {
            System.err.println("Error checking new messages: " + e.getMessage());
        }
    }

    private void loadLastMessage(int messageId) {
        String query = "SELECT sender_id, content FROM messages WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, messageId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int senderId = rs.getInt("sender_id");
                String content = rs.getString("content");

                // Fetch the username for the senderId
                String username = userService.getUsernameById(senderId);

                // Only add the message to the chat pane if the current user is not the sender
                if (senderId != userId) {
                    Platform.runLater(() -> chatPane.getItems().add(username + ": " + content));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error loading last message: " + e.getMessage());
        }
    }

    private int getLastMessageId() throws SQLException {
        String query = "SELECT MAX(id) FROM messages WHERE group_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, groupId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return -1;
    }

    private void loadChatHistory() {
        try {
            if (groupId == -1) {
                System.err.println("Error: Invalid groupId.");
                return;
            }

            List<Message> messages = messageService.getMessages(groupId);
            if (messages == null || messages.isEmpty()) {
                System.err.println("Error: No messages found.");
                return;
            }

            for (Message message : messages) {
                if (message != null) {
                    // Fetch the username for the senderId
                    String username = userService.getUsernameById(message.getSenderId());
                    String content = message.getContent() != null ? message.getContent() : "[No Content]";
                    chatPane.getItems().add(username + ": " + content); // Use username instead of senderId
                }
            }
        } catch (SQLException e) {
            System.err.println("Error loading chat history: " + e.getMessage());
            showAlert("Error", "Failed to load chat history.");
        }
    }

    @FXML
    private void sendButtonAction() {
        String message = messageBox.getText().trim();
        if (!message.isEmpty() && groupId != -1) {
            try {
                messageService.sendMessage(userId, groupId, message);
                out.println(userId + ":" + groupId + ":" + message);
                chatPane.getItems().add("You: " + message);
                messageBox.clear();
                notificationService.createNotificationForGroup(groupId, "New message in the group", "MESSAGE", userId);
            } catch (SQLException e) {
                System.err.println("Error saving message to database: " + e.getMessage());
                showAlert("Error", "Failed to send message.");
            }
        }
    }

    @FXML
    private void sendMethod(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            // Prevent adding a new line in the TextArea
            event.consume();

            // Send the message
            sendButtonAction();
        }
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    private void initializeWebSocketListener() {
        new Thread(() -> {
            try {
                String response;
                while ((response = in.readLine()) != null) {
                    String finalResponse = response;
                    Platform.runLater(() -> {
                        System.out.println("Received WebSocket message: " + finalResponse);
                        // Split the message into parts: userId:groupId:content
                        String[] parts = finalResponse.split(":", 3);
                        if (parts.length == 3) {
                            int senderId = Integer.parseInt(parts[0]);
                            String content = parts[2];

                            // Fetch the username for the senderId
                            try {
                                String username = userService.getUsernameById(senderId);
                                chatPane.getItems().add(username + ": " + content); // Use username instead of senderId
                            } catch (SQLException e) {
                                System.err.println("Error fetching username: " + e.getMessage());
                                chatPane.getItems().add("User " + senderId + ": " + content); // Fallback to senderId
                            }
                        }
                    });
                }
            } catch (IOException e) {
                System.err.println("Error reading from WebSocket: " + e.getMessage());
            }
        }).start();
    }

    private boolean isAdmin(int groupId) throws SQLException {
        GroupService groupService = new GroupService();
        int currentUserId = ApplicationContext.getCurrentUser().getId();
        return groupService.checkAdminPermission(currentUserId, groupId);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}