package org.example;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.api.APICallback;
import org.example.api.UserAPIClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserChatsController extends ListCell<Chat> {
    @FXML
    private ImageView imageView;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label messageLabel;
    //private List<User> userList;


    @Override
    protected void updateItem(Chat chat, boolean empty) {

        super.updateItem(chat, empty);
        if (empty || chat == null) {
            setText(null);
            setGraphic(null);
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("userchats.fxml"));
                Parent root = loader.load();
                UserChatsController controller = loader.getController();
                controller.setChat(chat);
                setGraphic(root);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setChat(Chat chat) {


    }
    /*
    public void setChat(Chat chat) {
        for (User user : LoggedController.userList) {
            if (user.getId() == chat.getUser2_id()) {
                if (user.getPhotourl() == null) {
                    Image img = new Image("https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png");
                    imageView.setImage(img);
                }
                Image img = new Image(user.getPhotourl());
                imageView.setImage(img);
                usernameLabel.setText(user.getUsername());
                messageLabel.setText("TEST.");
                break;
            }
        }
    }

     */


}


