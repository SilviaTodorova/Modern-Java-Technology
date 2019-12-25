package bg.sofia.uni.fmi.mjt.authroship.detection;

import bg.sofia.uni.fmi.mjt.authroship.detection.enums.FeatureType;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static bg.sofia.uni.fmi.mjt.authroship.detection.enums.FeatureType.*;
import static bg.sofia.uni.fmi.mjt.authroship.detection.utils.Validator.*;
import static bg.sofia.uni.fmi.mjt.authroship.detection.utils.GlobalConstants.*;

public class AuthorshipDetectorImpl implements AuthorshipDetector {
    private static final int INDEX_OF_AUTHOR = 0;

    private static Map<String, LinguisticSignature> knownSignatures;

    private InputStream signaturesDataset;
    private double[] weights;

    static {
        knownSignatures = new HashMap<>();

        String fileName = "D:\\JavaProjects\\ModernJavaTechnology\\hws\\AuthorshipDetection\\resources\\signatures\\knownSignatures.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // System.out.println(line);
                String key = line.replaceAll("[^a-zA-Z ]", "").strip();
                System.out.println(key);
                Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
                Matcher matcher = pattern.matcher(line);
                while(matcher.find()) {
                    System.out.println(Double.valueOf(matcher.group()));
                }

                int x = 0;
            }

        } catch (IOException ioExc) {
            throw new RuntimeException("Error occurred while checking style", ioExc);
        }

    }

    public AuthorshipDetectorImpl(InputStream signaturesDataset, double[] weights) {
        setSignaturesDataset(signaturesDataset);
        setWeights(weights);
    }

    @Override
    public LinguisticSignature calculateSignature(InputStream mysteryText) {
        checkNotNull(mysteryText);

        Map<FeatureType, Double> features = new HashMap<>(5);

        features.put(AVERAGE_WORD_LENGTH, calculateAverageWordComplexityFeature());
        features.put(TYPE_TOKEN_RATIO, calculateTypeTokenRatioWeight());
        features.put(HAPAX_LEGOMENA_RATIO, calculateHapaxLegomenaRatioWeight());
        features.put(AVERAGE_SENTENCE_LENGTH, calculateAverageSentenceLengthWeight());
        features.put(AVERAGE_SENTENCE_COMPLEXITY, calculateAverageSentenceComplexityWeight());

        return new LinguisticSignature(features);
    }

    // The smaller the number, the more similar the signatures.
    // Zero indicates identical signatures.
    @Override
    public double calculateSimilarity(LinguisticSignature firstSignature, LinguisticSignature secondSignature) {
        checkNotNull(firstSignature);
        checkNotNull(secondSignature);

        // TODO:
        return 0;
    }

    @Override
    public String findAuthor(InputStream mysteryText) {
        checkNotNull(mysteryText);
        // TODO:
        return null;
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

    private double calculateAverageWordComplexityFeature() {
        // TODO
        return weights[AVERAGE_WORK_COMPLEXITY_INDEX];
    }

    private double calculateTypeTokenRatioWeight() {
        // TODO:
        return weights[TYPE_TOKEN_RADIO_INDEX];
    }

    private double calculateHapaxLegomenaRatioWeight() {
        // TODO:
        return weights[HAPAX_LEGOMENA_RATIO_INDEX];
    }

    private double calculateAverageSentenceLengthWeight() {
        // TODO:
        return weights[AVERAGE_SENTENCE_LENGTH_INDEX];
    }

    private double calculateAverageSentenceComplexityWeight() {
        // TODO:
        return weights[AVERAGE_SENTENCE_COMPLEXITY_INDEX];
    }
}
