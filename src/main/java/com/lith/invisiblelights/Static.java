package com.lith.invisiblelights;

import java.util.logging.Logger;
import com.lith.lithcore.abstractClasses.AbstractConfigKey;

public class Static {
    public static final String pluginName = "InvisibleLights";
    public static final Logger log = Logger.getLogger(Static.pluginName);

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
