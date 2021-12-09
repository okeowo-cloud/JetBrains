package machine;

import java.util.Scanner;

class CoffeeMachine {

    private static final Scanner scanner = new Scanner(System.in);
    static int waterAvailable;
    static int milkAvailable;
    static int coffeeBeansAvailable;
    public static int disposableCups;
    public static int totalAmount;

    CoffeeMachine () {
        waterAvailable = 400;
        milkAvailable = 540;
        coffeeBeansAvailable = 120;
        disposableCups = 9;
        totalAmount = 550;
    }

    public static void fill() {
        System.out.println("Write how many ml of water you want to add: ");
        waterAvailable += scanner.nextInt();
        System.out.println("Write how many ml of milk you want to add: ");
        milkAvailable += scanner.nextInt();
        System.out.println("Write how many grams of coffee beans you want to add: ");
        coffeeBeansAvailable += scanner.nextInt();
        System.out.println("Write how many disposable cups of coffee you want to add: ");
        disposableCups += scanner.nextInt();
    }

    public static void displayIngredient(){
        System.out.println("\nThe coffee machine has:");
        System.out.printf("%d ml of water\n%d ml of milk\n%d g of coffee beans\n%d disposable cups\n$%d of money\n",
                waterAvailable, milkAvailable, coffeeBeansAvailable, disposableCups, totalAmount);
    }

    public static void buyCoffee() {
        System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
        String coffeeType = scanner.next();
        switch (coffeeType) {
            case "1":
                makeEspresso();
                break;
            case "2":
                makeLatte();
                break;
            case "3":
                makeCappuccino();
                break;
            case "back":
                return;
            default:
                System.out.println("Invalid Entry");
                break;
        }
    }

    public static void takeMoney(){
        System.out.printf("I gave you $%d\n", totalAmount);
        totalAmount -= totalAmount;
    }

    public void takeAction () {
        String userInput;
        do {
            System.out.println("\nWrite action (buy, fill, take, remaining, exit):");
            userInput = scanner.next();
            userInput = userInput.toUpperCase();
            for (Actions actions : Actions.values()) {
                String strAction = String.valueOf(actions);
                if (strAction.equals(userInput)) {
                    switch (userInput) {
                        case "BUY":
                            buyCoffee();
                            break;
                        case "FILL":
                            fill();
                            break;
                        case "TAKE":
                            takeMoney();
                            break;
                        case "REMAINING":
                            displayIngredient();
                            break;
                        case "EXIT":
                            return;
                        default:
                            System.out.println("Invalid action");
                            break;
                    }
                }
            }
        } while(!userInput.equals("EXIT"));
    }

    public static void makeEspresso() {
        if (waterAvailable >= 250 && coffeeBeansAvailable >= 16 && disposableCups >= 1) {
            System.out.println("I have enough resources, making you a coffee!");
            waterAvailable -= Coffee.ESPRESSO.getWATER();
            coffeeBeansAvailable -= Coffee.ESPRESSO.getCOFFEE_BEANS();
            totalAmount += Coffee.ESPRESSO.getPRICE();
            disposableCups -= 1;
        } else {
            if (waterAvailable < 250 && coffeeBeansAvailable >= 16) {
                System.out.println("Sorry, not enough water!");
            } else if (waterAvailable > 250 && coffeeBeansAvailable < 16) {
                System.out.println("Sorry, not enough coffee beans!");
            } else {
                System.out.println("Sorry, not enough cups!");
            }
        }
    }

    public static void makeLatte() {
        if (waterAvailable >= 350 && coffeeBeansAvailable >= 20 && milkAvailable >= 75 && disposableCups >= 1) {
            System.out.println("I have enough resources, making you a coffee!");
            waterAvailable -= Coffee.LATTE.getWATER();
            milkAvailable -= Coffee.LATTE.getMILK();
            coffeeBeansAvailable -= Coffee.LATTE.getCOFFEE_BEANS();
            totalAmount += Coffee.LATTE.getPRICE();
            disposableCups -= 1;
        } else if (waterAvailable >= 350 && coffeeBeansAvailable < 20) {
            System.out.println("Sorry, not enough coffee beans!");
        }
        else if (waterAvailable >= 350 && milkAvailable < 75) {
            System.out.println("Sorry, not enough milk!");
        }
        else if (waterAvailable < 350) {
            System.out.println("Sorry, not enough water!");
        } else {
            System.out.println("Sorry, not enough cups!");
        }
    }

    public static void makeCappuccino() {
        if (waterAvailable >= 200 && coffeeBeansAvailable >= 12 && milkAvailable >= 100 && disposableCups >= 1) {
            System.out.println("I have enough resources, making you a coffee!");
            waterAvailable -= Coffee.CAPPUCCINO.getWATER();
            milkAvailable -= Coffee.CAPPUCCINO.getMILK();
            coffeeBeansAvailable -= Coffee.CAPPUCCINO.getCOFFEE_BEANS();
            totalAmount += Coffee.CAPPUCCINO.getPRICE();
            disposableCups -= 1;
        } else if (waterAvailable < 200 && milkAvailable >= 100) {
            System.out.println("Sorry, not enough water!");
        } else if (waterAvailable >= 200 && milkAvailable < 100) {
            System.out.println("Sorry, not enough milk!");
        } else if (waterAvailable >= 200 && coffeeBeansAvailable < 12) {
            System.out.println("Sorry, not enough coffee beans");
        } else {
            System.out.println("Sorry, not enough cups");
        }
    }
}
