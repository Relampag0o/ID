package org.example;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.example.apimodels.AnimeData;

import java.io.IOException;

public class KitsuAPIClient {
    private static final String API_URL = "https://kitsu.io/api/edge/anime";
    private OkHttpClient client;
    private ObjectMapper objectMapper;

    public KitsuAPIClient() {
        client = new OkHttpClient();
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public AnimeData getAnimes(int limit, int offset) throws IOException {
        String apiUrl = API_URL + "?page[limit]=" + limit + "&page[offset]=" + offset;
        Request request = new Request.Builder()
                .url(apiUrl)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            System.out.println("Request a " + API_URL + "?page[limit]=" + limit + "&page[offset]=" + offset + " realizada.");
            ResponseBody responseBody = response.body();

            if (response.isSuccessful() && responseBody != null) {
                String responseData = responseBody.string();
                try {

                    AnimeData animeData = objectMapper.readValue(responseData, AnimeData.class);
                    return animeData;
                } catch (InvalidDefinitionException e) {
                    e.printStackTrace();
                }
            }
        }

        throw new IOException("No se pudo obtener la lista de animes.");
    }

    public static void main(String[] args) {
        try {
            KitsuAPIClient apiClient = new KitsuAPIClient();
            int limit = 2;
            int offset = 0;
            AnimeData animeData = apiClient.getAnimes(limit, offset);
            System.out.println(animeData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
