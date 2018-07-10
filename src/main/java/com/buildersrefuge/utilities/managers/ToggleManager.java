package com.buildersrefuge.utilities.managers;

import com.buildersrefuge.utilities.Main;
import org.bukkit.entity.Player;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class ToggleManager {
    private final Map<UUID, Set<ToggleOption>> playerToggles = new HashMap<>();
    private final Main plugin;

    public ToggleManager(Main main) {
        plugin = main;
    }

    public boolean toggle(Player player, ToggleOption option) {
        if (hasToggled(player, option)) {
            disable(player, option);
            return false;
        } else {
            enable(player, option);
            return true;
        }
    }

    public boolean disable(Player player, ToggleOption option) {
        if (playerToggles.containsKey(player.getUniqueId())) {
            return playerToggles.get(player.getUniqueId()).remove(option);
        }
        return false;
    }

    public boolean enable(Player player, ToggleOption option) {
        return playerToggles.computeIfAbsent(player.getUniqueId(), u -> EnumSet.noneOf(ToggleOption.class)).add(option);
    }

    public boolean hasToggled(Player player, ToggleOption option) {
        return playerToggles.containsKey(player.getUniqueId()) && playerToggles.get(player.getUniqueId()).contains(option);
    }
}
