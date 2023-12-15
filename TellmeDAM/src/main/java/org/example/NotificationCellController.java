package org.example;

import javafx.scene.control.ListCell;

public class NotificationCellController extends ListCell<String> {
    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            setText("The user " + item + " has sent a message!");
            setStyle("-fx-background-color: #f0f8ff; -fx-padding: 10px;");
        }
    }
}
