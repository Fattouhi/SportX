package com.example.appli.controller;

import com.example.appli.dao.EventDAO;
import com.example.appli.model.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.LocalDate;

public class AddEventController {

    @FXML private TextField titleField;
    @FXML private TextField sportField;
    @FXML private TextField locationField;
    @FXML private DatePicker datePicker;
    @FXML private TextArea descriptionField;
    @FXML private TextField statusField;
    @FXML private TextField imageUrlField; // Nouveau champ
    @FXML private Button submitButton;

    private EventDAO eventDAO = new EventDAO();

    @FXML
    private void initialize() {
        // Rien à initialiser pour l'instant
    }

    @FXML
    private void handleAddEvent() {
        if (titleField.getText().isEmpty() || sportField.getText().isEmpty() ||
                locationField.getText().isEmpty() || datePicker.getValue() == null ||
                statusField.getText().isEmpty() || imageUrlField.getText().isEmpty()) {
            showAlert("Error", "Please fill all required fields!");
            return;
        }

        Event event = new Event(
                titleField.getText(),
                sportField.getText(),
                locationField.getText(),
                datePicker.getValue(),
                imageUrlField.getText(),    // imageUrl
                descriptionField.getText(), // description
                statusField.getText()       // status
        );

        try {
            eventDAO.addEvent(event);
            showAlert("Success", "Event added successfully!");
            Stage stage = (Stage) submitButton.getScene().getWindow();
            stage.close();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to add event to database.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(title.equals("Error") ? Alert.AlertType.ERROR : Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}