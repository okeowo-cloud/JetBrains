package bullscows;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Input the length of the secret code:");
            int num = scanner.nextInt();
            System.out.println("Input the number of possible symbols in the code:");
            int num2 = scanner.nextInt();
            String secretCode = " ";
            boolean bool = false;
            if (num != 0 && num2 >= num && num2 <= 36) {
                while (!bool) {
                    secretCode = generatePseudoRandomNumber(num, num2);
                    bool = checkUniqueness(secretCode);
                }
                int upperBoundary = 97 - 1 - 10 + num2;
                System.out.print("The secret is prepared: ");
                for (int i = 0; i < num; i++) {
                    System.out.print("*");
                }
                System.out.print(num2 > 10 ? " (0-9, a-" +(char)upperBoundary+").\n" : " (0-9)\n");
                System.out.println("Okay, let's start a game!");
                int counter = 0;
                int bulls;
                do {
                    bulls = 0;
                    int cow = 0;
                    counter++;
                    System.out.printf("Turn %d:\n", counter);
                    String guess = scanner.next();
                    for (int i = 0; i < secretCode.length(); i++) {
                        if (Objects.equals(secretCode.charAt(i), guess.charAt(i))) {
                            bulls++;
                        } else if (secretCode.contains(guess.charAt(i)+"")) {
                            cow++;
                        }
                    }
                    System.out.print("Grade: ");
                    System.out.print(bulls == 0 && cow == 0 ? "None.\n" : cow == 0 ? bulls + " bull(s).\n" : bulls == 0 ?
                            cow + " cow(s).\n" : bulls + " bull(s) and " + cow + " cow(s).\n");
                } while (bulls != num);
                System.out.println("Congratulations! You guessed the secret code.");
            } else if (num2 < num) {
                System.out.printf("Error: it's not possible to generate a code with a length of %d with %d unique symbols.", num, num2);
            } else {
                System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
            }
        } catch (InputMismatchException e) {
            System.out.printf("Error: %s isn't a valid number.", e.getCause());
        }
    }

    public static String generatePseudoRandomNumber(int num, int num2) {
        String string = "0123456789abcdefghijklmnopqrstuvwxyz";
        int lowerBound = 0;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < num; i++) {
            sb.append(string.charAt((int) Math.round(Math.random() * (num2 - lowerBound + 1) + lowerBound)));
        }
        return sb.toString();
    }

    public static boolean checkUniqueness(String str) {
        boolean bool = true;
        for (int i = 0; i < str.length(); i++) {
            for (int j = i + 1; j < str.length(); j++) {
                if(str.charAt(i) == str.charAt(j)) {
                    bool = false;
                    break;
                }
            }
        }
        return bool;
    }
}
