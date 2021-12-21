package readability;

class AutomatedReadability extends Readable {

    private static final double constant1 = 4.71;
    private static final double constant2 = 21.43;
    private static final double constant3 = 0.5;

    public double getARScore(double noOfChars, double noOfWords, double noOfSentences) {
        return (constant1 * (noOfChars / noOfWords)) + (constant3 * (noOfWords / noOfSentences) - constant2);
    }

    public void displayAutomatedReadability(String s) {
        System.out.printf("Automated Readability Index: %.2f ", getARScore(getNoOfCharacter(s),getNoOfWords(s), getNoOfSentence(s)));
    }
}
