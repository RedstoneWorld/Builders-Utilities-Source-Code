package com.buildersrefuge.utilities.util;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class BannerUtil {

    @SuppressWarnings("deprecation")
    public static ItemStack createBanner(int amount, DyeColor base, String text, List<Pattern> patterns) {
        ItemStack item = Items.create(Material.BANNER, (short) 0, amount, text);
        BannerMeta meta = (BannerMeta) item.getItemMeta();
        if (com.buildersrefuge.utilities.Main.version.contains("v1_12") || com.buildersrefuge.utilities.Main.version.contains("v1_11")) {
            item.setDurability(base.getDyeData());
        } else {
            meta.setBaseColor(base);
        }

        meta.setPatterns(patterns);
        item.setItemMeta(meta);
        return item;
    }

    static ItemStack createBanner(int amount, DyeColor base, String text, Pattern... pat) {
        return createBanner(amount, base, text, Arrays.asList(pat));
    }

    public static ItemStack addPattern(ItemStack i, Pattern pat) {
        ItemMeta meta = i.getItemMeta();
        if (meta instanceof BannerMeta) {
            BannerMeta bannerMeta = (BannerMeta) meta;
            List<Pattern> patterns = bannerMeta.getPatterns();
            patterns.add(pat);
            bannerMeta.setPatterns(patterns);
            i.setItemMeta(bannerMeta);
        }
        return i;
    }

    @SuppressWarnings("deprecation")
    static DyeColor getBaseColor(ItemStack i) {
        if (i.getType().equals(Material.BANNER)) {
            BannerMeta meta = (BannerMeta) i.getItemMeta();
            if (com.buildersrefuge.utilities.Main.version.contains("v1_12") || com.buildersrefuge.utilities.Main.version.contains("v1_11")) {
                return DyeColor.getByDyeData((byte) i.getDurability());
            } else {
                return meta.getBaseColor();
            }

        }
        return null;
    }

    public static PatternType getPatternType(ItemStack i) {
        if (i.getType().equals(Material.BANNER)) {
            BannerMeta meta = (BannerMeta) i.getItemMeta();
            if (!meta.getPatterns().isEmpty()) {
                return meta.getPattern(0).getPattern();
            }
        }
        return null;
    }

    public static DyeColor getColorFromBanner(ItemStack i) {
        if (i.getType().equals(Material.BANNER)) {
            BannerMeta meta = (BannerMeta) i.getItemMeta();
            if (!meta.getPatterns().isEmpty()) {
                return meta.getPattern(0).getColor();
            }
        }
        return null;
    }

    public static DyeColor getRandomDye() {
        Random r = new Random();
        return DyeColor.values()[r.nextInt(DyeColor.values().length)];
    }

    public static PatternType getRandomPattern() {
        Random r = new Random();
        return PatternType.values()[r.nextInt(PatternType.values().length)];
    }

    static DyeColor getDyeColor(ItemStack i) {
        if (i.getType().equals(Material.INK_SACK)) {
            return DyeColor.getByDyeData((byte) i.getDurability());
        } else {
            return null;
        }
    }


}
