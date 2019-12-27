package bg.sofia.uni.fmi.mjt.authroship.detection.models;

import bg.sofia.uni.fmi.mjt.authroship.detection.models.contracts.WordAnalyzer;

import java.io.*;
import java.util.*;

import static bg.sofia.uni.fmi.mjt.authroship.detection.models.common.GlobalConstants.*;
import static bg.sofia.uni.fmi.mjt.authroship.detection.models.common.GlobalFunctions.cleanUp;
import static bg.sofia.uni.fmi.mjt.authroship.detection.models.common.Validator.checkNotNull;
import static java.nio.charset.StandardCharsets.UTF_8;

public class WordAnalyzerImpl implements WordAnalyzer {
    private final Map<String, Integer> map;

    public WordAnalyzerImpl(InputStream mysteryText) {
        map = new HashMap<>();



        try(BufferedReader br = new BufferedReader(new InputStreamReader(mysteryText, UTF_8), BUFFER_SIZE)) {
            String chunk;
            while ((chunk = br.readLine()) != null) {
                String[] tokens = cleanUp(chunk).split("\\s+");
                Arrays.stream(tokens).filter(x->!x.equals(EMPTY_STRING)).forEach(this::add);
            }
            int x = 0;
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