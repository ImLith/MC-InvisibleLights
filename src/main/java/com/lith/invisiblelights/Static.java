package com.lith.invisiblelights;

import com.lith.lithcore.abstractClasses.AbstractConfigKey;
import com.lith.lithcore.classes.Cords3D;

public class Static {
    final public static Cords3D particleOffset = new Cords3D(0.5, 0.5, 0.5);

    final public static class ConfigKeys {
        public static final String SEARCH_RADIUS = "search_radius";

        final public static class ParticleColor extends AbstractConfigKey {
            public static final String RED = setKey("red");
            public static final String GREEN = setKey("green");
            public static final String BLUE = setKey("blue");
        }

        final public static class Messages extends AbstractConfigKey {
            public static final String INCREASED_LIGHT_LEVEL = setKey("increased_light_level");
        }
    }

    final public static class MessageKey {
        public static final String LIGHT = "%light%";
    }
}
