package com.buildersrefuge.utilities.listeners;

import com.buildersrefuge.utilities.Main;
import com.buildersrefuge.utilities.util.BannerGUI;
import com.buildersrefuge.utilities.util.BannerUtil;
import com.buildersrefuge.utilities.util.InventoryUtil;
import org.bukkit.block.banner.Pattern;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Random;

public class BannerInventoryListener implements Listener {
    public Main plugin;

    public BannerInventoryListener(Main main) {
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

        if (name.equals(plugin.getText("banner.title.base-color"))) {
            e.setCancelled(true);
            if (slot == 1) {
                int ran = Main.RANDOM.nextInt(16);
                if (ran > 7) {
                    ran++;
                }
                p.openInventory(plugin.getBannerGui().generateColorInv(inv, inv.getItem(ran + 20), true));
            }
            if (slot == 7) {
                p.closeInventory();
            }
            if (slot > 18 && slot < 36 && (slot % 9) > 0) {
                p.openInventory(plugin.getBannerGui().generateColorInv(inv, e.getCurrentItem(), true));
            }
        } else if (name.equals(plugin.getText("banner.title.color"))) {
            e.setCancelled(true);
            if (slot == 1) {
                int ran = Main.RANDOM.nextInt(16);
                if (ran > 7) {
                    ran++;
                }
                p.openInventory(plugin.getBannerGui().generatePatternInv(inv, inv.getItem(ran + 20)));
            }
            if (slot == 7) {
                p.closeInventory();
            } else if (slot == 4) {
                ItemStack item = e.getCurrentItem();
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName("");
                item.setItemMeta(meta);
                p.getInventory().addItem(item);
                p.closeInventory();
            } else if (slot > 18 && slot < 36 && (slot % 9) > 0) {
                p.openInventory(plugin.getBannerGui().generatePatternInv(inv, e.getCurrentItem()));
            }
        } else if (name.equals(plugin.getText("banner.title.pattern"))) {
            e.setCancelled(true);
            if (slot == 1) {
                if (((BannerMeta) inv.getItem(4).getItemMeta()).numberOfPatterns() > 9) {
                    int ran = Main.RANDOM.nextInt(38) + 9;
                    ItemStack item = BannerUtil.addPattern(inv.getItem(4), new Pattern(BannerUtil.getColorFromBanner(inv.getItem(ran)), BannerUtil.getPatternType(inv.getItem(ran))));
                    ItemMeta meta = item.getItemMeta();
                    meta.setDisplayName("");
                    item.setItemMeta(meta);
                    p.getInventory().addItem(item);
                    p.closeInventory();
                } else {
                    p.openInventory(plugin.getBannerGui().generateColorInv(inv, inv.getItem(Main.RANDOM.nextInt(38) + 9), false));
                }
            }
            if (slot == 7) {
                p.closeInventory();
            } else if (slot == 4) {
                ItemStack item = e.getCurrentItem();
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName("");
                item.setItemMeta(meta);
                p.getInventory().addItem(item);
                p.closeInventory();
            } else if (slot > 8 && slot < 54) {
                if (((BannerMeta) inv.getItem(4).getItemMeta()).numberOfPatterns() > 9) {
                    ItemStack item = BannerUtil.addPattern(inv.getItem(4), new Pattern(BannerUtil.getColorFromBanner(e.getCurrentItem()), BannerUtil.getPatternType(e.getCurrentItem())));
                    ItemMeta meta = item.getItemMeta();
                    meta.setDisplayName("");
                    item.setItemMeta(meta);
                    p.getInventory().addItem(item);
                    p.closeInventory();
                } else {
                    p.openInventory(plugin.getBannerGui().generateColorInv(inv, e.getCurrentItem(), false));
                }
            }
        }
    }
}

