package com.buildersrefuge.utilities.listeners;

import com.boydti.fawe.object.FawePlayer;
import com.buildersrefuge.utilities.Main;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.blocks.BaseBlock;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class SecretBlockListener implements Listener {
    public Main plugin;

    public SecretBlockListener(Main main) {
        plugin = main;
    }

    @SuppressWarnings("deprecation")
    @EventHandler(priority = EventPriority.MONITOR)
    public void placeOfBlock(final BlockPlaceEvent e) {
        if (e.isCancelled()) {
            return;
        }
        if (!e.getBlock().getType().equals(Material.SKULL) || !e.getItemInHand().hasItemMeta() || !e.getItemInHand().getItemMeta().hasLore() || !e.getItemInHand().getItemMeta().getLore().get(0).startsWith("§7§lID§7 ")) {
            return;
        }
        String[] id = e.getItemInHand().getItemMeta().getLore().get(0).replace("§7§lID§7 ", "").split(":");
        final int _id = Integer.parseInt(id[0]);
        final int _data = Integer.parseInt(id[1]);
        Bukkit.getScheduler().runTask(this.plugin, () -> {
            final Material mat = Material.getMaterial(_id);
            Block b = e.getBlockPlaced();
            b.setType(mat, true);
            b.setData((byte) _data, true);
        });
        e.setCancelled(true);
    }

    @SuppressWarnings({"deprecation"})
    @EventHandler(priority = EventPriority.MONITOR)
    public void placeOfBlockInteract(final PlayerInteractEvent e) {
        if (e.isCancelled()) {
            return;
        }
        if (!e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            return;
        }
        if (!e.getPlayer().getItemInHand().getType().equals(Material.SKULL_ITEM) || !e.getPlayer().getItemInHand().hasItemMeta() || !e.getPlayer().getItemInHand().getItemMeta().hasLore() || !e.getPlayer().getItemInHand().getItemMeta().getLore().get(0).startsWith("§7§lID§7 ")) {
            return;
        }
        String[] id = e.getPlayer().getItemInHand().getItemMeta().getLore().get(0).replace("§7§lID§7 ", "").split(":");
        final int _id = Integer.parseInt(id[0]);
        final int _data = Integer.parseInt(id[1]);
        Bukkit.getScheduler().runTask(this.plugin, () -> {
            Block b = null;
            if (e.getBlockFace().equals(BlockFace.DOWN)) {
                b = e.getClickedBlock().getLocation().clone().add(0, -1, 0).getBlock();
            } else if (e.getBlockFace().equals(BlockFace.UP)) {
                b = e.getClickedBlock().getLocation().clone().add(0, 1, 0).getBlock();
            } else if (e.getBlockFace().equals(BlockFace.NORTH)) {
                b = e.getClickedBlock().getLocation().clone().add(0, 0, -1).getBlock();
            } else if (e.getBlockFace().equals(BlockFace.EAST)) {
                b = e.getClickedBlock().getLocation().clone().add(1, 0, 0).getBlock();
            } else if (e.getBlockFace().equals(BlockFace.SOUTH)) {
                b = e.getClickedBlock().getLocation().clone().add(0, 0, 1).getBlock();
            } else if (e.getBlockFace().equals(BlockFace.WEST)) {
                b = e.getClickedBlock().getLocation().clone().add(-1, 0, 0).getBlock();
            }
            if (b == null) {
                return;
            }
            try {
                FawePlayer<Object> fp = FawePlayer.wrap(e.getPlayer());
                EditSession editSession = fp.getNewEditSession();
                editSession.setBlock(new Vector(b.getLocation().getBlockX(), b.getLocation().getBlockY(), b.getLocation().getBlockZ()), new BaseBlock(_id, _data));
                editSession.flushQueue();
            } catch (Exception ignored) {}
        });
        e.setCancelled(true);
    }
}
