package bg.sofia.uni.fmi.mjt.authroship.detection;

import bg.sofia.uni.fmi.mjt.authroship.detection.enums.FeatureType;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static bg.sofia.uni.fmi.mjt.authroship.detection.enums.FeatureType.*;
import static bg.sofia.uni.fmi.mjt.authroship.detection.utils.GlobalFunctions.cleanUp;
import static bg.sofia.uni.fmi.mjt.authroship.detection.utils.Validator.*;
import static bg.sofia.uni.fmi.mjt.authroship.detection.utils.GlobalConstants.*;

public class AuthorshipDetectorImpl implements AuthorshipDetector {
    private InputStream signaturesDataset;
    private double[] weights;

    public AuthorshipDetectorImpl(InputStream signaturesDataset, double[] weights) {
        setSignaturesDataset(signaturesDataset);
        setWeights(weights);
    }

    public InputStream getSignaturesDataset() {
        return signaturesDataset;
    }

    private void setSignaturesDataset(InputStream signaturesDataset) {
        checkNotNull(signaturesDataset);
        this.signaturesDataset = signaturesDataset;
    }

    public double[] getWeights() {
        return weights.clone();
    }

    private void setWeights(double[] weights) {
        checkNotNull(weights);
        checkLengthDoubleArray(weights, COUNT_FEATURES);
        this.weights = weights;
    }

    @Override
    public LinguisticSignature calculateSignature(InputStream mysteryText) throws IOException {
        checkNotNull(mysteryText);

        Map<FeatureType, Double> features = new EnumMap<>(FeatureType.class);
        features.put(AVERAGE_WORD_LENGTH, calculateAverageWordComplexityFeature(mysteryText));
        features.put(TYPE_TOKEN_RATIO, calculateTypeTokenRatioWeight(mysteryText));
        features.put(HAPAX_LEGOMENA_RATIO, calculateHapaxLegomenaRatioWeight(mysteryText));
        features.put(AVERAGE_SENTENCE_LENGTH, calculateAverageSentenceLengthWeight(mysteryText));
        features.put(AVERAGE_SENTENCE_COMPLEXITY, calculateAverageSentenceComplexityWeight(mysteryText));

        return new LinguisticSignature(features);
    }

    @Override
    public double calculateSimilarity(LinguisticSignature firstSignature, LinguisticSignature secondSignature) {
        checkNotNull(firstSignature);
        checkNotNull(secondSignature);

        return calculateSumSimilarity(firstSignature, secondSignature, weights);
    }

    @Override
    public String findAuthor(InputStream mysteryText) throws IOException {
        checkNotNull(mysteryText);

        Map<String, LinguisticSignature> knownSignatures = getKnownSignatures();
        LinguisticSignature signature = calculateSignature(mysteryText);

        double min = Double.MIN_NORMAL;
        String minAuthorName = EMPTY_STRING;

        for (var curr : knownSignatures.entrySet()) {
            double value = calculateSimilarity(curr.getValue(), signature);
            if (min >= value) {
                minAuthorName = curr.getKey();
                min = value;
            }
        }
        return minAuthorName;
    }

    // Средна дължина на думите - средният брой символи в дума, след strip-ване на пунктуацията.
    private double calculateAverageWordComplexityFeature(InputStream mysteryText) throws IOException {
        WordAnalyzer counter = new WordAnalyzer();
        BufferedReader br = new BufferedReader(new InputStreamReader(mysteryText, UTF8), BUFFER_SIZE);
        String chunk;

        while ((chunk = br.readLine()) != null) {
            String[] words = cleanUp(chunk).replaceAll("\\d", " ").split("\\W+");
            Arrays.stream(words).filter(x->!x.equals(EMPTY_STRING)).forEach(w -> counter.add(w));
        }

        return counter.getAverageWordLength();
    }

