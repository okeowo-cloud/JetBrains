package budget;

import java.io.File;
import java.util.*;

public class Main {

    static ClothesService clothesService = new ClothesService();
    static EntertainmentService entertainmentService = new EntertainmentService();
    static FoodService foodService = new FoodService();
    static OtherServices otherServices = new OtherServices();
    static WalletService walletService = new WalletService();

    public static void main(String[] args) {
        String path = "./purchases.txt";
        File file = new File(path);
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.print("\nChoose your action:\n" +
                    "1) Add income\n" +
                    "2) Add purchase\n" +
                    "3) Show list of purchases\n" +
                    "4) Balance\n" +
                    "5) Save\n" +
                    "6) Load\n" +
                    "7) Analyze (Sort)\n" +
                    "0) Exit\n");
            int option = Integer.parseInt(scanner.nextLine());
            switch(option) {
                case 1:
                    System.out.println("\nEnter income:");
                    double amount = Double.parseDouble(scanner.nextLine());
                    walletService.incrementBalance(amount);
                    System.out.println("Income was added!");
                    break;
                case 2:
                    purchaseNavigator(scanner);
                    break;
                case 3:
                    if(foodService.getFoodPurchaseList().isEmpty() &&
                    clothesService.getClothesPurchaseList().isEmpty() &&
                    entertainmentService.getEntertainmentPurchaseList().isEmpty() &&
                    otherServices.getOtherPurchaseList().isEmpty()) {
                        System.out.println("\nThe purchase list is empty!");
                    } else {
                        displayPurchaseNavigator(scanner);
                    }
                    break;
                case 4:
                    walletService.displayBalance();
                    break;
                case 5:
                    foodService.savePurchase(file);
                    entertainmentService.savePurchase(file);
                    clothesService.savePurchase(file);
                    otherServices.savePurchase(file);
                    walletService.saveBalance(file);
                    System.out.println("\nPurchases were saved!");
                    break;
                case 6:
                    walletService.loadBalance(file);
                    foodService.loadFoodPurchases(file);
                    entertainmentService.loadEntertainmentPurchase(file);
                    clothesService.loadClothPurchases(file);
                    otherServices.loadOtherPurchases(file);
                    System.out.println("\nPurchases were loaded!");
                    break;
                case 7:
                    sortNavigator(scanner);
                    break;
                case 0:
                    System.out.println("\nBye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid entry, enter a number between 0 & 4");
                    break;
            }
        } while(true);
    }

    public static void purchaseNavigator(Scanner scanner) {
        do {
            System.out.println("\nChoose the type of purchase\n" +
                    "1) Food\n2) Clothes\n3) Entertainment\n4) Other" +
                    "\n5) Back");
            int option = Integer.parseInt(scanner.nextLine());
            switch(option) {
                case 1:
                    System.out.println("\nEnter purchase name:");
                    String foodItem = scanner.nextLine();
                    System.out.println("Enter its price:");
                    double foodPrice = Double.parseDouble(scanner.nextLine());
                    foodService.addPurchase(foodItem, foodPrice);
                    walletService.decrementBalance(foodPrice);
                    System.out.println("Purchase was added!");
                    break;
                case 2:
                    System.out.println("\nEnter purchase name:");
                    String clothItem = scanner.nextLine();
                    System.out.println("Enter its price:");
                    double clothPrice = Double.parseDouble(scanner.nextLine());
                    clothesService.addPurchase(clothItem, clothPrice);
                    walletService.decrementBalance(clothPrice);
                    System.out.println("Purchase was added!");
                    break;
                case 3:
                    System.out.println("\nEnter purchase name:");
                    String entertainmentItem = scanner.nextLine();
                    System.out.println("Enter its price:");
                    double entertainmentPrice = Double.parseDouble(scanner.nextLine());
                    entertainmentService.addPurchase(entertainmentItem, entertainmentPrice);
                    walletService.decrementBalance(entertainmentPrice);
                    System.out.println("Purchase was added!");
                    break;
                case 4:
                    System.out.println("\nEnter purchase name:");
                    String otherItem = scanner.nextLine();
                    System.out.println("Enter its price:");
                    double otherItemPrice = Double.parseDouble(scanner.nextLine());
                    otherServices.addPurchase(otherItem, otherItemPrice);
                    walletService.decrementBalance(otherItemPrice);
                    System.out.println("Purchase was added!");
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid request");
                    break;
            }
        } while(true);
    }

    public static void displayPurchaseNavigator(Scanner scanner) {
        do {
            System.out.println("\nChoose the type of purchases\n" +
                    "1) Food\n2) Clothes\n3) Entertainment\n4) Other" +
                    "\n5) All\n6) Back");
            int option = Integer.parseInt(scanner.nextLine());
            switch(option) {
                case 1:
                    foodService.showPurchaseList();
                    break;
                case 2:
                    clothesService.showPurchaseList();
                    break;
                case 3:
                    entertainmentService.showPurchaseList();
                    break;
                case 4:
                    otherServices.showPurchaseList();
                    break;
                case 5:
                    displayAllPurchase();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid request");
                    break;
            }
        } while(true);
    }

    public static double getTotalExpenses() {
        return foodService.getSum() +
                clothesService.getSum() +
                entertainmentService.getSum() +
                otherServices.getSum();
    }

    public static void displayAllPurchase() {
        System.out.println("\nAll:");
        for (String s : foodService.getFoodPurchaseList()) {
            System.out.println(s);
        }
        for (String s : clothesService.getClothesPurchaseList()) {
            System.out.println(s);
        }
        for (String s : entertainmentService.getEntertainmentPurchaseList()) {
            System.out.println(s);
        }
        for (String s : otherServices.getOtherPurchaseList()) {
            System.out.println(s);
        }
        System.out.printf("Total sum: $%.2f\n", getTotalExpenses());
    }

    public static void sortAllPurchases() {
        List<String> tempAllPurchases = new ArrayList<>();
        tempAllPurchases.addAll(foodService.getFoodPurchaseList());
        tempAllPurchases.addAll(clothesService.getClothesPurchaseList());
        tempAllPurchases.addAll(entertainmentService.getEntertainmentPurchaseList());
        tempAllPurchases.addAll(otherServices.getOtherPurchaseList());
        boolean bool;
        do {
            for (int i = 0; i < tempAllPurchases.size() - 1; i++) {
                if(getSum(tempAllPurchases.get(i + 1)) > getSum(tempAllPurchases.get(i))) {
                    String temp = tempAllPurchases.get(i + 1);
                    tempAllPurchases.set(i + 1, tempAllPurchases.get(i));
                    tempAllPurchases.set(i, temp);
                }
            }
            bool = isSorted(tempAllPurchases);
        } while (!bool);
        if (tempAllPurchases.isEmpty()) {
            System.out.println("\nThe purchase list is empty!");
        } else {
            System.out.print("\nAll:\n");
            tempAllPurchases.forEach(System.out::println);
            System.out.printf("Total: $%.2f\n", getTotalExpenses());
        }
    }


    public static boolean isSorted(List<String> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            if(getSum(list.get(i + 1)) > getSum(list.get(i))) {
                return false;
            }
        }
        return true;
    }

    public static double getSum(String str) {
        double sum = 0;
        for( String x : str.split(" ")) {
            if (x.matches("[$]\\d+.\\d+")) {
                sum = Double.parseDouble(x.substring(1));
            }
        }
        return sum;
    }

    public static void sortNavigator(Scanner scanner) {
        do {
            System.out.println("\nHow do you want to sort?\n" +
                    "1) Sort all purchases\n" +
                    "2) Sort by type\n" +
                    "3) Sort certain type\n" +
                    "4) Back");

            int option = Integer.parseInt(scanner.nextLine());
            switch(option) {
                case 1:
                    sortAllPurchases();
                    break;
                case 2:
                    sortByType();
                    break;
                case 3:
                    sortByTypeNavigator(scanner);
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid entry!");
                    break;
            }
        } while (true);
    }

    public static void sortByTypeNavigator(Scanner scanner) {
        System.out.println("\nChoose the type of purchase\n" +
                "1) Food\n" +
                "2) Clothes\n" +
                "3) Entertainment\n" +
                "4) Other");
        int option = Integer.parseInt(scanner.nextLine());
        switch(option) {
            case 1:
                foodService.sortFoodPurchaseList();
                foodService.showPurchaseList();
                break;
            case 2:
                clothesService.sortClothesPurchaseList();
                clothesService.showPurchaseList();
                break;
            case 3:
                entertainmentService.sortEntertainmentPurchaseList();
                entertainmentService.showPurchaseList();
                break;
            case 4:
                otherServices.sortOtherPurchaseList();
                otherServices.showPurchaseList();
                break;
            default:
                System.out.println("Invalid entry!");
        }
    }

    public static void sortByType() {
        List<String> sortTypeList = new ArrayList<>();
        System.out.println("\nTypes:");
        sortTypeList.add("Food - " + String.format("$%.2f",foodService.getSum()));
        sortTypeList.add("Entertainment - " + String.format("$%.2f",entertainmentService.getSum()));
        sortTypeList.add("Clothes - " + String.format("$%.2f",clothesService.getSum()));
        sortTypeList.add("Other - " + String.format("$%.2f",otherServices.getSum()));
        boolean bool;
        do {
            for (int i = 0; i < sortTypeList.size() - 1; i++) {
                if(getSum(sortTypeList.get(i + 1)) > getSum(sortTypeList.get(i))) {
                    String temp = sortTypeList.get(i + 1);
                    sortTypeList.set(i + 1, sortTypeList.get(i));
                    sortTypeList.set(i, temp);
                }
            }
            bool = isSorted(sortTypeList);
        } while (!bool);
        sortTypeList.forEach(System.out::println);
        System.out.printf("Total sum: $%.2f\n", getTotalExpenses());
    }
}
