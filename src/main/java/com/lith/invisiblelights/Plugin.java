package com.lith.invisiblelights;

import com.lith.invisiblelights.config.ConfigManager;
import com.lith.invisiblelights.events.PlayerEvents;
import com.lith.lithcore.abstractClasses.MainPlugin;

public class Plugin extends MainPlugin<ConfigManager> {
    public static Plugin plugin;

    public void onEnable() {
        Plugin.plugin = this;

        registerConfigs();
        registerEvents();

        Static.log.info("Plugin enabled");
    }

    public void onDisable() {
        Static.log.info("Plugin disabled");
    }

    private void registerConfigs() {
        new ConfigManager(this);
    }

    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new PlayerEvents(), this);
    }
}
