package readability;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        String file = args[0];
        String s = "";
        try (Scanner ignored = new Scanner(file)) {
            s = ReadFileAsString(file);
        } catch (IOException e) {
            System.out.println("Exception: " + e.getMessage() );
        }
        displayWordAttributes(s);
        System.out.println("Enter the score you want to calculate (ARI, FK, SMOG, CL, all):");
        String choice = new java.util.Scanner(System.in).next().toUpperCase();
        switch (choice) {
            case "ARI":
                AutomatedReadability ac = new AutomatedReadability();
                double acScore = ac.getARScore(ac.getNoOfCharacter(s), ac.getNoOfWords(s), ac.getNoOfSentence(s));
                ac.displayAutomatedReadability(s);
                System.out.printf("(about %d-year-olds)." ,getAge(acScore));
                break;
            case "FK":
                FleschKincaid fk = new FleschKincaid();
                double fkScore = fk.getFleschScore(fk.getNoOfWords(s), fk.getNoOfSentence(s), fk.getNoOfSyllables(s));
                fk.displayFleschScore(s);
                System.out.printf("(about %d-year-olds).",getAge(fkScore));
                break;
            case "SMOG":
                Smog smog = new Smog();
                double smogScore = smog.getSmogScore(smog.getPolysyllables(s), smog.getNoOfSentence(s));
                smog.displaySmogIndex(s);
                System.out.printf("(about %d-year-olds).",getAge(smogScore));
                break;
            case "CL":
                ColemanLiau cl = new ColemanLiau();
                double clScore = cl.getColemanScore(s);
                cl.displayColemanScore(s);
                System.out.printf("(about %d-year-olds).", getAge(clScore));
                break;
            case "ALL":
                System.out.println();
                AutomatedReadability ab = new AutomatedReadability();
                double abScore = ab.getARScore(ab.getNoOfCharacter(s), ab.getNoOfWords(s), ab.getNoOfSentence(s));
                ab.displayAutomatedReadability(s);
                System.out.printf("(about %d-year-olds).\n", getAge(abScore));
                FleschKincaid fd = new FleschKincaid();
                double fdScore = fd.getFleschScore(fd.getNoOfWords(s),fd.getNoOfSentence(s), fd.getNoOfSyllables(s));
                fd.displayFleschScore(s);
                System.out.printf("(about %d-year-olds).\n", getAge(fdScore));
                Smog smg = new Smog();
                double smgScore = smg.getSmogScore(smg.getPolysyllables(s), smg.getNoOfSentence(s));
                smg.displaySmogIndex(s);
                System.out.printf("(about %d-year-olds).\n", getAge(smgScore));
                ColemanLiau cl2 = new ColemanLiau();
                double cl2Score = cl2.getColemanScore(s);
                cl2.displayColemanScore(s);
                System.out.printf("(about %d-year-olds).\n", getAge(cl2Score));
                System.out.println();
                System.out.printf("This text should be understood in average by %.2f-year-olds.",
                        (double)(getAge(abScore) + getAge(fdScore) + getAge(smgScore) + getAge(cl2Score)) / 4);
                break;
            default:
                System.out.println("Pls enter a valid choice");
                break;
        }
    }

    public static String ReadFileAsString (String fileName) throws IOException {
        return Files.readString(Paths.get(fileName));
    }

    public static void displayWordAttributes(String s){
        Readable rd = new Readable();
        System.out.printf("The text is: \n%s\n", s);
        System.out.println();
        System.out.println("Words: " + rd.getNoOfWords(s));
        System.out.println("Sentences: " + rd.getNoOfSentence(s));
        System.out.println("Characters: " + rd.getNoOfCharacter(s));
        System.out.println("Syllables: " + rd.getNoOfSyllables(s));
        System.out.println("Polysyllables: " + rd.getPolysyllables(s));
    }

    public static int getAge (double score) {
        int newScore = (int) Math.round(score);
        if (newScore == 1) {
            newScore = 6;
        } else if (newScore == 2) {
            newScore = 7;
        } else if (newScore == 3) {
            newScore = 9;
        } else if (newScore == 4) {
            newScore = 10;
        } else if (newScore == 5) {
            newScore = 11;
        } else if (newScore == 6) {
            newScore = 12;
        } else if (newScore == 7) {
            newScore = 13;
        } else if (newScore == 8) {
            newScore = 14;
        } else if (newScore == 9) {
            newScore = 15;
        } else if (newScore == 10) {
            newScore = 16;
        } else if (newScore == 11) {
            newScore = 17;
        } else if (newScore == 12) {
            newScore = 18;
        } else if (newScore == 13) {
            newScore = 24;
        } else {
            newScore = 25;
        }
        return newScore;
    }
}