package bg.sofia.uni.fmi.mjt.authroship.detection.models;

import bg.sofia.uni.fmi.mjt.authroship.detection.models.contracts.TextAnalyzer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class TextAnalyzerImpl implements TextAnalyzer {
    private static final String ERROR_EXTRACT_DATA = "Error while extracting data from InputStream";

    private static final String REGEX_DELIMITERS_SENTENCES = "[.!?]";
    private static final String REGEX_DELIMITERS_PHRASE = "[,:;]";
    private static final String REGEX_DELIMITER_TOKENS = "\\s+";
    private static final String REGEX_DELIMITER_WORDS = "[\\w0-9]";

    private List<String> sentences;
    private List<String> words;
    private Map<String, Long> wordsCount;

    public TextAnalyzerImpl(InputStream mysteryText) {
        extractData(mysteryText);
    }

    @Override
    public double getAverageWordLength() {
        return words.stream()
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
        long countPhrases = sentences.stream()
                .flatMap(str -> Arrays.stream(str.split(REGEX_DELIMITERS_PHRASE)))
                .count();

        long countSentences = sentences.size();

        return (double) countPhrases / countSentences;
    }

    private void extractData(InputStream mysteryText) {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        mysteryText,
                        StandardCharsets.UTF_8))) {

            int ascii;
            StringBuilder textBuilder = new StringBuilder();

            while ((ascii = reader.read()) != -1) {
                char character = (char) ascii;
                textBuilder.append(character);
            }

            String text = textBuilder.toString();
            setWords(text);
            setSentences(text);

        } catch (IOException ex) {
            throw new RuntimeException(ERROR_EXTRACT_DATA, ex);
        }
    }

    private void setSentences(String text) {
        String[] sentences = text.split(REGEX_DELIMITERS_SENTENCES);
        this.sentences = Arrays.stream(sentences)
                .filter(str -> !str.isEmpty() && !str.isBlank())
                .collect(Collectors.toList());
    }

    private void setWords(String text) {
        Pattern patternDelimitersWords = Pattern.compile(REGEX_DELIMITER_WORDS);
        String[] tokens = text.split(REGEX_DELIMITER_TOKENS);

        this.words = Arrays.stream(tokens)
                .map(this::cleanUp)
                .filter(token -> patternDelimitersWords.matcher(token).find())
                .collect(Collectors.toList());

        this.wordsCount = Arrays.stream(tokens)
                .map(this::cleanUp)
                .filter(token -> patternDelimitersWords.matcher(token).find())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    private long getCountSentences() {
        return sentences.size();
    }

    private long getCountOfUniqWords() {
        return wordsCount.values()
                .stream()
                .filter(value -> value == 1)
                .count();
    }

    private long getCountOfUsedWords() {
        return wordsCount.values()
                .size();
    }

    private long getCountOfAllWords() {
        return words.size();
    }

    private String cleanUp(String word) {
        String regex = "^[!.,:;\\-?<>#*\'\"\\[\\(\\]\\)\\n\\t\\\\]+|[!.,:;\\-?<>#\\*\'\"\\[\\(\\]\\)\\n\\t\\\\]+$";
        return word.toLowerCase()
                .replaceAll( regex, "");
    }
}
