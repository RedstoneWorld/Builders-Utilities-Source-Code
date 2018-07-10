package com.buildersrefuge.utilities.util;

import com.buildersrefuge.utilities.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

public class ColorGUI {
    private final Main plugin;

    public ColorGUI(Main main) {
        plugin = main;
    }

    public Inventory generateInv() {
        Inventory inv = Bukkit.createInventory(null, 54, plugin.getText("armor-color.title"));
        for (int x = 0; x < 54; x++) {
            inv.setItem(x, Items.create(Material.STAINED_GLASS_PANE, (short) 8, 1, "&7"));
        }
        inv.setItem(10, Items.create(Material.LEATHER_HELMET, (short) 0, 1, plugin.getText("armor-color.helmet")));
        inv.setItem(19, Items.create(Material.LEATHER_CHESTPLATE, (short) 0, 1, plugin.getText("armor-color.chestplate")));
        inv.setItem(28, Items.create(Material.LEATHER_LEGGINGS, (short) 0, 1, plugin.getText("armor-color.leggings")));
        inv.setItem(37, Items.create(Material.LEATHER_BOOTS, (short) 0, 1, plugin.getText("armor-color.boots")));
        inv.setItem(22, Items.createHead(plugin.getTexture("randomise.red"), 1, plugin.getText("armor-color.randomize")));
        inv.setItem(23, Items.createHead(plugin.getTexture("randomise.green"), 1, plugin.getText("armor-color.randomize")));
        inv.setItem(24, Items.createHead(plugin.getTexture("randomise.blue"), 1, plugin.getText("armor-color.randomize")));
        inv.setItem(31, Items.createHead(plugin.getTexture("colors.red"), 10, plugin.getText("armor-color.color", "color", ChatColor.RED + "Red")));
        inv.setItem(32, Items.createHead(plugin.getTexture("colors.green"), 10, plugin.getText("armor-color.color", "color", ChatColor.GREEN + "Green")));
        inv.setItem(33, Items.createHead(plugin.getTexture("colors.blue"), 10, plugin.getText("armor-color.color", "color", ChatColor.BLUE + "Blue")));
        updateInv(inv);
        return inv;
    }

    public void updateInv(Inventory inv) {
        float multiplier = 255f / 20f;
        int r = (int) (((float) inv.getItem(31).getAmount()) * multiplier);
        int g = (int) (((float) inv.getItem(32).getAmount()) * multiplier);
        int b = (int) (((float) inv.getItem(33).getAmount()) * multiplier);
        inv.setItem(10, Items.color(inv.getItem(10), r, g, b));
        inv.setItem(19, Items.color(inv.getItem(19), r, g, b));
        inv.setItem(28, Items.color(inv.getItem(28), r, g, b));
        inv.setItem(37, Items.color(inv.getItem(37), r, g, b));
    }
}
