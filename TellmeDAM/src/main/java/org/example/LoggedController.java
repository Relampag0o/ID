package org.example;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXListView;
import io.github.palexdev.materialfx.controls.MFXTextField;

import io.github.palexdev.materialfx.dialogs.MFXGenericDialog;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialogBuilder;
import io.github.palexdev.materialfx.dialogs.MFXStageDialog;
import io.github.palexdev.materialfx.enums.ScrimPriority;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import com.jfoenix.controls.JFXListView;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import org.example.api.*;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

import java.awt.*;

import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.List;

import static org.example.App.stage;

public class LoggedController extends Application {


    public ScrollPane chatScrollPane;
    public VBox chatBox;
    public MFXTextField messageField;

    public MFXTextField searchField;
    public Button chatsButton;
    public MFXButton settingsButton;
    public MFXButton leaveButton;
    public ImageView userImage;
    public Label userName;
    public MFXButton notificationsButton;


    private Chat currentChat;

    @FXML
    public JFXListView<Chat> chatsView;

    private JFXListView<User> userListView;


    public ListView msgList;


    private ObservableList<Message> observableMessageList = FXCollections.observableArrayList();


    // all the chats:

    public static ArrayList<Chat> chats = new ArrayList<>();

    private FilteredList<Chat> filteredChats;


    private JFXListView<Chat> notificationListView;

