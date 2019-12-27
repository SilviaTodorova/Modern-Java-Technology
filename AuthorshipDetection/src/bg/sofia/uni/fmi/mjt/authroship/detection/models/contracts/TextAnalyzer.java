package bg.sofia.uni.fmi.mjt.authroship.detection.models.contracts;

public interface TextAnalyzer {
    double getAverageCountWords();

    double getTypeTokeRatio();

    double getHapaxLegomenaRatio();

    double getAverageSentenceLength();

    double getAverageSentenceComplexity();
}


