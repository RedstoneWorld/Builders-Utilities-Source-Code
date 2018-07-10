package com.buildersrefuge.utilities.listeners;

import com.buildersrefuge.utilities.Main;
import com.buildersrefuge.utilities.managers.ToggleOption;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class PlayerOptionsListener implements Listener {
    private final Main plugin;
    private HashMap<UUID, Double> lastVelocity = new HashMap<>();
    private final Set<UUID> slower = new HashSet<>();
    private final Set<UUID> slower2 = new HashSet<>();

    public PlayerOptionsListener(Main main) {
        plugin = main;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onMove(final PlayerMoveEvent e) {
        if (e.getPlayer().isFlying()) {
            if (!plugin.getToggleManager().hasToggled(e.getPlayer(), ToggleOption.ADVANCED_FLY)) {
                return;
            }
            Double speed = e.getFrom().clone().add(0, -e.getFrom().getY(), 0).distance(e.getTo().clone().add(0, -e.getTo().getY(), 0));

            if (Math.abs(e.getFrom().getYaw() - e.getTo().getYaw()) > 5) {
                return;
            }
            if (Math.abs(e.getFrom().getPitch() - e.getTo().getPitch()) > 5) {
                return;
            }
            if (lastVelocity.containsKey(e.getPlayer().getUniqueId())) {
                Double lastSpeed = lastVelocity.get(e.getPlayer().getUniqueId());
                if (speed * 1.3 < lastSpeed) {
                    if (slower.contains(e.getPlayer().getUniqueId())) {
                        if (slower2.contains(e.getPlayer().getUniqueId())) {
                            Vector v = e.getPlayer().getVelocity().clone();
                            v.setX(0);
                            v.setZ(0);
                            e.getPlayer().setVelocity(v);
                            lastVelocity.put(e.getPlayer().getUniqueId(), 0.0);
                            //No more if slower.contains** as if e.getPlayer().getName() isn't there, it won't get removed (:
                            slower.remove(e.getPlayer().getUniqueId());
                            slower2.remove(e.getPlayer().getUniqueId());
                        } else {
                            slower2.add(e.getPlayer().getUniqueId());
                        }
                    } else {
                        slower.add(e.getPlayer().getUniqueId());
                    }
                } else if (speed > lastSpeed) {
                    lastVelocity.put(e.getPlayer().getUniqueId(), speed);
                    slower.remove(e.getPlayer().getUniqueId());
                    slower2.remove(e.getPlayer().getUniqueId());
                }
            } else {
                lastVelocity.put(e.getPlayer().getUniqueId(), speed);
                slower.remove(e.getPlayer().getUniqueId());
            }
        }
    }

    @EventHandler
    public void onLogout(PlayerQuitEvent e) {
        lastVelocity.remove(e.getPlayer().getUniqueId());
        slower.remove(e.getPlayer().getUniqueId());
        slower2.remove(e.getPlayer().getUniqueId());
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBlockBreak(PlayerInteractEvent e) {
        if (plugin.getToggleManager().hasToggled(e.getPlayer(), ToggleOption.SLAB_BREAKING_DISABLED)
                || !e.getPlayer().getGameMode().equals(GameMode.CREATIVE)
                || !e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            return;
        }

        Material type = e.getPlayer().getInventory().getItemInMainHand().getType();
        if (!plugin.getServerVersion().contains("v1_8")) {
            if (!(type.equals(Material.STEP) || type.equals(Material.WOOD_STEP) || type.equals(Material.STONE_SLAB2) || type.equals(Material.PURPUR_SLAB))) {
                return;
            }
        } else {
            if (!(type.equals(Material.STEP) || type.equals(Material.WOOD_STEP) || type.equals(Material.STONE_SLAB2))) {
                return;
            }
        }

        switch (e.getClickedBlock().getType()) {
            case DOUBLE_STEP:
                handleClick(e, Material.STEP);
                break;
            case WOOD_DOUBLE_STEP:
                handleClick(e, Material.WOOD_STEP);
                break;
            case DOUBLE_STONE_SLAB2:
                handleClick(e, Material.STONE_SLAB2);
                break;
            case PURPUR_DOUBLE_SLAB:
                handleClick(e, Material.PURPUR_SLAB);
                break;
        }

    }

    private void handleClick(PlayerInteractEvent e, Material toSet) {
        if (e.getClickedBlock().getData() <= 7) {
            e.setCancelled(true);
            byte data = e.getClickedBlock().getData();
            if (isTop(e.getPlayer(), e.getClickedBlock())) {
                e.getClickedBlock().setType(toSet);
                e.getClickedBlock().setData(data);
            } else {
                e.getClickedBlock().setType(toSet);
                e.getClickedBlock().setData((byte) (data + 8));
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void GlazedTerracottaInteract(final PlayerInteractEvent e) {
        if (!plugin.getServerVersion().contains("v1_12") && (e.getHand() == null)) {
            return;
        }
        if (e.getHand() == null) {
            return;
        }
        if (!e.getHand().equals(EquipmentSlot.HAND)) {
            return;
        }
        if (!plugin.getToggleManager().hasToggled(e.getPlayer(), ToggleOption.TERRACOTTA_ROTATING)) {
            return;
        }
        if (e.isCancelled()) {
            return;
        }
        if (!e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            return;
        }
        if (!e.getClickedBlock().getType().name().contains("GLAZED")) {
            return;
        }
        if (!e.getPlayer().isSneaking()) {
            return;
        }
        Material type = e.getPlayer().getInventory().getItemInHand().getType();
        if (!(type.equals(Material.AIR))) {
            return;
        }
        Bukkit.getScheduler().runTask(plugin, () -> {
            Block b = e.getClickedBlock();
            byte da = b.getData();
            byte data = (byte) (da + 1);
            if (!(da >= 0 && da < 4)) {
                data = 0;
            }
            b.setData(data, true);
        });
        e.setCancelled(true);
    }

    @SuppressWarnings("deprecation")
    @EventHandler(priority = EventPriority.MONITOR)
    public void ironTrapDoorInteract(final PlayerInteractEvent e) {
        if (plugin.getToggleManager().hasToggled(e.getPlayer(), ToggleOption.IRON_TRAPDOOR_DISABLED)) {
            return;
        }
        if (!e.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
            return;
        }
        if (e.isCancelled()) {
            return;
        }
        if (!e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            return;
        }
        if (!e.getClickedBlock().getType().equals(Material.IRON_TRAPDOOR)) {
            return;
        }
        if (!plugin.getServerVersion().contains("v1_8")) {
            if (!e.getHand().equals(EquipmentSlot.HAND)) {
                return;
            }
        }
        if (e.getPlayer().isSneaking()) {
            return;
        }

        Bukkit.getScheduler().runTask(plugin, () -> {
            Block b = e.getClickedBlock();
            byte da = b.getData();
            byte data = 0;
            if (da >= 0 && da < 4 || da >= 8 && da < 12) {
                data = (byte) (da + 4);
            } else if (da >= 4 && da < 8 || da >= 12 && da < 16) {
                data = (byte) (da - 4);
            }
            b.setData(data, true);
        });
        e.setCancelled(true);
    }

    private boolean isTop(Player p, Block b) {
        Location start = p.getEyeLocation().clone();
        while ((!start.getBlock().equals(b)) && start.distanceSquared(p.getEyeLocation()) < 36) {
            start.add(p.getLocation().getDirection().multiply(0.05));
        }
        return start.getY() % 1 > 0.5;
    }
}
