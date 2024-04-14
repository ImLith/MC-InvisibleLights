package com.lith.invisiblelights;

import com.lith.lithcore.abstractClasses.AbstractConfigKey;
import com.lith.lithcore.classes.Cords3D;

public class Static {
    public static final Cords3D particleOffset = new Cords3D(0.5, 0.5, 0.5);

    public static final class ConfigKey {
        public static final String SEARCH_RADIUS = "search_radius";

        public static final class ParticleColor extends AbstractConfigKey {
            public static final String RED = setKey("red");
            public static final String GREEN = setKey("green");
            public static final String BLUE = setKey("blue");
        }

        public static final class Messages extends AbstractConfigKey {
            public static final String INCREASED_LIGHT_LEVEL = setKey("increased_light_level");
        }
    }

    public static final class MessageKey {
        public static final String LIGHT = "%light%";
    }

    public final static class Commands {
        public final static class Name {
            public final static String RELOAD = "lightReload";
        }

        public final static class Permission {
            private final static String PREFIX = "invislight.";
            public final static String RELOAD = PREFIX + "reload";
        }
    }
}
