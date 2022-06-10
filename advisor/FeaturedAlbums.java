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

public class FeaturedAlbums implements Menu {

    private String featuredEndPoint = "%s/v1/browse/featured-playlists";
    private List<String> featuredAlbumList;

    public void setEndpoint(String resource) {
        this.featuredEndPoint = String.format(this.featuredEndPoint, resource);
    }

    public HttpRequest createRequest(String token) {
        return HttpRequest
                .newBuilder()
                .header("Authorization", "Bearer " + token)
                .uri(URI.create(featuredEndPoint))
                .GET()
                .timeout(Duration.ofSeconds(30))
                .build();
    }

    public void getResponse(HttpClient client, String token) throws IOException, InterruptedException {
        HttpResponse<String> response = client.send(createRequest(token), HttpResponse.BodyHandlers.ofString());
        featuredAlbumList = parseResponse(response.body());
    }

    public List<String> parseResponse(String featured) {
        List<String> featuredPlaylist = new ArrayList<>();
        JsonObject jo = JsonParser.parseString(featured).getAsJsonObject();
        JsonObject temp = jo.getAsJsonObject("playlists");
        JsonArray arr = temp.getAsJsonArray("items");
        for(JsonElement element : arr) {
            String desc = element.getAsJsonObject().get("name").getAsString();
            String link = element.getAsJsonObject().get("external_urls").getAsJsonObject().get("spotify").getAsString();
            featuredPlaylist.add(desc + ";" + link);
        }
        return featuredPlaylist;
    }

    public List<String> getFeaturedAlbumList() {
        return featuredAlbumList;
    }
}