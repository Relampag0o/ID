package org.example;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXListView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
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
    public MFXListView listView;
    public MFXTextField searchField;

    public MFXButton chatButton;

    // all the users:
    private List<User> userList;

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
        userListView.setCellFactory(param -> {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("users.fxml"));
            try {
                fxmlLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            UserCellController controller = fxmlLoader.getController();

            return new ListCell<User>() {
                @Override
                protected void updateItem(User user, boolean empty) {
                    super.updateItem(user, empty);
                    if (empty || user == null) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        controller.setUser(user);
                        setGraphic(fxmlLoader.getRoot());
                    }
                }
            };
        });

        userListView.getItems().addAll(userList);

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
                List<Message> messages = new ArrayList<>();

                // Extract the last message from each chat and add it to the messages list
                for (Chat chat : chats) {
                    // Use the MessageAPIClient to get the messages for each chat
                    MessageAPIClient messageAPIClient = new MessageAPIClient();
                    try {
                        messageAPIClient.getMessagesFromChat(chat.getId(), new APICallback() {
                            @Override
                            public void onSuccess(Object response) {
                                List<Message> chatMessages = (List<Message>) response;
                                if (!chatMessages.isEmpty()) {
                                    Message lastMessage = chatMessages.get(chatMessages.size() - 1);
                                    messages.add(lastMessage);
                                }
                            }

                            @Override
                            public void onError(Object error) {
                                // Handle error
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                listView.setCellFactory(param -> {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("userchats.fxml"));
                    try {
                        fxmlLoader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    UserChatsController controller = fxmlLoader.getController();

                    return new ListCell<Message>() {
                        @Override
                        protected void updateItem(Message message, boolean empty) {
                            super.updateItem(message, empty);
                            if (empty || message == null) {
                                setGraphic(null);
                                setText(null);
                            } else {
                                //User user = getUserFromMessage(message); // Necesitarás implementar este método
                                controller.setChat(user, message);
                                setGraphic(fxmlLoader.getRoot());
                            }
                        }
                    };
                });

                listView.getItems().addAll(messages);
                listView.setItems(FXCollections.observableArrayList(messages));



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
