package org.example;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.awt.*;

public class ChatCell extends ListCell<Chat> {
    HBox hbox = new HBox();

    Label label = new Label("");
    Pane pane = new Pane();
    ImageView image = new ImageView();

    public ChatCell() {
        super();

        image.setFitWidth(30);
        image.setFitHeight(30);
        image.setPreserveRatio(true);


        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.setSpacing(10);


        hbox.getChildren().addAll(image, label, pane);


    }

    @Override
    protected void updateItem(Chat chat, boolean empty) {
        super.updateItem(chat, empty);
        if (empty || chat == null) {
            setGraphic(null);
        } else {
            setGraphic(hbox);
            setData(chat);
            setStyle("-fx-background-color:  #202124;");

            Label label1 = new Label(chat.getUser2_username());
            label1.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 14; -fx-text-fill: white;");
            ContextMenu contextMenu = new ContextMenu();
            MenuItem deleteMenuItem = new MenuItem("Delete chat");
            contextMenu.getItems().add(deleteMenuItem);

            setOnContextMenuRequested(event -> {
                contextMenu.show(this, event.getScreenX(), event.getScreenY());
            });

            deleteMenuItem.setOnAction(event -> {
                System.out.println("Delete Chat: " + getItem().getId());
            });
        }
    }

    private void setData(Chat chat) {
        System.out.println("Setting data for chat: " + chat.getId());
        for (User u : App.allUsers) {
            if (u.getId() == chat.getUser2_id()) {
                System.out.println("Setting image for user: " + u.getUsername());

                String imageUrl = u.getPhotourl();
                if (imageUrl != null && !imageUrl.isEmpty()) {
                    image.setImage(new Image(imageUrl, 50, 50, true, true, true));
                } else {
                    image.setImage(new Image("https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png", 50, 50, true, true, true));
                }
            }
        }
        label.setText(chat.getUser2_username());
    }
}

