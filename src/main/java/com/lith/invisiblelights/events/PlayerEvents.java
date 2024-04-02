package com.lith.invisiblelights.events;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
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
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import com.lith.invisiblelights.Plugin;
import com.lith.invisiblelights.Static.MessageKey;
import com.lith.invisiblelights.config.ConfigManager;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerEvents implements Listener {
    private final Map<UUID, BukkitTask> particleTasks = new HashMap<>();

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
            startParticleTask(player);
        else
            stopParticleTask(player);

    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        stopParticleTask(event.getPlayer());
    }

    private void startParticleTask(Player player) {
        UUID playerUUID = player.getUniqueId();
        if (particleTasks.containsKey(playerUUID))
            return;

        BukkitTask task = new BukkitRunnable() {
            @Override
            public void run() {
                spawnParticlesAroundPlayer(player);
            }
        }.runTaskTimer(Plugin.plugin, 0, 10);
        particleTasks.put(playerUUID, task);
    }

    private void stopParticleTask(Player player) {
        BukkitTask task = particleTasks.remove(player.getUniqueId());

        if (task != null)
            task.cancel();
    }

    private void spawnParticlesAroundPlayer(Player player) {
        Location playerLocation = player.getLocation();

        int searchRadius = ConfigManager.configs.searchRadius;
        int playerX = playerLocation.getBlockX();
        int playerY = playerLocation.getBlockY();
        int playerZ = playerLocation.getBlockZ();

        for (int x = playerX - searchRadius; x <= playerX + searchRadius; x++) {
            for (int y = playerY - searchRadius; y <= playerY + searchRadius; y++) {
                for (int z = playerZ - searchRadius; z <= playerZ + searchRadius; z++) {
                    Location blockLocation = new Location(player.getWorld(), x, y, z);
                    Block block = blockLocation.getBlock();

                    if (block.getType() == Material.LIGHT) {
                        player.spawnParticle(
                                Particle.REDSTONE,
                                blockLocation.add(0.5, 0.5, 0.5), 10, 0.5, 0.5, 0.5, 1,
                                ConfigManager.configs.dustOptions);
                    }
                }
            }
        }
    }
}
