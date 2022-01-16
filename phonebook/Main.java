package phonebook;

import java.io.File;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        File fileDirectory = new File("/Users/tundeokeowo/Downloads/directory.txt");
        File fileFind = new File("/Users/tundeokeowo/Downloads/find.txt");

        List<String> list = SearchUtil.readFile(fileFind, true);
        List<String> ls = SearchUtil.readFile(fileDirectory, false);
        List<String> ls1 = SearchUtil.readFile(fileDirectory,false);
        List<String> unsortedLs = SearchUtil.readFile(fileDirectory, true);

        long startLinearSearch = System.currentTimeMillis();
        SearchUtil.linearSearch(unsortedLs, list);
        long endLinearSearch = System.currentTimeMillis();
        System.out.print(SearchUtil.timeConverter(startLinearSearch, endLinearSearch));
        System.out.println();
        System.out.println();
        SearchUtil.bubbleSort(ls1);
        long endBubbleSort = System.currentTimeMillis();
        SearchUtil.bubbleSortPlusJumpSearch(ls1, list);
        System.out.print(SearchUtil.timeConverter(startLinearSearch, endBubbleSort));
        System.out.printf("\nSorting time: %s\nSearching time: %s\n",
                SearchUtil.timeConverter(startLinearSearch, endLinearSearch),
                SearchUtil.timeConverter(endLinearSearch, endBubbleSort));
        System.out.println();
        SearchUtil.quickSortPluBinarySearch(ls, list);

        SearchUtil.HashTable(ls, list);
    }
}
