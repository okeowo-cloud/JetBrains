package budget;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class WalletService {

    private double balance = 0;

    public void incrementBalance(double balance) {
        this.balance += balance;
    }

    public void decrementBalance(double balance) {
        this.balance -= balance;
    }

    public void displayBalance() {
        System.out.printf("\nBalance: $%.2f\n", balance);
    }

    public void saveBalance(File file) {
        try (FileWriter writer = new FileWriter(file, true)) {
            writer.write("balance:\n");
            writer.write(balance + "\n");
        } catch (IOException e) {
            System.out.printf("An exception occurred %s", e.getMessage());
        }
    }

    public void loadBalance(File file) {
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                if (scanner.nextLine().equals("balance:")) {
                    balance = Double.parseDouble(scanner.nextLine());
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("No file found: " + e.getMessage());
        }
    }
}