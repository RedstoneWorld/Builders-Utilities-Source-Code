package com.buildersrefuge.utilities.util;

import com.buildersrefuge.utilities.Main;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class BannerGUI {

    private final Main plugin;

    public BannerGUI(Main main) {
        plugin = main;
    }

    public Inventory generateStartInv() {
        Inventory inv = Bukkit.createInventory(null, 54, plugin.getText("banner.title.base-color"));
        for (int x = 0; x < 54; x++) {
            inv.setItem(x, Items.create(Material.STAINED_GLASS_PANE, (short) 7, 1, "&7"));
        }
        inv.setItem(1, Items.createHead(plugin.getTexture("randomise.banner"), 1, plugin.getText("banner.randomise")));
        inv.setItem(4, BannerUtil.createBanner(1, DyeColor.WHITE, ""));
        inv.setItem(7, Items.create(Material.BARRIER, (short) 0, 1, plugin.getText("banner.close")));
        for (int x = 19; x < 27; x++) {
            DyeColor color = DyeColor.getByDyeData((byte) (x - 19));
            inv.setItem(x, BannerUtil.createBanner(1, color, plugin.getText("banner.select", "what", color.toString().toLowerCase().replace("_", " "))));
        }
        for (int x = 28; x < 36; x++) {
            DyeColor color = DyeColor.getByDyeData((byte) (x - 20));
            inv.setItem(x, BannerUtil.createBanner(1, color, plugin.getText("banner.select", "what", color.toString().toLowerCase().replace("_", " "))));
        }
        return inv;
    }

    public Inventory generateColorInv(Inventory inv, ItemStack clicked, boolean first) {
        Inventory inv1 = Bukkit.createInventory(null, 54, plugin.getText("banner.title.color"));
        for (int x = 0; x < 54; x++) {
            inv1.setItem(x, Items.create(Material.STAINED_GLASS_PANE, (short) 7, 1, "&7"));
        }
        inv1.setItem(1, Items.createHead(plugin.getTexture("randomise.banner"), 1, plugin.getText("banner.randomise")));
        if (first) {
            inv1.setItem(4, BannerUtil.createBanner(1, BannerUtil.getBaseColor(clicked), ""));
        } else {
            inv1.setItem(4, BannerUtil.addPattern(inv.getItem(4), new Pattern(BannerUtil.getColorFromBanner(clicked), BannerUtil.getPatternType(clicked))));
        }
        inv1.setItem(7, Items.create(Material.BARRIER, (short) 0, 1, plugin.getText("banner.close")));
        for (int x = 19; x < 27; x++) {
            DyeColor color = DyeColor.getByDyeData((byte) (x - 19));
            inv1.setItem(x, Items.create(Material.INK_SACK, (short) (x - 19), 1, plugin.getText("banner.select", "what", color.toString().toLowerCase().replace("_", " "))));
        }
        for (int x = 28; x < 36; x++) {
            DyeColor color = DyeColor.getByDyeData((byte) (x - 20));
            inv1.setItem(x, Items.create(Material.INK_SACK, (short) (x - 20), 1, plugin.getText("banner.select", "what", color.toString().toLowerCase().replace("_", " "))));
        }
        return inv1;
    }

    public Inventory generatePatternInv(Inventory inv, ItemStack clicked) {
        Inventory inv1 = Bukkit.createInventory(null, 54, plugin.getText("banner.title.pattern"));
        for (int x = 0; x < 54; x++) {
            inv1.setItem(x, Items.create(Material.STAINED_GLASS_PANE, (short) 7, 1, "&7"));
        }
        inv1.setItem(1, Items.createHead(plugin.getTexture("randomise.banner"), 1, plugin.getText("banner.randomise")));
        inv1.setItem(4, inv.getItem(4));
        inv1.setItem(7, Items.create(Material.BARRIER, (short) 0, 1, plugin.getText("banner.close")));
        DyeColor base = DyeColor.WHITE;
        DyeColor click = BannerUtil.getDyeColor(clicked);
        if (click == null || click.equals(DyeColor.WHITE) || click.equals(DyeColor.SILVER) || click.equals(DyeColor.LIME) || click.equals(DyeColor.LIGHT_BLUE) || click.equals(DyeColor.YELLOW)) {
            base = DyeColor.BLACK;
        }
        for (int x = 9; x < 47; x++) {
            PatternType pattern = PatternType.values()[x - 8];
            inv1.setItem(x, BannerUtil.createBanner(1, base,
                    plugin.getText("banner.select", "what", pattern.toString().toLowerCase().replace("_", " ")),
                    new Pattern(click, pattern)));
        }
        return inv1;
    }

}
