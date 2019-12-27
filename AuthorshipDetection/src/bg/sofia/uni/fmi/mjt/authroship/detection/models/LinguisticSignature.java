package bg.sofia.uni.fmi.mjt.authroship.detection.models.common;

import bg.sofia.uni.fmi.mjt.authroship.detection.models.common.enums.FeatureType;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static bg.sofia.uni.fmi.mjt.authroship.detection.models.common.Validator.checkNotNegative;
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

    private void addFeature(FeatureType featureType, double weight) {
        checkNotNull(features);
        checkNotNull(featureType);
        checkNotNegative(weight);
        this.features.put(featureType, weight);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LinguisticSignature that = (LinguisticSignature) o;
        return Objects.equals(features, that.features);
    }

    @Override
    public int hashCode() {
        return Objects.hash(features);
    }
}
