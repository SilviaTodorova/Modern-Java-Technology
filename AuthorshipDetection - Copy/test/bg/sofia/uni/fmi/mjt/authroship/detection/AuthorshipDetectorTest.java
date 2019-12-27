package bg.sofia.uni.fmi.mjt.authroship.detection;

import bg.sofia.uni.fmi.mjt.authroship.detection.models.AuthorshipDetectorImpl;
import bg.sofia.uni.fmi.mjt.authroship.detection.models.LinguisticSignature;
import bg.sofia.uni.fmi.mjt.authroship.detection.models.common.enums.FeatureType;
import bg.sofia.uni.fmi.mjt.authroship.detection.models.contracts.AuthorshipDetector;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.EnumMap;
import java.util.Map;

import static bg.sofia.uni.fmi.mjt.authroship.detection.models.common.enums.FeatureType.*;
import static bg.sofia.uni.fmi.mjt.authroship.detection.models.common.GlobalConstants.*;
import static bg.sofia.uni.fmi.mjt.authroship.detection.models.common.GlobalConstants.AVERAGE_SENTENCE_COMPLEXITY_INDEX;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.assertEquals;

public class AuthorshipDetectorTest {
    public static final String RESOURCES_FOLDER = "resources";
    public static final String KNOWN_SIGNATURES_FOLDER = "signatures";
    public static final String MYSTERY_FILES_FOLDER = "mysteryFiles";
    public static final String AUTHOR_OF_MYSTERY_1 = "Jane Austen";
    public static final String FILE_KNOWN_SIGNATURES = "knownSignatures.txt";
    public static final String FILE_NAME_MYSTERY_1 = "mystery1.txt";

    public static final double[] SIGNATURE_OF_JANE_AUSTEN = new double[]{4.41553119311, 0.0563451817574, 0.02229943808, 16.8869087498, 2.54817097682};
    public static final double[] SIGNATURE_OF_AGATHA_CHRISTIE = new double[]{4.40212537354, 0.103719383127, 0.0534892315963, 10.0836888743, 1.90662947161};

    private static final double[] WEIGHTS = new double[]{11, 33, 50, 0.4, 4};

    private static AuthorshipDetector authorshipDetector;

    private static LinguisticSignature firstSignature;
    private static LinguisticSignature secondSignature;

    public static String fileNameWithAbsolutePathMystery1;

    @BeforeClass
    public static void setUp() throws FileNotFoundException {
        String pathToProject = Paths.get("").toAbsolutePath().toString();
        String fileNameKnownSignatures = Paths.get(pathToProject, RESOURCES_FOLDER, KNOWN_SIGNATURES_FOLDER, FILE_KNOWN_SIGNATURES).toString();
        fileNameWithAbsolutePathMystery1 = Paths.get(pathToProject, RESOURCES_FOLDER, MYSTERY_FILES_FOLDER, FILE_NAME_MYSTERY_1).toString();

        firstSignature = createSignature(SIGNATURE_OF_JANE_AUSTEN);
        secondSignature = createSignature(SIGNATURE_OF_AGATHA_CHRISTIE);

        InputStream signaturesDataset = new FileInputStream(new File(fileNameKnownSignatures));
        authorshipDetector = new AuthorshipDetectorImpl(signaturesDataset, WEIGHTS);
    }

    private static LinguisticSignature createSignature(double[] signatures){
        Map<FeatureType, Double> features = new EnumMap<>(FeatureType.class);

        FeatureType[] values = FeatureType.values();
        for (int index = 0; index < COUNT_FEATURES; index++) {
            features.put(values[index] ,signatures[index]);
        }

        return new LinguisticSignature(features);
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
    public void testCalculateSimilarityWithTwoIdenticalSignatures() throws IOException {
        double value = authorshipDetector.calculateSimilarity(firstSignature, firstSignature);
        assertEquals("Similarity between two identical signatures must be 0", 0, value, 0.1);
    }

    @Test
    public void testCalculateSimilarityWithTwoDifferentSignatures() throws IOException {
        double value = authorshipDetector.calculateSimilarity(firstSignature, secondSignature);
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
        try (BufferedReader reader = new BufferedReader(new FileReader(fileNameWithAbsolutePathMystery1))) {
            InputStream stream = new ByteArrayInputStream(reader.toString().getBytes(UTF_8));
            LinguisticSignature janeAustenSignature = authorshipDetector.calculateSignature(stream);
            assertEquals(String.format("Signature of file: %s must be the same as %s's signature", FILE_NAME_MYSTERY_1, AUTHOR_OF_MYSTERY_1), janeAustenSignature, firstSignature);
        }
    }
}
