package bg.sofia.uni.fmi.mjt.authroship.detection.models;

import bg.sofia.uni.fmi.mjt.authroship.detection.models.common.enums.FeatureType;
import bg.sofia.uni.fmi.mjt.authroship.detection.models.contracts.AuthorshipDetector;
import bg.sofia.uni.fmi.mjt.authroship.detection.models.contracts.TextAnalyzer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static bg.sofia.uni.fmi.mjt.authroship.detection.models.common.enums.FeatureType.*;
import static bg.sofia.uni.fmi.mjt.authroship.detection.models.common.Validator.*;
import static java.nio.charset.StandardCharsets.UTF_8;

public class AuthorshipDetectorImpl implements AuthorshipDetector {
    private static final int COUNT_FEATURES = 5;

    private static final String REGEX_ALL_SYMBOLS_WITHOUT_LETTERS_AND_SPACE = "[^a-zA-Z ]";
    private static final String REGEX_DOUBLE_NUMBERS = "-?\\d+(\\.\\d+)?";

    private static final String EMPTY_STRING = "";

    private double[] weights;
    private Map<String, LinguisticSignature> knownSignatures;

    public AuthorshipDetectorImpl(InputStream signaturesDataset, double[] weights) {
        setSignaturesDataset(signaturesDataset);
        setWeights(weights);
    }

    private void setSignaturesDataset(InputStream signaturesDataset) {
        checkNotNull(signaturesDataset);
        knownSignatures = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(signaturesDataset, UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                addSignature(line);
            }
        } catch (IOException ex) {
            throw new RuntimeException("Error occurred while reading known signatures", ex);
        }

    }

    private void setWeights(double[] weights) {
        checkNotNull(weights);
        checkLengthDoubleArray(weights, COUNT_FEATURES);
        checkNotNegativeDoubleValuesInArray(weights);
        this.weights = weights;
    }

    @Override
    public LinguisticSignature calculateSignature(InputStream mysteryText) {
        checkNotNull(mysteryText);

        Map<FeatureType, Double> features = new EnumMap<>(FeatureType.class);

        TextAnalyzer analyzer = new TextAnalyzerImpl(mysteryText);

        // 1. Average Word Complexity
        double averageWordLength = analyzer.getAverageWordLength();
        features.put(AVERAGE_WORD_LENGTH, averageWordLength);

        // 2. Type Token Ratio
        double typeTokeRatio = analyzer.getTypeTokeRatio();
        features.put(TYPE_TOKEN_RATIO, typeTokeRatio);

        // 3. Hapax Legomena Ratio
        double hapaxLegomenaRatio = analyzer.getHapaxLegomenaRatio();
        features.put(HAPAX_LEGOMENA_RATIO, hapaxLegomenaRatio);

        //4. Average Sentence Length
        double averageSentenceLength = analyzer.getAverageSentenceLength();
        features.put(AVERAGE_SENTENCE_LENGTH, averageSentenceLength);

        // 5. Average Sentence Complexity
        double averageSentenceComplexity = analyzer.getAverageSentenceComplexity();
        features.put(AVERAGE_SENTENCE_COMPLEXITY, averageSentenceComplexity);

        return new LinguisticSignature(features);
    }

    @Override
    public double calculateSimilarity(LinguisticSignature first, LinguisticSignature second) {
        checkNotNull(first);
        checkNotNull(second);

        Map<FeatureType, Double> firstMysteryFeatures = first.getFeatures();
        Map<FeatureType, Double> secondMysteryFeatures = second.getFeatures();

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

    @Override
    public String findAuthor(InputStream mysteryText) {
        checkNotNull(mysteryText);

        LinguisticSignature signature = calculateSignature(mysteryText);

        double closestVal = Double.MAX_VALUE;
        String closestAuthorName = EMPTY_STRING;

        for (var curr : knownSignatures.entrySet()) {
            LinguisticSignature currSignature = curr.getValue();
            double value = calculateSimilarity(currSignature, signature);

            double valuePowTwo = value * value;
            if (valuePowTwo <= (closestVal * closestVal)) {
                closestAuthorName = curr.getKey();
                closestVal = value;
            }
        }
        return closestAuthorName;
    }

    private void addSignature(String line) {
        String key = line.replaceAll(REGEX_ALL_SYMBOLS_WITHOUT_LETTERS_AND_SPACE, EMPTY_STRING).strip();
        Pattern pattern = Pattern.compile(REGEX_DOUBLE_NUMBERS);
        Matcher matcher = pattern.matcher(line);

        Map<FeatureType, Double> features = new HashMap<>();
        FeatureType[] values = FeatureType.values();
        int index = 0;
        while (matcher.find()) {
            double value = Double.parseDouble(matcher.group());
            FeatureType feature = values[index];

            features.put(feature, value);
            index++;
        }

        LinguisticSignature signature = new LinguisticSignature(features);
        knownSignatures.put(key, signature);
    }
}
