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

public class PlayerInteract implements Listener {
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

        player.sendMessage("New level: " + newLightLevel);
    }
}
