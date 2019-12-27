package bg.sofia.uni.fmi.mjt.authroship.detection.models.common;

import java.util.function.LongPredicate;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class GlobalConstants {
    public static final int BUFFER_SIZE = 8192;
    public static final double DELTA = 0.01;

    public static final int APPEAR_ONE = 1;
    public static final int COUNT_FEATURES = 5;

    public static final String REGEX_ALL_SYMBOLS_WITHOUT_LETTERS_AND_SPACE = "[^a-zA-Z ]";
    public static final String REGEX_DOUBLE_NUMBERS = "-?\\d+(\\.\\d+)?";

    public static final String EMPTY_STRING = "";

    public static final String REGEX_DELIMITERS_SENTENCES = "[.!?]";
    public static final String REGEX_DELIMITERS_PHRASE = "[,:;]";
    public static final String REGEX_DELIMITER_WORDS = "\\s+";
    public static final String REGEX_PUNCTUATION = "\\p{Punct}";

    public static final Pattern PATTERN_DELIMITERS_SENTENCES = Pattern.compile(REGEX_DELIMITERS_SENTENCES);

    public static final Predicate<String> PREDICATE_REMOVE_EMPTY_WORDS = str -> !str.isEmpty() && !str.isBlank();
    public static final LongPredicate LONG_PREDICATE_APPEAR_ONE_PREDICATE = value -> value == APPEAR_ONE;

    public static final String FOLDER_RESOURCES = "resources";
    public static final String FOLDER_KNOWN_SIGNATURES = "signatures";
    public static final String FOLDER_MYSTERY_FILES = "mysteryFiles";

    public static final String FILE_KNOWN_SIGNATURES = "knownSignatures.txt";
    public static final String FILE_NAME_MYSTERY_1 = "mystery1.txt";

    public static final String ERROR_EXTRACT_DATA = "Error while extracting data from InputStream";
    public static final String ERROR_NULL_VALUE = "Null value";
    public static final String ERROR_NEGATIVE_VALUE = "Negative value";
    public static final String ERROR_COUNT_VALUES_IN_ARRAY = "Array must contains %d elements not %d";
}
