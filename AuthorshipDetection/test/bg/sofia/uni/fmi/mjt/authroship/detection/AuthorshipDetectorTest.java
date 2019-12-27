package bg.sofia.uni.fmi.mjt.authroship.detection;

import bg.sofia.uni.fmi.mjt.authroship.detection.models.AuthorshipDetectorImpl;
import bg.sofia.uni.fmi.mjt.authroship.detection.models.LinguisticSignature;
import bg.sofia.uni.fmi.mjt.authroship.detection.models.common.enums.FeatureType;
import bg.sofia.uni.fmi.mjt.authroship.detection.models.contracts.AuthorshipDetector;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.*;
import java.nio.file.Paths;
import java.util.*;

import static bg.sofia.uni.fmi.mjt.authroship.detection.models.common.GlobalConstants.*;
import static org.junit.Assert.assertEquals;

public class AuthorshipDetectorTest {
    public static final String AUTHOR_OF_MYSTERY_1 = "Jane Austen";

    public static final double[] SIGNATURE_OF_JANE_AUSTEN = new double[]{4.41553119311, 0.0563451817574, 0.02229943808, 16.8869087498, 2.54817097682};
    public static final double[] SIGNATURE_OF_AGATHA_CHRISTIE = new double[]{4.40212537354, 0.103719383127, 0.0534892315963, 10.0836888743, 1.90662947161};

    private static final double[] WEIGHTS = new double[]{11, 33, 50, 0.4, 4};
    private static final double[] NEGATIVE_WEIGHTS = new double[]{-11, 33, 50, 0.4, 4};
    private static final double[] MORE_WEIGHTS = new double[]{00, 11, 33, 50, 0.4, 4};

    private static AuthorshipDetector authorshipDetector;

    private static LinguisticSignature janeAustenSignature;
    private static LinguisticSignature agathaChristieSignature;

    public static String fileNameKnownSignatures;
    public static String fileNameWithAbsolutePathMystery1;

    @BeforeClass
    public static void setUp() throws FileNotFoundException {
        String pathToProject = Paths.get(EMPTY_STRING).toAbsolutePath().toString();
        fileNameKnownSignatures = Paths.get(pathToProject, FOLDER_RESOURCES, FOLDER_KNOWN_SIGNATURES, FILE_KNOWN_SIGNATURES).toString();
        fileNameWithAbsolutePathMystery1 = Paths.get(pathToProject, FOLDER_RESOURCES, FOLDER_MYSTERY_FILES, FILE_NAME_MYSTERY_1).toString();

        janeAustenSignature = createSignature(SIGNATURE_OF_JANE_AUSTEN);
        agathaChristieSignature = createSignature(SIGNATURE_OF_AGATHA_CHRISTIE);

        authorshipDetector = new AuthorshipDetectorImpl(new FileInputStream(new File(fileNameKnownSignatures)), WEIGHTS);
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
    public void testCreateInstanceOfAuthorshipDetectorImplWithNegativeWeightsThrowsIllegalArgumentException() throws FileNotFoundException {
        InputStream signaturesDataset = new FileInputStream(new File(fileNameKnownSignatures));
        AuthorshipDetector detector = new AuthorshipDetectorImpl(signaturesDataset, NEGATIVE_WEIGHTS);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateInstanceOfAuthorshipDetectorImplWithMoreFeathersThrowsIllegalArgumentException() throws FileNotFoundException {
        InputStream signaturesDataset = new FileInputStream(new File(fileNameKnownSignatures));
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
        assertEquals("Similarity between two identical signatures must be 0", 0, value, 0.1);
    }

    @Test
    public void testCalculateSimilarityWithTwoDifferentSignatures() throws IOException {
        double value = authorshipDetector.calculateSimilarity(janeAustenSignature, agathaChristieSignature);
        assertEquals("Similarity between two different signatures can't be 0", 0, value, 0.1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindAuthorWithInvalidArgumentThrowsIllegalArgumentException() throws IOException {
        authorshipDetector.findAuthor(null);
    }

    @Test
    public void testFindAuthor() throws IOException {
        InputStream stream = new FileInputStream(fileNameWithAbsolutePathMystery1);
        String author = authorshipDetector.findAuthor(stream);
        assertEquals(String.format("Author of file: %s must be %s", FILE_NAME_MYSTERY_1, AUTHOR_OF_MYSTERY_1), author, AUTHOR_OF_MYSTERY_1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCalculateSignatureWithInvalidArgumentThrowsIllegalArgumentException() throws IOException {
        authorshipDetector.calculateSignature(null);
    }

    @Test
    public void testCalculateSignature() throws IOException {
        InputStream stream = new FileInputStream(fileNameWithAbsolutePathMystery1);
        LinguisticSignature janeAustenSignature = authorshipDetector.calculateSignature(stream);
        assertEquals(String.format("Signature of file: %s must be the same as %s's signature", FILE_NAME_MYSTERY_1, AUTHOR_OF_MYSTERY_1), janeAustenSignature, this.janeAustenSignature);
    }
}
