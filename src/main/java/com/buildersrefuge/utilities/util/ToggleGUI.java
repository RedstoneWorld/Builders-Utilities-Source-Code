package com.buildersrefuge.utilities.util;

import com.buildersrefuge.utilities.Main;
import com.buildersrefuge.utilities.managers.ToggleOption;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

public class ToggleGUI {
    private final Main plugin;
    private final ItemStack RED_GLASS_PANE;
    private final ItemStack GREEN_GLASS_PANE;
    private final ItemStack ORANGE_GLASS_PANE;

    public ToggleGUI(Main main) {
        plugin = main;
        GREEN_GLASS_PANE = Items.create(Material.STAINED_GLASS_PANE, (short) 5, 1, "&7");
        ORANGE_GLASS_PANE = Items.create(Material.STAINED_GLASS_PANE, (short) 1, 1, "&7");
        RED_GLASS_PANE = Items.create(Material.STAINED_GLASS_PANE, (short) 14, 1, "&7");
    }

    public Inventory generateInv(Player p) {
        Inventory inv = Bukkit.createInventory(null, 27, plugin.getText("toggle.title"));
        ItemStack GRAY_GLASS_PANE = Items.create(Material.STAINED_GLASS_PANE, (short) 8, 1, "&7");
        for (int x = 0; x < 27; x++) {
            inv.setItem(x, GRAY_GLASS_PANE);
        }
        updateInv(inv, p);
        return inv;
    }

    public void updateInv(Inventory inv, Player p) {
        setToggleItem(inv, 1, Material.IRON_TRAPDOOR, "iron-trapdoor", !plugin.getToggleManager().hasToggled(p, ToggleOption.IRON_TRAPDOOR_DISABLED));

        setToggleItem(inv, 2, Material.STEP, "slab-breaking", !plugin.getToggleManager().hasToggled(p, ToggleOption.SLAB_BREAKING_DISABLED));

        if (Main.version.contains("v1_12")) {
            setToggleItem(inv, 3, Material.ORANGE_GLAZED_TERRACOTTA, "terracotta-rotating", plugin.getToggleManager().hasToggled(p, ToggleOption.TERRACOTTA_ROTATING));
        } else {
            setItem(inv, 3, Material.STAINED_GLASS, ORANGE_GLASS_PANE, "not-supported", "terracotta-rotating", false);
        }

        if (p.hasPermission("builders.util.nightvision")) {
            setToggleItem(inv, 5, Material.EYE_OF_ENDER, "night-vision", p.hasPotionEffect(PotionEffectType.NIGHT_VISION));
        } else {
            setItem(inv, 5, Material.EYE_OF_ENDER, ORANGE_GLASS_PANE, "no-permission", "night-vision", false);
        }

        if (p.hasPermission("builders.util.noclip")) {
            setToggleItem(inv, 6, Material.COMPASS, "no-clip", plugin.getNoClipManager().isEnabled(p));
        } else {
            setItem(inv, 6, Material.COMPASS, ORANGE_GLASS_PANE, "no-permission", "no-clip", false);
        }

        if (p.hasPermission("builders.util.advancedfly")) {
            setToggleItem(inv, 7, Material.FEATHER, "advanced-fly", plugin.getToggleManager().hasToggled(p, ToggleOption.ADVANCED_FLY));
        } else {
            setItem(inv, 7, Material.FEATHER, ORANGE_GLASS_PANE, "no-permission", "advanced-fly", false);
        }
    }

    private void setToggleItem(Inventory inv, int slot, Material material, String option, boolean enabled) {
        setItem(inv, slot, material, enabled ? GREEN_GLASS_PANE : RED_GLASS_PANE, "click", option, enabled);
    }

    private void setItem(Inventory inv, int slot, Material material, ItemStack border, String path, String option, boolean enabled) {
        inv.setItem(slot, border);
        inv.setItem(slot + 9, getItem(material, path, option, enabled));
        inv.setItem(slot + 18, border);
    }

    private ItemStack getItem(Material mat, String path, String option, boolean enabled) {
        return Items.create(mat, (short) 0, 1, plugin.getText("toggle." + path,
                "option", plugin.getText("toggle.option." + option),
                "status", plugin.getText("toggle.status." + (enabled ? "enabled" : "disabled"))));
    }
}
