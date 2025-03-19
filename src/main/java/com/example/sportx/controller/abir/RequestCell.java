package com.example.sportx.controller.abir;

import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;

public class RequestCell extends ListCell<String> {
    private final HBox hbox = new HBox();
    private final Text requestText = new Text();
    private final Button acceptButton = new Button("Accept");
    private final Button refuseButton = new Button("Refuse");

    public RequestCell(AdminApprovalController controller) {
        super();

        // Set up the layout
        hbox.setSpacing(10);
        hbox.getChildren().addAll(requestText, acceptButton, refuseButton);
        HBox.setHgrow(requestText, Priority.ALWAYS);

        // Handle Accept button click
        acceptButton.setOnAction(event -> {
            String request = getItem();
            if (request != null) {
                // Call the controller's approveRequest method with the request
                controller.approveRequest(request);
            }
        });

        // Handle Refuse button click
        refuseButton.setOnAction(event -> {
            String request = getItem();
            if (request != null) {
                // Call the controller's rejectRequest method with the request
                controller.rejectRequest(request);
            }
        });
    }

    @Override
    protected void updateItem(String request, boolean empty) {
        super.updateItem(request, empty);

        if (empty || request == null) {
            setText(null);
            setGraphic(null);
        } else {
            requestText.setText(request); // ✅ Corrige l'affichage
            setGraphic(hbox);
        }
    }

}