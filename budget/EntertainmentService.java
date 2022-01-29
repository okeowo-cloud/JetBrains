package budget;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EntertainmentService {
    private final Menu menu;
    private final List<String> entertainmentPurchaseList;

    EntertainmentService() {
        this.menu = new Menu();
        this.entertainmentPurchaseList = new ArrayList<>();
    }

    public void addPurchase(String item, double price) {
        entertainmentPurchaseList.add(menu.addPurchase(item, price));
    }

    public void showPurchaseList() {
        if (entertainmentPurchaseList.isEmpty()) {
            System.out.println("\nEntertainment:");
            System.out.println("The purchase list is empty!");
        } else {
            System.out.println("\nEntertainment:");
            menu.displayPurchaseList(entertainmentPurchaseList);
            System.out.printf("Total sum: $%.2f\n", getSum());
        }
    }

    public List<String> getEntertainmentPurchaseList() {
        return entertainmentPurchaseList;
    }

    public void savePurchase(File file) {
        try (FileWriter writer = new FileWriter(file, true)) {
            for (String s : entertainmentPurchaseList) {
                writer.write("3\n");
                writer.write(s + "\n");
            }
        } catch (IOException e) {
            System.out.printf("An exception occurred %s", e.getMessage());
        }
    }

    public void loadEntertainmentPurchase(File file) {
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                if (scanner.nextLine().equals("3")) {
                    entertainmentPurchaseList.add(scanner.nextLine());
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("No file found: " + e.getMessage());
        }
    }

    public double getSum() {
        double sum = 0;
        for (String s : entertainmentPurchaseList) {
            for( String x : s.split(" ")) {
                if (x.matches("[$]\\d+.\\d+")) {
                    sum += Double.parseDouble(x.substring(1));
                }
            }
        }
        return sum;
    }

    public void sortEntertainmentPurchaseList() {
        boolean bool;
        do {
            for (int i = 0; i < entertainmentPurchaseList.size() - 1; i++) {
                if(getSum(entertainmentPurchaseList.get(i + 1)) > getSum(entertainmentPurchaseList.get(i))) {
                    String temp = entertainmentPurchaseList.get(i + 1);
                    entertainmentPurchaseList.set(i + 1, entertainmentPurchaseList.get(i));
                    entertainmentPurchaseList.set(i, temp);
                }
            }
            bool = isSorted();
        } while (!bool);
    }

    public boolean isSorted() {
        for (int i = 0; i < entertainmentPurchaseList.size() - 1; i++) {
            if(getSum(entertainmentPurchaseList.get(i + 1)) > getSum(entertainmentPurchaseList.get(i))) {
                return false;
            }
        }
        return true;
    }

    public double getSum(String str) {
        double sum = 0;
        for( String x : str.split(" ")) {
            if (x.matches("[$]\\d+.\\d+")) {
                sum = Double.parseDouble(x.substring(1));
            }
        }
        return sum;
    }
}