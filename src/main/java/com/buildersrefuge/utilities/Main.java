package com.buildersrefuge.utilities;

import com.buildersrefuge.utilities.cmd.BannerHandler;
import com.buildersrefuge.utilities.cmd.ColorHandler;
import com.buildersrefuge.utilities.cmd.CommandHandler;
import com.buildersrefuge.utilities.cmd.SecretBlockHandler;
import com.buildersrefuge.utilities.listeners.*;
import com.buildersrefuge.utilities.managers.ToggleManager;
import com.buildersrefuge.utilities.managers.NoClipManager;
import com.buildersrefuge.utilities.util.BannerGUI;
import com.buildersrefuge.utilities.util.ColorGUI;
import com.buildersrefuge.utilities.util.SecretBlockGUI;
import com.buildersrefuge.utilities.util.ToggleGUI;
import org.bukkit.ChatColor;
import com.massivestats.MassiveStats;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public class Main extends JavaPlugin implements Listener {
    public static final Random RANDOM = new Random();
    public static String version;
    private BannerGUI bannerGui;
    private ColorGUI colorGui;
    private SecretBlockGUI secretBlockGui;
    private ToggleManager toggleManager;
    private NoClipManager noClipManager;
    private ToggleGUI toggleGui;
    private GlobalOptionsListener globalOptionsListener;

    public void onEnable() {
        String a = this.getServer().getClass().getPackage().getName();
        version = a.substring(a.lastIndexOf('.') + 1);

        PluginManager pm = getServer().getPluginManager();
        CommandHandler commandHandler = new CommandHandler(this);

        globalOptionsListener = new GlobalOptionsListener(this);

        saveDefaultConfig();
        reload();
        noClipManager = new NoClipManager(this);
        toggleManager = new ToggleManager(this);

        pm.registerEvents(new BannerInventoryListener(this), this);
        pm.registerEvents(new ColorInventoryListener(this), this);
        pm.registerEvents(new SecretBlocksInventoryListener(this), this);
        pm.registerEvents(new ToggleInventoryListener(this), this);

        pm.registerEvents(new SecretBlockListener(this), this);
        pm.registerEvents(new PlayerOptionsListener(this), this);
        pm.registerEvents(globalOptionsListener, this);
        pm.registerEvents(this, this);

        getCommand("builderutilities").setExecutor(commandHandler);
        getCommand("banner").setExecutor(new BannerHandler(this));
        getCommand("armorcolor").setExecutor(new ColorHandler(this));
        getCommand("blocks").setExecutor(new SecretBlockHandler(this));
        getCommand("n").setExecutor(commandHandler);
        getCommand("nc").setExecutor(commandHandler);
        getCommand("/1").setExecutor(commandHandler);
        getCommand("/2").setExecutor(commandHandler);
        getCommand("/cuboid").setExecutor(commandHandler);
        getCommand("/convex").setExecutor(commandHandler);
        getCommand("/s").setExecutor(commandHandler);
        getCommand("/r").setExecutor(commandHandler);
        getCommand("/f").setExecutor(commandHandler);
        getCommand("/pa").setExecutor(commandHandler);
        getCommand("/c").setExecutor(commandHandler);
        getCommand("ws").setExecutor(commandHandler);
        getCommand("fs").setExecutor(commandHandler);
        getCommand("af").setExecutor(commandHandler);
        getCommand("advfly").setExecutor(commandHandler);
        getCommand("/derot").setExecutor(commandHandler);
        getCommand("/scale").setExecutor(commandHandler);
        getCommand("/twist").setExecutor(commandHandler);
        getCommand("butil").setExecutor(commandHandler);

        new MassiveStats(this);

        bannerGui = new BannerGUI(this);
        colorGui = new ColorGUI(this);
        secretBlockGui = new SecretBlockGUI(this);
        toggleGui = new ToggleGUI(this);
    }

    public void reload() {
        reloadConfig();
        globalOptionsListener.reload();
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        noClipManager.disable(p);
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        if (event.getCause() == null) {
            return;
        }
        if (event.getCause().equals(PlayerTeleportEvent.TeleportCause.SPECTATE)) {
            if (!event.getPlayer().hasPermission("builders.util.tpgm3")) {
                event.setCancelled(true);
            }
        }
    }

    public String getTexture(String path) {
        return getConfig().getString("heads." + path);
    }

    public String getText(String path, String... replacements) {
        String text = getConfig().getString("messages." + path);
        if (text == null) {
            return ChatColor.RED + "Missing message " + ChatColor.YELLOW + path;
        }
        for (int i = 0; i + 1 < replacements.length; i+=2) {
            text = text.replace("%" + replacements[i] + "%", replacements[i+1]);
        }

        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public BannerGUI getBannerGui() {
        return bannerGui;
    }

    public ColorGUI getColorGui() {
        return colorGui;
    }

    public SecretBlockGUI getSecretBlockGui() {
        return secretBlockGui;
    }

    public ToggleGUI getToggleGui() {
        return toggleGui;
    }

    public ToggleManager getToggleManager() {
        return toggleManager;
    }

    public NoClipManager getNoClipManager() {
        return noClipManager;
    }

    public String getServerVersion() {
        return version;
    }
}
