package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.api.APICallback;
import org.example.api.RootAPIClient;
import org.example.api.UserAPIClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * JavaFX App
 */
public class App extends Application {


    public static User userLogged;
    public static List<User> allUsers = loadUsers();


    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"), 650, 400);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

    public static List<User> loadUsers() {
        UserAPIClient userAPIClient = new UserAPIClient();
        List<User> listUsers = new ArrayList<>();

        try {
            CompletableFuture<Void> future = new CompletableFuture<>();

            userAPIClient.getAllUsers(new APICallback() {
                @Override
                public void onSuccess(Object response) throws IOException {
                    listUsers.addAll((List<User>) response);
                    future.complete(null);
                }

                @Override
                public void onError(Object error) {
                    System.out.println("No users found");
                    future.completeExceptionally(new RuntimeException("Error fetching users"));
                }
            });


            future.get();
        } catch (IOException | InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

        return listUsers;
    }
}
