package org.example.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import javafx.application.Platform;
import org.example.Chat;
import org.example.Error;
import org.example.User;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatAPIClient extends RootAPIClient {
    public void createChat(Integer userId, Integer otherUserId, APICallback callback) {
        new Thread(() -> {
            try {
                doCreateChat(userId, otherUserId, callback);
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

    }

    public void getAllChatsFromUser(Integer userId, APICallback callback) {
        new Thread(() -> {
            try {
                doGetAllChatsFromUser(userId, callback);
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

    }

    public void deleteChat(Integer chatId, APICallback callback) {
        new Thread(() -> {
            try {
                doDeleteChat(chatId, callback);
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    public void cleanChat(Integer chatId, APICallback callback) {
        new Thread(() -> {
            try {
                doCleanChat(chatId, callback);
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    private void doCleanChat(Integer chatId, APICallback callback) throws IOException, InterruptedException {
        String url = BASE_URL + "/chats/" + chatId + "/clean";
        HttpResponse<String> response = doDELETERequest(url);

        if (response.statusCode() == 200) {
            Gson gson = new Gson();
            Chat chat = gson.fromJson(response.body(), Chat.class);
            onSuccess(callback, chat);
        } else {
            Gson gson = new Gson();
            Error error = gson.fromJson(response.body(), Error.class);
            onError(callback, error);
        }
    }

    private void doDeleteChat(Integer chatId, APICallback callback) throws IOException, InterruptedException {
        String url = BASE_URL + "/chats/" + chatId;
        HttpResponse<String> response = doDELETERequest(url);

        if (response.statusCode() == 200) {
            Gson gson = new Gson();
            Chat chat = gson.fromJson(response.body(), Chat.class);
            onSuccess(callback, chat);
        } else {
            Gson gson = new Gson();
            Error error = gson.fromJson(response.body(), Error.class);
            onError(callback, error);
        }
    }


    private void doCreateChat(Integer userId, Integer otherUserId, APICallback callback) throws IOException, InterruptedException {
        String url = BASE_URL + "/chats";
        String requestBody = "{\"user1_id\":" + userId + ",\"user2_id\":" + otherUserId + "}";
        HttpResponse<String> response = doPOSTRequest(url, requestBody);

        if (response.statusCode() == 200) {
            Gson gson = new Gson();
            Chat chat = gson.fromJson(response.body(), Chat.class);
            onSuccess(callback, chat);
        } else {
            Gson gson = new Gson();
            Error error = gson.fromJson(response.body(), Error.class);
            onError(callback, error);
        }
    }

    private void doGetAllChatsFromUser(Integer userId, APICallback callback) throws IOException, InterruptedException {
        String url = BASE_URL + "/users/" + userId + "/chats";
        HttpResponse<String> response = doGETRequest(url);

        if (response.statusCode() == 200) {
            Gson gson = new Gson();
            List<Chat> list = Arrays.asList(gson.fromJson(response.body(), Chat[].class));

            onSuccess(callback, list);
        } else {
            Gson gson = new Gson();
            Error error = gson.fromJson(response.body(), Error.class);
            onError(callback, error);
        }
    }
}

