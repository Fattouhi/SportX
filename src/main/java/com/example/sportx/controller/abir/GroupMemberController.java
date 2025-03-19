package com.example.sportx.controller.abir;

import com.example.sportx.models.abir.GroupMember;
import com.example.sportx.services.abir.GroupMemberService;
import com.example.sportx.services.abir.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.util.List;


public class GroupMemberController {
    @FXML
    private TextField userInputField; // TextField to enter the username to remove

    private int groupId;
    private final GroupMemberService groupMemberService = new GroupMemberService();
    private final UserService userService = new UserService(); // Add UserService to fetch usernames

    public GroupMemberController() throws SQLException {
    }

    public void setGroupId(int groupId) {
        if (groupId <= 0) {
            System.err.println("Invalid group ID: " + groupId);
            return;
        }
        this.groupId = groupId;
        System.out.println("Group ID set in GroupMemberController: " + groupId); // Debugging
    }

    @FXML
    private void handleRemoveMember(ActionEvent event) {
        String username = userInputField.getText().trim();

        if (username.isEmpty()) {
            showAlert("Error", "Please enter a username.");
            return;
        }

        try {
            // Fetch the user ID from the username
            int userId = userService.getUserIdByUsername(username);

            if (userId == -1) {
                showAlert("Error", "User not found.");
                return;
            }

            // Check if the user is a member of the group
            boolean isMember = groupMemberService.isMember(userId, groupId);

            if (!isMember) {
                showAlert("Error", "The user is not a member of this group.");
                return;
            }

            // Remove the member from the group
            groupMemberService.removeMember(userId, groupId);
            showAlert("Success", "Member removed successfully.");
            userInputField.clear(); // Clear the input field after removal
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to remove member. Please try again.");
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