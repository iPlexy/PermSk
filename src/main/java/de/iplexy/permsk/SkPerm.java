package de.iplexy.permsk;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import de.iplexy.permsk.utils.api.API;
import net.milkbowl.vault.permission.Permission;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.DrilldownPie;
import org.bstats.charts.SimplePie;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SkPerm extends JavaPlugin {

    private static Permission perms;
    private static API api;
    SkPerm instance;
    SkriptAddon addon;
    private final String prefix = ChatColor.translateAlternateColorCodes('&', "&7[&bPermSk&7] ");

    public static API getAPI() {
        return api;
    }

    public static Permission getPerms() {
        return perms;
    }

    @Override
    public void onDisable() {
        sendConsoleMessage("Unloaded successfully");
    }

    @Override
    public void onEnable() {
        instance = this;
        addon = Skript.registerAddon(this);
        if ((Bukkit.getPluginManager().getPlugin("Skript") != null) && (Skript.isAcceptRegistrations())) {
            try {
                addon.loadClasses("de.iplexy.permsk.misc", "elements");
                sendConsoleMessage("&7[&bSkript&7]&a Dependency found, loading Skript syntaxes");
            } catch (Exception e) {
                sendConsoleMessage("&cLoading error, try restarting your server");
                Bukkit.getPluginManager().disablePlugin(this);
                return;
            }
            if (Bukkit.getPluginManager().getPlugin("Vault") != null) {
                try {
                    setupPermissions();
                    addon.loadClasses("de.iplexy.permsk.vault", "elements");
                    sendConsoleMessage("&7[&bVault&7] &aDependency found, Vault syntaxes loaded");
                } catch (IOException e) {
                    sendConsoleMessage("&7[&bVault&7] &cError loading Vault syntaxes");
                }
            } else {
                sendConsoleMessage("&7[&eVault&7]&c Dependency not found, ignoring Vault syntaxes");
            }
            if (Bukkit.getPluginManager().getPlugin("PermissionsEx") != null) {
                loadApi("PermissionsEX", "PexAPI");
            } else if (Bukkit.getPluginManager().getPlugin("LuckPerms") != null) {
                loadApi("LuckPerms", "LuckAPI");
            } else if (Bukkit.getPluginManager().getPlugin("UltraPermissions") != null) {
                loadApi("UltraPermissions", "UltraAPI");
            } else {
                sendConsoleMessage("&7[&ePermPlugin&7]&e No permission plugin found, ignoring PermPlugin syntaxes");
            }
            sendConsoleMessage("&aAdd-on loaded successfully");
        } else {
            sendConsoleMessage("&c[Skript] Dependency was not found, plugin disabling");
            sendConsoleMessage(Skript.isAcceptRegistrations() + "");
            Bukkit.getPluginManager().disablePlugin(this);
        }
        loadStats();
    }

    private void sendConsoleMessage(String message) {
        Bukkit.getConsoleSender().sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', message));
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }

    private void loadApi(String permPlugin, String API) {
        try {
            addon.loadClasses("de.iplexy.permsk.plugins", "elements");
            api = (API) Class.forName("de.iplexy.permsk.utils.api." + API).newInstance();
            permPluginFound(permPlugin);
        } catch (Exception e) {
            permPluginLoadingError(permPlugin);
            throw new RuntimeException(e);
        }
    }

    private void permPluginFound(String plugin) {
        String plug = ChatColor.translateAlternateColorCodes('&', "&7[&b" + plugin + "&7] ");
        sendConsoleMessage(plug + "&aDependency found, permission plugin syntaxes loaded");
    }

    private void permPluginLoadingError(String plugin) {
        String plug = ChatColor.translateAlternateColorCodes('&', "&7[&b" + plugin + "&7] ");
        sendConsoleMessage(plug + "&aLoading error, try restarting your server");
    }

    private void loadStats(){
        Metrics metrics = new Metrics(this, 16435);
        metrics.addCustomChart(new SimplePie("skript_version", () -> Skript.getVersion().toString()));
        sendConsoleMessage("§7[§bbStats§7] Sucessfully loaded");
    }

}