    // броят на всички различни думи, използвани в текста, разделен на броя на всички думи.
    // Измерва колко повтаряща се е лексиката.
    private double calculateTypeTokenRatioWeight(InputStream mysteryText) throws IOException {
        WordAnalyzer counter = new WordAnalyzer();
        BufferedReader br = new BufferedReader(new InputStreamReader(mysteryText, UTF8), BUFFER_SIZE);
        String chunk;

        while ((chunk = br.readLine()) != null) {
            String[] words = cleanUp(chunk).replaceAll("\\d", " ").split("\\W+");
            Arrays.stream(words).filter(x->!x.equals(EMPTY_STRING)).forEach(w -> counter.add(w));
        }

        long countOfUniqueWords = counter.getCountOfWords();
        long countOfAllWords = counter.getCountOfAllWords();
        return (double) countOfUniqueWords / countOfAllWords;
    }

    // Hapax Legomena Ratio - броят на думите, срещащи се само по веднъж в даден текст,
    // разделен на броя на всички думи.
    private double calculateHapaxLegomenaRatioWeight(InputStream mysteryText) throws IOException {
        WordAnalyzer counter = new WordAnalyzer();
        BufferedReader br = new BufferedReader(new InputStreamReader(mysteryText, UTF8), BUFFER_SIZE);
        String chunk;

        while ((chunk = br.readLine()) != null) {
            String[] words = cleanUp(chunk).replaceAll("\\d", " ").split("\\W+");
            Arrays.stream(words).filter(x->!x.equals(EMPTY_STRING)).forEach(w -> counter.add(w));
        }


        long countOfWordAppearOnes = counter.getCountOfUniqueWords();
        long countOfAllWords = counter.getCountOfAllWords();

        return (double) countOfWordAppearOnes / countOfAllWords;
    }

    private double calculateAverageSentenceLengthWeight(InputStream mysteryText) throws IOException {
        // TODO:
        return weights[AVERAGE_SENTENCE_LENGTH_INDEX];
    }

    private static double calculateSumSimilarity(LinguisticSignature firstMystery, LinguisticSignature secondMystery, double[] weights) {
        Map<FeatureType, Double> firstMysteryFeatures = firstMystery.getFeatures();
        Map<FeatureType, Double> secondMysteryFeatures = secondMystery.getFeatures();

        FeatureType[] features = FeatureType.values();
        double result = 0;
        for (int index = 0; index < COUNT_FEATURES; index++) {
            FeatureType feature = features[index];

            double firstMysteryFeatureValue = firstMysteryFeatures.get(feature);
            double secondMysteryFeatureValue = secondMysteryFeatures.get(feature);
            double absValue = Math.abs(firstMysteryFeatureValue - secondMysteryFeatureValue);

            result += absValue * weights[index];
        }

        return result;
    }

    private double calculateAverageSentenceComplexityWeight(InputStream mysteryText) throws IOException {
        // TODO:
        return weights[AVERAGE_SENTENCE_COMPLEXITY_INDEX];
    }

    private Map<String, LinguisticSignature> getKnownSignatures() {
        Map<String, LinguisticSignature> knownSignatures = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(signaturesDataset, "UTF-8"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                addSignature(knownSignatures, line);
            }

            return knownSignatures;
        } catch (IOException ioExc) {
            throw new RuntimeException("Error occurred while checking style", ioExc);
        }
    }

    private static void addSignature(Map<String, LinguisticSignature> knownSignatures, String line) {
        String key = line.replaceAll(REGEX_ALL_SYMBOLS_WITHOUT_LETTERS_AND_SPACE, EMPTY_STRING).strip();
        Pattern pattern = Pattern.compile(REGEX_DOUBLE_NUMBERS);
        Matcher matcher = pattern.matcher(line);

        Map<FeatureType, Double> features = new HashMap<>();
        FeatureType[] values = FeatureType.values();
        int index = 0;
        while (matcher.find()) {
            double value = Double.valueOf(matcher.group());
            FeatureType feature = values[index];

            features.put(feature, value);
            index++;
        }

        LinguisticSignature signature = new LinguisticSignature(features);
        knownSignatures.put(key, signature);
    }
}
