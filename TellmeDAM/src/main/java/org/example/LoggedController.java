package org.example;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXListView;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;

import io.github.palexdev.materialfx.dialogs.MFXGenericDialog;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialogBuilder;
import io.github.palexdev.materialfx.dialogs.MFXStageDialog;
import io.github.palexdev.materialfx.enums.NotificationPos;
import io.github.palexdev.materialfx.enums.ScrimPriority;
import io.github.palexdev.materialfx.notifications.MFXNotificationCenterSystem;
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
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import org.example.api.*;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.paint.Color;
import java.awt.*;

import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.List;
import java.util.regex.Pattern;

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

                Platform.runLater(() -> {
                    MFXNotificationCenterSystem.instance()
                            .setPosition(NotificationPos.TOP_RIGHT)
                            .publish(createCustomNotification((List<Chat>) response));
                });

                chats = new ArrayList<>((List<Chat>) response);
                loadChats();
            }

            @Override
            public void onError(Object error) {

            }
        });
    }

    private CustomNotification createCustomNotification(List<Chat> chats) {
        CustomNotification notification = new CustomNotification();

        StringBuilder contentBuilder = new StringBuilder();
        for (Chat chat : chats) {
            contentBuilder.append(chat.getUser2_username() + " has wroten a message!.");
            contentBuilder.append("\n");
        }

        notification.setContentText(contentBuilder.toString());
        notification.setHeaderText("New notifications");

        return notification;
    }

    public void openUserSettings() {
        MFXTextField usernameField = createStyledTextField("Username:");
        MFXPasswordField passwordField = createStyledPasswordField("Password:");
        MFXPasswordField repeatPasswordField = createStyledPasswordField("Repeat Password:");
        MFXTextField emailField = createStyledTextField("Email:");
        MFXTextField repeatEmailField = createStyledTextField("Repeat Email:");

        FileChooser fileChooser = new FileChooser();
        Button uploadButton = new Button("Upload profile picture");
        uploadButton.setOnAction(e -> {
            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                // handle file selection
            }
        });

        VBox vbox = new VBox(
                createLabeledField("Username:", usernameField),
                createLabeledField("Password:", passwordField),
                createLabeledField("Repeat Password:", repeatPasswordField),
                createLabeledField("Email:", emailField),
                createLabeledField("Repeat Email:", repeatEmailField),
                uploadButton
        );
        vbox.setSpacing(10);
        vbox.setBackground(new Background(new BackgroundFill(Color.web("#840cc5"), CornerRadii.EMPTY, Insets.EMPTY)));
        dialogContent = MFXGenericDialogBuilder.build()
                .setContent(vbox)
                .makeScrollable(true)
                .get();
        dialog = MFXGenericDialogBuilder.build(dialogContent)
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
                    String repeatPassword = repeatPasswordField.getText();
                    String email = emailField.getText();
                    String repeatEmail = repeatEmailField.getText();

                    String photoUrl = "";

                    if (isValidEmail(email) && email.equals(repeatEmail) && isValidPassword(password) && password.equals(repeatPassword)) {
                        saveUserSettings(username, password, email, photoUrl);
                        dialog.close();
                    } else {
                        showFieldError(usernameField, "Username is required.");
                        showFieldError(passwordField, "Invalid password.");
                        showFieldError(repeatPasswordField, "Passwords do not match.");
                        showFieldError(emailField, "Invalid email address.");
                        showFieldError(repeatEmailField, "Emails do not match.");
                    }
                }),
                Map.entry(new MFXButton("Cancel"), event -> dialog.close())
        );

        Scene scene = new Scene(vbox, 800, 600);
        this.dialogContent.setContent(vbox);
        dialog.show();
    }

    private MFXTextField createStyledTextField(String promptText) {
        MFXTextField textField = new MFXTextField();
        textField.setPromptText(promptText);
        textField.setStyle("-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%); -fx-pref-width: 200; -fx-background-radius: 20; -fx-background-color: transparent;");
        return textField;
    }

    private MFXPasswordField createStyledPasswordField(String promptText) {
        MFXPasswordField passwordField = new MFXPasswordField();
        passwordField.setPromptText(promptText);
        passwordField.setStyle("-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%); -fx-pref-width: 200; -fx-background-radius: 20; -fx-background-color: transparent;");
        return passwordField;
    }

    private void showFieldError(MFXTextField field, String errorMessage) {
        Tooltip errorTooltip = new Tooltip(errorMessage);
        Tooltip.install(field, errorTooltip);
        field.setStyle("-fx-border-color: red; -fx-pref-width: 200; -fx-background-radius: 20; -fx-background-color: transparent;");
    }

    private Node createLabeledField(String labelText, Node field) {
        Label label = new Label(labelText);
        label.setLabelFor(field);
        label.setStyle("-fx-font-weight: bold; -fx-font-size: 20;");
        VBox vbox = new VBox(label, field);
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-background-color: transparent;");
        return vbox;
    }

    

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pat = Pattern.compile(emailRegex);
        return pat.matcher(email).matches();
    }

    private boolean isValidPassword(String password) {
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$";
        Pattern pat = Pattern.compile(passwordRegex);
        return pat.matcher(password).matches();
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



