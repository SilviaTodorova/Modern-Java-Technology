package bg.sofia.uni.fmi.mjt.authroship.detection.models.contracts;

public interface SentenceAnalyzer {
    double getAverageCountWords();

    double getTypeTokeRatio();

    double getHapaxLegomenaRatio();

    double getAverageSentenceLength();

    double getAverageSentenceComplexity();
}


