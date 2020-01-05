package bg.sofia.uni.fmi.mjt.authroship.detection.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;

public class TextStreamInitializer {
    private static final String SIG_CHRISTIE = "Agatha Christie, 4.52, 0.75, 0.61, 36.5, 3.5";
    private static final String SIG_DUMAS = "Alexandre Dumas, 5.0, 0.6666, 0.3, 15.23, 2.33";
    private static final String SIG_GRIM = "Brothers Grim, 4.26, 0.88, 0.76, 42.0, 3.0";
    private static final String SIG_DICKENS = "Charles Dickens, 10.23, 10.0, 2.0222, 16.0, 12.0";
    private static final String SIG_ADAMS = "Douglas Adams, 4.0, 0.87, 0.78, 8.25, 1.75";

    private static final String[] SIGNATURES = {SIG_CHRISTIE, SIG_DUMAS, SIG_GRIM, SIG_DICKENS, SIG_ADAMS};

    private static final String MYSTERY_1 = "The evil of the actual disparity in " +
            "their ages (and Mr. Woodhouse had\n" +
            "not married early) was much increased by his constitution and habits;\n" +
            "for having been a valetudinarian all his life, without activity of\n" +
            "mind or body, he was a much older man in ways than in years; and though\n" +
            "everywhere beloved for the friendliness of his heart and his amiable\n" +
            "temper, his talents could not have recommended him at any time.";

    private static final String MYSTERY_2 = "Here something began squeaking on " +
            "the table behind Alice, and made her\n" +
            "turn her head just in time to see one of the White Pawns roll over and\n" +
            "begin kicking: she watched it with great curiosity to see what would\n" +
            "happen next.";

    private static final String MYSTERY_3 = "\"Hold your noise!\" cried a terrible voice, " +
            "as a man started up from\n" +
            "among the graves at the side of the church porch. \"Keep still, you\n" +
            "little devil, or I'll cut your throat!\"";

    public static InputStream initKnownSignaturesStream() {
        return new ByteArrayInputStream(
                Arrays.stream(SIGNATURES)
                        .collect(Collectors.joining(System.lineSeparator()))
                        .getBytes());

    }

    public static InputStream initFirstMysteryStream() {
        return new ByteArrayInputStream(MYSTERY_1.getBytes(UTF_8));
    }

    public static InputStream initSecondMysteryStream() {
        return new ByteArrayInputStream(MYSTERY_2.getBytes(UTF_8));
    }

    public static InputStream initThirdMysteryStream() {
        return new ByteArrayInputStream(MYSTERY_3.getBytes(UTF_8));
    }
}
