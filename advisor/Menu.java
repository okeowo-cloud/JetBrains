package advisor;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.util.List;

public interface Menu {
    void setEndpoint(String endpoint);
    HttpRequest createRequest(String token);
    void getResponse(HttpClient client, String token) throws IOException, InterruptedException;
    List<String> parseResponse(String response);
}