package bg.sofia.uni.fmi.mjt.authroship.detection.models;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static bg.sofia.uni.fmi.mjt.authroship.detection.models.common.GlobalConstants.*;
import static bg.sofia.uni.fmi.mjt.authroship.detection.models.common.GlobalFunctions.cleanUp;

public class SentenceAnalyzer {
    private final Set<String> sentences;

    public SentenceAnalyzer(InputStream mysteryText) {
        sentences = new HashSet<>();

        try(BufferedReader br = new BufferedReader(new InputStreamReader(mysteryText, UTF8), BUFFER_SIZE)) {
            String chunk;

            while ((chunk = br.readLine()) != null) {
                String[] arr = cleanUp(chunk).split("!?.");
                Arrays.stream(arr).filter(x->!x.equals(EMPTY_STRING)).forEach(sent->sentences.add(sent));
            }

            int x =0;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public double averageCountWords() {
        return sentences.stream().mapToLong(sent->countWordsInSentence(sent)).average().getAsDouble();
    }

    private long countWordsInSentence(String sentence) {
        InputStream stream = new ByteArrayInputStream(sentence.getBytes(StandardCharsets.UTF_8));
        WordAnalyzer analyzer = new WordAnalyzer(stream);
        return analyzer.getCountOfAllWords();
    }
}
