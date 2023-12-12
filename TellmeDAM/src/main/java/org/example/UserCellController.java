package org.example;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class UserCellController extends ListCell<User> {
    @FXML
    private ImageView imageView;
    @FXML
    private Label usernameLabel;


    @Override
    protected void updateItem(User user, boolean empty) {
        super.updateItem(user, empty);

        if (empty || user == null) {
            setText(null);
            setGraphic(null);
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("users.fxml"));
                Parent root = loader.load();
                UserCellController controller = loader.getController();
                controller.setChat(user);
                setGraphic(root);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public void setChat(User user) {
        /*
        if (user != null && user.getPhotourl() != null && !user.getPhotourl().isEmpty()) {
            Image img = new Image(user.getPhotourl());
            imageView.setImage(img);
        } else {
            imageView.setImage(new Image("https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png"));
        }

         */

        usernameLabel.setText(user != null ? user.getUsername() : "");
    }
}
