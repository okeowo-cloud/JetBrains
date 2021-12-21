package readability;

public class Smog extends Readable {
    private static final double constant1 = 1.0430;
    private static final double constant2 = 3.1291;
    private static final double constant3 = 30;

    public double getSmogScore(int noOfPolysyllables, int noOfSentences) {
        return constant1 * Math.sqrt(noOfPolysyllables * (constant3/noOfSentences)) + constant2;
    }

    public void displaySmogIndex(String s) {
        System.out.printf("Simple Measure of Gobbledygook: %.2f ", getSmogScore(getPolysyllables(s), getNoOfSentence(s)));

    }
}
