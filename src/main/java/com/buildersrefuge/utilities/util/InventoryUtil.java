package com.buildersrefuge.utilities.util;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class InventoryUtil {

    public static int getRawSlot(InventoryClickEvent e) {
        try {
            return e.getRawSlot();
        } catch (NoSuchMethodError error) {
            return -1;
        }
    }

    public static Inventory getClickedInventory(InventoryClickEvent e) {
        Inventory clicked;
        try {
            clicked = e.getClickedInventory();
        } catch (NoSuchMethodError error) {
            if (e.getRawSlot() < e.getView().getTopInventory().getSize()) {
                clicked = e.getView().getTopInventory();
            } else {
                return null;
            }
        }
        return clicked;
    }
}
