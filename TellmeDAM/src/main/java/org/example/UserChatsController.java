package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class UserChatsController {
    @FXML
    private ImageView imageView;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label messageLabel;

    public void setChat(User user, Message message) {
        Image img = new Image(user.getPhotourl());
        imageView.setImage(img);
        usernameLabel.setText(user.getUsername());
        messageLabel.setText(message.getContent());
    }
}

