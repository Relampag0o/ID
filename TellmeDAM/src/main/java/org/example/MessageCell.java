package org.example;

import javafx.geometry.Pos;
import javafx.scene.control.ListCell;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class MessageCell extends ListCell<Message> {

    @Override
    protected void updateItem(Message message, boolean empty) {
        super.updateItem(message, empty);

        if (empty || message == null) {
            setText(null);
            setGraphic(null);
        } else {
            FlowPane flowPane = new FlowPane();
            Text messageText = new Text(message.getContent());
            messageText.setFont(Font.font("Arial", FontWeight.NORMAL, 12));

            if (message.getIdsender() == App.userLogged.getId()) {
                messageText.setFill(Color.BLUE);
                flowPane.setAlignment(Pos.CENTER_RIGHT);
            } else {
                messageText.setFill(Color.GREEN);
                flowPane.setAlignment(Pos.CENTER_LEFT);
            }

            flowPane.getChildren().add(messageText);
            setGraphic(flowPane);
        }
    }
}