package bg.sofia.uni.fmi.mjt.authroship.detection.models.contracts;

public interface WordAnalyzer {
    long getCountOfWords();

    long getCountOfUniqueWords();

    long getCountOfAllWords();

    double getAverageWordLength();

    void add(String word);
}
