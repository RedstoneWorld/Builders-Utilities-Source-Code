package com.buildersrefuge.utilities.util;

import com.buildersrefuge.utilities.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

public class SecretBlockGUI {

    private static final String endGatewayB64 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGU0MDNmNDc1YThhNGU1M2ZhMmYxN2Q4MmZkOTU3Y2NkZDFmOWY2NzViYTRhYzQzOWJjN2VmNGJjNjE2ZmIifX19";
    private static final String mushroomB64 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2ZhMzljY2Y0Nzg4ZDkxNzlhODc5NWU2YjcyMzgyZDQ5Mjk3YjM5MjE3MTQ2ZWRhNjhhZTc4Mzg0MzU1YjEzIn19fQ==";
    private static final String stemB64 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODRkNTQxMjc1YzdmOTI0YmNiOWViMmRiYmY0Yjg2NmI3NjQ5YzMzMGE2YTAxM2I1M2Q1ODRmZDRkZGYxODZjYSJ9fX0=";
    private static final String fullStemB64 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjU1ZmE2NDJkNWViY2JhMmM1MjQ2ZmU2NDk5YjFjNGY2ODAzYzEwZjE0ZjUyOTljOGU1OTgxOWQ1ZGMifX19";
    private static final String doubleSlabB64 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWVjZTM2OWE0YTE0YzdiOGU3ZWNhYmJjOWQ4MzIzOGI1ZjU1YzQ3Mzg0NjNmNjY4NzhjOTZiNGM3MjE1M2UifX19";
    private static final String stoneB64 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGRkMGNkMTU4YzJiYjY2MTg2NTBlMzk1NGIyZDI5MjM3ZjViNGMwZGRjN2QyNThlMTczODBhYjY5NzlmMDcxIn19fQ==";
    private static final String sandstoneB64 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDM2ZTk4NjI4MzJhNmZjYzQ4NTVmYTc5YzFhYWU1ZTczYjkxOTdlYzZiYmQzOGY2MzEyZWZhYzY4MGM2MzI4NSJ9fX0=";
    private static final String redSandstoneB64 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTJkYTdhYTFhZTZjYzlkNmMzNmMxOGE0NjBkMjM5ODE2MmVkYzIyMDdmZGZjOWUyOGE3YmY4NGQ3NDQxYjhhMiJ9fX0=";
    private static final String oakB64 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTA1NWFjNzE1YmVkMjdiYTMyODkwNzFmN2QxYjFjNWFiOTRkMTJjNDA5ZjkwNDc1MGMxM2NkM2QwYWI0YTQifX19";
    private static final String spruceB64 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmZmODk2MWRhODQ3ZTM3YmJkMTE0ZWUxZjc5ODg4MmE5ZWY1NTFjNjkyZjE2NWNhYWFlNGNmNWJmZWVmIn19fQ==";
    private static final String birchB64 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGIxMjM5NzMxZTM5ZjMxYTFhZDFkYmNkMmI3ODI4YzU5ZmVjY2RlNzhhNzhhNzJmMjVkZjNjMWUyMjY3MzRlYyJ9fX0=";
    private static final String jungleB64 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTkyYmE5MzYxYmRkODVlMjI2OWU3ZjBiZTEzMWNjMzI1NDFkMWUzOGM2YmExMzdlOGQyMWM2MmY2MjZhYWIxIn19fQ==";
    private static final String acaciaB64 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTM5ZmE2ZjFhOTY0NTExMWIyNzk2M2E3NmI4YzY2MTE2YTA3MGJjMzkzMDdjNWI5ODU2NzVjODE0ZTI2NSJ9fX0=";
    private static final String darkOakB64 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWU1YjQ4YTU5MmUxZWI4NGRkMjMwMWU2OWUyYWFhNTFlN2I3YWZjODJmMzFmZjRkMzYzMzFmZTczNzVjNzIifX19";

    private final Main plugin;

    public SecretBlockGUI(Main main) {
        plugin = main;
    }

    public Inventory generateStartInv() {
        Items i = new Items();
        Inventory inv = Bukkit.createInventory(null, 27, plugin.getText("secret-blocks.title"));
        inv.setItem(0, Items.create(Material.HUGE_MUSHROOM_1, (short) 0, 1, plugin.getText("secret-blocks.mushrrom1")));
        inv.setItem(1, Items.create(Material.HUGE_MUSHROOM_2, (short) 0, 1, plugin.getText("secret-blocks.mushroom2")));
        inv.setItem(2, Items.createHead(mushroomB64, 1, plugin.getText("secret-blocks.mushroom-block")));
        inv.setItem(3, Items.createHead(stemB64, 1, plugin.getText("secret-blocks.mushroom-stem")));
        inv.setItem(4, Items.createHead(fullStemB64, 1, plugin.getText("secret-blocks.mushroom-fullstem")));
        inv.setItem(5, Items.createHead(doubleSlabB64, 1, plugin.getText("secret-blocks.double-stone-slab")));
        inv.setItem(6, Items.createHead(stoneB64, 1, plugin.getText("secret-blocks.smooth-stone")));
        inv.setItem(7, Items.createHead(sandstoneB64, 1, plugin.getText("secret-blocks.smooth-sandstone")));
        inv.setItem(8, Items.createHead(redSandstoneB64, 1, plugin.getText("secret-blocks.smooth-red-sandstone")));

        inv.setItem(9, Items.createHead(oakB64, 1, plugin.getText("secret-blocks.full-bark-oak")));
        inv.setItem(10, Items.createHead(spruceB64, 1, plugin.getText("secret-blocks.full-bark-spruce")));
        inv.setItem(11, Items.createHead(birchB64, 1, plugin.getText("secret-blocks.full-bark-birch")));
        inv.setItem(12, Items.createHead(jungleB64, 1, plugin.getText("secret-blocks.full-bark-jungle")));
        inv.setItem(13, Items.createHead(acaciaB64, 1, plugin.getText("secret-blocks.full-bark-acacia")));
        inv.setItem(14, Items.createHead(darkOakB64, 1, plugin.getText("secret-blocks.full-bark-dark-oak")));


        inv.setItem(18, Items.create(Material.MOB_SPAWNER, (short) 0, 1, plugin.getText("secret-blocks.mob-spawner")));
        inv.setItem(19, Items.create(Material.BARRIER, (short) 0, 1, plugin.getText("secret-blocks.barrier")));
        inv.setItem(20, Items.create(Material.DRAGON_EGG, (short) 0, 1, plugin.getText("secret-blocks.dragon-egg")));
        if (!plugin.getServerVersion().contains("v1_8")) {
            inv.setItem(21, Items.create(Material.GRASS_PATH, (short) 0, 1, plugin.getText("secret-blocks.grass-path")));
            if (!plugin.getConfig().getBoolean("remove-end-gate-from-blocks")){
                inv.setItem(22, Items.createHead(endGatewayB64, 1, plugin.getText("secret-blocks.end-gateway")));
            }
        }
        return inv;
    }

}
