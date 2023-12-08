package org.example;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class UserChatsController extends ListCell<User> {
    @FXML
    private ImageView imageView;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label messageLabel;

    @Override
    protected void updateItem(User user, boolean empty) {
        super.updateItem(user, empty);
        if (empty || user == null) {
            setText(null);
            setGraphic(null);
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("userchats.fxml"));
                Parent root = loader.load();
                UserChatsController controller = loader.getController();
                controller.setChat(user);
                setGraphic(root);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setChat(User user) {
        Image img = new Image(user.getPhotourl());
        imageView.setImage(img);
        usernameLabel.setText(user.getUsername());
        messageLabel.setText("");
    }
}

