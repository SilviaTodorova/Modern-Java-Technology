package bg.sofia.uni.fmi.mjt.authroship.detection.models.contracts;

public interface TextAnalyzer {
    double getAverageWordLength();

    double getTypeTokeRatio();

    double getHapaxLegomenaRatio();

    double getAverageSentenceLength();

    double getAverageSentenceComplexity();
}


