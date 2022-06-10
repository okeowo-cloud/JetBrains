package advisor;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.*;

public class Categories implements Menu {

    private String categoryEndPoint = "%s/v1/browse/categories";
    public Map<String, String> categoryMap = new LinkedHashMap<>();
    private Set<String> categorySet;

    public void setEndpoint(String resource) {
        this.categoryEndPoint = String.format(categoryEndPoint, resource);
    }

    public HttpRequest createRequest(String token) {
        return HttpRequest.newBuilder()
                .header("Authorization", "Bearer " + token)
                .uri(URI.create(categoryEndPoint))
                .GET()
                .timeout(Duration.ofSeconds(30))
                .build();
    }

    public void getResponse(HttpClient client, String token) throws IOException, InterruptedException {
        HttpResponse<String> response = client.send(createRequest(token), HttpResponse.BodyHandlers.ofString());
        categorySet = parseCategories(response.body()).keySet();
    }

    public Map<String, String> parseCategories(String categories) {
        JsonObject jo = JsonParser.parseString(categories).getAsJsonObject();
        JsonArray ja = jo.get("categories").getAsJsonObject().get("items").getAsJsonArray();
        for (JsonElement element : ja) {
            String categoryName = element.getAsJsonObject().get("name").getAsString();
            String categoryId = element.getAsJsonObject().get("id").getAsString();
            categoryMap.put(categoryName, categoryId);
        }
        return categoryMap;
    }

    public List<String> parseResponse(String category) {
        List<String> categoryList = new ArrayList<>();
        JsonObject jo = JsonParser.parseString(category).getAsJsonObject();
        JsonArray ja = jo.get("playlists").getAsJsonObject().get("items").getAsJsonArray();
        for (JsonElement element : ja) {
            String playlistName = element.getAsJsonObject()
                    .get("name")
                    .getAsString();

            String link = element.getAsJsonObject()
                    .get("external_urls")
                    .getAsJsonObject()
                    .get("spotify")
                    .getAsString();

            categoryList.add(playlistName + ";" +  link);
        }
        return categoryList;
    }

    public List<String> getCategoryList() {
        return new ArrayList<>(categorySet);
    }
}