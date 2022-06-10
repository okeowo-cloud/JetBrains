package advisor;

import java.util.*;

public class PaginatedResponse {

    public HashMap<Integer, List<String>> page;
    public Integer pageNo = 1;
    public List<String> stringResponse;
    List<String> tmp = new ArrayList<>();
    static int pageLimit;

    private static PaginatedResponse INSTANCE;

    private PaginatedResponse(List<String> response) {
        this.stringResponse = response;
    }

    public static PaginatedResponse getNewPaginatedResponse(List<String> resp) {
        INSTANCE = new PaginatedResponse(resp);
        return INSTANCE;
    }

    public static PaginatedResponse getPaginatedResponse() {
        if (INSTANCE != null) {
            return INSTANCE;
        }
        return null;
    }

    public static void setPageLimit(int limit) {
        pageLimit = limit;
    }

    public void paginateStringResponse() {
        Integer pageCount = 0;
        int counter = 0;
        page = new HashMap<>();
        for (String s : stringResponse) {
            tmp.add(s);
            counter++;
            if (counter == pageLimit) {
                pageCount++;
                page.put(pageCount, tmp);
                tmp = new ArrayList<>();
                counter = 0;
            }
        }
    }

    public void nextPage() {
        if (this.pageNo < page.size()) {
            this.pageNo++;
            displayCurrentPage();
        } else {
            System.out.println("No more pages.");
        }
    }

    public void prevPage() {
        if (this.pageNo > 1) {
            this.pageNo--;
            displayCurrentPage();
        } else {
            System.out.println("No more pages.");
        }
    }

    public void displayCurrentPage() {
        paginateStringResponse();
        List<String> pageToDisplay = page.get(pageNo);
        for (String str : pageToDisplay) {
            Arrays.stream(str.split(";")).forEach(System.out::println);
            System.out.println();
        }
        String desc = "---PAGE %d OF %d---";
        System.out.printf(desc,pageNo, page.size());
    }
}