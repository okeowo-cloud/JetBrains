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
import java.util.ArrayList;
import java.util.List;

public class CategoryById implements Menu {

    String categoryByIdEndPoint = "%s/v1/browse/categories";
    String browseCategories;
    List<String> categoryByIdList;

    @Override
    public void setEndpoint(String resource) {
        this.categoryByIdEndPoint = String.format(categoryByIdEndPoint,resource);
    }

    public void setCategoryByIdEndPoint(String categoryId) {
        this.browseCategories = String.format(this.categoryByIdEndPoint+"/%s/playlists", categoryId);
    }

    @Override
    public HttpRequest createRequest(String token) {
        return HttpRequest.newBuilder()
                .header("Authorization", "Bearer " + token)
                .uri(URI.create(browseCategories))
                .GET()
                .timeout(Duration.ofSeconds(30))
                .build();
    }

    @Override
    public void getResponse(HttpClient client, String token) throws NullPointerException, InterruptedException {
        String rep = "";
        try {
            HttpResponse<String> response = client.send(createRequest(token), HttpResponse.BodyHandlers.ofString());
            if(response.body().contains("Test unpredictable error message")) {
                rep = response.body();
                System.out.println(rep);
            } else {
                categoryByIdList = parseResponse(response.body());
            }
        } catch (IOException ex) {
            System.out.println(rep);
        }
    }

    @Override
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

    public List<String> getCategoryByIdList() {
        return categoryByIdList;
    }
}
