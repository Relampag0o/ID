package org.example.api;

import com.google.gson.Gson;
import javafx.application.Platform;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class RootAPIClient {
    protected final String BASE_URL = "https://discord-server-flask.vercel.app";
    //protected final String BASE_URL = "http://localhost:5000";

    protected Gson gson;

    public RootAPIClient() {
        gson = new Gson();
    }

    protected HttpResponse<String> doPOSTRequest(String url, String requestBody) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    protected HttpResponse<String> doGETRequest(String url) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    protected HttpResponse<String> doPUTRequest(String url, String requestBody) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    protected HttpResponse<String> doDELETERequest(String url) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .DELETE()
                .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    protected void onSuccess(APICallback callback, Object response) {
        Platform.runLater(() -> {
            try {
                callback.onSuccess(response);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    protected void onError(APICallback callback, Object error) {
        Platform.runLater(() -> callback.onError(error));
    }

}
