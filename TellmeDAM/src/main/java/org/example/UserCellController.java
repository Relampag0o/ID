package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class UserCellController {
    @FXML
    private ImageView imageView;
    @FXML
    private Label usernameLabel;

    public void setUser(User user) {
        Image img = new Image(user.getPhotourl());
        imageView.setImage(img);
        usernameLabel.setText(user.getUsername());
    }
}
