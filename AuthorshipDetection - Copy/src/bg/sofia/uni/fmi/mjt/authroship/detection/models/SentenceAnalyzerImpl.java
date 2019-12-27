package bg.sofia.uni.fmi.mjt.authroship.detection.models;

import bg.sofia.uni.fmi.mjt.authroship.detection.models.contracts.SentenceAnalyzer;

import java.io.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static bg.sofia.uni.fmi.mjt.authroship.detection.models.common.GlobalConstants.*;
import static bg.sofia.uni.fmi.mjt.authroship.detection.models.common.GlobalFunctions.cleanUp;
import static java.nio.charset.StandardCharsets.UTF_8;

public class SentenceAnalyzerImpl implements SentenceAnalyzer {
    private final Set<String> sentences;

    public SentenceAnalyzerImpl(InputStream mysteryText) {
        sentences = new HashSet<>();

        try(BufferedReader br = new BufferedReader(new InputStreamReader(mysteryText, UTF_8), BUFFER_SIZE)) {
            String chunk;

            while ((chunk = br.readLine()) != null) {
                String[] arr = cleanUp(chunk).split("!?.");
                Arrays.stream(arr).filter(x->!x.equals(EMPTY_STRING)).forEach(sentences::add);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getCountSentence() {
        return sentences.size();
    }

    public double getAverageCountWords() {
        return sentences.stream().mapToLong(this::countWordsInSentence).average().getAsDouble();
    }

    private long countWordsInSentence(String sentence) {
        InputStream stream = new ByteArrayInputStream(sentence.getBytes(UTF_8));
        WordAnalyzerImpl analyzer = new WordAnalyzerImpl(stream);
        return analyzer.getCountOfAllWords();
    }
}
