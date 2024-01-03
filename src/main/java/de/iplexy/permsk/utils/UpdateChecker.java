package de.iplexy.permsk.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import de.iplexy.permsk.PermSk;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.function.Consumer;

public class UpdateChecker implements Listener {

    private static String UPDATE_VERSION;

    public static void checkForUpdate(String pluginVersion) {
        PermSk.sendConsoleMessage("Checking for update...");
        getVersion(version -> {
            if (version.equalsIgnoreCase(pluginVersion)) {
                PermSk.sendConsoleMessage("<green>Plugin is up to date!");
            } else {
                PermSk.sendConsoleMessage("<red>Plugin is not up to date!");
                PermSk.sendConsoleMessage(" - Current version: <red>v"+ pluginVersion);
                PermSk.sendConsoleMessage(" - Available update: <green>v"+ version);
                PermSk.sendConsoleMessage(" - Download available at: https://github.com/iPlexy/PermSk/releases");
                UPDATE_VERSION = version;
            }
        });
    }

    private static void getVersion(final Consumer<String> consumer) {
        try {
            URL url = new URL("https://api.github.com/repos/iPlexy/PermSk/releases/latest");
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            JsonObject jsonObject = new Gson().fromJson(reader, JsonObject.class);
            String tag_name = jsonObject.get("tag_name").getAsString();
            consumer.accept(tag_name);
        } catch (IOException e) {
            PermSk.sendConsoleMessage("&cChecking for update failed!");
        }
    }

    private final PermSk plugin;

    public UpdateChecker(PermSk plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    private void onJoin(PlayerJoinEvent event) {
        if (UPDATE_VERSION == null) return;

        Player player = event.getPlayer();
        if (!player.hasPermission("permsk.update.check")) return;

        Bukkit.getScheduler().runTaskLater(plugin, bukkitTask -> {
            player.sendMessage(Component.text(ChatColor.translateAlternateColorCodes('&',"&7[&bPerm&3Sk&7] update available: &a" + UPDATE_VERSION)));
            player.sendMessage(Component.text(ChatColor.translateAlternateColorCodes('&',"&7[&bPerm&3Sk&7] download at &bhttps://github.com/iPlexy/PermSk/releases")));
        }, 60);
    }

}