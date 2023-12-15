package org.example;

import com.jfoenix.controls.JFXListView;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.*;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.geometry.Insets;
import org.example.api.APICallback;
import org.example.api.ChatAPIClient;

import java.awt.*;
import java.io.IOException;

public class ChatCell extends ListCell<Chat> {
    HBox hbox = new HBox();

    Label label = new Label("");
    Pane pane = new Pane();
    ImageView image = new ImageView();
    LoggedController loggedController;


    public ChatCell(LoggedController loggedController) {
        super();


        image.setFitWidth(40);
        image.setFitHeight(40);
        image.setPreserveRatio(true);

        Circle clip = new Circle(20, 20, 20);
        image.setClip(clip);

        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.setSpacing(10);


        hbox.setPadding(new Insets(10, 10, 10, 10));

        hbox.getChildren().addAll(image, label, pane);


        this.loggedController = loggedController;

    }

    @Override
    protected void updateItem(Chat chat, boolean empty) {
        super.updateItem(chat, empty);
        if (empty || chat == null) {
            setGraphic(null);
        } else {
            setGraphic(hbox);
            setData(chat);
            setStyle("-fx-background-color:   #402e58;");
            String name = "";


            Label label1 = new Label(name);





        }
    }


    private void setData(Chat chat) {
        for (User u : App.allUsers) {
            if (u.getId() == chat.getUser1_id()) {
                String imageUrl = u.getPhotourl();
                if (imageUrl != null && !imageUrl.isEmpty()) {
                    image.setImage(new Image(imageUrl, 50, 50, true, true, true));
                } else {
                    image.setImage(new Image("https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png", 50, 50, true, true, true));
                }
            }
        }
        String name = "";
        if (chat.getUser2_id() == App.userLogged.getId()) {
            name = chat.getUser1_username();
        } else {
            name = chat.getUser2_username();
        }
        label.setText(name);
        label.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 18; -fx-text-fill: white; ");

    }


}

