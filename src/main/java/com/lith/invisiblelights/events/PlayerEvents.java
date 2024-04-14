package com.lith.invisiblelights.events;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
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
import com.lith.invisiblelights.Static;
import com.lith.invisiblelights.Static.MessageKey;
import com.lith.invisiblelights.config.ConfigManager;
import com.lith.lithcore.utils.LightUtil;
import com.lith.lithcore.utils.ParticleUtil;
import net.kyori.adventure.text.Component;

public class PlayerEvents implements Listener {
    private final Map<UUID, BukkitTask> particleTasks;
    private final Plugin plugin;

    public PlayerEvents(Plugin plugin) {
        this.plugin = plugin;
        this.particleTasks = new HashMap<>();
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Block block = event.getClickedBlock();
        if (block == null || block.getType() != Material.LIGHT)
            return;

        Player player = event.getPlayer();
        if (player == null)
            return;

        Action playerAction = event.getAction();
        if (playerAction == Action.LEFT_CLICK_BLOCK)
            handleLeftClickInteraction(block);
        else if (playerAction == Action.RIGHT_CLICK_BLOCK)
            handleRightClickInteraction(player, block);
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

    private void handleRightClickInteraction(Player player, Block block) {
        BlockData blockData = block.getBlockData();
        if (blockData == null)
            return;

        final Levelled level = (Levelled) block.getBlockData();
        final int newLightLevel = LightUtil.setLevel(blockData.getLightEmission() + 1, 1, true);

        level.setLevel(newLightLevel);
        block.setBlockData(level, true);

        player.sendActionBar(Component.text(
                ConfigManager.messages.increaseLightLevel
                        .replace(MessageKey.LIGHT, String.valueOf(newLightLevel))));
    }

    private void handleLeftClickInteraction(Block block) {
        block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.LIGHT));
        block.setType(Material.AIR);
    }

    private void startParticleTask(Player player) {
        UUID playerUUID = player.getUniqueId();

        if (particleTasks.containsKey(playerUUID))
            return;

        BukkitTask task = new BukkitRunnable() {
            @Override
            public void run() {
                ParticleUtil.spawnOnBlockAroundPlayer(player, ConfigManager.configs.searchRadius, Material.LIGHT,
                        Particle.REDSTONE, 10, Static.particleOffset, 1, ConfigManager.configs.dustOptions);
            }
        }.runTaskTimer(plugin, 0, 10);

        particleTasks.put(playerUUID, task);
    }

    private void stopParticleTask(Player player) {
        BukkitTask task = particleTasks.remove(player.getUniqueId());

        if (task != null)
            task.cancel();
    }
}
