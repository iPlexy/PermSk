package tk.shanebee.skperm;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import tk.shanebee.skperm.utils.api.API;

public class SkPerm extends JavaPlugin {

    private static Permission perms;
    private static API api;
    private SkriptAddon addon;
    private String prefix = ChatColor.translateAlternateColorCodes('&', "&7[&bSk-Perm&7] ");

    @Override
    public void onEnable() {
        if ((Bukkit.getPluginManager().getPlugin("Skript") != null) && (Skript.isAcceptRegistrations())) {
            addon = Skript.registerAddon(this);
            try {
                addon.loadClasses("tk.shanebee.skperm.vault", "elements");
                sendConsoleMessage("&7[&bSkript&7]&a Dependency found, loading Skript syntaxes");
            } catch (Exception e) {
                sendConsoleMessage("&cLoading error, try restarting your server");
                Bukkit.getPluginManager().disablePlugin(this);
                return;
            }
            if (Bukkit.getPluginManager().getPlugin("Vault") != null) {
                setupPermissions();
                sendConsoleMessage("&7[&bVault&7] &aDependency found, Vault syntaxes loaded");
            } else {
                sendConsoleMessage("&7[&eVault&7]&c Dependency not found, plugin disabling");
                Bukkit.getPluginManager().disablePlugin(this);
                return;
            }
            if (Bukkit.getPluginManager().getPlugin("PermissionsEX") != null) {
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
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        sendConsoleMessage("Unloaded successfully");
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }

    private void sendConsoleMessage(String message) {
        Bukkit.getConsoleSender().sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', message));
    }

    private void permPluginFound(String plugin) {
        String plug = ChatColor.translateAlternateColorCodes('&', "&7[&b" + plugin + "&7] ");
        sendConsoleMessage(plug + "&aDependency found, permission plugin syntaxes loaded");
    }

    private void permPluginLoadingError(String plugin) {
        String plug = ChatColor.translateAlternateColorCodes('&', "&7[&b" + plugin + "&7] ");
        sendConsoleMessage(plug + "&aLoading error, try restarting your server");
    }

    private void loadApi(String permPlugin, String API) {
        try {
            addon.loadClasses("tk.shanebee.skperm.permPlugins", "elements");
            api = (API) Class.forName("tk.shanebee.skperm.utils.api." + API).newInstance();
            permPluginFound(permPlugin);
        } catch (Exception e) {
            permPluginLoadingError(permPlugin);
        }
    }

    public static API getAPI() {
        return api;
    }

    public static Permission getPerms(){
        return perms;
    }

}
