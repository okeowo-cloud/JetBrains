package advisor;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class Authorization {

    private String spotifyServer = "https://accounts.spotify.com";
    private final String clientID = "1cd14099103546738b7ffa557cacb3a8";
    private final String localHost = "http://localhost:9090";
    private String accessCode;

    public void setSpotifyServer(String serverSpotify) {
        this.spotifyServer = serverSpotify;
    }

    public void getAccessCode() throws IOException, InterruptedException {

        System.out.println("use this link to request the access code:" +
                spotifyServer + "/authorize?" +
                "client_id=" + clientID +
                "&redirect_uri=" + localHost +
                "&response_type=code\n" +
                "waiting for code...");

        HttpServer server = HttpServer.create();

        server.bind(new InetSocketAddress(9090), 0);

        server.createContext("/", exchange -> {
            String query = exchange.getRequestURI().getQuery();
            String request;
            if (query != null && query.contains("code")) {
                accessCode = query.substring(5);
                System.out.println("code received");
                request = "Got the code. Return back to your program.";
            } else {
                request = "Authorization code not found. Try again.";
            }
            exchange.sendResponseHeaders(200, request.length());
            exchange.getResponseBody().write(request.getBytes(StandardCharsets.UTF_8));
            exchange.getResponseBody().close();
        });

        server.start();

        while(accessCode == null) {
            Thread.sleep(100);
        }
        server.stop(5);
    }

    public String getToken(HttpClient client) throws IOException, InterruptedException {

        System.out.println("making http request for access_token...\n" +
                "response:");

        String clientSecret = "960e5e71fffc4f8fa3e9a62e2cff9132";

        HttpRequest request = HttpRequest.newBuilder()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .uri(URI.create(spotifyServer + "/api/token"))
                .POST(HttpRequest.BodyPublishers.ofString("grant_type=authorization_code" +
                        "&code=" + accessCode +
                        "&client_id=" + clientID +
                        "&client_secret=" + clientSecret +
                        "&redirect_uri=" + localHost))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String responseToken = response.body();
        System.out.println("success!");
        return responseToken;
    }

    public String parseToken(String jsonToken) {
        JsonObject jo = JsonParser.parseString(jsonToken).getAsJsonObject();
        return jo.get("access_token").getAsString();
    }
}
