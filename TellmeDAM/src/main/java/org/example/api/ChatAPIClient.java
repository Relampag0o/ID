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
    public void createChat(String userId, String otherUserId, APICallback callback) {
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

    private void doCreateChat(String userId, String otherUserId, APICallback callback) throws IOException, InterruptedException {
        String url = BASE_URL + "/chat";
        String requestBody = "{\n" +
                "  \"data\": {\n" +
                "    \"type\": \"chat\",\n" +
                "    \"attributes\": {},\n" +
                "    \"relationships\": {\n" +
                "      \"messages\": {\n" +
                "        \"data\": []\n" +
                "      },\n" +
                "      \"users\": {\n" +
                "        \"data\": [\n" +
                "          {\n" +
                "            \"type\": \"user\",\n" +
                "            \"id\": \"" + userId + "\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"type\": \"user\",\n" +
                "            \"id\": \"" + otherUserId + "\"\n" +
                "          }\n" +
                "        ]\n" +
                "      }\n" +
                "    }\n" +
                "  },\n" +
                "  \"meta\": {\n" +
                "    \"serialization\": \"string\",\n" +
                "    \"additionalProp1\": {}\n" +
                "  }\n" +
                "}";


        HttpResponse<String> response = doPOSTRequest(url, requestBody);

        JsonNode jsonNode = new ObjectMapper().readTree(response.body());
        if (jsonNode.has("error")) {
            Map<String, String> errorResult = new HashMap<>();
            errorResult.put("error", jsonNode.get("error").asText());
            Platform.runLater(() -> callback.onError(errorResult));
        } else {
            JsonNode dataNode = jsonNode.get("data");
            JsonNode attributesNode = dataNode.get("attributes");
            JsonNode relationshipsNode = dataNode.get("relationships");
            Map<String, String> result = new HashMap<>();
            result.put("id", dataNode.get("id").asText());
            result.put("createdAt", attributesNode.get("createdAt").asText());
            result.put("updatedAt", attributesNode.get("updatedAt").asText());

            int index = 0;
            for (JsonNode user : relationshipsNode.get("users").get("data")) {
                result.put("user" + index, user.get("id").asText());
                index++;
            }

            Platform.runLater(() -> {
                try {
                    callback.onSuccess(result);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
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
