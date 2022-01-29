package budget;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OtherServices {
    private final Menu menu;
    private final List<String> otherPurchaseList;

    OtherServices(){
        this.menu = new Menu();
        this.otherPurchaseList = new ArrayList<>();
    }

    public void addPurchase(String item, double price) {
        otherPurchaseList.add(menu.addPurchase(item, price));
    }

    public void showPurchaseList() {
        if (otherPurchaseList.isEmpty()) {
            System.out.println("\nOther:");
            System.out.println("The purchase list is empty!");
        } else {
            System.out.println("Other:");
            menu.displayPurchaseList(otherPurchaseList);
            System.out.printf("Total sum: $%.2f\n", getSum());
        }
    }

    public List<String> getOtherPurchaseList() {
        return otherPurchaseList;
    }

    public void savePurchase(File file) {
        try (FileWriter writer = new FileWriter(file, true)) {
            for (String s : otherPurchaseList) {
                writer.write("4\n");
                writer.write(s + "\n");
            }
        } catch (IOException e) {
            System.out.printf("An exception occurred %s", e.getMessage());
        }
    }

    public void loadOtherPurchases(File file) {
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                if (scanner.nextLine().equals("4")) {
                    otherPurchaseList.add(scanner.nextLine());
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("No file found: " + e.getMessage());
        }
    }

    public double getSum() {
        double sum = 0;
        for (String s : otherPurchaseList) {
            for( String x : s.split(" ")) {
                if (x.matches("[$]\\d+.\\d+")) {
                    sum += Double.parseDouble(x.substring(1));
                }
            }
        }
        return sum;
    }

    public void sortOtherPurchaseList() {
        boolean bool;
        do {
            for (int i = 0; i < otherPurchaseList.size() - 1; i++) {
                if(getSum(otherPurchaseList.get(i + 1)) > getSum(otherPurchaseList.get(i))) {
                    String temp = otherPurchaseList.get(i + 1);
                    otherPurchaseList.set(i + 1, otherPurchaseList.get(i));
                    otherPurchaseList.set(i, temp);
                }
            }
            bool = isSorted();
        } while (!bool);
    }

    public boolean isSorted() {
        for (int i = 0; i < otherPurchaseList.size() - 1; i++) {
            if(getSum(otherPurchaseList.get(i + 1)) > getSum(otherPurchaseList.get(i))) {
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