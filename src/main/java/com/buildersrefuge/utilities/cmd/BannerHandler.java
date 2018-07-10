package com.buildersrefuge.utilities.cmd;

import com.buildersrefuge.utilities.Main;
import com.buildersrefuge.utilities.util.BannerGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class BannerHandler implements Listener, CommandExecutor {
    private final Main plugin;

    public BannerHandler(Main main) {
        plugin = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Can only be run by a player!");
            return false;
        }
        Player p = (Player) sender;
        if (p.hasPermission("builders.util.banner")) {
            p.openInventory(plugin.getBannerGui().generateStartInv());
        } else {
            sender.sendMessage(plugin.getText("no-permission"));
        }
        return true;
    }
}
