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


public class UserAPIClient extends RootAPIClient {

    public void login(String email, String password, APICallback callback) throws IOException, InterruptedException {
        new Thread(() -> {
            try {
                doLogin(email, password, callback);
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    public void register(String email, String username, String password, APICallback callback) throws IOException, InterruptedException {
        new Thread(() -> {
            try {
                doRegister(email, username, password, callback);
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    public void getAllUsers(APICallback callback) throws IOException, InterruptedException {
        new Thread(() -> {
            try {
                doGetAllUsers(callback);
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    public void getAllUsersByChatId(String chatId, APICallback callback) throws IOException, InterruptedException {
        new Thread(() -> {
            try {
                doGetAllUsersByChatId(chatId, callback);
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    public void getUserById(Integer userId, APICallback callback) throws IOException, InterruptedException {
        new Thread(() -> {
            try {
                doGetUserById(userId, callback);
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    public void updateUser(Integer userId, String username, String password, String email, String photoUrl, APICallback callback) throws IOException, InterruptedException {
        new Thread(() -> {
            try {
                doUpdateUser(userId, username, password, email, photoUrl, callback);
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    public void deleteUserAndChats(Integer userId, APICallback callback) throws IOException, InterruptedException {
        new Thread(() -> {
            try {
                doDeleteUserAndChats(userId, callback);
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    private void doDeleteUserAndChats(Integer userId, APICallback callback) throws IOException, InterruptedException {
        String url = BASE_URL + "/users/" + userId;
        HttpResponse<String> response = doDELETERequest(url);

        if (response.statusCode() == 200) {
            Gson gson = new Gson();
            User user = gson.fromJson(response.body(), User.class);
            onSuccess(callback, user);
        } else {
            Gson gson = new Gson();
            Error error = gson.fromJson(response.body(), Error.class);
            onError(callback, error);
        }
    }

    private void doUpdateUser(Integer userId, String username, String password, String email, String photoUrl, APICallback callback) throws IOException, InterruptedException {
        String url = BASE_URL + "/users/" + userId;
        String requestBody = "{\"email\":\"" + email + "\",\"username\":\"" + username + "\",\"password\":\"" + password + "\",\"photourl\":\"" + photoUrl + "\"}";
        HttpResponse<String> response = doPUTRequest(url, requestBody);
        if (response.statusCode() == 200) {
            Gson gson = new Gson();
            User user = gson.fromJson(response.body(), User.class);
            onSuccess(callback, user);
        } else {
            Gson gson = new Gson();
            Error error = gson.fromJson(response.body(), Error.class);
            onError(callback, error);
        }
    }

    private void doGetUserById(Integer userId, APICallback callback) throws IOException, InterruptedException {
        String url = BASE_URL + "/users/" + userId;
        HttpResponse<String> response = doGETRequest(url);
        if (response.statusCode() == 200) {
            Gson gson = new Gson();
            User user = gson.fromJson(response.body(), User.class);
            onSuccess(callback, user);
        } else {
            Gson gson = new Gson();
            Error error = gson.fromJson(response.body(), Error.class);
            onError(callback, error);
        }
    }

    private void doLogin(String email, String password, APICallback callback) throws IOException, InterruptedException {
        String url = BASE_URL + "/login";
        String requestBody = "{\"email\":\"" + email + "\",\"password\":\"" + password + "\"}";

        HttpResponse<String> response = doPOSTRequest(url, requestBody);

        if (response.statusCode() != 200) {
            Gson gson = new Gson();
            Error error = gson.fromJson(response.body(), Error.class);
            Platform.runLater(() -> callback.onError(error));
        } else {
            Gson gson = new Gson();
            User userLogged = gson.fromJson(response.body(), User.class);
            Platform.runLater(() -> {
                try {
                    callback.onSuccess(userLogged);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    private void doRegister(String email, String username, String password, APICallback callback) throws IOException, InterruptedException {
        String url = BASE_URL + "/users";

        String requestBody = "{\"email\":\"" + email + "\",\"username\":\"" + username + "\",\"password\":\"" + password + "\"}";

        HttpResponse<String> response = doPOSTRequest(url, requestBody);

        if (response.statusCode() == 200) {
            Gson gson = new Gson();
            User userRegistered = gson.fromJson(response.body(), User.class);

            Platform.runLater(() -> {
                try {
                    callback.onSuccess(userRegistered);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } else {
            Gson gson = new Gson();
            Error error = gson.fromJson(response.body(), Error.class);
            Platform.runLater(() -> callback.onError(error));
        }

    }

    private void doGetAllUsers(APICallback callback) throws IOException, InterruptedException {
        String url = BASE_URL + "/users";
        HttpResponse<String> response = doGETRequest(url);

        if (response.statusCode() == 200) {
            Gson gson = new Gson();
            List<User> list = Arrays.asList(gson.fromJson(response.body(), User[].class));
            onSuccess(callback, list);
        } else {
            Gson gson = new Gson();
            Error error = gson.fromJson(response.body(), Error.class);
            onError(callback, error);
        }
    }

    private void doGetAllUsersByChatId(String chatId, APICallback callback) throws IOException, InterruptedException {
        String url = BASE_URL + "/chat/" + chatId + "/users";
        HttpResponse<String> response = doGETRequest(url);

        JsonNode jsonNode = new ObjectMapper().readTree(response.body());
        if (response.statusCode() == 200) {
            Map<String, String> userIds = new HashMap<>();
            int index = 0;
            for (JsonNode userNode : jsonNode.get("data")) {
                userIds.put("user" + index + ".id", userNode.get("id").asText());
                userIds.put("user" + index + ".username", userNode.get("attributes").get("username").asText());
                userIds.put("user" + index + ".photoUrl", userNode.get("attributes").get("photoUrl").asText());
                index++;
            }

            Platform.runLater(() -> {
                try {
                    callback.onSuccess(userIds);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } else {
            Map<String, String> errors = new HashMap<>();
            errors.put("error", jsonNode.get("error").asText());
            Platform.runLater(() -> callback.onError(errors));
        }
    }

}
