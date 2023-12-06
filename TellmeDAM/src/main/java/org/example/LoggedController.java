package org.example;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXListView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.api.APICallback;
import org.example.api.UserAPIClient;

import java.io.IOException;
import java.util.List;

public class LoggedController extends Application {

    public ScrollPane chatScrollPane;
    public VBox chatBox;
    public MFXTextField messageField;
    public MFXListView listView;
    public MFXTextField searchField;

    public MFXButton chatButton;

    private List<User> userList;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {


    }

    public void initialize() {

        loadUsers();

    }

    public void showChat() {
        Stage chatWindow = new Stage();
        chatWindow.setTitle("Chat");

        ListView<User> userListView = new ListView<>();
        userListView.setCellFactory(param -> new ListCell<User>() {
            private ImageView imageView = new ImageView();
            private Label usernameLabel = new Label();

            @Override
            protected void updateItem(User user, boolean empty) {
                super.updateItem(user, empty);
                if (empty || user == null) {
                    setGraphic(null);
                    setText(null);
                } else {
                    Image img = new Image("https://via.placeholder.com/50"); // Asume que User tiene un método getProfilePictureUrl
                    imageView.setImage(img);
                    imageView.setFitHeight(50); // Ajusta el tamaño de la imagen como necesites
                    imageView.setFitWidth(50);
                    usernameLabel.setText(user.getUsername());
                    HBox hBox = new HBox(10, imageView, usernameLabel);
                    hBox.setAlignment(Pos.CENTER); // Alinea los elementos a la izquierda
                    setGraphic(hBox);
                }
            }
        });

        userListView.getItems().addAll(userList);

        VBox vbox = new VBox(userListView);
        vbox.setPadding(new Insets(10));

        Scene scene = new Scene(vbox);
        chatWindow.setScene(scene);
        chatWindow.show();
    }

    private void loadUsers() {
        UserAPIClient userAPIClient = new UserAPIClient();
        try {
            userAPIClient.getAllUsers(new APICallback() {
                @Override
                public void onSuccess(Object response) throws IOException {
                    userList = (List<User>) response;
                }

                @Override
                public void onError(Object error) {
                    System.out.println("No users found");
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


}
