package readability;

public class Readable {

    public int getNoOfWords(String str) {
        return str.split("\\s+").length;
    }

    public int getNoOfSentence(String str) {
        return str.split("[.?!]").length;
    }

    public int getNoOfCharacter(String str) {
        return str.replaceAll("\\s+", "").length();
    }

    public int getNoOfSyllables(String text) {
        text = text.toLowerCase();
        String[] words = text.replaceAll("e\\b", "").
                replaceAll("le", "a").
                replaceAll("[aeiouy]{2,}", "a").
                replaceAll("[0-9]+", "a").split("\\s+");
        int counter = 0;
        int mem = 0;
        for (String word : words) {
            if (counter == 0) {
                mem += 1;
            }
            mem += counter;
            counter = 0;
            for (int i = 0; i < word.length(); i++) {
                if("aeiouy".contains(word.charAt(i)+"")) {
                    counter++;
                }
            }
        }
        return mem;
    }

    public int getPolysyllables (String text) {
        text = text.toLowerCase();
        String[] words = text.replaceAll("e\\b", "").
                replaceAll("le", "a").
                replaceAll("[aeiouy]{2,}", "a").
                replaceAll("[0-9]+", "a").split("\\s+");
        int counter = 0;
        int mem = 0;
        for (String word : words) {
            if (counter > 2) {
                mem += 1;
            }
            counter = 0;
            for (int i = 0; i < word.length(); i++) {
                if("aeiouy".contains(word.charAt(i)+"")) {
                    counter++;
                }
            }
        }
        return mem;
    }
}
