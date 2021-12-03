package cinema;

import java.util.Scanner;

public class Cinema {

    private static int noOfPurchasedTickets = 0;
    private static int currentIncome = 0;
    private static final Scanner scanner = new Scanner(System.in);
    private static final int screenRoomPrice = 10;
    private static final int largerFrontHalfPrice = 10;
    private static final int largerBackHalfPrice = 8;
    private static final int seatLimit = 60;

    public static void main(String[] args) {
        System.out.println("Enter the number of rows:");
        int row = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int column = scanner.nextInt();
        int seatRow;
        int seatNo;
        int menu;

        String[][] seatArray = new String[row][column + 1];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column + 1; j++) {
                if (j == 0) {
                    seatArray[i][j] = String.valueOf(i + 1);
                } else {
                    seatArray[i][j] = "S";
                }
            }
        }
        do {
            System.out.println("\n1. Show the seats\n2. Buy a ticket\n3. Statistics\n0. Exit");
            menu = scanner.nextInt();
            switch (menu) {
                case 1:
                    showSeat(seatArray);
                    break;
                case 2:
                    System.out.println("\nEnter a row number:");
                    seatRow = scanner.nextInt();
                    System.out.println("Enter a seat number in that row:");
                    seatNo = scanner.nextInt();
                    buyATicket(seatArray, row, column, seatRow, seatNo);
                    break;
                case 3:
                    statistics(row, column);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("\nInvalid menu option!");
                    break;
            }
        } while (true);
    }

    public static void showSeat(String[][] seatArray) {
        System.out.println("\nCinema: ");
        for (int i = 1; i < seatArray[0].length; i++) {
            if (i == 1) {
                System.out.print("  " + i + " ");
            } else {
                System.out.print(i + " ");
            }
        }
        System.out.println();
        for (String[] strings : seatArray) {
            for (int j = 0; j < seatArray[0].length; j++) {
                System.out.print(strings[j] + " ");
            }
            System.out.println();
        }
    }

    public static int determineTicketPrice(int row, int col, int seatRow) {
        int ticketPrice = 0;
        if (row * col <= seatLimit) {
            ticketPrice = screenRoomPrice;
        } else if (seatRow <= row / 2) {
            ticketPrice = largerFrontHalfPrice;
        } else if (seatRow > row / 2) {
            ticketPrice = largerBackHalfPrice;
        }
        return ticketPrice;
    }

    public static void displayTicketPrice(int row, int col, int seatRow) {
        int ticketPrice = 0;
        if (row * col <= seatLimit) {
            ticketPrice = screenRoomPrice;
        } else if (seatRow <= row / 2) {
            ticketPrice = largerFrontHalfPrice;
        } else if (seatRow > row / 2) {
            ticketPrice = largerBackHalfPrice;
        }
        System.out.println("\nTicket price: " + "$" + ticketPrice);
    }

    public static void buyATicket(String[][] array, int row, int col, int seatRow, int seatNo) {

        if (seatRow < 1 || seatNo < 1 || seatRow > array.length ||
                seatNo > array[0].length) {
            do {
                System.out.println("Wrong input!");
                System.out.println("Enter a row number:");
                seatRow = scanner.nextInt();
                System.out.println("Enter a seat number in that row:");
                seatNo = scanner.nextInt();
            } while (seatRow < 1 || seatNo < 1 || seatRow > array.length || seatNo > array[0].length - 1);
        }

        if (array[seatRow - 1][seatNo].equals("B")) {
            System.out.println("\nThat ticket has already been purchased!");
            do {
                System.out.println("\nEnter a row number:");
                seatRow = scanner.nextInt();
                System.out.println("Enter a seat number in that row:");
                seatNo = scanner.nextInt();
                if (seatRow < 1 || seatRow > array.length ||
                        seatNo > array[0].length - 1){
                    System.out.println("\nWrong input!");
                }
            } while (seatRow < 1 || seatRow > array.length ||
                            seatNo > array[0].length - 1);
        }
        displayTicketPrice(row, col, seatRow);
        array[seatRow - 1][seatNo] = "B";
        noOfPurchasedTickets++;
        currentIncome += determineTicketPrice(row, col, seatRow);
    }

    public static void statistics(int row, int column) {
        System.out.printf("\nNumber of purchased tickets: %d", noOfPurchasedTickets);
        System.out.println();
        double percentagePurchased = (double) noOfPurchasedTickets / (row * column) * 100;
        System.out.printf("Percentage: %.2f", percentagePurchased);
        System.out.print("%");
        System.out.println();
        System.out.printf("Current income: $%d", currentIncome);
        System.out.println();
        System.out.printf("Total income: $%d", calculateTotalIncome(row, column));
        System.out.println();
    }

    public static int calculateTotalIncome(int row, int col) {
        int totalIncome;
        if (row * col <= seatLimit) {
            totalIncome = row * col * screenRoomPrice;
        } else {
            if (row % 2 == 0) {
                totalIncome = ((row / 2) * col * largerFrontHalfPrice) + ((row / 2) * col * largerBackHalfPrice);
            }
            else {
                totalIncome = (col * (row / 2) * largerFrontHalfPrice) + (((row / 2) + 1) * col * largerBackHalfPrice);
            }
        }
        return totalIncome;
    }
}