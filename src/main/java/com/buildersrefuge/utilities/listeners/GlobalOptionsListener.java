package com.buildersrefuge.utilities.listeners;

import com.buildersrefuge.utilities.Main;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class GlobalOptionsListener implements Listener {
    private final Main plugin;
    private boolean fixAttackSpeed;
    private boolean preventDragonEggTeleport;
    private boolean disableWeatherChanges;
    private boolean disableRedstone;
    private boolean disablePhysics;
    private boolean disableGravityPhysics;
    private boolean disableExplosions;
    private boolean disableSoilTrample;
    private boolean disableLeavesDecay;

    public GlobalOptionsListener(Main main) {
        plugin = main;
    }

    public void reload() {
        fixAttackSpeed = plugin.getConfig().getBoolean("fix-attackspeed");
        preventDragonEggTeleport = plugin.getConfig().getBoolean("prevent-dragon-egg-teleport");
        disableWeatherChanges = plugin.getConfig().getBoolean("disable-weather-changes");
        disableRedstone = plugin.getConfig().getBoolean("disable-redstone");
        disablePhysics = plugin.getConfig().getBoolean("disable-physics");
        disableGravityPhysics = plugin.getConfig().getBoolean("disable-gravity-physics");
        disableExplosions = plugin.getConfig().getBoolean("disable-explosions");
        disableSoilTrample = plugin.getConfig().getBoolean("disable-soil-trample");
        disableLeavesDecay = plugin.getConfig().getBoolean("disable-leaves-decay");
    }

    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent e) {
        if (!plugin.getServerVersion().contains("v1_8")) {
            if (fixAttackSpeed) {
                AttributeInstance attribute = e.getPlayer().getAttribute(Attribute.GENERIC_ATTACK_SPEED);
                attribute.setBaseValue(1024.0D);
                e.getPlayer().saveData();
            } else {
                AttributeInstance attribute = e.getPlayer().getAttribute(Attribute.GENERIC_ATTACK_SPEED);
                if (attribute.getBaseValue() == 1024.0D) {
                    attribute.setBaseValue(attribute.getDefaultValue());
                    e.getPlayer().saveData();
                }
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (preventDragonEggTeleport
                && e.getClickedBlock() != null
                && e.getClickedBlock().getType().equals(Material.DRAGON_EGG)
                && e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            e.setCancelled(true);
            return;
        }

        if (disableSoilTrample) {
            if (e.getAction() == Action.PHYSICAL) {
                Block block = e.getClickedBlock();
                if (block == null) return;
                if (block.getType() == Material.SOIL) {
                    e.setUseInteractedBlock(Event.Result.DENY);
                    e.setCancelled(true);
                    block.setTypeIdAndData(block.getType().getId(), block.getData(), true);
                    return;
                }
            }
        }
    }

    @EventHandler
    public void onWeather(WeatherChangeEvent e) {
        if (disableWeatherChanges) {
            e.setCancelled(true);
        }
    }


    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPhysics(BlockPhysicsEvent e) {
        if (!disableRedstone) {
            if (e.getChangedType().name().toLowerCase().contains("redstone") ||
                    e.getChangedType().name().toLowerCase().contains("air") ||
                    e.getChangedType().name().toLowerCase().contains("daylight") ||
                    e.getChangedType().name().toLowerCase().contains("diode") ||
                    e.getChangedType().name().toLowerCase().contains("note") ||
                    e.getChangedType().name().toLowerCase().contains("lever") ||
                    e.getChangedType().name().toLowerCase().contains("button") ||
                    e.getChangedType().name().toLowerCase().contains("command") ||
                    e.getChangedType().name().toLowerCase().contains("tripwire") ||
                    e.getChangedType().name().toLowerCase().contains("plate") ||
                    e.getChangedType().name().toLowerCase().contains("string") ||
                    e.getChangedType().name().toLowerCase().contains("piston")) {
                if (!e.getBlock().getType().name().contains("air")) {
                    return;
                }
            }
        }
        if (!e.getChangedType().hasGravity()) {
            if (disablePhysics) {
                e.setCancelled(true);
            }
        } else {
            if (disableGravityPhysics) {
                e.setCancelled(true);
            }
        }

    }


    @EventHandler
    public void onExplode(EntityExplodeEvent e) {
        if (disableExplosions) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onLeafDecay(LeavesDecayEvent e) {
        if (disableLeavesDecay) {
            e.setCancelled(true);
        }
    }
}
