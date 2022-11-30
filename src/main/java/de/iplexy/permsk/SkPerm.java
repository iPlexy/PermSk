package de.iplexy.permsk;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import de.iplexy.permsk.utils.UpdateChecker;
import de.iplexy.permsk.utils.api.API;
import de.iplexy.permsk.utils.api.GroupManagerAPI;
import net.milkbowl.vault.permission.Permission;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.DrilldownPie;
import org.bstats.charts.SimplePie;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SkPerm extends JavaPlugin {

    private static Permission perms;
    private static API api;
    public static final String prefix = ChatColor.translateAlternateColorCodes('&', "&7[&bPermSk&7] ");
    private static SkPerm instance;

    public static String PermissionPlugin;
    SkriptAddon addon;

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
        checkUpdate(getDescription().getVersion());
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
                PermissionPlugin = "PermissionsEX";
            } else if (Bukkit.getPluginManager().getPlugin("LuckPerms") != null) {
                loadApi("LuckPerms", "LuckAPI");
                PermissionPlugin = "LuckPerms";
            } else if (Bukkit.getPluginManager().getPlugin("UltraPermissions") != null) {
                loadApi("UltraPermissions", "UltraAPI");
                PermissionPlugin = "UltraPermissions";
            } else if (GroupManagerAPI.hasGroupManager()) {
                loadApi("GroupManager", "GroupManagerAPI");
                PermissionPlugin = "GroupManager";
            }  else {
                sendConsoleMessage("&7[&ePermPlugin&7]&e No permission plugin found, ignoring PermPlugin syntaxes");
                PermissionPlugin = "none";
            }
            sendConsoleMessage("&aAdd-on loaded successfully");
        } else {
            sendConsoleMessage("&c[Skript] Dependency was not found, plugin disabling");
            sendConsoleMessage(Skript.isAcceptRegistrations() + "");
            Bukkit.getPluginManager().disablePlugin(this);
        }
        loadStats();
    }

    public static void sendConsoleMessage(String message) {
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

    private void loadStats() {
        Metrics metrics = new Metrics(this, 16435);
        metrics.addCustomChart(new SimplePie("skript_version", () -> Skript.getVersion().toString()));
        metrics.addCustomChart(new DrilldownPie("permission_plugin", () -> {
            Map<String, Map<String, Integer>> map = new HashMap<>();
            Map<String, Integer> entry = new HashMap<>();
            entry.put(PermissionPlugin, 1);
            map.put(PermissionPlugin, entry);
            return map;
        }));
        sendConsoleMessage("§7[§bbStats§7] Sucessfully loaded");
    }

    public static SkPerm getInstance(){
        return instance;
    }

    private void checkUpdate(String version) {
        if (true) {
            UpdateChecker.checkForUpdate(version);
        } else {
            sendConsoleMessage("Update checker disabled... will not check for update!");
        }
    }
}
