package bg.sofia.uni.fmi.mjt.authroship.detection;

import java.util.Map;

import static bg.sofia.uni.fmi.mjt.authroship.detection.helpers.ValidationHelper.checkNotNull;

public class LinguisticSignature {
    private Map<FeatureType, Double> features;

    public LinguisticSignature(Map<FeatureType, Double> features) {
        setFeatures(features);
    }

    public Map<FeatureType, Double> getFeatures() {
        return features;
    }

    private void setFeatures(Map<FeatureType, Double> features) {
        checkNotNull(features);
        this.features = features;
    }
}
