package com.buildersrefuge.utilities.managers;

import com.buildersrefuge.utilities.Main;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class NoClipManager {
    private final Set<UUID> noClipPlayers = new HashSet<>();
    private final Main plugin;

    public NoClipManager(Main main) {
        this.plugin = main;
        everyTick();
    }

    private void everyTick() {
        Bukkit.getScheduler().runTaskTimer(this.plugin, this::checkForBlocks, 1L, 1L);
    }

    private void checkForBlocks() {
        for (UUID uuid : noClipPlayers) {
            Player p = Bukkit.getPlayer(uuid);
            if (p.isOnline()) {
                if (p.getGameMode().equals(GameMode.CREATIVE)) {
                    boolean noClip;
                    if (p.getLocation().add(0, -0.1, 0).getBlock().getType() != Material.AIR && p.isSneaking()) {
                        noClip = true;
                    } else {
                        noClip = isNoClip(p);
                    }
                    if (noClip) {
                        p.setGameMode(GameMode.SPECTATOR);
                    }
                } else if (p.getGameMode().equals(GameMode.SPECTATOR)) {
                    boolean noClip;
                    if (p.getLocation().add(0, -0.1, 0).getBlock().getType() != Material.AIR) {
                        noClip = true;
                    } else noClip = isNoClip(p);
                    if (!noClip) {
                        p.setGameMode(GameMode.CREATIVE);
                    }
                }
            }
        }
    }

    private boolean isNoClip(Player player) {
        boolean noClip = false;
        Location location = player.getLocation();
        if (location.add(+0.4, 0, 0).getBlock().getType() != Material.AIR) {
            noClip = true;
        } else if (location.add(-0.4, 0, 0).getBlock().getType() != Material.AIR) {
            noClip = true;
        } else if (location.add(0, 0, +0.4).getBlock().getType() != Material.AIR) {
            noClip = true;
        } else if (location.add(0, 0, -0.4).getBlock().getType() != Material.AIR) {
            noClip = true;
        } else if (location.add(+0.4, 1, 0).getBlock().getType() != Material.AIR) {
            noClip = true;
        } else if (location.add(-0.4, 1, 0).getBlock().getType() != Material.AIR) {
            noClip = true;
        } else if (location.add(0, 1, +0.4).getBlock().getType() != Material.AIR) {
            noClip = true;
        } else if (location.add(0, 1, -0.4).getBlock().getType() != Material.AIR) {
            noClip = true;
        } else if (location.add(0, +1.9, 0).getBlock().getType() != Material.AIR) {
            noClip = true;
        }
        return noClip;
    }

    public void disable(Player p) {
        noClipPlayers.remove(p.getUniqueId());
    }

    public boolean isEnabled(Player p) {
        return noClipPlayers.contains(p.getUniqueId());
    }

    public void enable(Player p) {
        noClipPlayers.add(p.getUniqueId());
    }

    public boolean toggle(Player p) {
        if (isEnabled(p)) {
            disable(p);
            if (p.getGameMode() == GameMode.SPECTATOR) {
                p.setGameMode(GameMode.CREATIVE);
            }
            return false;
        } else {
            enable(p);
            return true;
        }
    }
}
