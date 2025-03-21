package com.example.demo1;
import com.example.UserDao.UserDAO;
import com.example.model.User;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class AddUserController {

    @FXML
    private TextField nom;

    @FXML
    private TextField prenom;

    @FXML
    private TextField mail;

    @FXML
    private PasswordField passwd;

    @FXML
    private PasswordField confirmPasswd;

    @FXML
    private TextField telephone;

    @FXML
    private TextField pays;

    @FXML
    private ComboBox<String> genre;

    @FXML
    private DatePicker dateNaissance;

    @FXML
    private TextField besamee;

    @FXML
    private CheckBox termsCheckBox;

    @FXML
    private Button btnCreateAccount;

    @FXML
    private Label errorLabel;

    private UserDAO userDAO;

    public AddUserController() {
        this.userDAO = new UserDAO();
    }

    @FXML
    public void initialize() {
        if (genre != null) {
            genre.setItems(FXCollections.observableArrayList("Male", "Female", "Other"));
        } else {
            System.err.println("Genre ComboBox is null in initialize!");
        }
    }

    @FXML
    void onCreateAccount(ActionEvent event) {
        errorLabel.setText("");

        if (!termsCheckBox.isSelected()) {
            errorLabel.setText("You must agree to the terms!");
            return;
        }

        String firstName = nom.getText().trim();
        String lastName = prenom.getText().trim();
        String email = mail.getText().trim();
        String password = passwd.getText().trim();
        String confirmPassword = confirmPasswd.getText().trim();
        String phone = telephone.getText().trim();
        String country = pays.getText().trim();
        String gender = genre.getValue();
        java.sql.Date birthDate = dateNaissance.getValue() != null ? java.sql.Date.valueOf(dateNaissance.getValue()) : null;
        String besameeValue = besamee.getText().trim();

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || phone.isEmpty() || country.isEmpty() || gender == null || birthDate == null || besameeValue.isEmpty()) {
            errorLabel.setText("Please fill all fields!");
            return;
        }

        if (!password.equals(confirmPassword)) {
            errorLabel.setText("Passwords do not match!");
            return;
        }

        try {
            if (userDAO.emailExists(email)) {
                errorLabel.setText("❌ Email '" + email + "' is already registered!");
                return;
            }

            User newUser = new User(0, firstName, lastName, email, password, phone, country, gender, birthDate, besameeValue);
            userDAO.addUser(newUser);
            loadAccScene();
        } catch (SQLException e) {
            errorLabel.setText("❌ Database Error: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            errorLabel.setText("❌ Error loading acc.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void loadAccScene() throws IOException {
        String fxmlPath = "/com/example/demo1/Acc.fxml";
        System.out.println("Attempting to load: " + fxmlPath);
        java.net.URL resourceUrl = getClass().getResource(fxmlPath);
        if (resourceUrl == null) {
            throw new IOException("Cannot find acc.fxml at path: " + fxmlPath);
        }

        FXMLLoader loader = new FXMLLoader(resourceUrl);
        Parent root = loader.load();
        Stage stage = (Stage) btnCreateAccount.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Account Page");
        stage.show();
    }
}