import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Scanner;

public class Main {

    static enum LyricType {
        INTRO,
        CHORUS,
        VERSE,
        BRIDGE,
        OUTRO
    }

    public static String filterLyric(String lyric) {
        String filteredCharacters = ".,\"\'{}";

        for (char c : filteredCharacters.toCharArray()) {
            if (lyric.contains(String.valueOf(c))) {
                lyric = lyric.replaceAll(String.valueOf(c), "");
            }
        }
        lyric = lyric.replace("?", "").replace("(", "").replace(")", "")
                .replace("[", "").replace("]", "");
        return lyric;
    }

    public static void addToMap(HashMap<String, Double> wordMap, String word) {
        if (wordMap.containsKey(word)) {
            wordMap.put(word, wordMap.get(word) + 1.0);
        } else {
            wordMap.put(word, 1.0);
        }
    }

    public static void cleanMap(HashMap<String, Double> wordMap) {
        int total = 0;

        for (Double value : wordMap.values()) {
            total += value;
        }

        for (String key : wordMap.keySet()) {
            wordMap.put(key, wordMap.get(key)/total);
        }
    }

    public static void main(String[] args) throws FileNotFoundException {

        File file = new File("/Users/rehanparwani/Desktop/Algorithms/Drake Markov Models/src/drake_lyrics.txt");
        Scanner scanner = new Scanner(file);

        ArrayList<String> lyrics = new ArrayList<String>();

        while (scanner.hasNext()) {
            String lyric = scanner.next();
            if (lyric.contains("[") || lyric.contains("]")) {
                continue;
            }
            lyric = lyric.toLowerCase();
            lyrics.add(filterLyric(lyric));
        }

        scanner = new Scanner(file);

        LyricType lyricType = LyricType.VERSE;

        HashMap<String, Double> startingIntro = new HashMap<String, Double>();
        ArrayList<String> introLyrics = new ArrayList<String>();

        HashMap<String, Double> startingVerse = new HashMap<String, Double>();
        ArrayList<String> verseLyrics = new ArrayList<String>();

        HashMap<String, Double> startingChorus = new HashMap<String, Double>();
        ArrayList<String> chorusLyrics = new ArrayList<String>();

        HashMap<String, Double> startingBridge = new HashMap<String, Double>();
        ArrayList<String> bridgeLyrics = new ArrayList<String>();

        HashMap<String, Double> startingOutro = new HashMap<String, Double>();
        ArrayList<String> outroLyrics = new ArrayList<String>();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().toLowerCase();
            boolean firstWord = true;
            if (line.contains("[") && line.contains("intro")) {
                lyricType = LyricType.INTRO;
                continue;
            } else if (line.contains("[") && line.contains("verse")) {
                lyricType = LyricType.VERSE;
                continue;
            } else if (line.contains("[") && line.contains("chorus")) {
                lyricType = LyricType.CHORUS;
                continue;
            } else if (line.contains("[") && line.contains("bridge")) {
                lyricType = LyricType.BRIDGE;
                continue;
            } else if (line.contains("[") && line.contains("outro")) {
                lyricType = LyricType.OUTRO;
                continue;
            }
            while(!line.isEmpty()) {
                String word = "";
                if (line.contains(" ")) {
                    word = line.substring(0, line.indexOf(" "));
                    line = line.substring(line.indexOf(" ") + 1);
                } else {
                    line = "";
                }
                word = filterLyric(word);
                switch (lyricType) {
                    case INTRO:
                        if (firstWord) {
                            addToMap(startingIntro, word);
                        }
                        introLyrics.add(word);
                        break;
                    case VERSE:
                        if (firstWord) {
                            addToMap(startingVerse, word);
                        }
                        verseLyrics.add(word);
                        break;
                    case CHORUS:
                        if (firstWord) {
                            addToMap(startingChorus, word);
                        }
                        chorusLyrics.add(word);
                        break;
                    case BRIDGE:
                        if (firstWord) {
                            addToMap(startingBridge, word);
                        }
                        bridgeLyrics.add(word);
                        break;
                    case OUTRO:
                        if (firstWord) {
                            addToMap(startingOutro, word);
                        }
                        outroLyrics.add(word);
                        break;
                }
                firstWord = false;
            }
        }

        System.out.println("Original Lyrics: " + lyrics.size());
        System.out.println("Indiv Lyrics Size - Intro: " + introLyrics.size() + " Verse: " + verseLyrics.size() + " Chorus: "
        + chorusLyrics.size() + " Bridge: " + bridgeLyrics.size() + " Outro: " + outroLyrics.size() + " Total: "
        + (introLyrics.size() + verseLyrics.size() + chorusLyrics.size() + bridgeLyrics.size() + outroLyrics.size()));

        System.out.println();

//        //intro, chorus, verse 1, chorus, verse 2, outro

        cleanMap(startingIntro);
        cleanMap(startingVerse);
        cleanMap(startingBridge);
        cleanMap(startingChorus);
        cleanMap(startingOutro);

        Markov markovModel = new Markov(lyrics);

        System.out.println("INTRO");
        markovModel.generateLyricLength(startingIntro, 5, 4, 3, 5);

        System.out.println();

        System.out.println("CHORUS");
        markovModel.generateLyricLength(startingChorus, 5, 4, 5, 4);

        System.out.println();

        System.out.println("VERSE 1");
        markovModel.generateLyricLength(startingVerse, 5, 4, 5, 4);

        System.out.println();

        System.out.println("VERSE 2");
        markovModel.generateLyricLength(startingVerse, 5, 4, 5, 4);

        System.out.println();

        System.out.println("OUTRO");
        markovModel.generateLyricLength(startingOutro, 5, 4, 3, 5);



    }
}