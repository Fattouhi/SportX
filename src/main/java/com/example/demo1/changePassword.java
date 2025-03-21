package com.example.demo1;

import com.example.UserDao.UserDAO;
import com.example.model.User;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class changePassword {

    @FXML
    private TextField txtNom; // Matches FXML

    @FXML
    private TextField txtPrenom; // Matches FXML

    @FXML
    private TextField txtEmail; // Matches FXML

    @FXML
    private TextField txtTelephone; // Matches FXML

    @FXML
    private TextField txtPays; // Matches FXML

    @FXML
    private ChoiceBox<String> choiceGenre; // Matches FXML (changed from ComboBox)

    @FXML
    private DatePicker dateNaissance; // Matches FXML

    @FXML
    private TextField txtPassword; // Added for password reset

    @FXML
    private TextField txtNewPassword; // Added for password reset

    @FXML
    private TextField txtConfirmPassword; // Added for password reset

    @FXML
    private Button btnValider; // Matches FXML

    @FXML
    private Button btnSavePassword; // Matches FXML

    @FXML
    private Label statusLabel; // Add if you want feedback (not in FXML yet)

    private UserDAO userDAO;
    private User currentUser;

    public changePassword() {
        this.userDAO = new UserDAO();
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
        loadUserData();
    }

    @FXML
    public void initialize() {
        choiceGenre.setItems(FXCollections.observableArrayList("Male", "Female", "Other"));
    }

    private void loadUserData() {
        if (currentUser != null) {
            txtNom.setText(currentUser.getNom());
            txtPrenom.setText(currentUser.getPrenom());
            txtEmail.setText(currentUser.getEmail());
            txtEmail.setEditable(false); // Email typically not editable
            txtTelephone.setText(currentUser.getTelephone());
            txtPays.setText(currentUser.getPays());
            choiceGenre.setValue(currentUser.getGenre());
            dateNaissance.setValue(currentUser.getDateNaissance() != null ? currentUser.getDateNaissance().toLocalDate() : null);
        }
    }

    @FXML
    public void updateProfile() { // Matches FXML #updateProfile
        String firstName = txtNom.getText().trim();
        String lastName = txtPrenom.getText().trim();
        String phone = txtTelephone.getText().trim();
        String country = txtPays.getText().trim();
        String gender = choiceGenre.getValue();
        java.sql.Date birthDate = dateNaissance.getValue() != null ? java.sql.Date.valueOf(dateNaissance.getValue()) : null;

        if (firstName.isEmpty() || lastName.isEmpty() || phone.isEmpty() || country.isEmpty() || gender == null || birthDate == null) {
            showError("Please fill all required fields!");
            return;
        }

        try {
            currentUser.setNom(firstName);
            currentUser.setPrenom(lastName);
            currentUser.setTelephone(phone);
            currentUser.setPays(country);
            currentUser.setGenre(gender);
            currentUser.setDateNaissance(birthDate);

            userDAO.updateUserProfile(currentUser.getEmail(), firstName, lastName, currentUser.getPassword(), phone, country, birthDate, currentUser.getBesamee());
            showSuccess("✅ Profile updated successfully!");
        } catch (SQLException e) {
            showError("❌ Database Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void resetPassword() { // Matches FXML #resetPassword
        String currentPass = txtPassword.getText().trim();
        String newPass = txtNewPassword.getText().trim();
        String confirmPass = txtConfirmPassword.getText().trim();

        if (currentPass.isEmpty() || newPass.isEmpty() || confirmPass.isEmpty()) {
            showError("Please fill all password fields!");
            return;
        }

        if (!newPass.equals(confirmPass)) {
            showError("New passwords do not match!");
            return;
        }

        try {
            if (!userDAO.authenticate(currentUser.getEmail(), currentPass)) {
                showError("❌ Current password is incorrect!");
                return;
            }

            currentUser.setPassword(newPass);
            userDAO.updateUserProfile(currentUser.getEmail(), currentUser.getNom(), currentUser.getPrenom(), newPass, currentUser.getTelephone(), currentUser.getPays(), currentUser.getDateNaissance(), currentUser.getBesamee());
            showSuccess("✅ Password updated successfully!");
        } catch (SQLException e) {
            showError("❌ Database Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void goBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo1/acc.fxml")); // Corrected path
            Parent root = loader.load();
            Stage stage = (Stage) btnValider.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Account Page");
            stage.show();
        } catch (IOException e) {
            showError("❌ Error loading acc.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}