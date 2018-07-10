package com.buildersrefuge.utilities.listeners;

import com.buildersrefuge.utilities.Main;
import com.buildersrefuge.utilities.managers.ToggleOption;
import com.buildersrefuge.utilities.util.InventoryUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ToggleInventoryListener implements Listener {
    public Main plugin;

    public ToggleInventoryListener(Main main) {
        plugin = main;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        int slot = InventoryUtil.getRawSlot(e);
        Inventory inv = InventoryUtil.getClickedInventory(e);
        if (slot == -1 || inv == null) {
            return;
        }
        String name = inv.getName();

        if (name.equals(plugin.getText("toggle.title"))) {
            e.setCancelled(true);
            switch (slot) {
                case 1:
                case 10:
                case 19:
                    plugin.getToggleManager().toggle(p, ToggleOption.IRON_TRAPDOOR_DISABLED);
                    break;
                case 2:
                case 11:
                case 20:
                    plugin.getToggleManager().toggle(p, ToggleOption.SLAB_BREAKING_DISABLED);
                    break;
                case 3:
                case 12:
                case 21:
                    if (plugin.getServerVersion().contains("v1_12")) {
                        plugin.getToggleManager().toggle(p, ToggleOption.TERRACOTTA_ROTATING);
                    }
                    break;
                case 5:
                case 14:
                case 23:
                    if (p.hasPermission("builders.util.nightvision")) {
                        if (p.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
                            p.removePotionEffect(PotionEffectType.NIGHT_VISION);
                        } else {
                            p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0, true, false));
                        }
                    }
                    break;
                case 6:
                case 15:
                case 24:
                    if (p.hasPermission("builders.util.noclip")) {
                        plugin.getNoClipManager().toggle(p);
                    }
                    break;
                case 7:
                case 16:
                case 25:
                    if (p.hasPermission("builders.util.advancedfly")) {
                        plugin.getToggleManager().toggle(p, ToggleOption.ADVANCED_FLY);
                    }
                    break;
            }
            plugin.getToggleGui().updateInv(inv, p);
        }
    }
}

