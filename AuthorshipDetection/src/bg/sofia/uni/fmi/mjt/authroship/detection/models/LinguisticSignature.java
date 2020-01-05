package bg.sofia.uni.fmi.mjt.authroship.detection.models;

import bg.sofia.uni.fmi.mjt.authroship.detection.models.common.enums.FeatureType;

import java.util.HashMap;
import java.util.Map;

import static bg.sofia.uni.fmi.mjt.authroship.detection.models.common.Validator.checkNotNull;

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
}
