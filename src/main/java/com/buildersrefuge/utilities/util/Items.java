package com.buildersrefuge.utilities.util;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Items {

    public static ItemStack create(Material mat, short data, int amount, String... text) {
        List<String> textList = new ArrayList<>();
        for (String line1 : text) {
            textList.addAll(Arrays.asList(line1.split("\\n|__")));
        }

        String name = textList.size() > 0 ? textList.get(0) : null;
        List<String> loreList = textList.stream().skip(1)
                .map(s -> ChatColor.translateAlternateColorCodes('&', s))
                .collect(Collectors.toList());

        ItemStack is = new ItemStack(mat);
        is.setAmount(amount);
        ItemMeta meta = is.getItemMeta();
        if (!loreList.isEmpty()) {
            meta.setLore(loreList);
        }
        if (name != null && !name.equals("")) {
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        }
        is.setItemMeta(meta);
        is.setDurability(data);
        return is;
    }

    public static ItemStack color(ItemStack is, int r, int g, int b) {
        LeatherArmorMeta lam = (LeatherArmorMeta) is.getItemMeta();
        Color c = Color.fromRGB(r, g, b);
        lam.setColor(c);
        is.setItemMeta(lam);
        return is;
    }

    public static ItemStack createHead(String data, int amount, String... text) {
        ItemStack item = create(Material.SKULL_ITEM, (short) 3, amount, text);
        SkullMeta headMeta = (SkullMeta) item.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", data));
        Field profileField;
        try {
            profileField = headMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(headMeta, profile);
        } catch (Exception ignored) {}
        item.setItemMeta(headMeta);
        return item;
    }
}
