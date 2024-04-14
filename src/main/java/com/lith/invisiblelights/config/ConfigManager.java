package com.lith.invisiblelights.config;

import org.bukkit.Particle.DustOptions;
import com.lith.invisiblelights.Plugin;
import com.lith.invisiblelights.Static;
import com.lith.invisiblelights.Static.ConfigKey;
import com.lith.lithcore.abstractClasses.AbstractConfigManager;
import static org.bukkit.Color.fromRGB;

public class ConfigManager extends AbstractConfigManager<Plugin, ConfigManager> {
    public static ConfigMessages messages;
    public static Configs configs;

    public ConfigManager(final Plugin plugin) {
        super(plugin);
    }

    @Override
    public void load() {
        super.load();

        messages = new ConfigMessages();
        configs = new Configs();
    }

    public final class ConfigMessages {
        public final String increaseLightLevel = getMessage(ConfigKey.Messages.INCREASED_LIGHT_LEVEL);
    }

    public class Configs {
        public final int searchRadius = config.getInt(ConfigKey.SEARCH_RADIUS, Static.DEFAULT_SEARCH_RADIUS);
        public final DustOptions dustOptions;

        public Configs() {
            dustOptions = new DustOptions(fromRGB(
                    getRgbValue(ConfigKey.ParticleColor.RED),
                    getRgbValue(ConfigKey.ParticleColor.GREEN),
                    getRgbValue(ConfigKey.ParticleColor.BLUE)),
                    1);
        }
    }
}
