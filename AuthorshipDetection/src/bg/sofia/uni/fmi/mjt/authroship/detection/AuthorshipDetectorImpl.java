package bg.sofia.uni.fmi.mjt.authroship.detection;

import java.io.InputStream;

import static bg.sofia.uni.fmi.mjt.authroship.detection.helpers.ValidationHelper.checkNotNull;

public class AuthorshipDetectorImpl implements AuthorshipDetector {
    // stream към файл с лингвистичните подписи на множество автори
    private InputStream signaturesDataset;
    // масив от тегла, коитo се прилагат върху всеки feature,
    // при изчислението на лингвистичния подпис
    private double[] weights;

    public AuthorshipDetectorImpl(InputStream signaturesDataset, double[] weights) {
        setSignaturesDataset(signaturesDataset);
        setWeights(weights);
    }

    /**
     *
     * Returns the linguistic signature for the given input stream @mysteryText based on the following features:
     * 1. Average Word Complexity
     * 2. Type Token Ratio
     * 3. Hapax Legomena Ratio
     * 4. Average Sentence Length
     * 5. Average Sentence Complexity
     *
     * @throws IllegalArgumentException if @mysteryText is null
     *
     */
    @Override
    public LinguisticSignature calculateSignature(InputStream mysteryText) {
        checkNotNull(mysteryText);
        // TODO:
        return null;
    }

    /**
     *
     * Returns a non-negative real number indicating the similarity between @firstSignature and @secondSignature.
     * The calculation should be done using the formula in the assignment.
     *
     * The smaller the number, the more similar the signatures. Zero indicates identical signatures.
     *
     * @throws IllegalArgumentException if @firstSignature or @secondSignature is null
     *
     */
    @Override
    public double calculateSimilarity(LinguisticSignature firstSignature, LinguisticSignature secondSignature) {
        checkNotNull(firstSignature);
        checkNotNull(secondSignature);

        // TODO:
        return 0;
    }

    /**
     *
     * Returns the name of the author that best matches the given @mysteryText
     *
     * @throws IllegalArgumentException if @mysteryText is null
     *
     */
    @Override
    public String findAuthor(InputStream mysteryText) {
        checkNotNull(mysteryText);
        // TODO:
        return null;
    }

    public InputStream getSignaturesDataset() {
        return signaturesDataset;
    }

    private void setSignaturesDataset(InputStream signaturesDataset) {
        checkNotNull(signaturesDataset);
        this.signaturesDataset = signaturesDataset;
    }

    public double[] getWeights() {
        return weights;
    }

    private void setWeights(double[] weights) {
        checkNotNull(weights);
        this.weights = weights;
    }
}
