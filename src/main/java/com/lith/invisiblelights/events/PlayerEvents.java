package com.lith.invisiblelights.events;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Levelled;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import com.lith.invisiblelights.Static.MessageKey;
import com.lith.invisiblelights.classes.ParticaleHandler;
import com.lith.invisiblelights.config.ConfigManager;

public class PlayerEvents implements Listener {
    @SuppressWarnings("deprecation")
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Block clickedBlock = event.getClickedBlock();
        if (clickedBlock == null)
            return;

        Player player = event.getPlayer();
        if (player == null)
            return;
        if (clickedBlock.getType() != Material.LIGHT)
            return;
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK)
            return;

        BlockData blockData = clickedBlock.getBlockData();
        if (blockData == null)
            return;

        final Levelled level = (Levelled) clickedBlock.getBlockData();
        final int lightLevel = blockData.getLightEmission();
        final int newLightLevel = lightLevel < 15 ? lightLevel + 1 : 1;

        level.setLevel(newLightLevel);
        clickedBlock.setBlockData(level, true);

        player.sendActionBar(ConfigManager.messages.increaseLightLevel
                .replace(MessageKey.LIGHT, String.valueOf(newLightLevel)));
    }

    @EventHandler
    public void onPlayerItemHeld(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        ItemStack heldItem = player.getInventory().getItem(event.getNewSlot());

        if (heldItem != null && heldItem.getType() == Material.LIGHT)
            ParticaleHandler.startParticleTask(player);
        else
            ParticaleHandler.stopParticleTask(player);

    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        ParticaleHandler.stopParticleTask(event.getPlayer());
    }
}
