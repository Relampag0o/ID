package org.example;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.example.api.APICallback;
import org.example.api.UserAPIClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserChatsController extends ListCell<Chat> {
    private ImageView imageView = new ImageView();
    private Text name = new Text();
    private Text message = new Text();
    private VBox vBox = new VBox(name, message);
    private List<User> userList;

    public UserChatsController(List<User> userList) {
        vBox.setAlignment(Pos.CENTER_LEFT);
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);
        setPrefWidth(USE_PREF_SIZE);
        this.userList = userList;
    }

    @Override
    protected void updateItem(Chat chat, boolean empty) {
        super.updateItem(chat, empty);

        if (empty || chat == null) {
            setGraphic(null);
        } else {
            setUserImage(chat.getUser2_id());
            name.setText(chat.getUser2_username());
            message.setText("RANDOM TEST");
            setGraphic(new HBox(imageView, vBox));
        }
    }

    private void setUserImage(int userId) {
        for (User user : App.allUsers) {
            if (user.getId() == userId) {
                if (user.getPhotourl() == null) {
                    Image img = new Image("https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png");
                    imageView.setImage(img);

                }
            }
        }
    }
}


