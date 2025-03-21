package com.example.demo;
import com.example.demo.dao.DatabaseConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class AdminApplication extends Application {
    private static Stage primaryStage; // Stocke la fenêtre principale

    @Override
    public void start(Stage stage) {
        try {primaryStage = stage;
        changeScene("/com/example/demo/View/dashboard-view.fxml"); // Charge la première scène (Dashboard)
        primaryStage.setTitle("Tableau de bord Admin - SportX");

        primaryStage.show();
        // Test de la connexion
        System.out.println("🔄 Vérification de la connexion BD...");
        DatabaseConnection.getConnection();

    } catch (Exception e) {
        System.out.println("❌ Erreur lors du chargement de l'application !");
        e.printStackTrace();
    }
    }

    // Méthode pour changer de scène
    public static void changeScene(String fxmlFile) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(AdminApplication.class.getResource(fxmlFile));
            if (fxmlLoader.getLocation() == null) {
                throw new IOException("Fichier FXML non trouvé : " + fxmlFile);
            }
            Parent root = fxmlLoader.load();
            primaryStage.setScene(new Scene(root ,1450 ,750));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erreur lors du chargement de : " + fxmlFile);
        }
    }

    public static void main(String[] args) {launch(args);}

}