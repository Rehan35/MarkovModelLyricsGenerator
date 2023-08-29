import java.util.ArrayList;
import java.util.HashMap;

public class Markov {

    private static int nGrams = 1;

    private ArrayList<String> lyrics;

    public Markov(ArrayList<String> lyrics) {
        this.lyrics = lyrics;
    }

    private void addWord(HashMap<String, Double> map, String word) {
        if (map.containsKey(word)) {
            map.put(word, map.get(word) + 1);
        } else {
            map.put(word, 1.0);
        }
    }

    public HashMap<String, Double> getWordMap(String initialWord) {
        HashMap<String, Double> wordMap = new HashMap<>();
        int total = 0;

        for (int i = 0; i < lyrics.size(); i++) {
            if (lyrics.get(i).equals(initialWord.toLowerCase())) {
                if (i < lyrics.size() - 1) {
                    addWord(wordMap, lyrics.get(i + 1));
                    total++;
                }
            }
        }

        for (String key : wordMap.keySet()) {
            double percentage = wordMap.get(key)/(double) total;
            wordMap.put(key, percentage);
        }

        return wordMap;
    }

    public String getClosestRandomKey(HashMap<String, Double> wordMap, Double percentage) {
        Double percentageDifference = Double.MAX_VALUE;
        String word = "";
        for (String key : wordMap.keySet()) {
            double pDiff = Math.abs(percentage - wordMap.get(key));
            if (pDiff < percentageDifference) {
                word = key;
                percentageDifference = pDiff;
            }
        }
        return word;
    }

    public String generateLyric(String initialWord, int length) {
        initialWord = initialWord.toLowerCase();
        String lyric = initialWord;

        for (int i = 1; i <= length; i++) {
            HashMap<String, Double> wordMap = getWordMap(initialWord);
            Double randomDouble = Math.random();
            if (wordMap.size() > 6) {
                randomDouble /= 10;
            }
            String word = getClosestRandomKey(wordMap, randomDouble);
            lyric += " " + word;
            initialWord = word;
        }
        return lyric;
    }

    public void generateLyricLength(HashMap<String, Double> startingMap, int param1, int param2, int param3, int param4) {
        int randomLinesValue = param1 + (int) (Math.random() * param2);

        String introWord = getClosestRandomKey(startingMap, Math.random()/10);

        for (int i = 0; i < randomLinesValue; i++) {
            int lyricLength = param3 + (int) (Math.random() * param4);
            if (i > 0) {
                String startingWord = generateLyric(introWord, 1);
                introWord = startingWord.substring(startingWord.lastIndexOf(" ") + 1);
            }
            String lyric = generateLyric(introWord, lyricLength - 1);
            System.out.println(lyric);
            introWord = lyric.substring(lyric.lastIndexOf(" ") + 1);
        }
    }

}