    private MFXGenericDialog dialogContent;
    private MFXStageDialog dialog;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        chatsButton.setPadding(Insets.EMPTY);
        chatsView = new JFXListView<>();
        initialize();
    }

    public void initialize() {
        loadChats();

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                try {
                    showConversation();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 0, 20000);

        userName.setText("");
        observeNotifications();
    }


    public void showUsers() {


        JFXListView<User> userListView = new JFXListView<User>();
        userListView.setItems(FXCollections.observableList(App.allUsers));
        userListView.setCellFactory(param -> new UserCellController());


        this.dialogContent = MFXGenericDialogBuilder.build()
                .setContent(userListView)
                .makeScrollable(true)
                .get();
        this.dialog = MFXGenericDialogBuilder.build(dialogContent)
                .toStageDialogBuilder()
                .initOwner(stage)
                .initModality(Modality.APPLICATION_MODAL)
                .setDraggable(true)
                .setTitle("Dialogs Preview")
                //.setOwnerNode(grid)
                .setScrimPriority(ScrimPriority.WINDOW)
                .setScrimOwner(true)
                .get();

        dialogContent.addActions(
                Map.entry(new MFXButton("Confirm"), event -> {
                    createChat(userListView.getSelectionModel().getSelectedItem());

                    dialog.close();

                }),
                Map.entry(new MFXButton("Cancel"), event -> dialog.close())
        );

        dialogContent.setMaxSize(700, 450);
        ObservableList<User> observableUserList = FXCollections.observableArrayList(App.allUsers);
        userListView.setItems(observableUserList);

        VBox vbox = new VBox(userListView);
        vbox.setPadding(new Insets(10));

        Scene scene = new Scene(vbox);
        this.dialogContent.setContent(vbox);
        dialog.show();

    }


    public void loadChats() {
        System.out.println("Loading chats...");
        ChatAPIClient chatAPIClient = new ChatAPIClient();
        chatAPIClient.getAllChatsFromUser(App.userLogged.getId(), new APICallback() {
            @Override
            public void onSuccess(Object response) {
                chats = new ArrayList<>((List<Chat>) response);
                ObservableList<Chat> observableUserList = FXCollections.observableArrayList(chats);

                chatsView.setCellFactory(param -> {
                    ChatCell cell = new ChatCell(LoggedController.this);

                    ContextMenu contextMenu = new ContextMenu();
                    MenuItem deleteMenuItem = new MenuItem("Delete chat");
                    contextMenu.getItems().add(deleteMenuItem);

                    deleteMenuItem.setOnAction(event -> {
                        Chat selectedChat = cell.getItem();
                        if (selectedChat != null) {
                            chatAPIClient.cleanChat(selectedChat.getId(), new APICallback() {
                                @Override
                                public void onSuccess(Object response) throws IOException {
                                    System.out.println("Chat cleaned: " + selectedChat.getId());
                                }

                                @Override
                                public void onError(Object error) {
                                }
                            });

                            chatAPIClient.deleteChat(selectedChat.getId(), new APICallback() {
                                @Override
                                public void onSuccess(Object response) throws IOException {
                                    System.out.println("Chat deleted: " + selectedChat.getId());
                                    chats.remove(selectedChat);
                                    chatsView.getItems().remove(selectedChat);
                                    chatsView.refresh();
                                    userImage.setImage(null);
                                    userName.setText("");

                                }

                                @Override
                                public void onError(Object error) {
                                }
                            });
                        }
                    });

                    cell.setOnContextMenuRequested(event -> {
                        contextMenu.show(cell, event.getScreenX(), event.getScreenY());
                    });

                    return cell;
                });

                chatsView.setItems(observableUserList);

                chatsView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        currentChat = newSelection;
                        showConversation();

                        System.out.println(" Chat selected: " + currentChat.getId() +
                                " with users: " + currentChat.getUser1_username() + " and " +
                                currentChat.getUser2_username());
                    }
                });
            }

            @Override
            public void onError(Object error) {
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
            if (currentChat == null) {
                return;
            }
            int chatId = currentChat.getUser1_id();
            User user = null;
            if (chatId == App.userLogged.getId()) {
                user = findUser(currentChat.getUser2_id());
            } else
                user = findUser(currentChat.getUser1_id());

            try {
                String photoUrl = user.getPhotourl();
                userImage.setImage(new Image(new URL(photoUrl).toExternalForm()));
            } catch (MalformedURLException e) {

                userImage.setImage(null);
            }
            userName.setText(user.getUsername());


            messageAPIClient.getMessagesFromChat(currentChat.getId(), new APICallback() {

                @Override
                public void onSuccess(Object response) {
                    LinkedList<Message> messages = new LinkedList<>((List<Message>) response);
                    System.out.println("Messages loaded: " + messages.size());
                    messages.sort(Comparator.comparingInt(Message::getId));
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

    public User findUser(int id) {
        for (User user : App.allUsers) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    public void sendMessage() {
        MessageAPIClient messageAPIClient = new MessageAPIClient();

        try {
            messageAPIClient.sendMessageToChat(currentChat.getId(), messageField.getText(), App.userLogged.getId(), new APICallback() {
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
        if (messageField != null)
            messageField.clear();
    }


    public void search() {
        String pattern = searchField.getText();
        ObservableList<Chat> observableUserList = FXCollections.observableArrayList();
        observableUserList.addAll(chats);
        ObservableList<Chat> filteredUsers = observableUserList.filtered(chat -> chat.containsName(pattern));

        chatsView.setItems(filteredUsers);

    }

    public void logout() {
        try {
            App.userLogged = null;
            App.stage.setWidth(600);
            App.stage.setHeight(460);
            App.setRoot("primary");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void observeNotifications() {
        NotificationAPIClient notificationAPIClient = new NotificationAPIClient();
        notificationAPIClient.observeNewMessages(App.userLogged.getId(), new APICallback() {

            @Override
            public void onSuccess(Object response) throws IOException {
                System.out.println("There was an update!");
                chats = new ArrayList<>((List<Chat>) response);
                loadChats();


            }

            @Override
            public void onError(Object error) {

            }
        });

    }

    /*
    public void openNotifications() {
        notificationListView = new JFXListView<Chat>();
        notificationListView.setItems(FXCollections.observableList(App.allNotifications));
        notificationListView.setCellFactory(param -> new NotificationCellController());

        this.dialogContent = MFXGenericDialogBuilder.build()
                .setContent(notificationListView)
                .makeScrollable(true)
                .get();
        this.dialog = MFXGenericDialogBuilder.build(dialogContent)
                .toStageDialogBuilder()
                .initOwner(stage)
                .initModality(Modality.APPLICATION_MODAL)
                .setDraggable(true)
                .setTitle("Notifications Preview")
                .setScrimPriority(ScrimPriority.WINDOW)
                .setScrimOwner(true)
                .get();

        dialogContent.addActions(
                Map.entry(new MFXButton("Close"), event -> dialog.close())
        );
    }

     */

    public void openUserSettings() {
        TextField usernameField = new TextField();
        usernameField.setPromptText("Nombre de usuario");
        usernameField.setStyle("-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Contraseña");
        passwordField.setStyle("-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");

        TextField emailField = new TextField();
        emailField.setPromptText("Correo electrónico");
        emailField.setStyle("-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");

        FileChooser fileChooser = new FileChooser();
        Button uploadButton = new Button("Subir foto de perfil");
        uploadButton.setOnAction(e -> {
            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {

            }
        });


        VBox vbox = new VBox(usernameField, passwordField, emailField, uploadButton);
        vbox.setSpacing(10);

        this.dialogContent = MFXGenericDialogBuilder.build()
                .setContent(vbox)
                .makeScrollable(true)
                .get();
        this.dialog = MFXGenericDialogBuilder.build(dialogContent)
                .toStageDialogBuilder()
                .initOwner(stage)
                .initModality(Modality.APPLICATION_MODAL)
                .setDraggable(true)
                .setTitle("User Settings")
                .setScrimPriority(ScrimPriority.WINDOW)
                .setScrimOwner(true)
                .get();

        dialogContent.addActions(
                Map.entry(new MFXButton("Save"), event -> {

                    String username = usernameField.getText();
                    String password = passwordField.getText();
                    String email = emailField.getText();

                    String photoUrl = "";

                    saveUserSettings(username, password, email, photoUrl);
                    dialog.close();
                }),
                Map.entry(new MFXButton("Cancel"), event -> dialog.close())
        );
    }

    public void saveUserSettings(String username, String password, String email, String photoUrl) {
        try {
            UserAPIClient userAPIClient = new UserAPIClient();

            userAPIClient.updateUser(App.userLogged.getId(), username, password, email, photoUrl, new APICallback() {
                @Override
                public void onSuccess(Object response) {

                    User user = (User) response;

                    App.userLogged = user;
                }

                @Override
                public void onError(Object error) {

                }
            });
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}



