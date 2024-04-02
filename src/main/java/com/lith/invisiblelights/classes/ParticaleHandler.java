package com.lith.invisiblelights.classes;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import com.lith.invisiblelights.Plugin;
import com.lith.invisiblelights.config.ConfigManager;

public class ParticaleHandler {
    private static final Map<UUID, BukkitTask> particleTasks = new HashMap<>();

    public static void startParticleTask(Player player) {
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

    public static void stopParticleTask(Player player) {
        BukkitTask task = particleTasks.remove(player.getUniqueId());

        if (task != null)
            task.cancel();
    }

    public static void spawnParticlesAroundPlayer(Player player) {
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
