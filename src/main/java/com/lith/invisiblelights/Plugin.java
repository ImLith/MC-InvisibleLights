package com.lith.invisiblelights;

import com.lith.invisiblelights.config.ConfigManager;
import com.lith.invisiblelights.events.PlayerEvents;
import com.lith.lithcore.abstractClasses.AbstractPlugin;

public class Plugin extends AbstractPlugin<Plugin, ConfigManager> {
    public static Plugin plugin;

    @Override
    public void onEnable() {
        plugin = this;
        configs = new ConfigManager(this);

        super.onEnable();
    }

    @Override
    protected void registerEvents() {
        registerEvent(new PlayerEvents(this));
    }
}
