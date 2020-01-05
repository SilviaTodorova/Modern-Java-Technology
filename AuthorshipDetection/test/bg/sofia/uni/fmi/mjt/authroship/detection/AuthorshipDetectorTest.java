package bg.sofia.uni.fmi.mjt.authroship.detection;

import bg.sofia.uni.fmi.mjt.authroship.detection.models.AuthorshipDetectorImpl;
import bg.sofia.uni.fmi.mjt.authroship.detection.models.LinguisticSignature;
import bg.sofia.uni.fmi.mjt.authroship.detection.models.common.enums.FeatureType;
import bg.sofia.uni.fmi.mjt.authroship.detection.models.contracts.AuthorshipDetector;
import bg.sofia.uni.fmi.mjt.authroship.detection.util.TextStreamInitializer;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.EnumMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class AuthorshipDetectorTest {
    public static final double DELTA = 0.01;
    public static final int COUNT_FEATURES = 5;
    public static final int SIMILARITY_SIG_1_2 = 12;

    private static final double[] WEIGHTS = new double[]{11, 33, 50, 0.4, 4};
    private static final double[] NEGATIVE_WEIGHTS = new double[]{-11, 33, 50, 0.4, 4};
    private static final double[] INVALID_WEIGHTS = new double[]{00, 11, 33, 50, 0.4, 4};

    private static final double[] SIGNATURE_1 = new double[]{4.4, 0.1, 0.05, 10, 2};
    private static final double[] SIGNATURE_2 = new double[]{4.3, 0.1, 0.04, 16, 4};

    public static final String AUTHOR_OF_MYSTERY_1 = "Agatha Christie";
    public static final String AUTHOR_OF_MYSTERY_2 = "Brothers Grim";
    public static final String AUTHOR_OF_MYSTERY_3 = "Douglas Adams";

    private static AuthorshipDetector detector;
    private static LinguisticSignature linguisticSignature1;
    private static LinguisticSignature linguisticSignature2;

    @BeforeClass
    public static void setUp() {
        InputStream signaturesDataset = TextStreamInitializer.initKnownSignaturesStream();
        detector = new AuthorshipDetectorImpl(signaturesDataset, WEIGHTS);

        linguisticSignature1 = createSignature(SIGNATURE_1);
        linguisticSignature2 = createSignature(SIGNATURE_2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateInstanceOfAuthorshipDetectorImplWithInvalidArgumentsThrowsIllegalArgumentException() {
        AuthorshipDetector detector = new AuthorshipDetectorImpl(null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateInstanceOfAuthorshipDetectorImplWithNegativeWeightsThrowsIllegalArgumentException() {
        InputStream signaturesDataset = TextStreamInitializer.initKnownSignaturesStream();
        AuthorshipDetector invalidDetector = new AuthorshipDetectorImpl(signaturesDataset, NEGATIVE_WEIGHTS);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateInstanceOfAuthorshipDetectorImplWithMoreFeathersThrowsIllegalArgumentException() {
        InputStream signaturesDataset = TextStreamInitializer.initKnownSignaturesStream();
        AuthorshipDetector invalidDetector = new AuthorshipDetectorImpl(signaturesDataset, INVALID_WEIGHTS);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateInstanceOfLinguisticSignatureWithInvalidArgumentsThrowsIllegalArgumentException() {
        LinguisticSignature signature = new LinguisticSignature(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCalculateSimilarityWithInvalidArgumentsThrowsIllegalArgumentException() {
        detector.calculateSimilarity(null, null);
    }

    @Test
    public void testCalculateSimilarityWithTwoIdenticalSignatures() {
        double value = detector.calculateSimilarity(linguisticSignature1, linguisticSignature1);
        assertEquals("Similarity between two identical signatures must be 0", 0, value, DELTA);
    }

    @Test
    public void testCalculateSimilarity() {
        double value = detector.calculateSimilarity(linguisticSignature1, linguisticSignature2);
        String message = String.format("Similarity must be %d", SIMILARITY_SIG_1_2);
        assertEquals(message, SIMILARITY_SIG_1_2, value, DELTA);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindAuthorWithInvalidArgumentThrowsIllegalArgumentException() throws IOException {
        detector.findAuthor(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCalculateSignatureWithInvalidArgumentThrowsIllegalArgumentException() throws IOException {
        detector.calculateSignature(null);
    }

    private static LinguisticSignature createSignature(double[] signatures) {
        Map<FeatureType, Double> features = new EnumMap<>(FeatureType.class);

        FeatureType[] values = FeatureType.values();
        for (int index = 0; index < COUNT_FEATURES; index++) {
            features.put(values[index], signatures[index]);
        }

        return new LinguisticSignature(features);
    }

    @Test
    public void testFindAuthorOfMystery1() {
        InputStream stream = TextStreamInitializer.initFirstMysteryStream();
        String author = detector.findAuthor(stream);
        String message = String.format("Author of first mystery must be %s", AUTHOR_OF_MYSTERY_1);
        assertEquals(message, AUTHOR_OF_MYSTERY_1, author);
    }

    @Test
    public void testFindAuthorOfMystery2() {
        InputStream stream = TextStreamInitializer.initSecondMysteryStream();
        String author = detector.findAuthor(stream);
        String message = String.format("Author of second mystery must be %s", AUTHOR_OF_MYSTERY_2);
        assertEquals(message, AUTHOR_OF_MYSTERY_2, author);
    }

    @Test
    public void testFindAuthorOfMystery3() {
        InputStream stream = TextStreamInitializer.initThirdMysteryStream();
        String author = detector.findAuthor(stream);
        String message = String.format("Author of third mystery must be %s", AUTHOR_OF_MYSTERY_3);
        assertEquals(message, AUTHOR_OF_MYSTERY_3, author);
    }

    @Test
    public void testFindAuthorOfMystery1ReadFromFile() throws IOException {
        InputStream stream = new FileInputStream("D:\\JavaProjects\\ModernJavaTechnology\\hws\\AuthorshipDetection\\resources\\mysteryFiles\\mystery1.txt");
        InputStream signaturesDataset = new FileInputStream(new File("D:\\JavaProjects\\ModernJavaTechnology\\hws\\AuthorshipDetection\\resources\\signatures\\knownSignatures.txt"));
        AuthorshipDetector authorshipDetector = new AuthorshipDetectorImpl(signaturesDataset, WEIGHTS);
        String author = authorshipDetector.findAuthor(stream);
        assertEquals("Jane Austen", author);
    }
}
