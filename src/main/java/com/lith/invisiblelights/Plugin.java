package com.lith.invisiblelights;

import com.lith.invisiblelights.Static.Commands;
import com.lith.invisiblelights.config.ConfigManager;
import com.lith.invisiblelights.events.ParticleTaskEvent;
import com.lith.lithcore.abstractClasses.AbstractPlugin;
import com.lith.lithcore.helpers.ReloadConfigCmd;

public class Plugin extends AbstractPlugin<Plugin, ConfigManager> {
    @Override
    public void onEnable() {
        configs = new ConfigManager(this);
        super.onEnable();
    }

    @Override
    protected void registerEvents() {
        registerEvent(new ParticleTaskEvent(this));
    }

    @Override
    protected void registerCommands() {
        new ReloadConfigCmd<Plugin>(this, Commands.Permission.RELOAD, Commands.Name.RELOAD);
    }
}
