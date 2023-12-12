package org.example;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.awt.*;

public class ChatCell extends ListCell<Chat> {
    HBox hbox = new HBox();
    Button btn = new Button("Delete");
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

        // Agrega elementos al contenedor
        hbox.getChildren().addAll(image, label, btn, pane);

        btn.setOnAction(event -> {
            // Lógica para manejar el clic en el botón
            System.out.println("Delete Chat: " + getItem().getId());
        });
    }

    @Override
    protected void updateItem(Chat chat, boolean empty) {
        super.updateItem(chat, empty);
        if (empty || chat == null) {
            setGraphic(null);
        } else {
            setGraphic(hbox);
            setData(chat);
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

