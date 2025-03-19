package com.example.sportxnews.controller;

import com.example.sportxnews.FlashscoreScraper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class LiveScoresController {

    @FXML
    private VBox liveScoresContainer;

    @FXML
    private void initialize() {
        updateLiveScores(); // Fetch and display live scores when the screen loads
    }

    @FXML
    private void updateLiveScores() {
        if (liveScoresContainer != null) {
            liveScoresContainer.getChildren().clear(); // Clear previous scores

            // Fetch live scores using the FlashscoreScraper
            String liveScoresData = FlashscoreScraper.fetchLiveScores();
            System.out.println("Live Scores: " + liveScoresData);

            if (liveScoresData.startsWith("Failed") || liveScoresData.startsWith("No live matches")) {
                // Display an error message if fetching fails
                Label errorLabel = new Label(liveScoresData);
                errorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
                liveScoresContainer.getChildren().add(errorLabel);
            } else {
                // Add the live scores to the UI
                addLiveScoreCards(liveScoresData);
            }
        } else {
            System.out.println("liveScoresContainer is null");
        }
    }

    private void addLiveScoreCards(String liveScores) {
        if (liveScores != null && !liveScores.isEmpty()) {
            String[] scores = liveScores.split("\n");
            for (String score : scores) {
                Label scoreLabel = new Label(score);
                scoreLabel.setStyle("-fx-font-size: 14px; -fx-padding: 5;");
                liveScoresContainer.getChildren().add(scoreLabel);
            }
        }
    }

    @FXML
    private void goToMainScreen() {
        try {
            // Load the main screen FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainScreen.fxml"));
            Parent root = loader.load();

            // Create a new scene and show it
            Stage stage = (Stage) liveScoresContainer.getScene().getWindow();
            stage.setScene(new Scene(root, 1100, 700));
            stage.setTitle("SportX - Actualités");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}