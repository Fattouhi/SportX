package com.example.demo.controllers;

import com.example.demo.dao.AdminActionDAO;
import com.example.demo.dao.BannedUsersDAO;
import com.example.demo.dao.DatabaseConnection;
import com.example.demo.dao.UsersDAO;
import com.example.demo.model.Users;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Objects;

public class UserManageController {
    @FXML private TableView<Users> userTable;
    @FXML private TableColumn<Users, String> colPhone;
    @FXML private TableColumn<Users, String> colFirstName;
    @FXML private TableColumn<Users, String> colLastName;
    @FXML private TableColumn<Users, String> colEmail;
    @FXML private TableColumn<Users, String> colCountry;
    @FXML private TableColumn<Users, String> colBan;
    @FXML private TextField searchField;
    @FXML private AnchorPane mainLayoutContainer;
    @FXML private ChoiceBox<String> statusFilterChoiceBox;

    private ObservableList<Users> userList;

    @FXML
    public void initialize() {
        System.out.println("📋 Interface Gestion des Utilisateurs chargée !");

        // Configurer les colonnes
        colPhone.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getPhone())));
        colFirstName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirstName()));
        colLastName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLastName()));
        colEmail.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        colCountry.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getCountry())));

        // Configurer la colonne d'action (bouton Bannir)
        colBan.setCellFactory(param -> new ButtonTableCell<>("Bannir", user -> {
            banUser((Users) user);
            return null; // Retour explicite null pour correspondre à Void
        }));
        // Initialiser le filtre par statut
        if (statusFilterChoiceBox.getItems().isEmpty()) {
            statusFilterChoiceBox.setItems(FXCollections.observableArrayList("Tous", "Active", "Banned"));
            statusFilterChoiceBox.setValue("Tous");
            statusFilterChoiceBox.setOnAction(event -> {
                try {
                    filterUsersByStatus();
                } catch (SQLException e) {
                    e.printStackTrace();
                    showAlert("Erreur", "Impossible de filtrer les utilisateurs : " + e.getMessage());
                }
            });
        }

        loadUsers();
    }

    @FXML
    private void goToDashboard() {
        try {
            Stage stage = (Stage) userTable.getScene().getWindow();
            stage.setScene(new javafx.scene.Scene(
                    javafx.fxml.FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/demo/View/dashboard-view.fxml")))
            ));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadUsers() {
        userList = FXCollections.observableArrayList(UsersDAO.getAllUsers());

        // Vérifier les données récupérées
        for (Users user : userList) {
            System.out.println(user.getFirstName() + " - " + user.getEmail());
        }

        userTable.setItems(userList);
    }

    @FXML
    private void addUser() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Ajouter un Utilisateur");
        dialog.setHeaderText("Entrez les informations du nouvel utilisateur");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        TextField phoneField = new TextField();
        TextField firstNameField = new TextField();
        TextField lastNameField = new TextField();
        TextField emailField = new TextField();
        PasswordField passwordField = new PasswordField();
        TextField countryField = new TextField();

        grid.add(new Label("Téléphone:"), 0, 0);
        grid.add(phoneField, 1, 0);
        grid.add(new Label("Prénom:"), 0, 1);
        grid.add(firstNameField, 1, 1);
        grid.add(new Label("Nom:"), 0, 2);
        grid.add(lastNameField, 1, 2);
        grid.add(new Label("Email:"), 0, 3);
        grid.add(emailField, 1, 3);
        grid.add(new Label("Mot de passe:"), 0, 4);
        grid.add(passwordField, 1, 4);
        grid.add(new Label("Pays:"), 0, 5);
        grid.add(countryField, 1, 5);

        dialog.getDialogPane().setContent(grid);

        dialog.showAndWait().ifPresent(response -> {
            String phone = phoneField.getText().trim();
            String firstName = firstNameField.getText().trim();
            String lastName = lastNameField.getText().trim();
            String email = emailField.getText().trim();
            String password = passwordField.getText().trim();
            String country = countryField.getText().trim();

            if (!phone.isEmpty() && !firstName.isEmpty() && !lastName.isEmpty() &&
                    !email.isEmpty() && !password.isEmpty() && !country.isEmpty()) {
                Users newUser = new Users(0, Integer.parseInt(phone), firstName, lastName, email, password, Integer.parseInt(country));
                UsersDAO.addUser(newUser);

                loadUsers();
                showAlert("Succès", "Utilisateur ajouté avec succès !");
            } else {
                showAlert("Erreur", "Tous les champs doivent être remplis !");
            }
        });
    }

    @FXML
    private void editUser() {
        Users selectedUser = userTable.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            showAlert("Erreur", "Veuillez sélectionner un utilisateur !");
            return;
        }

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Modifier l'Utilisateur");
        dialog.setHeaderText("Modifiez les informations de l'utilisateur");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField userIdField = new TextField(String.valueOf(selectedUser.getUserId()));
        TextField phoneField = new TextField(String.valueOf(selectedUser.getPhone()));
        TextField firstNameField = new TextField(selectedUser.getFirstName());
        TextField lastNameField = new TextField(selectedUser.getLastName());
        TextField emailField = new TextField(selectedUser.getEmail());
        PasswordField passwordField = new PasswordField();
        TextField countryField = new TextField(String.valueOf(selectedUser.getCountry()));

        grid.add(new Label("ID:"), 0, 0);
        grid.add(userIdField, 1, 0);
        grid.add(new Label("Téléphone:"), 0, 1);
        grid.add(phoneField, 1, 1);
        grid.add(new Label("Prénom:"), 0, 2);
        grid.add(firstNameField, 1, 2);
        grid.add(new Label("Nom:"), 0, 3);
        grid.add(lastNameField, 1, 3);
        grid.add(new Label("Email:"), 0, 4);
        grid.add(emailField, 1, 4);
        grid.add(new Label("Mot de passe:"), 0, 5);
        grid.add(passwordField, 1, 5);
        grid.add(new Label("Pays:"), 0, 6);
        grid.add(countryField, 1, 6);

        dialog.getDialogPane().setContent(grid);

        dialog.showAndWait().ifPresent(response -> {
            selectedUser.setUserId(Integer.parseInt(userIdField.getText().trim()));
            if (!phoneField.getText().trim().isEmpty()) {
                selectedUser.setPhone(Integer.parseInt(phoneField.getText().trim()));
            }
            if (!firstNameField.getText().trim().isEmpty()) {
                selectedUser.setFirstName(firstNameField.getText().trim());
            }
            if (!lastNameField.getText().trim().isEmpty()) {
                selectedUser.setLastName(lastNameField.getText().trim());
            }
            if (!emailField.getText().trim().isEmpty()) {
                selectedUser.setEmail(emailField.getText().trim());
            }
            if (!passwordField.getText().trim().isEmpty()) {
                selectedUser.setPassword(passwordField.getText().trim());
            }
            if (!countryField.getText().trim().isEmpty()) {
                selectedUser.setCountry(Integer.parseInt(countryField.getText().trim()));
            }

            UsersDAO.updateUser(selectedUser);
            loadUsers();
            showAlert("Succès", "Utilisateur modifié avec succès !");
        });
    }

    // Dans la méthode deleteUser() de UserManageController.java
    @FXML
    private void deleteUser() {
        Users selectedUser = userTable.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            showAlert("Erreur", "Veuillez sélectionner un utilisateur !");
            return;
        }

        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "Voulez-vous vraiment supprimer cet utilisateur ?", ButtonType.YES, ButtonType.NO);
        confirmation.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                UsersDAO.deleteUser(selectedUser.getUserId()); // Utilise userId au lieu de email
                loadUsers();
                showAlert("Succès", "Utilisateur supprimé avec succès !");
            }
        });
    }

    @FXML
    private void searchUsers() {
        String keyword = searchField.getText().toLowerCase().trim();
        ObservableList<Users> filteredList = FXCollections.observableArrayList();

        for (Users user : userList) {
            boolean matches = user.getFirstName().toLowerCase().contains(keyword) ||
                    user.getLastName().toLowerCase().contains(keyword) ||
                    user.getEmail().toLowerCase().contains(keyword) ||
                    String.valueOf(user.getPhone()).contains(keyword);

            if (matches) {
                filteredList.add(user);
            }
        }

        userTable.setItems(filteredList);
    }

    // Filtrer les utilisateurs par statut (Active/Banned)
    @FXML
    private void filterUsersByStatus() throws SQLException {
        String status = statusFilterChoiceBox.getValue();
        ObservableList<Users> filteredList = FXCollections.observableArrayList();

        for (Users user : userList) {
            boolean isBanned = UsersDAO.isUserBanned(user.getUserId());
            if ("Tous".equals(status) ||
                    ("Active".equals(status) && !isBanned) ||
                    ("Banned".equals(status) && isBanned)) {
                filteredList.add(user);
            }
        }
        userTable.setItems(filteredList);
    }

    // Méthode pour bannir un utilisateur
    private void banUser(Users user) {
        // Demander la raison du bannissement via une boîte de dialogue
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Bannir Utilisateur");
        dialog.setHeaderText("Bannir " + user.getFirstName() + " " + user.getLastName());
        dialog.setContentText("Raison du bannissement :");

        dialog.showAndWait().ifPresent(banReason -> {
            if (!banReason.trim().isEmpty()) {
                int adminId = 1; // Remplacez par l'ID de l'administrateur connecté (à implémenter)
                int userId = user.getUserId();

                // Bannir l'utilisateur dans banned_users
                BannedUsersDAO bannedUsersDAO = null;
                try {
                    bannedUsersDAO = new BannedUsersDAO(DatabaseConnection.getConnection());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                boolean banned = bannedUsersDAO.banUser(userId, adminId, banReason);

                // Réinitialiser le mot de passe de l'utilisateur dans users
                UsersDAO.resetPasswordOnBan(userId);

                // Enregistrer l'action dans admin_actions
                AdminActionDAO adminActionDAO = null;
                try {
                    adminActionDAO = new AdminActionDAO(DatabaseConnection.getConnection());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                boolean logged = adminActionDAO.logAdminAction(adminId, "ban_user", userId);

                if (banned && logged) {
                    user.setBanned(true); // Mise à jour locale (optionnel)
                    showAlert("Succès", "Utilisateur banni avec succès ! Son mot de passe a été supprimé.");
                    loadUsers(); // Rafraîchir la liste
                    try {
                        filterUsersByStatus(); // Appliquer le filtre après la mise à jour
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    showAlert("Erreur", "Échec du bannissement.");
                }
            } else {
                showAlert("Erreur", "Veuillez spécifier une raison pour le bannissement.");
            }
        });
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Classe personnalisée pour le bouton dans la TableView
    private static class ButtonTableCell<S> extends TableCell<S, String> {
        private final Button button;

        public ButtonTableCell(String text, javafx.util.Callback<S, Void> action) {
            this.button = new Button(text);
            this.button.setOnAction(event -> {
                S item = getTableView().getItems().get(getIndex());
                action.call(item);
            });
            this.button.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-padding: 5px 10px; -fx-background-radius: 5px;");
            this.button.setCursor(Cursor.HAND);
        }

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setGraphic(null);
            } else {
                setGraphic(button);
            }
        }
    }


}