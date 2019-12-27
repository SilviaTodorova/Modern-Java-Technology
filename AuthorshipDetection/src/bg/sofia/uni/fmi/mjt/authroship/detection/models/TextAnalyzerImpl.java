package bg.sofia.uni.fmi.mjt.authroship.detection.models;

import bg.sofia.uni.fmi.mjt.authroship.detection.models.contracts.TextAnalyzer;

import java.io.*;
import java.util.*;
import java.util.function.ToLongFunction;
import java.util.regex.Matcher;

import static bg.sofia.uni.fmi.mjt.authroship.detection.models.common.GlobalConstants.*;
import static bg.sofia.uni.fmi.mjt.authroship.detection.models.common.GlobalFunctions.cleanUp;
import static bg.sofia.uni.fmi.mjt.authroship.detection.models.common.GlobalFunctions.lastIndexOfRegex;
import static bg.sofia.uni.fmi.mjt.authroship.detection.models.common.Validator.checkNotNull;
import static java.nio.charset.StandardCharsets.UTF_8;

public class TextAnalyzerImpl implements TextAnalyzer {
    private Set<String> sentences;
    private Map<String, Integer> words;

    public TextAnalyzerImpl(InputStream mysteryText) {
        setSentences(mysteryText);
        fillWords(sentences);
    }

    @Override
    public double getAverageCountWords() {
        return words.entrySet()
                .stream()
                .map(Map.Entry::getKey)
                .map(str -> str.replaceAll(PUNCTUATION_REGEX, EMPTY_STRING))
                .mapToInt(String::length)
                .average()
                .getAsDouble();
    }

    @Override
    public double getTypeTokeRatio() {
        long countOfUsedWords = getCountOfUsedWords();
        long countOfAllWords = getCountOfAllWords();
        return (double) countOfUsedWords / countOfAllWords;
    }

    @Override
    public double getHapaxLegomenaRatio() {
        long countOfUniqWords = getCountOfUniqWords();
        long countOfAllWords = getCountOfAllWords();
        return (double) countOfUniqWords / countOfAllWords;
    }

    @Override
    public double getAverageSentenceLength() {
        long countOfAllWords = getCountOfAllWords();
        long countSentences = getCountSentences();
        return (double) countOfAllWords / countSentences;
    }

    @Override
    public double getAverageSentenceComplexity() {
        // Сложност на изречение - средният брой фрази в изречение
        ToLongFunction<String> mapper = sentence -> Arrays.stream(sentence.split(DELIMITERS_PHRASE_REGEX)).mapToLong(String::length).filter(APPEAR_ONE_PREDICATE).count();
        return sentences.stream().mapToLong(mapper).average().getAsDouble();
    }

    private void setSentences(InputStream mysteryText) {
        sentences = new HashSet<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(mysteryText, UTF_8), BUFFER_SIZE)) {
            String buff;
            StringBuilder remainder = new StringBuilder();

            while ((buff = reader.readLine()) != null) {
                StringBuilder sentence = remainder.append(" ").append(cleanUp(buff).trim());
                Matcher matcher = PATTERN_DELIMITERS_SENTENCES.matcher(sentence);

                if (matcher.find()) {
                    int lastIndex = lastIndexOfRegex(sentence.toString(), DELIMITERS_SENTENCES_REGEX);
                    String start = sentence.substring(0, lastIndex);
                    String[] allSentences = start.trim().split(DELIMITERS_SENTENCES_REGEX);

                    Arrays.stream(allSentences).filter(REMOVE_EMPTY_WORDS_PREDICATE).forEach(sentences::add);
                    remainder.replace(0, remainder.length(), sentence.substring(lastIndex + 1));

                }
            }

            String remainderAsString = remainder.toString().trim();
            if (REMOVE_EMPTY_WORDS_PREDICATE.test(remainderAsString)) {
                sentences.add(remainderAsString);
            }

        } catch (Exception ex) {
            // TODO:
        }
    }

    private void fillWords(Set<String> sentences) {
        words = new HashMap<>();
        sentences.stream()
                .flatMap(str -> Arrays.stream(str.split(DELIMITER_WORDS_REGEX)))
                .filter(REMOVE_EMPTY_WORDS_PREDICATE)
                .forEach(this::addWord);

    }

    private void addWord(String word) {
        checkNotNull(word);

        int counter = 0;
        if (words.containsKey(word)) {
            counter = words.get(word);
        }

        words.put(word, ++counter);
    }

    private long getCountSentences() {
        return sentences.size();
    }

    private long getCountOfUniqWords() {
        return words.entrySet()
                .stream()
                .mapToInt(Map.Entry::getValue)
                .filter(value -> value == APPEAR_ONE)
                .count();
    }

    private long getCountOfUsedWords() {
        return words.entrySet()
                .stream()
                .mapToInt(Map.Entry::getValue)
                .count();
    }

    private long getCountOfAllWords() {
        return words.entrySet()
                .stream()
                .mapToInt(Map.Entry::getValue)
                .sum();
    }

    //    private void fillPhrases(Set<String> sentences) {
//        for (String sentence : sentences) {
//            String[] splitSentence = sentence.split(DELIMITERS_PHRASE_REGEX);
//
//            if (splitSentence.length > 1) {
//                phrases = Arrays.stream(splitSentence)
//                        .filter(REMOVE_EMPTY_WORDS)
//                        .collect(Collectors.toSet());
//            }
//
//        }
//    }
}
