package bg.sofia.uni.fmi.mjt.authroship.detection.models.common;

import java.util.function.Consumer;
import java.util.function.IntPredicate;
import java.util.function.LongPredicate;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class GlobalConstants {
    public static final String UTF8 = "utf8";
    public static final int BUFFER_SIZE = 8192;

    public static final int APPEAR_ONE = 1;
    public static final int COUNT_FEATURES = 5;
    public static final int AVERAGE_WORK_COMPLEXITY_INDEX = 0;
    public static final int TYPE_TOKEN_RADIO_INDEX = 1;
    public static final int HAPAX_LEGOMENA_RATIO_INDEX = 2;
    public static final int AVERAGE_SENTENCE_LENGTH_INDEX = 3;
    public static final int AVERAGE_SENTENCE_COMPLEXITY_INDEX = 4;

    public static final String FILE_NAME_KNOWN_SIGNATURES = "D:\\JavaProjects\\ModernJavaTechnology\\hws\\AuthorshipDetection\\resources\\signatures\\knownSignatures.txt";
    public static final String REGEX_ALL_SYMBOLS_WITHOUT_LETTERS_AND_SPACE = "[^a-zA-Z ]";
    public static final String REGEX_DOUBLE_NUMBERS = "-?\\d+(\\.\\d+)?";
    public static final String EMPTY_STRING = "";

    public static final String DELIMITERS_SENTENCES_REGEX = "[\\.!\\?]";
    public static final String DELIMITERS_PHRASE_REGEX = "[,:;]";
    public static final String DELIMITER_WORDS_REGEX = "\\s+";
    public static final String PUNCTUATION_REGEX = "\\p{Punct}";

    public static final Pattern PATTERN_DELIMITERS_SENTENCES = Pattern.compile(DELIMITERS_SENTENCES_REGEX);
    public static final Pattern PATTERN_DELIMITERS_PHRASE = Pattern.compile(DELIMITERS_PHRASE_REGEX);

    public static final Predicate<String> REMOVE_EMPTY_WORDS_PREDICATE = str -> !str.isEmpty() && !str.isBlank();
    public static final LongPredicate APPEAR_ONE_PREDICATE = value -> value == APPEAR_ONE;
}
