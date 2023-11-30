package org.example.api;

import com.google.gson.Gson;
import org.example.Message;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

public class MessageAPIClient extends RootAPIClient {
    public void getMessagesFromChat(Integer chatId, APICallback callback) throws IOException {
        new Thread(() -> {
            try {
                doGetMessagesFromChat(chatId, callback);
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    public void sendMessageToChat(Integer chatId, String message, int idSender, APICallback callback) throws IOException {
        new Thread(() -> {
            try {
                doSendMessageToChat(chatId, message, idSender, callback);
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }



    private void doGetMessagesFromChat(Integer chatId, APICallback callback) throws IOException, InterruptedException {
        String url = BASE_URL + "/chats/" + chatId + "/messages";
        HttpResponse<String> response = doGETRequest(url);
        if (response.statusCode() == 200) {
            Gson gson = new Gson();
            List<Message> list = Arrays.asList(gson.fromJson(response.body(), Message[].class));
            onSuccess(callback, list);
        } else {
            Gson gson = new Gson();
            Error error = gson.fromJson(response.body(), Error.class);
            onError(callback, error);
        }
    }

    private void doSendMessageToChat(Integer chatId, String message, int idSender, APICallback callback) throws IOException, InterruptedException {
        String url = BASE_URL + "/chats/" + chatId + "/messages";
        String requestBody = "{\"content\":\"" + message + "\",\"idSender\": " + idSender + " }";

        HttpResponse<String> response = doPOSTRequest(url, requestBody);
        if(response.statusCode() == 200) {
            Gson gson = new Gson();
            Message messageCreated = gson.fromJson(response.body(), Message.class);
            onSuccess(callback, messageCreated);
        } else {
            Gson gson = new Gson();
            Error error = gson.fromJson(response.body(), Error.class);
            onError(callback, error);
        }
    }
}
