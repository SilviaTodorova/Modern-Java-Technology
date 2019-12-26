package bg.sofia.uni.fmi.mjt.authroship.detection.models;

import java.io.*;
import java.text.BreakIterator;
import java.util.*;

import static bg.sofia.uni.fmi.mjt.authroship.detection.models.common.GlobalConstants.*;
import static bg.sofia.uni.fmi.mjt.authroship.detection.models.common.GlobalFunctions.cleanUp;
import static bg.sofia.uni.fmi.mjt.authroship.detection.models.common.Validator.checkNotNull;

public class WordAnalyzer {
    private final Map<String, Integer> map;

    public WordAnalyzer(InputStream mysteryText) {
        map = new HashMap<>();

        try(BufferedReader br = new BufferedReader(new InputStreamReader(mysteryText, UTF8), BUFFER_SIZE)) {
            String chunk;

            while ((chunk = br.readLine()) != null) {
                String[] tokens = cleanUp(chunk).split("\\s+");
                Arrays.stream(tokens).filter(x->!x.equals(EMPTY_STRING)).forEach(w -> add(w));
            }

            int x =0;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public long getCountOfWords() {
        return map.size();
    }

    public long getCountOfUniqueWords() {
        return map.entrySet()
                .stream()
                .mapToInt(Map.Entry::getValue)
                .filter(count -> count == 1)
                .count();
    }

    public long getCountOfAllWords() {
        return map.entrySet()
                .stream()
                .mapToInt(Map.Entry::getValue)
                .sum();
    }

    public double getAverageWordLength() {
        return map.entrySet()
                .stream()
                .map(Map.Entry::getKey)
                .mapToInt(String::length)
                .average()
                .getAsDouble();
    }

    public void add(String word) {
        checkNotNull(word);

        int counter = 0;
        if(map.containsKey(word)){
            counter = map.get(word);
        }

        map.put(word, ++counter);
    }

}