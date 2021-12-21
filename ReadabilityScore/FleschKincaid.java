package readability;

public class FleschKincaid extends Readable {
    private static final double constant1 = 0.39;
    private static final double constant2 = 11.8;
    private static final double constant3 = 15.59;

    public double getFleschScore(double noOfWords, double noOfSentences, double noOfSyllables) {
        return constant1 * (noOfWords / noOfSentences) + constant2 *
                (noOfSyllables / noOfWords) - constant3;
    }

    public void displayFleschScore(String s) {
        System.out.printf("Flesch–Kincaid readability tests: %.2f ",
                getFleschScore(getNoOfWords(s), getNoOfSentence(s), getNoOfSyllables(s)));
    }

}
