package com.example.sportxnews;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;

public class HelloApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("Loading FXML...");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hello-view.fxml"));
        Parent root = loader.load();
        System.out.println("FXML loaded successfully.");

        // Configurer la scène et afficher la fenêtre
        primaryStage.setTitle("SportX - Accueil");
        primaryStage.setScene(new Scene(root, 1100, 700)); // Ajustez la taille selon vos besoins
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
