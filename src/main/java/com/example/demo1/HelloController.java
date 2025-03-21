package com.example.demo1;

import com.example.UserDao.UserDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class HelloController {

    @FXML
    private TextField email;

    @FXML
    private TextField password;

    @FXML
    private Button submit;

    @FXML
    private Text welcomeMessage;

    @FXML
    private Label feedback;

    @FXML
    private Button submit1;

    private UserDAO userDAO;

    public HelloController() {
        this.userDAO = new UserDAO();
    }

    @FXML
    public void handleLogin() {
        feedback.setText("");

        String enteredEmail = email.getText().trim();
        String enteredPassword = password.getText().trim();

        if (enteredEmail.isEmpty() || enteredPassword.isEmpty()) {
            feedback.setText("Please fill in all fields.");
            return;
        }

        try {
            boolean isAuthenticated = userDAO.authenticate(enteredEmail, enteredPassword);
            if (isAuthenticated) {
                feedback.setText("✅ Login successful. Welcome!");
                welcomeMessage.setText("Welcome to SportConnect!");
                loadAccScene();
            } else {
                feedback.setText("❌ Invalid email or password.");
            }
        } catch (SQLException e) {
            feedback.setText("❌ Database Error: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            feedback.setText("❌ Error loading acc.fxml: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            feedback.setText("❌ Unexpected Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void handleRegister() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo1/authentification.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) submit1.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Register");
            stage.show();
        } catch (IOException e) {
            feedback.setText("❌ Error loading Authentification.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void loadAccScene() throws IOException {
        String fxmlPath = "/com/example/demo1/Acc.fxml";
        System.out.println("Attempting to load: " + fxmlPath);
        java.net.URL resourceUrl = getClass().getResource(fxmlPath);
        if (resourceUrl == null) {
            throw new IOException("Cannot find acc.fxml at path: " + fxmlPath + ". Ensure it’s in src/main/resources/com/example/demo2/");
        }

        System.out.println("Resource URL: " + resourceUrl);
        FXMLLoader loader = new FXMLLoader(resourceUrl);
        Parent root = loader.load();
        Stage stage = (Stage) submit.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Account Page");
        stage.show();
    }
}