package com.example.sportx;

import com.example.sportx.controller.abir.SceneManager;
import com.example.sportx.websocket.ChatServer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

import com.example.sportx.services.abir.ServiceFactory;

public class CommunityApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Set the primaryStage in SceneManager
        SceneManager.setPrimaryStage(primaryStage);

        // Load the FXML and start the JavaFX application
        //Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/LoginView.fxml")));
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/chatboot_interface.fxml")));
        primaryStage.setTitle("Discover your next community");
        primaryStage.setScene(new Scene(root, 1350, 750));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}