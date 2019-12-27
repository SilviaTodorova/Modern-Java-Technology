package bg.sofia.uni.fmi.mjt.authroship.detection;

import bg.sofia.uni.fmi.mjt.authroship.detection.models.AuthorshipDetectorImpl;
import bg.sofia.uni.fmi.mjt.authroship.detection.models.LinguisticSignature;
import bg.sofia.uni.fmi.mjt.authroship.detection.models.common.enums.FeatureType;
import bg.sofia.uni.fmi.mjt.authroship.detection.models.contracts.AuthorshipDetector;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.EnumMap;
import java.util.Map;

import static bg.sofia.uni.fmi.mjt.authroship.detection.models.common.GlobalConstants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class AuthorshipDetectorTest {
    public static final String AUTHOR_OF_MYSTERY = "Douglas Adams";

    private static final double[] WEIGHTS = new double[]{11, 33, 50, 0.4, 4};
    private static final double[] NEGATIVE_WEIGHTS = new double[]{-11, 33, 50, 0.4, 4};
    private static final double[] MORE_WEIGHTS = new double[]{00, 11, 33, 50, 0.4, 4};

    private static final double WEIGHT_1 = 4.41553119311;
    private static final double WEIGHT_2 = 0.0563451817574;
    private static final double WEIGHT_3 = 0.02229943808;
    private static final double WEIGHT_4 = 16.8869087498;
    private static final double WEIGHT_5 = 2.54817097682;

    private static final double WEIGHT_6 = 4.40212537354;
    private static final double WEIGHT_7 = 0.103719383127;
    private static final double WEIGHT_8 = 0.0534892315963;
    private static final double WEIGHT_9 = 10.0836888743;
    private static final double WEIGHT_10 = 1.90662947161;

    public static double[] signatureJnAusten;
    public static double[] signatureAgChristie;

    private static InputStream signaturesDataset;
    private static AuthorshipDetector authorshipDetector;

    private static LinguisticSignature janeAustenSignature;
    private static LinguisticSignature agathaChristieSignature;

    public static String fileNameKnownSignatures;
    public static String fileNameWithAbsolutePathMystery1;

    @BeforeClass
    public static void setUp() throws FileNotFoundException {
        String pathToProject = Paths.get(EMPTY_STRING).toAbsolutePath().toString();
        fileNameKnownSignatures = Paths.get(
                pathToProject,
                FOLDER_RESOURCES,
                FOLDER_KNOWN_SIGNATURES,
                FILE_KNOWN_SIGNATURES
        ).toString();

        fileNameWithAbsolutePathMystery1 = Paths.get(pathToProject,
                FOLDER_RESOURCES,
                FOLDER_MYSTERY_FILES,
                FILE_NAME_MYSTERY
        ).toString();

        signatureJnAusten = new double[]{WEIGHT_1, WEIGHT_2, WEIGHT_3, WEIGHT_4, WEIGHT_5};
        signatureAgChristie = new double[]{WEIGHT_6, WEIGHT_7, WEIGHT_8, WEIGHT_9, WEIGHT_10};

        janeAustenSignature = createSignature(signatureJnAusten);
        agathaChristieSignature = createSignature(signatureAgChristie);

        signaturesDataset = new FileInputStream(new File(fileNameKnownSignatures));
        authorshipDetector = new AuthorshipDetectorImpl(signaturesDataset, WEIGHTS);
    }

    private static LinguisticSignature createSignature(double[] signatures) {
        Map<FeatureType, Double> features = new EnumMap<>(FeatureType.class);

        FeatureType[] values = FeatureType.values();
        for (int index = 0; index < COUNT_FEATURES; index++) {
            features.put(values[index], signatures[index]);
        }

        return new LinguisticSignature(features);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateInstanceOfAuthorshipDetectorImplWithInvalidArgumentsThrowsIllegalArgumentException() {
        AuthorshipDetector detector = new AuthorshipDetectorImpl(null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateInstanceOfAuthorshipDetectorImplWithNegativeWeightsThrowsIllegalArgumentException() {
        AuthorshipDetector detector = new AuthorshipDetectorImpl(signaturesDataset, NEGATIVE_WEIGHTS);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateInstanceOfAuthorshipDetectorImplWithMoreFeathersThrowsIllegalArgumentException() {
        AuthorshipDetector detector = new AuthorshipDetectorImpl(signaturesDataset, MORE_WEIGHTS);
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
    public void testCalculateSimilarityWithTwoIdenticalSignatures() throws IOException {
        double value = authorshipDetector.calculateSimilarity(janeAustenSignature, janeAustenSignature);
        assertEquals("Similarity between two identical signatures must be 0", 0, value, DELTA);
    }

    @Test
    public void testCalculateSimilarityWithTwoDifferentSignatures() throws IOException {
        double value = authorshipDetector.calculateSimilarity(janeAustenSignature, agathaChristieSignature);
        assertNotEquals("Similarity between two different signatures can't be 0", 0, value, DELTA);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindAuthorWithInvalidArgumentThrowsIllegalArgumentException() throws IOException {
        authorshipDetector.findAuthor(null);
    }

    @Test
    public void testFindAuthor() throws IOException {
        InputStream stream = new FileInputStream(fileNameWithAbsolutePathMystery1);
        String author = authorshipDetector.findAuthor(stream);
        String message = String.format("Author of file: %s must be %s", FILE_NAME_MYSTERY, AUTHOR_OF_MYSTERY);
        assertEquals(message, AUTHOR_OF_MYSTERY, author);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCalculateSignatureWithInvalidArgumentThrowsIllegalArgumentException() throws IOException {
        authorshipDetector.calculateSignature(null);
    }
}
