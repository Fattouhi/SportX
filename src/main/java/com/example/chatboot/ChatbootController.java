package com.example.chatboot;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ChatbootController {
    @FXML
    private TextArea chatArea; // Chat history display
    @FXML
    private TextField inputField; // User input field

    private HttpClient httpClient = HttpClient.newHttpClient(); // HTTP client for API communication
    private ObjectMapper objectMapper = new ObjectMapper(); // JSON parser

    public void initialize() {
        // Add an event handler for the Enter key
        inputField.setOnKeyPressed(this::handleKeyPress);
    }

    private void handleKeyPress(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            sendMessage(); // Call the sendMessage method when Enter is pressed
        }
    }

    @FXML
    private void sendMessage() {
        String userInput = inputField.getText().trim(); // Get user input
        if (!userInput.isEmpty()) {
            // Display user's message in the chat area
            chatArea.appendText("You: " + userInput + "\n");

            // Clear the input field
            inputField.clear();

            // Send the message to the Python chatbot API
            sendMessageToChatbot(userInput);
        }
    }

    private void sendMessageToChatbot(String userInput) {
        try {
            // Create the JSON request body
            String requestBody = String.format("{\"message\": \"%s\"}", userInput);

            // Create the HTTP POST request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://127.0.0.1:5001/chat")) // Python API endpoint (port 5001)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            // Send the request and get the response
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // Parse the JSON response
            JsonNode jsonNode = objectMapper.readTree(response.body());
            String chatbotResponse = jsonNode.get("response").asText(); // Extract the "response" field

            // Display the chatbot's response in the chat area
            chatArea.appendText("Chatbot: " + chatbotResponse + "\n");
        } catch (Exception e) {
            e.printStackTrace();
            chatArea.appendText("Error communicating with the chatbot.\n");
        }
    }
}