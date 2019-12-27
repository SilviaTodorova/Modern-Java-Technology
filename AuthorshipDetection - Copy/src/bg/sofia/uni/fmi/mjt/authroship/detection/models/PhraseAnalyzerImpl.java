package bg.sofia.uni.fmi.mjt.authroship.detection.models;

import bg.sofia.uni.fmi.mjt.authroship.detection.models.contracts.PhraseAnalyzer;

import java.io.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static bg.sofia.uni.fmi.mjt.authroship.detection.models.common.GlobalConstants.*;
import static bg.sofia.uni.fmi.mjt.authroship.detection.models.common.GlobalFunctions.cleanUp;
import static java.nio.charset.StandardCharsets.UTF_8;

public class PhraseAnalyzerImpl implements PhraseAnalyzer {
    private final Set<String> phrases;

    public PhraseAnalyzerImpl(InputStream mysteryText) {
        phrases = new HashSet<>();

        try(BufferedReader br = new BufferedReader(new InputStreamReader(mysteryText, UTF_8), BUFFER_SIZE)) {
            String chunk;

            while ((chunk = br.readLine()) != null) {
                String[] arr = cleanUp(chunk).split(",:;");
                Arrays.stream(arr).filter(x->!x.equals(EMPTY_STRING)).forEach(phrases::add);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public double getAverageCountPhrases() {
        return phrases.size();
    }
}
