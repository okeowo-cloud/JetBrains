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
import java.util.Arrays;
import java.util.List;

public class NewAlbums implements Menu {

    private String newEndpoint = "%s/v1/browse/new-releases";
    private List<String> newAlbumList;

    public void setEndpoint(String resource) {
        this.newEndpoint = String.format(this.newEndpoint, resource);
    }

    public HttpRequest createRequest(String token) {
        return HttpRequest
                .newBuilder()
                .header("Authorization", "Bearer " + token)
                .uri(URI.create(newEndpoint))
                .GET()
                .timeout(Duration.ofSeconds(30))
                .build();
    }

    public void getResponse(HttpClient client, String token) throws IOException, InterruptedException {
        HttpResponse<String> response = client.send(createRequest(token), HttpResponse.BodyHandlers.ofString());
        newAlbumList = parseResponse(response.body());
    }

    public List<String> parseResponse(String newAlbums) {
        String link;
        String albumName;
        String artist;

        List<String> newAlbumList = new ArrayList<>();

        JsonObject jo = JsonParser.parseString(newAlbums).getAsJsonObject();

        JsonArray temp = jo.getAsJsonObject("albums")
                .getAsJsonArray("items");

        for (JsonElement element : temp) {

            link = element.getAsJsonObject()
                    .get("external_urls")
                    .getAsJsonObject()
                    .get("spotify")
                    .getAsString();

            albumName = element.getAsJsonObject().get("name").getAsString();

            JsonArray tmp = element.getAsJsonObject().get("artists").getAsJsonArray();

            String[] allArtist = new String[tmp.size()];
            int i = 0;
            for (JsonElement element1 : tmp) {
                artist = element1
                        .getAsJsonObject()
                        .get("name")
                        .getAsString();

                allArtist[i] = artist;
                i++;
            }
            newAlbumList.add(String.format("%s;%s;%s", albumName, Arrays.toString(allArtist), link));
        }
        return newAlbumList;
    }

    public List<String> getNewAlbumList() {
        return newAlbumList;
    }
}