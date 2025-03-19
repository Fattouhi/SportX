package com.example.sportx.controller.abir;

import com.example.sportx.models.abir.Group;
import com.example.sportx.services.abir.GroupMemberService;
import com.example.sportx.utils.abir.ApplicationContext;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class MyGroupsController {
    @FXML
    private ListView<Group> groupListView;

    private final GroupMemberService groupMemberService = new GroupMemberService();

    public MyGroupsController() throws SQLException {
    }

    @FXML
    public void initialize() {
        // Get the current user ID from ApplicationContext
        int userId = ApplicationContext.getCurrentUser().getId();
        loadUserGroups(userId); // Load groups when the view is initialized

        // Set a custom cell factory to display group names and categories
        groupListView.setCellFactory(param -> new ListCell<Group>() {
            @Override
            protected void updateItem(Group group, boolean empty) {
                super.updateItem(group, empty);
                if (empty || group == null) {
                    setText(null);
                } else {
                    setText(group.getName() + " - " + group.getCategory());
                }
            }
        });

        // Bind the handleGroupSelection method to the ListView
        groupListView.setOnMouseClicked(this::handleGroupSelection);
    }

    private void loadUserGroups(int userId) {
        try {
            List<Group> groups = groupMemberService.getGroupsByUserId(userId);
            groupListView.getItems().setAll(groups);
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load user groups.");
        }
    }

    @FXML
    private void handleGroupSelection(MouseEvent event) {
        Group selectedGroup = groupListView.getSelectionModel().getSelectedItem();
        if (selectedGroup != null) {
            try {
                SceneManager.switchToScene("ChatView.fxml", selectedGroup.getId());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    private void handleJoinGroup(ActionEvent event) {
        try {
            SceneManager.switchToScene("Community-View.fxml", null);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load the community view.");
        }
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}