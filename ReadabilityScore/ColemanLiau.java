package readability;

public class ColemanLiau extends Readable {
    private static final double constant1 = 0.0588;
    private static final double constant2 = 0.296;
    private static final double constant3 = 15.8;

    public double getColemanScore(String s) {
        double avgCharacter = ((double)getNoOfCharacter(s) / (double)getNoOfWords(s)) * 100;
        double avgSentence = ((double)getNoOfSentence(s) / (double)getNoOfWords(s)) * 100;
        return constant1 * avgCharacter - constant2 * avgSentence - constant3;
    }

    public void displayColemanScore(String s) {
        System.out.printf("Coleman–Liau index: %.2f ", getColemanScore(s));
    }
}
