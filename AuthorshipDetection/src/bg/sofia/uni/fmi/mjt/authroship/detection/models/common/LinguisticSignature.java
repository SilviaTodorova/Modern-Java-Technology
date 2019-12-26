package bg.sofia.uni.fmi.mjt.authroship.detection;

import bg.sofia.uni.fmi.mjt.authroship.detection.enums.FeatureType;

import java.util.HashMap;
import java.util.Map;

import static bg.sofia.uni.fmi.mjt.authroship.detection.utils.Validator.checkNotNegative;
import static bg.sofia.uni.fmi.mjt.authroship.detection.utils.Validator.checkNotNull;

public class LinguisticSignature {
    private Map<FeatureType, Double> features;

    public LinguisticSignature(Map<FeatureType, Double> features) {
        checkNotNull(features);
        setFeatures(features);
    }

    public Map<FeatureType, Double> getFeatures() {
        return new HashMap<>(features);
    }

    private void setFeatures(Map<FeatureType, Double> features) {
        checkNotNull(features);
        this.features = features;
    }

    private void addFeature(FeatureType featureType, double weight) {
        checkNotNull(features);
        checkNotNull(featureType);
        checkNotNegative(weight);
        this.features.put(featureType, weight);
    }
}
