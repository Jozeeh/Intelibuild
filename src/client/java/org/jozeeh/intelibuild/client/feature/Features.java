package org.jozeeh.intelibuild.client.feature;

import java.util.ArrayList;
import java.util.List;

public final class Features {
    private static final List<Feature> FEATURES = new ArrayList<>();

    private Features() {
    }

    public static void register(Feature feature) {
        FEATURES.add(feature);
    }

    public static void initializeAll() {
        FEATURES.forEach(Feature::onInitialize);
    }
}
