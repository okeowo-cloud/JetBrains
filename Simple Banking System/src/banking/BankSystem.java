package banking;

import java.sql.*;
import java.util.Objects;
import java.util.Scanner;

public class BankSystem {
    String cardNumber;
    String pin;
    int balance;
    private static final Scanner scanner = new Scanner(System.in);

    public BankSystem() {
        this.cardNumber = generateCardNumber();
        this.pin = generatePinCode();
        this.balance = getBalance();
    }

    public String getCardNumber () {
        return this.cardNumber;
    }

    public String getPin() {
        return this.pin;
    }

    public int getBalance() {
        return this.balance;
    }

    public String generateCardNumber() {
        StringBuilder sb = new StringBuilder();
        sb.append(400000);
        for (int i = 0; i < 10; i++) {
            sb.append(Math.round(Math.random() * (9) + 0));
        }
        StringBuilder luhnBuilder = new StringBuilder();
        for (int i = 0; i < sb.length() - 1; i++) {
            if (i % 2 == 0) {
                luhnBuilder.append(Integer.parseInt(sb.charAt(i) + "") * 2);
            } else {
                luhnBuilder.append(sb.charAt(i));
            }
        }
        for (int j = 0; j < luhnBuilder.length(); j++) {
            if (Integer.parseInt(luhnBuilder.charAt(j) + "") > 9) {
                int diff = Integer.parseInt(luhnBuilder.charAt(j) + "") - 9;
                luhnBuilder.deleteCharAt(j);
                luhnBuilder.insert(j, diff);
            }
        }
        int sum = 0;
        for (int i = 0; i < luhnBuilder.length(); i++) {
            sum += Integer.parseInt(luhnBuilder.charAt(i) + "");
        }
        int checkSum = 10 - (sum % 10);
        if (sum % 10 > 0) {
            sb.deleteCharAt(sb.length() - 1);
            sb.append(checkSum);
        } else {
            generateCardNumber();
        }
        this.cardNumber = sb.toString();
        return this.cardNumber;
    }

    public String generatePinCode() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            sb.append(Math.round(Math.random() * (9) + 0));
        }
        this.pin = sb.toString();
        return this.pin;
    }

    public int getBalanceByCard(String fileName, String card) {
        Database db = new Database();
        String sql = "SELECT balance FROM card WHERE number LIKE ?";
        try (Connection conn = db.connect(fileName)) {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, card);
            ResultSet rs = preparedStatement.executeQuery();
            this.balance = rs.getInt("balance");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return this.balance;
    }

    public void logIntoAccount(String fileName) {
        boolean isLoggedIn = false;
        System.out.println("\nEnter your cardNumber:");
        String accountNum = scanner.next();
        System.out.println("Enter your PIN:");
        String pin = scanner.next();
        Database db = new Database();
        String sql = "SELECT * FROM card WHERE number == " + accountNum
                + " AND pin == " + pin;
        try (Connection conn = db.connect(fileName)) {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            if(rs.next()) {
                isLoggedIn = true;
                System.out.println("\nYou have successfully logged in!");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        if (isLoggedIn) {
            boolean bool = true;
            while (bool) {
                System.out.println("\n1. Balance\n2. Add income\n3. Do transfer\n4. Close account\n5. Log out\n0. Exit");
                int option = scanner.nextInt();
                switch (option) {
                    case 1:
                        System.out.printf("\nBalance: %d\n", getBalanceByCard(fileName, accountNum));
                        break;
                    case 2:
                        System.out.println("\nEnter income:");
                        int amount = scanner.nextInt();
                        deposit(fileName, accountNum, amount);
                        System.out.println("Income was added!");
                        break;
                    case 3:
                        doTransfer(fileName, accountNum);
                        break;
                    case 4:
                        closeAccount(fileName, accountNum);
                        bool = false;
                        break;
                    case 5:
                        System.out.println("\nYou have successfully logged out!");
                        bool = false;
                        break;
                    case 0:
                        System.out.println("\nBye!");
                        System.exit(1);
                        break;
                    default:
                        System.out.println("\nInvalid option");
                        break;
                }
            }
        } else {
            System.out.println("\nWrong card number or PIN!");
        }
    }

    public void deposit(String fileName, String card, int amount) {
        Database db = new Database();
        String sql = "UPDATE card SET balance = ? WHERE number = ?";
        try (Connection conn = db.connect(fileName)) {
            if (conn.isValid(5)) {
                try (PreparedStatement prpstmt = conn.prepareStatement(sql)) {
                    int bal = getBalanceByCard(fileName, card);
                    prpstmt.setInt(1, bal + amount);
                    prpstmt.setString(2, card);
                    prpstmt.executeUpdate();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void doTransfer(String fileName, String card) {
        System.out.println("Enter card number:");
        String cardNum = scanner.next();
        if(validateCard(cardNum)) {
            Database db = new Database();
            String sql = "SELECT * FROM card where number LIKE " + cardNum;
            try (Connection conn = db.connect(fileName)) {
                Statement statement = conn.createStatement();
                ResultSet rs = statement.executeQuery(sql);
                if (rs.next() && !Objects.equals(card, cardNum)) {
                    System.out.println("Enter how much money you want to transfer:");
                    int amount = scanner.nextInt();
                    int bal = getBalanceByCard(fileName, card);
                    if (bal >= amount) {
                        conn.setAutoCommit(false);
                        String sqlTransfer = "UPDATE card SET balance = ? WHERE number = ?";
                        PreparedStatement prepStatement = conn.prepareStatement(sqlTransfer);
                        prepStatement.setInt(1, bal - amount);
                        prepStatement.setString(2, card);
                        prepStatement.executeUpdate();
                        int balance = getBalanceByCard(fileName, cardNum);
                        PreparedStatement preparedStatement = conn.prepareStatement(sqlTransfer);
                        preparedStatement.setInt(1, balance + amount);
                        preparedStatement.setString(2, cardNum);
                        preparedStatement.executeUpdate();
                        conn.commit();
                    } else {
                        System.out.println("Not Enough money!");
                    }
                } else if (Objects.equals(card, cardNum)) {
                    System.out.println("You cannot transfer to the same account.");
                } else {
                    System.out.println("Such a card does not exist.");
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Probably you made a mistake in the card number. Please try again!");
        }
    }

    public boolean validateCard(String accNo) {
        boolean isValid = false;
        StringBuilder sb = new StringBuilder();
        sb.append(accNo);
        StringBuilder sbd = new StringBuilder();
        for (int i = 0; i < sb.length(); i++) {
            if (i % 2 == 0) {
                sbd.append(Integer.parseInt(sb.charAt(i) + "") * 2);
            } else {
                sbd.append(sb.charAt(i));
            }
        }
        for (int j = 0; j < sbd.length(); j++) {
            if (Integer.parseInt(sbd.charAt(j) + "") > 9) {
                int diff = Integer.parseInt(sbd.charAt(j) + "") - 9;
                sbd.deleteCharAt(j);
                sbd.insert(j, diff);
            }
        }
        int sum = 0;
        for (int i = 0; i < sbd.length(); i++) {
            sum += Integer.parseInt(sbd.charAt(i) + "");
        }
        if (sum % 10 == 0) {
            isValid = true;
        }
        return isValid;
    }

    public void closeAccount(String fileName, String card) {
        Database db = new Database();
        String sql = "DELETE FROM card WHERE number LIKE ?";
        try (Connection conn = db.connect(fileName)) {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, card);
            preparedStatement.executeUpdate();
            System.out.println("The account has been closed!");
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}