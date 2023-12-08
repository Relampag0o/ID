package org.example;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXListView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
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
import org.example.api.ChatAPIClient;
import org.example.api.MessageAPIClient;
import org.example.api.UserAPIClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class LoggedController extends Application {

    public ScrollPane chatScrollPane;
    public VBox chatBox;
    public MFXTextField messageField;
    public ListView listView;
    public MFXTextField searchField;

    public MFXButton chatButton;

    // all the users:
    public static List<User> userList;

    // all the chats:

    private ArrayList<Chat> chats = new ArrayList<>();

    private Stage chatWindow;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {


    }

    public void initialize() {
        loadUsers();

        loadChat(App.userLogged);

    }

    public void showUsers() {
        chatWindow = new Stage();
        chatWindow.setTitle("Chat");

        ListView<User> userListView = new ListView<>();
        userListView.setCellFactory(param -> new UserCellController());

        ObservableList<User> observableUserList = FXCollections.observableArrayList(userList);
        userListView.setItems(observableUserList);

        VBox vbox = new VBox(userListView);
        vbox.setPadding(new Insets(10));

        Scene scene = new Scene(vbox);
        chatWindow.setScene(scene);
        chatWindow.show();

        userListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                createChat(newSelection);
                chatWindow.close();
            }
        });
    }


    private void loadChat(User user) {
        ChatAPIClient chatAPIClient = new ChatAPIClient();
        chatAPIClient.getAllChatsFromUser(user.getId(), new APICallback() {
            @Override
            public void onSuccess(Object response) {
                chats = new ArrayList<>((List<Chat>) response);

                System.out.println("Chats loaded: " + chats.size());

                for (Chat chat : chats) {
                    System.out.println("Chat id: " + chat.getId());
                    System.out.println("User 1: " + chat.getUser1_username());
                    System.out.println("User 2: " + chat.getUser2_username());
                    System.out.println("------------------------");


                }
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                listView.setCellFactory(param -> new UserChatsController());

                ObservableList<Chat> observableUserList = FXCollections.observableArrayList(chats);
                listView.setItems(observableUserList);
            }

            @Override
            public void onError(Object error) {
                // Handle error
            }
        });
    }


    private void createChat(User user) {
        ChatAPIClient chatAPIClient = new ChatAPIClient();
        chatAPIClient.createChat(App.userLogged.getId(), user.getId(), new APICallback() {
            @Override
            public void onSuccess(Object response) {
                Chat chat = (Chat) response;
                System.out.println("Creating chat..");
                chats.add(chat);
                loadChat(App.userLogged);
                System.out.println(chats.size() + "  " + chat.getId());


            }

            @Override
            public void onError(Object error) {
                System.out.println("The chat was not created successfully for the user " + user.getUsername());
            }
        });
    }

    private void loadUsers() {
        UserAPIClient userAPIClient = new UserAPIClient();
        try {
            userAPIClient.getAllUsers(new APICallback() {
                @Override
                public void onSuccess(Object response) throws IOException {
                    userList = (List<User>) response;

                    System.out.println("Users loaded: " + userList.size());
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
