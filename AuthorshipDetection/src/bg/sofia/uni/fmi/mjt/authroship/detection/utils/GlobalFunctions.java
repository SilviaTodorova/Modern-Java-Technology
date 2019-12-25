package bg.sofia.uni.fmi.mjt.authroship.detection.utils;

import bg.sofia.uni.fmi.mjt.authroship.detection.LinguisticSignature;
import bg.sofia.uni.fmi.mjt.authroship.detection.enums.FeatureType;

import java.util.Map;

import static bg.sofia.uni.fmi.mjt.authroship.detection.utils.GlobalConstants.COUNT_FEATURES;

public final class GlobalFunctions {

    private GlobalFunctions() {
    }

    public static String cleanUp(String word) {
        return word.toLowerCase()
                .replaceAll("^[!,:;\\-?<>#\\*\'\"\\[\\(\\]\\n\\t\\\\]+|[!,:;\\-?<>#\\*\'\"\\[\\(\\]\\n\\t\\\\]+$", "");
    }

    public static double calculateSumSimilarity(LinguisticSignature firstMystery, LinguisticSignature secondMystery, double[] weights) {
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
}
