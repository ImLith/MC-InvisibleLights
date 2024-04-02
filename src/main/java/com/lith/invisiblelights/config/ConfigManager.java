package com.lith.invisiblelights.config;

import org.bukkit.Particle.DustOptions;
import com.lith.invisiblelights.Static.ConfigKeys;
import com.lith.lithcore.abstractClasses.MainPlugin;
import com.lith.lithcore.abstractClasses.PluginConfigManager;
import static org.bukkit.Color.fromRGB;

public class ConfigManager extends PluginConfigManager {
    public static ConfigMessages messages;
    public static Configs configs;

    public ConfigManager(final MainPlugin<ConfigManager> plugin) {
        super(plugin);

        messages = new ConfigMessages();
        configs = new Configs();
    }

    public final class ConfigMessages {
        public final String increaseLightLevel = getMessage(ConfigKeys.Messages.INCREASED_LIGHT_LEVEL);
    }

    public class Configs {
        public final int searchRadius = config.getInt(ConfigKeys.SEARCH_RADIUS);
        public final DustOptions dustOptions;

        public Configs() {
            dustOptions = new DustOptions(fromRGB(
                    getRgbValue(ConfigKeys.ParticleColor.RED),
                    getRgbValue(ConfigKeys.ParticleColor.GREEN),
                    getRgbValue(ConfigKeys.ParticleColor.BLUE)),
                    1);
        }
    }
}
