package budget;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClothesService {
    private final Menu menu;
    private final List<String> clothesPurchaseList;

    ClothesService() {
        this.clothesPurchaseList = new ArrayList<>();
        this.menu = new Menu();
    }

    public void addPurchase(String item, double price) {
        clothesPurchaseList.add(menu.addPurchase(item, price));
    }

    public void showPurchaseList() {
        if (clothesPurchaseList.isEmpty()) {
            System.out.println("\nClothes:");
            System.out.println("The purchase list is empty!");
        } else {
            System.out.println("\nClothes:");
            menu.displayPurchaseList(clothesPurchaseList);
            System.out.printf("Total sum: $%.2f\n", getSum());
        }
    }

    public List<String> getClothesPurchaseList() {
        return clothesPurchaseList;
    }

    public void savePurchase(File file) {
        try (FileWriter writer = new FileWriter(file, true)) {
            for (String s : clothesPurchaseList) {
                writer.write("2\n");
                writer.write(s + "\n");
            }
        } catch (IOException e) {
            System.out.printf("An exception occurred %s", e.getMessage());
        }
    }

    public void loadClothPurchases(File file) {
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                if (scanner.nextLine().equals("2")) {
                    clothesPurchaseList.add(scanner.nextLine());
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("No file found: " + e.getMessage());
        }
    }

    public double getSum() {
        double sum = 0;
        for (String s : clothesPurchaseList) {
            for( String x : s.split(" ")) {
                if (x.matches("[$]\\d+.\\d+")) {
                    sum += Double.parseDouble(x.substring(1));
                }
            }
        }
        return sum;
    }

    public void sortClothesPurchaseList() {
        boolean bool;
        do {
            for (int i = 0; i < clothesPurchaseList.size() - 1; i++) {
                if(getSum(clothesPurchaseList.get(i + 1)) > getSum(clothesPurchaseList.get(i))) {
                    String temp = clothesPurchaseList.get(i + 1);
                    clothesPurchaseList.set(i + 1, clothesPurchaseList.get(i));
                    clothesPurchaseList.set(i, temp);
                }
            }
            bool = isSorted();
        } while (!bool);
    }

    public boolean isSorted() {
        for (int i = 0; i < clothesPurchaseList.size() - 1; i++) {
            if(getSum(clothesPurchaseList.get(i + 1)) > getSum(clothesPurchaseList.get(i))) {
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