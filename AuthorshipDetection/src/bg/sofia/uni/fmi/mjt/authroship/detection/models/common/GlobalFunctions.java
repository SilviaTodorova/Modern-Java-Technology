package bg.sofia.uni.fmi.mjt.authroship.detection.utils;

import bg.sofia.uni.fmi.mjt.authroship.detection.LinguisticSignature;
import bg.sofia.uni.fmi.mjt.authroship.detection.enums.FeatureType;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Stream;

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

//    public static void readFromFileWithBufferedInputStream(InputStream is) throws IOException {
//        String fileName = "D:\\JavaProjects\\ModernJavaTechnology\\hws\\AuthorshipDetection\\resources\\mysteryFiles\\mystery1.txt"; //this path is on my local
//        // lines(Path path, Charset cs)
//
//
//        try (Stream inputStream = Files.lines(Paths.get(fileName), StandardCharsets.UTF_8)) {
//            inputStream.forEach(line -> {
//                int x =0;
//                System.out.println(line);
//            });
//        }
//    }

}
