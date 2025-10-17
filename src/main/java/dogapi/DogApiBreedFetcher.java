package dogapi;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DogApiBreedFetcher implements BreedFetcher {

    private static final String BASE = "https://dog.ceo/api/breed/%s/list";
    private final OkHttpClient client;

    public DogApiBreedFetcher() {
        this(new OkHttpClient());
    }

    public DogApiBreedFetcher(OkHttpClient client) {
        this.client = client;
    }

    @Override
    public List<String> getSubBreeds(String breed) throws BreedNotFoundException {
        if (breed == null || breed.isBlank()) {
            throw new IllegalArgumentException("breed must not be null/blank");
        }

        String url = String.format(Locale.ROOT, BASE, breed.toLowerCase(Locale.ROOT).trim());
        Request req = new Request.Builder().url(url).get().build();

        try (Response resp = client.newCall(req).execute()) {
            if (resp.body() == null) {
                throw new RuntimeException("Empty response body");
            }
            String body = resp.body().string();

            JSONObject json = new JSONObject(body);
            String status = json.optString("status", "");

            if (!resp.isSuccessful()) {
                if ("error".equals(status) && json.optInt("code", 0) == 404) {
                    throw new BreedNotFoundException(breed);
                }
                throw new RuntimeException("HTTP error: " + resp.code());
            }

            if ("success".equals(status)) {
                JSONArray arr = json.getJSONArray("message");
                List<String> list = new ArrayList<>(arr.length());
                for (int i = 0; i < arr.length(); i++) {
                    list.add(arr.getString(i));
                }
                return list;
            }

            if ("error".equals(status) && json.optInt("code", 0) == 404) {
                throw new BreedNotFoundException(breed);
            }

            throw new RuntimeException("Unexpected API payload");
        } catch (JSONException e) {
            throw new RuntimeException("Invalid JSON", e);
        } catch (IOException e) {
            throw new RuntimeException("Network/IO error", e);
        }
    }
}

