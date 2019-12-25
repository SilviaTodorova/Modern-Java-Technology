package bg.sofia.uni.fmi.mjt.authroship.detection;

import bg.sofia.uni.fmi.mjt.authroship.detection.enums.FeatureType;
import bg.sofia.uni.fmi.mjt.authroship.detection.utils.MysteryTextStreamInitializer;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class AuthorshipDetectorTest {
    private static AuthorshipDetector authorshipDetector;
    private static InputStream signaturesDataset;
    private static double[] weights;
    private static LinguisticSignature firstSignature;
    private static LinguisticSignature secondSignature;
    private static Map<FeatureType, Double> firstFeatures;
    private static Map<FeatureType, Double> secondFeatures;

    @BeforeClass
    public static void setUp() throws IOException {
        // TODO
        signaturesDataset = MysteryTextStreamInitializer.initMysteryTextStream("");
        weights = new double[]{1, 2, 3, 4, 5};

        // TODO
        firstFeatures = new HashMap<>();
        secondFeatures = new HashMap<>();

        // TODO
        firstSignature = new LinguisticSignature(firstFeatures);
        secondSignature = new LinguisticSignature(secondFeatures);

        authorshipDetector = new AuthorshipDetectorImpl(signaturesDataset, weights);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateInstanceOfAuthorshipDetectorImplWithInvalidArgumentsThrowsIllegalArgumentException() {
        AuthorshipDetector detector = new AuthorshipDetectorImpl(null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateInstanceOfLinguisticSignatureWithInvalidArgumentsThrowsIllegalArgumentException() {
        LinguisticSignature signature = new LinguisticSignature(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCalculateSimilarityWithInvalidArgumentsThrowsIllegalArgumentException() {
        authorshipDetector.calculateSimilarity(null, null);
    }

    @Test
    public void testCalculateSimilarity() {
        // TODO
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindAuthorWithInvalidArgumentThrowsIllegalArgumentException() {
        authorshipDetector.findAuthor(null);
    }

    @Test
    public void testFindAuthor() {
        // TODO
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCalculateSignatureWithInvalidArgumentThrowsIllegalArgumentException() {
        authorshipDetector.calculateSignature(null);
    }

    @Test
    public void testCalculateSignature() {
        // TODO
    }

}
