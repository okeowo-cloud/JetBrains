package phonebook;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class SearchUtil {

    public static List<String> readFile(File aFile, Boolean bool) {
        List<String> lst = new ArrayList<>();
        try (Scanner scanner = new Scanner(aFile)) {
            if (bool) {
                while (scanner.hasNext()) {
                    lst.add(scanner.nextLine().toLowerCase().strip());
                }
            } else {
                while (scanner.hasNext()) {
                    String s = scanner.nextLine().toLowerCase();
                    lst.add(s.substring(8).strip() + " " + (s.substring(0, 8).strip()).strip());
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found!: " + e.getMessage());
        }
        return lst;
    }

    public static void bubbleSort(List<String> phonebook) {
        Collections.sort(phonebook);
    }

    public static void linearSearch(List<String> firstList, List<String> secondList) {
        System.out.println("Start searching (linear search)...");
        int counter = 0;
        for (String name : secondList) {
            for (String record : firstList) {
                if (record.contains(name)) {
                    counter++;
                    break;
                }
            }
        }
        System.out.printf("Found %d / %d entries. Time taken: ", counter, secondList.size());
    }

    public static int jumpSearch(List<String> sortedList, String value) {
        int n = sortedList.size();
        double step = Math.floor(Math.sqrt(n));
        int curr = 1;
        int index;
        int count = 0;
        while (curr <= n) {
            if (sortedList.get(curr).substring(0, sortedList.get(curr).length() - 8).strip().compareTo(value) == 0) {
                count++;
                return count;
            } else if (sortedList.get(curr).substring(0, sortedList.get(curr).length() - 8).strip().
                    compareTo(value) > 0) {
                index = curr - 1;
                while (index > curr - step && index >= 0) {
                    if (sortedList.get(index).substring(0, sortedList.get(index).length() - 8).strip().
                            compareTo(value) == 0) {
                        count++;
                        return count;
                    }
                    index -= 1;
                }
                return count;
            }
            curr += step;
        }
        index = sortedList.size() - 1;
        while (index > curr - step) {
            if (sortedList.get(index).substring(0, sortedList.get(index).length() - 8).strip().compareTo(value) == 0) {
                count++;
                return count;
            }
            index -= 1;
        }
        return count;
    }

    public static void bubbleSortPlusJumpSearch(List<String> l1, List<String> l2) {
        System.out.println("Start searching (bubble sort + jump search)...");
        int counter = 0;
        for (String s : l2) {
            if (jumpSearch(l1, s) > 0) {
                counter++;
            }
        }
        System.out.printf("Found %d / %d entries. Time taken: ", counter, l2.size());
    }

    public static int partition(List<String> arr, int start, int end) {
        int i = start - 1;
        for (int j = start; j <= end; j++) {
            if (arr.get(j).substring(0, arr.get(j).length() - 8).strip().
                    compareTo(arr.get(end).substring(0, arr.get(end).length() - 8).strip()) <= 0) {
                i++;
                String temp = arr.get(i);
                arr.set(i, arr.get(j));
                arr.set(j, temp);
            }
        }
        return i;
    }

    public static void quickSort(List<String> array, int start, int end) {
        if (start < end) {
            int pivot = partition(array, start, end);
            quickSort(array, start, pivot - 1);
            quickSort(array, pivot + 1, end);
        }
    }

    public static int binarySearch(List<String> array, String value) {
        int left = 0;
        int right = array.size() - 1;
        int middle = (left + right) / 2;

        while (!Objects.equals(array.get(middle).substring(0, array.get(middle).length() - 8).strip(), value)) {
            if (value.compareTo(array.get(middle).substring(0, array.get(middle).length() - 8).strip()) < 0) {
                right = middle - 1;
            } else {
                left = middle + 1;
            }
            middle = (left + right) / 2;
        }
        if (Objects.equals(array.get(middle).substring(0, array.get(middle).length() - 8).strip(), value)) {
            return 1;
        }
        else {
            return 0;
        }
    }

    public static void quickSortPluBinarySearch(List<String> arr1, List<String> arr2) {
        System.out.println("Start searching (quick sort + binary search)...");
        long startQuickSort = System.currentTimeMillis();
        quickSort(arr1, 0, arr1.size() - 1);
        long endQuickSort = System.currentTimeMillis();
        int counter = 0;
        for (String s : arr2) {
            counter += binarySearch(arr1, s);
        }
        long endBinarySearch = System.currentTimeMillis();
        System.out.printf("Found %d / %d entries. Time taken: %s\nSorting time: %s\nSearching time: %s",
                counter,
                arr2.size(),
                timeConverter(startQuickSort, endBinarySearch),
                timeConverter(startQuickSort, endQuickSort),
                timeConverter(endQuickSort, endBinarySearch));
    }

    public static String timeConverter(long startTime, long endTime) {
        long totalTime = endTime - startTime;
        int milliseconds = (int) totalTime % 1000;
        int seconds = (int) totalTime / 1000 % 60;
        int minutes = (int) totalTime / 60 / 1000;
        return String.format("%d min. %d sec. %d ms. ", minutes, seconds, milliseconds);
    }

    public static HashMap<String, String> storage(List<String> list) {
        HashMap<String, String> store = new HashMap<>();
        for (String s : list) {
            store.put(s.substring(0, s.length() - 8).strip(), s);
        }
        return store;
    }

    public static void HashTable(List<String> listToStore, List<String> listToSearch) {
        System.out.println("\n\nStart searching (hash table)...");
        long startStore = System.currentTimeMillis();
        HashMap<String, String> store = storage(listToStore);
        long endStore = System.currentTimeMillis();
        int totalHit = 0;
        for (String toSearch : listToSearch) {
            String result = store.get(toSearch);
            if (result != null) {
                totalHit += 1;
            }
        }
        long endSearch = System.currentTimeMillis();
        System.out.printf("Found %d / %d entries. Time taken: %s\n", totalHit, listToSearch.size(),
                timeConverter(startStore, endSearch));
        System.out.printf("Creating time: %s\nSearching time: %s",
                timeConverter(startStore, endStore),timeConverter(endStore, endSearch));
    }
}
