package tk.shanebee.skperm;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import tk.shanebee.skperm.utils.api.API;
import tk.shanebee.skperm.utils.api.LuckAPI;
import tk.shanebee.skperm.utils.api.PexAPI;
import tk.shanebee.skperm.utils.api.UltraAPI;

public class SkPerm extends JavaPlugin {

    public static Permission perms = null;
    private static API api;
    private String prefix = ChatColor.translateAlternateColorCodes('&', "&7[&bSk-Perm&7] ");

    @Override
    public void onEnable() {

        if ((Bukkit.getPluginManager().getPlugin("Skript") != null) && (Skript.isAcceptRegistrations())) {
            SkriptAddon addon = Skript.registerAddon(this);
            try {
                addon.loadClasses("tk.shanebee.skperm.vault", "elements");
                sendConsoleMessage(prefix + ChatColor.GREEN + "[Skript] Dependency found");
            } catch (Exception e) {
                sendConsoleMessage(prefix + ChatColor.RED + "Loading error, try restarting your server");
                Bukkit.getPluginManager().disablePlugin(this);
                return;
            }
            if (Bukkit.getPluginManager().getPlugin("Vault") != null) {
                setupPermissions();
                sendConsoleMessage(prefix + ChatColor.GREEN + "[Vault] Dependency found, Vault syntaxes loaded");
            } else {
                sendConsoleMessage(prefix + ChatColor.RED + "[Vault] Dependency not found, plugin disabling");
                Bukkit.getPluginManager().disablePlugin(this);
            }
            if (Bukkit.getPluginManager().getPlugin("PermissionsEX") != null) {
                try {
                    addon.loadClasses("tk.shanebee.skperm.permPlugins", "elements");
                    api = (API) Class.forName(PexAPI.class.getName()).newInstance();
                    permPluginFound("PEX");
                } catch (Exception e) {
                    permPluginLoadingError("PEX");
                }
            } else if (Bukkit.getPluginManager().getPlugin("LuckPerms") != null) {
                try {
                    addon.loadClasses("tk.shanebee.skperm.permPlugins", "elements");
                    api = (API) Class.forName(LuckAPI.class.getName()).newInstance();
                    permPluginFound("LuckPerms");
                } catch (Exception e) {
                    permPluginLoadingError("LuckPerms");
                }
            } else if (Bukkit.getPluginManager().getPlugin("UltraPermissions") != null) {
                try {
                    addon.loadClasses("tk.shanebee.skperm.permPlugins", "elements");
                    api = (API) Class.forName(UltraAPI.class.getName()).newInstance();
                    permPluginFound("UltraPermissions");
                } catch (Exception e) {
                    permPluginLoadingError("UltraPermissions");
                }
            } else {
                sendConsoleMessage(prefix + ChatColor.YELLOW + "[PermPlugin] Dependency not found, ignoring PermPlugin syntaxes");
            }
            sendConsoleMessage(prefix + ChatColor.GREEN + "Loaded successfully");
        } else {
            getLogger().info(ChatColor.RED + "[Skript] Dependency was not found, plugin disabling");
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        sendConsoleMessage(prefix + "Unloaded successfully");
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }

    private void sendConsoleMessage(String message) {
        Bukkit.getConsoleSender().sendMessage(message);
    }

    private void permPluginFound(String plugin) {
        String plug = ChatColor.translateAlternateColorCodes('&', "&7[&b" + plugin + "&7] ");
        Bukkit.getConsoleSender().sendMessage(prefix + plug + ChatColor.GREEN + "Dependency found, permission plugin syntaxes loaded");
    }

    private void permPluginLoadingError(String plugin) {
        String plug = ChatColor.translateAlternateColorCodes('&', "&7[&b" + plugin + "&7] ");
        Bukkit.getConsoleSender().sendMessage(prefix + plug + ChatColor.RED + "Loading error, try restarting your server");
    }

    public static API getAPI() {
        return api;
    }

}
