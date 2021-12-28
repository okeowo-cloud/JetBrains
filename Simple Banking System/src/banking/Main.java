package banking;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String fileName = args[1];
        Database db = new Database();
        db.createNewDatabase(fileName);
        db.createNewTable(fileName);
        do {
            System.out.println();
            System.out.println("\n1. Create an account\n2. Log into account\n0. Exit");
            int option = scanner.nextInt();
            BankSystem bk = new BankSystem();
            switch (option) {
                case 1:
                    String cardNum = bk.getCardNumber();
                    String pin = bk.getPin();
                    System.out.printf("\nYour card has been created\nYour card number:\n%s\nYour card PIN:\n%s", cardNum, pin);
                    db.insertIntoTable(fileName, cardNum, pin, bk.getBalance());
                    break;
                case 2:
                    bk.logIntoAccount(fileName);
                    break;
                case 0:
                    System.out.println("\nBye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("\nInvalid option");
                    break;
            }
        } while (true);
    }
}