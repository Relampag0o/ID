package org.example;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXListView;
import io.github.palexdev.materialfx.controls.MFXTextField;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import com.jfoenix.controls.JFXListView;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;
import org.example.api.APICallback;
import org.example.api.ChatAPIClient;
import org.example.api.MessageAPIClient;
import org.example.api.UserAPIClient;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class LoggedController extends Application {


    public ScrollPane chatScrollPane;
    public VBox chatBox;
    public MFXTextField messageField;

    public MFXTextField searchField;
    public Button chatsButton;


    private Chat currentChat;
    @FXML
    private JFXListView<Chat> chatsView;
    private JFXListView<User> userListView;



    public ListView msgList;
    private ObservableList<Message> observableMessageList = FXCollections.observableArrayList();


    // all the chats:

    private ArrayList<Chat> chats = new ArrayList<>();
    private Stage chatWindow;
    private Node sourceNode;



    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        initialize();
    }

    public void initialize() {
        System.out.println(App.allUsers.size());

        loadChats();

    }


    public void showUsers() {
        chatWindow = new Stage();
        chatWindow.setTitle("Chat");

        JFXListView<User> userListView = new JFXListView<User>();
        userListView.setCellFactory(param -> new UserCellController());

        ObservableList<User> observableUserList = FXCollections.observableArrayList(App.allUsers);
        userListView.setItems(observableUserList);

        VBox vbox = new VBox(userListView);
        vbox.setPadding(new Insets(10));

        Scene scene = new Scene(vbox);
        chatWindow.setScene(scene);
        chatWindow.show();
/*
        userListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                createChat(newSelection);
                chatWindow.close();
            }
        });
        */
    }



    private void loadChats() {
        System.out.println("Loading chats...");
        ChatAPIClient chatAPIClient = new ChatAPIClient();
        chatAPIClient.getAllChatsFromUser(App.userLogged.getId(), new APICallback() {
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

                chatsView.setCellFactory(param -> new ChatCell());

                ObservableList<Chat> observableUserList = FXCollections.observableArrayList(chats);
                chatsView.setItems(observableUserList);

                chatsView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        currentChat = (Chat) newSelection;
                        showConversation();
                        System.out.println(" Chat selected: " + currentChat.getId() + " with users: " + currentChat.getUser1_username() + " and " + currentChat.getUser2_username());
                    }
                });
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
                loadChats();
                System.out.println(chats.size() + "  " + chat.getId());


            }

            @Override
            public void onError(Object error) {
                System.out.println("The chat was not created successfully for the user " + user.getUsername());
            }
        });
    }


    private void showConversation() {
        MessageAPIClient messageAPIClient = new MessageAPIClient();
        try {
            messageAPIClient.getMessagesFromChat(currentChat.getId(), new APICallback() {

                @Override
                public void onSuccess(Object response) {
                    LinkedList<Message> messages = new LinkedList<>((List<Message>) response);
                    System.out.println("Messages loaded: " + messages.size());
                    observableMessageList.clear();
                    observableMessageList.addAll(messages);

                    updateListView();
                    msgList.setItems(observableMessageList);
                    msgList.setCellFactory(param -> new MessageCell());

                }

                @Override
                public void onError(Object error) {

                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendMessage() {
        MessageAPIClient messageAPIClient = new MessageAPIClient();
        try {
            messageAPIClient.sendMessageToChat(currentChat.getId(), messageField.getText(), currentChat.getUser1_id(), new APICallback() {
                @Override
                public void onSuccess(Object response) {
                    Message message = (Message) response;
                    System.out.println("Message sent: " + message.getId());

                    observableMessageList.add(message);

                    updateListView();
                }

                @Override
                public void onError(Object error) {
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateListView() {
        msgList.setItems(observableMessageList);
        msgList.scrollTo(observableMessageList.size() - 1);
        messageField.clear();
    }
}
