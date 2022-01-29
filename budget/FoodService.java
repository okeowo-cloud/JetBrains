package budget;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FoodService {
    private final Menu menu;
    private final List<String> FoodPurchaseList;

    FoodService() {
        this.menu = new Menu();
        this.FoodPurchaseList = new ArrayList<>();
    }

    public void addPurchase(String item, double price) {
        FoodPurchaseList.add(menu.addPurchase(item, price));
    }

    public void showPurchaseList() {
        if (FoodPurchaseList.isEmpty()) {
            System.out.println("\nFood:");
            System.out.println("\nThe purchase list is empty!");
        } else {
            System.out.println("\nFood:");
            menu.displayPurchaseList(FoodPurchaseList);
            System.out.printf("Total sum: $%.2f\n", getSum());
        }
    }

    public void savePurchase(File file) {
        try (FileWriter writer = new FileWriter(file, true)) {
            for (String s : FoodPurchaseList) {
                writer.write("1\n");
                writer.write(s + "\n");
            }
        } catch (IOException e) {
            System.out.printf("An exception occurred %s", e.getMessage());
        }
    }

    public List<String> getFoodPurchaseList() {
        return FoodPurchaseList;
    }

    public void loadFoodPurchases(File file) {
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                if (scanner.nextLine().equals("1")) {
                    FoodPurchaseList.add(scanner.nextLine());
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("No file found: " + e.getMessage());
        }
    }

    public void sortFoodPurchaseList() {
        boolean bool;
        do {
            for (int i = 0; i < FoodPurchaseList.size() - 1; i++) {
                if(getSum(FoodPurchaseList.get(i + 1)) > getSum(FoodPurchaseList.get(i))) {
                    String temp = FoodPurchaseList.get(i + 1);
                    FoodPurchaseList.set(i + 1, FoodPurchaseList.get(i));
                    FoodPurchaseList.set(i, temp);
                }
            }
            bool = isSorted();
        } while (!bool);
    }

    public boolean isSorted() {
        for (int i = 0; i < FoodPurchaseList.size() - 1; i++) {
            if(getSum(FoodPurchaseList.get(i + 1)) > getSum(FoodPurchaseList.get(i))) {
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

    public double getSum() {
        double sum = 0;
        for (String s : FoodPurchaseList) {
            for( String x : s.split(" ")) {
                if (x.matches("[$]\\d+.\\d+")) {
                    sum += Double.parseDouble(x.substring(1));
                }
            }
        }
        return sum;
    }
}