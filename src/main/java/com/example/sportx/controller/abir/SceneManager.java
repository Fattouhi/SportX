package com.example.sportx.controller.abir;

import com.example.sportx.utils.abir.ApplicationContext;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class SceneManager {
    private static Stage primaryStage;

    public static void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }

    public static void switchToScene(String fxmlFile, Object controllerData) throws IOException, SQLException {
        System.out.println("Switching to scene: " + fxmlFile); // Debugging
        System.out.println("Controller data: " + controllerData); // Debugging

        FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource("/" + fxmlFile));
        Parent root = loader.load();

        if (controllerData != null) {
            Object controller = loader.getController();

            // Debugging: Print the controller class name
            System.out.println("Controller class: " + controller.getClass().getSimpleName());

            // Handle ChatController
            if (controller instanceof ChatController) {
                System.out.println("Setting group ID and user ID for ChatController"); // Debugging
                ((ChatController) controller).setGroupId((Integer) controllerData);
                ((ChatController) controller).setUserId(ApplicationContext.getCurrentUser().getId());
            }

            // Handle GroupMemberController
            if (controller instanceof GroupMemberController) {
                System.out.println("Setting group ID for GroupMemberController"); // Debugging
                ((GroupMemberController) controller).setGroupId((Integer) controllerData);
            }
        }

        Scene scene = new Scene(root, 1350, 750);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}