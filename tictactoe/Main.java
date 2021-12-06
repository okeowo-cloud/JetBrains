package tictactoe;

import java.util.*;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static boolean bool = true;
    private static int cordX = 0;
    private static int cordY = 0;

    public static void main(String[] args) {
        char[][] cellArray = new char[3][3];
        for (char[] chars : cellArray) {
            Arrays.fill(chars, ' ');
        }
        displayGameState(cellArray);
        do {
            playerX(cellArray);
            displayGameState(cellArray);
            if (checkWinDraw(cellArray).equals("X wins") || checkWinDraw(cellArray).equals("Draw")) {
                break;
            }
            playerO(cellArray);
            displayGameState(cellArray);
            checkWinDraw(cellArray);
        } while (Objects.equals(checkWinDraw(cellArray), "Game not finished") &&
                (!Objects.equals(checkWinDraw(cellArray), "X wins") ||
                !Objects.equals(checkWinDraw(cellArray), "O wins") ||
                        Objects.equals(checkWinDraw(cellArray), "Draw")));
        System.out.println(checkWinDraw(cellArray));
    }

    public static void displayGameState(char[][] arr) {
        System.out.println("---------");
        for (char[] chars : arr) {
            for (int j = 0; j < chars.length; j++) {
                if (j == 0) {
                    System.out.printf("| %c ", chars[j]);
                }
                if (j == 1) {
                    System.out.printf("%c ", chars[j]);
                }
                if (j == 2) {
                    System.out.printf("%c |", chars[j]);
                }
            }
            System.out.println();
        }
        System.out.println("---------");
    }

    public static String checkWinDraw(char[][] arr) {
        String result;
        char[] row1 = arr[0];
        char[] row2 = arr[1];
        char[] row3 = arr[2];
        boolean isSpace1 = Objects.equals(row1[0], ' ') || Objects.equals(row1[1], ' ') || Objects.equals(row1[2], ' ');
        boolean isSpace2 = Objects.equals(row2[0], ' ') || Objects.equals(row2[1], ' ') || Objects.equals(row2[2], ' ');
        boolean isSpace3 = Objects.equals(row3[0], ' ') || Objects.equals(row3[1], ' ') || Objects.equals(row3[2], ' ');
        boolean boolRow1o = Objects.equals(row1[0], 'O') && Objects.equals(row1[1], 'O') && Objects.equals(row1[2], 'O');
        boolean boolRow1x = Objects.equals(row1[0], 'X') && Objects.equals(row1[1], 'X') && Objects.equals(row1[2], 'X');
        boolean boolRow2x = Objects.equals(row2[0], 'X') && Objects.equals(row2[1], 'X') && Objects.equals(row2[2], 'X');
        boolean boolRow2o = Objects.equals(row2[0], 'O') && Objects.equals(row2[1], 'O') && Objects.equals(row2[2], 'O');
        boolean boolRow3x = Objects.equals(row3[0], 'X') && Objects.equals(row3[1], 'X') && Objects.equals(row3[2], 'X');
        boolean boolRow3o = Objects.equals(row3[0], 'O') && Objects.equals(row3[1], 'O') && Objects.equals(row3[2], 'O');

        boolean b1 = Objects.equals(arr[0][0], arr[1][0]) && Objects.equals(arr[1][0], arr[2][0]);
        boolean vertical1x = arr[0][0] == 'X' && b1;
        boolean vertical1o = arr[0][0] == 'O' && b1;

        boolean b2 = Objects.equals(arr[0][1], arr[1][1]) && Objects.equals(arr[1][1], arr[2][1]);
        boolean vertical2o = Objects.equals(arr[0][1], 'O') && b2;
        boolean vertical2x = Objects.equals(arr[0][1], 'X') && b2;

        boolean b3 = Objects.equals(arr[0][2], arr[1][2]) && Objects.equals(arr[1][2], arr[2][2]);
        boolean vertical3o = Objects.equals(arr[0][2], 'O') && b3;
        boolean vertical3x = Objects.equals(arr[0][2], 'X') && b3;

        boolean d1 = Objects.equals(arr[0][0], arr[1][1]) && Objects.equals(arr[1][1], arr[2][2]);
        boolean diagonal1x = Objects.equals(arr[0][0], 'X') && d1;
        boolean diagonal1o = Objects.equals(arr[0][0], 'O') && d1;

        boolean d2 = Objects.equals(arr[0][2], arr[1][1]) && Objects.equals(arr[1][1], arr[2][0]);
        boolean diagonal2x = Objects.equals(arr[0][2] , 'X') && d2;
        boolean diagonal2o = Objects.equals(arr[0][2], 'O') && d2;

        if (boolRow1o || boolRow2o || boolRow3o|| vertical1o || vertical2o ||
                vertical3o || diagonal1o || diagonal2o) {
            result = "O wins";
        } else if (boolRow1x || boolRow2x || boolRow3x || vertical1x ||
                vertical2x || vertical3x || diagonal1x || diagonal2x) {
            result = "X wins";
        } else if (!isSpace1 && !isSpace2 && !isSpace3) {
            result = "Draw";
        } else {
            result = "Game not finished";
        }
        return result;
    }

    public static void readCoordinates(char[][] charArr) {
        while (bool) {
            try {
                System.out.print("Enter the coordinates: ");
                String coordinates = scanner.nextLine();
                String[] arr = coordinates.split(" ");
                cordX = Integer.parseInt(arr[0]);
                cordY = Integer.parseInt(arr[1]);
                try {
                    if (Objects.equals(charArr[cordX - 1][cordY - 1], 'O') || Objects.equals(charArr[cordX - 1][cordY - 1], 'X')) {
                        System.out.println("This cell is occupied! Choose another one!");
                        bool = true;
                    } else {
                        bool = false;
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Coordinates should be from 1 to 3!");
                }
            } catch (NumberFormatException e) {
                System.out.println("You should enter numbers!");
                bool = true;
            }
        }
    }

    public static void playerX (char[][] arr) {
        bool = true;
        readCoordinates(arr);
        arr[cordX - 1][cordY - 1] = 'X';
    }
    public static void playerO (char[][] arr) {
        bool = true;
        readCoordinates(arr);
        arr[cordX - 1][cordY - 1] = 'O';
    }
}