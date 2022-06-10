package advisor;

import java.io.IOException;
import java.net.http.HttpClient;
import java.util.List;
import java.util.Scanner;

public class Main {

    static final Scanner scanner = new Scanner(System.in);
    static final HttpClient client = HttpClient.newBuilder().build();
    static final Authorization authorization = new Authorization();
    static final FeaturedAlbums featured = new FeaturedAlbums();
    static final Categories categories = new Categories();
    static final CategoryById cid = new CategoryById();
    static final NewAlbums nb = new NewAlbums();
    static boolean isAuth = false;
    static String responseToken = "";

    public static void main(String[] args) throws IOException, InterruptedException {
        if (args.length > 1 && "-access".equals(args[0])) {
            authorization.setSpotifyServer(args[1]);
        }
        if (args.length > 1 && "-resource".equals(args[2])) {
            featured.setEndpoint(args[3]);
            nb.setEndpoint(args[3]);
            categories.setEndpoint(args[3]);
            cid.setEndpoint(args[3]);
        }

        if(args.length > 1 && "-page".equals(args[4])) {
            PaginatedResponse.setPageLimit(Integer.parseInt(args[5]));
        }
        getUserRequest(scanner);
    }

    public static void getUserRequest(Scanner scanner) throws IOException, InterruptedException {
        do {
            String userRequest = scanner.nextLine();
            if(userRequest.equals("new")) {
                if (isAuth) {
                    nb.getResponse(client, responseToken);
                    List<String> resp = nb.getNewAlbumList();
                    PaginatedResponse pg = PaginatedResponse.getNewPaginatedResponse(resp);
                    pg.displayCurrentPage();
                } else {
                    System.out.println("Please, provide access for application.");
                }
            }
            else if(userRequest.equals("featured")) {
                if(isAuth) {
                    featured.getResponse(client, responseToken);
                    List<String> resp = featured.getFeaturedAlbumList();
                    PaginatedResponse pg = PaginatedResponse.getNewPaginatedResponse(resp);
                    pg.displayCurrentPage();
                } else {
                    System.out.println("Please, provide access for application.");
                }
            }
            else if(userRequest.equals("auth")) {
                authorization.getAccessCode();
                String token = authorization.getToken(client);
                responseToken = authorization.parseToken(token);
                System.out.println(responseToken);
                isAuth = true;
            }
            else if (userRequest.equals("next")) {
                PaginatedResponse pg = PaginatedResponse.getPaginatedResponse();
                if (pg != null) {
                    pg.nextPage();
                }
            }

            else if (userRequest.equals("prev")) {
                PaginatedResponse pg = PaginatedResponse.getPaginatedResponse();
                if  (pg != null) {
                    pg.prevPage();
                }
            }
            else if(userRequest.equals("categories")) {
                if(isAuth) {
                    categories.getResponse(client, responseToken);
                    List<String> resp = categories.getCategoryList();
                    PaginatedResponse pg = PaginatedResponse.getNewPaginatedResponse(resp);
                    pg.displayCurrentPage();
                } else {
                    System.out.println("Please, provide access for application.");
                }
            }
            else if(userRequest.contains("playlists ")) {
                if(isAuth) {
                    categories.categoryMap.put("Party Time", "party");
                    categories.categoryMap.put("Top Lists", "toplists");
                    String categoryId = categories.categoryMap.get(userRequest.substring(10));
                    cid.setCategoryByIdEndPoint(categoryId);
                    cid.getResponse(client, responseToken);
                    List<String> resp = cid.getCategoryByIdList();
                    PaginatedResponse pg = PaginatedResponse.getNewPaginatedResponse(resp);
                    pg.displayCurrentPage();
                } else {
                    System.out.println("Please, provide access for application.");
                }
            }
            else if(userRequest.equals("exit")) {
                continue;
            } else {
                System.out.println("Enter Valid Command.");
            }
        } while(true);
    }
}