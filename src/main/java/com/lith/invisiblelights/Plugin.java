package com.lith.invisiblelights;

import com.lith.invisiblelights.events.PlayerInteract;
import com.lith.lithcore.abstractClasses.MainPlugin;
import com.lith.lithcore.abstractClasses.PluginConfigManager;

public class Plugin extends MainPlugin<PluginConfigManager> {
    public static Plugin plugin;

    public void onEnable() {
        Plugin.plugin = this;

        registerEvents();

        Static.log.info("Plugin enabled");
    }

    public void onDisable() {
        Static.log.info("Plugin disabled");
    }

    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new PlayerInteract(), this);
    }
}
