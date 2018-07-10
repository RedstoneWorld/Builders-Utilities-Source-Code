package com.buildersrefuge.utilities.listeners;

import com.buildersrefuge.utilities.Main;
import com.buildersrefuge.utilities.util.InventoryUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class SecretBlocksInventoryListener implements Listener {
    public Main plugin;

    public SecretBlocksInventoryListener(Main main) {
        plugin = main;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryClick(InventoryClickEvent e) {
        Inventory inv = InventoryUtil.getClickedInventory(e);
        if (inv == null) {
            return;
        }
        String name = inv.getName();

        if (name.equals(plugin.getText("secret-blocks.title"))) {
            e.getWhoClicked().getInventory().addItem(e.getCurrentItem());
            e.setCancelled(true);
        }
    }
}

