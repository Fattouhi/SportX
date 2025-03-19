package com.example.sportxnews.controller;

import com.example.sportxnews.NewsFetcher;
import com.example.sportxnews.SportsApiClient;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.application.HostServices;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.application.Platform;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainScreenController {

    @FXML
    private ListView<String> categoryListView;

    @FXML
    private TextField searchField;

    @FXML
    private Button homeButton;

    @FXML
    private VBox newsContainer;

    @FXML
    private VBox liveScoresContainer;

    private ObservableList<String> categories = FXCollections.observableArrayList();
    private HostServices hostServices;
    private ScheduledExecutorService scheduler;

    public void setHostServices(HostServices hostServices) {
        this.hostServices = hostServices;
    }

    @FXML
    private void goToHome() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/hello-view.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) homeButton.getScene().getWindow();
            stage.setScene(new Scene(root, 1100, 700));
            stage.setTitle("SportX - Accueil");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goToLiveScores() {
        try {
            // Load the LiveScoresScreen FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LiveScoresScreen.fxml"));
            Parent root = loader.load();

            // Create a new scene and show it
            Stage stage = new Stage();
            stage.setTitle("SportX - Live Scores");
            stage.setScene(new Scene(root, 1100, 700));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isValidUrl(String url) {
        try {
            new URL(url).toURI(); // Try to create a URL object
            return true;
        } catch (Exception e) {
            return false; // URL is invalid
        }
    }
    @FXML
    public void initialize() {
        categories.addAll(
                "Football", "Basketball", "Tennis", "Volleyball", "Baseball", "Cricket",
                "Rugby", "Golf", "Table Tennis", "Badminton", "Handball", "Boxing",
                "Wrestling", "Judo", "Karate", "Taekwondo", "Mixed Martial Arts",
                "Swimming", "Water Polo", "Diving", "Rowing", "Surfing", "Sailing",
                "Kayaking", "Skiing", "Snowboarding", "Ice Hockey", "Figure Skating",
                "Speed Skating", "Curling", "Formula One", "MotoGP", "Rally", "NASCAR",
                "Athletics", "Cycling", "Gymnastics", "Archery", "Equestrian", "Fencing",
                "Weightlifting", "Shooting", "Skateboarding", "Climbing", "Triathlon"
        );

        categoryListView.setItems(categories);

        categoryListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                updateNewsFeed(newValue.toLowerCase());
            }
        });

        FilteredList<String> filteredCategories = new FilteredList<>(categories, p -> true);
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredCategories.setPredicate(category -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                return category.toLowerCase().contains(newValue.toLowerCase());
            });
        });

        SortedList<String> sortedCategories = new SortedList<>(filteredCategories);
        categoryListView.setItems(sortedCategories);

        startLiveScoresScheduler();
    }

    private void startLiveScoresScheduler() {
        scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> {
            Platform.runLater(() -> {
                updateLiveScores("133604"); // ID de l'équipe Arsenal
            });
        }, 0, 1, TimeUnit.MINUTES);
    }

    public void stopScheduler() {
        if (scheduler != null) {
            scheduler.shutdown();
        }
    }

    private void updateLiveScores(String teamId) {
        if (liveScoresContainer != null) {
            liveScoresContainer.getChildren().clear();
            String liveScoresData = SportsApiClient.fetchNextEvents(teamId);
            System.out.println("Événements à venir: " + liveScoresData);

            if (liveScoresData.startsWith("Failed") || liveScoresData.startsWith("No events")) {
                // Afficher un message d'erreur
                Label errorLabel = new Label(liveScoresData);
                liveScoresContainer.getChildren().add(errorLabel);
            } else {
                // Ajouter les événements à venir à l'interface utilisateur
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
                scoreLabel.setStyle("-fx-font-size: 14px;");
                liveScoresContainer.getChildren().add(scoreLabel);
            }
        }
    }

    private VBox createLiveScoreCard(String sport, String score) {
        VBox card = new VBox(10);
        card.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #CCCCCC; -fx-border-radius: 10px; -fx-padding: 15px;");
        Label sportLabel = new Label("Sport: " + sport);
        sportLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");
        Label scoreLabel = new Label(score);
        scoreLabel.setStyle("-fx-font-size: 14px;");
        card.getChildren().addAll(sportLabel, scoreLabel);
        return card;
    }

    private void updateNewsFeed(String sport) {
        newsContainer.getChildren().clear();
        String newsData = NewsFetcher.fetchSportsNews(sport);
        addNewsCards(sport, newsData);
    }

    private void addNewsCards(String category, String news) {
        if (news != null && !news.isEmpty()) {
            String[] newsLines = news.split("\n");
            for (String line : newsLines) {
                String[] parts = line.split(":", 3); // Split into 3 parts only

                if (parts.length == 3) {
                    String title = parts[0].trim();
                    String link = parts[1].trim() + ":" + parts[2].substring(0, parts[2].indexOf(":")).trim(); // Fix broken link
                    String imageUrl = parts[2].substring(parts[2].indexOf(":") + 1).trim(); // Extract image URL

                    // Fix malformed URLs (e.g., prepend "https:" if missing)
                    if (imageUrl.startsWith("//")) {
                        imageUrl = "https:" + imageUrl;
                    }

                    VBox card = createNewsCard(category, title, link, imageUrl);
                    newsContainer.getChildren().add(card);
                }
            }
        }
    }



    private VBox createNewsCard(String category, String title, String link, String imageUrl) {
        System.out.println("Image URL: " + imageUrl); // Debugging log

        VBox card = new VBox(10);
        card.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #CCCCCC; -fx-border-radius: 10px; -fx-padding: 15px;");

        Label categoryLabel = new Label("Catégorie: " + category);
        categoryLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");

        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        // Handle Image Loading Safely
        ImageView imageView = new ImageView();
        try {
            // Validate the image URL
            if (isValidUrl(imageUrl)) {
                Image image = new Image(imageUrl, 200, 100, false, false, true); // Background loading
                imageView.setImage(image);
            } else {
                // Use a default image if the URL is invalid
                System.err.println("Invalid image URL: " + imageUrl);
                Image defaultImage = new Image("sportx_transparent.png", 200, 100, false, false); // Default image
                imageView.setImage(defaultImage);
            }
        } catch (IllegalArgumentException e) {
            // Handle invalid URLs
            System.err.println("Invalid image URL: " + imageUrl);
            Image defaultImage = new Image("sportx_transparent.png", 200, 100, false, false); // Default image
            imageView.setImage(defaultImage);
        }

        // Button to open in external browser (Optional)
        Button openBrowserButton = new Button("Lire l'article");
        openBrowserButton.setOnAction(event -> {
            try {
                java.awt.Desktop.getDesktop().browse(new java.net.URI(link));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        card.getChildren().addAll(categoryLabel, titleLabel, imageView, openBrowserButton);
        return card;
    }

    // Method to open link in WebView inside JavaFX
    private void openInWebView(String url) {
        Stage webStage = new Stage();
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();
        webEngine.load(url);

        StackPane root = new StackPane(webView);
        Scene scene = new Scene(root, 800, 600);
        webStage.setScene(scene);
        webStage.setTitle("News Article");
        webStage.show();
    }

    @FXML
    private void showFootballScores() {
        updateLiveScores("football");
    }

    @FXML
    private void showBasketballScores() {
        updateLiveScores("basketball");
    }

    @FXML
    private void showTennisScores() {
        updateLiveScores("tennis");
    }

    @FXML
    private void showVolleyballScores() {
        updateLiveScores("volleyball");
    }

    @FXML
    private void showRugbyScores() {
        updateLiveScores("rugby");
    }


}