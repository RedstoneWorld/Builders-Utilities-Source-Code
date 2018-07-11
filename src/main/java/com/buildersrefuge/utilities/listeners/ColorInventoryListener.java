package com.buildersrefuge.utilities.listeners;

import com.buildersrefuge.utilities.Main;
import com.buildersrefuge.utilities.util.InventoryUtil;
import com.buildersrefuge.utilities.util.Items;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ColorInventoryListener implements Listener {
    public Main plugin;

    public ColorInventoryListener(Main main) {
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

        if (name.equals(plugin.getText("armor-color.title"))) {
            e.setCancelled(true);
            if (slot == 10 || slot == 19 || slot == 28 || slot == 37) {
                float multiplier = 255f / 20f;
                int r = (int) ((float) inv.getItem(31).getAmount() * multiplier);
                int g = (int) ((float) inv.getItem(32).getAmount() * multiplier);
                int b = (int) ((float) inv.getItem(33).getAmount() * multiplier);
                p.getInventory().addItem(Items.color(Items.create(e.getCurrentItem().getType(), (short) 0, 1), r, g, b));

            } else if (slot >= 31 && slot <= 33) { //colors
                ItemStack item = e.getCurrentItem();
                if (e.getClick() == ClickType.LEFT && item.getAmount() < 20) {
                    item.setAmount(item.getAmount() + 1);
                } else if (e.getClick() == ClickType.RIGHT && item.getAmount() > 1) {
                    item.setAmount(item.getAmount() - 1);
                } else if (e.getClick() == ClickType.SHIFT_LEFT) { 
                    if (item.getAmount() < 16) {
                        item.setAmount(item.getAmount() + 5);
                    } else {
                        item.setAmount(20);
                    }
                } else if (e.getClick() == ClickType.SHIFT_RIGHT) {
                    if (item.getAmount() > 5) {
                        item.setAmount(item.getAmount() - 5);
                    } else {
                        item.setAmount(1);
                    }
                }
                plugin.getColorGui().updateInv(inv);

            } else if (slot >= 22 && slot <= 24) { //RANDOM colors
                inv.getItem(slot + 9).setAmount(Main.RANDOM.nextInt(20) + 1);
                plugin.getColorGui().updateInv(inv);
            }
        }
    }
}

