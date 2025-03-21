package com.example.appli;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/com/example/appli/fxml/EvenementInterface.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 600, 600); // Taille ajustée
        stage.setScene(scene);
        stage.setTitle("Calendrier Événements Sportifs");
        stage.setX(0); // Positionner la fenêtre en haut à gauche
        stage.setY(0);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}