package tk.shanebee.skperm;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class SkPerm extends JavaPlugin {

    public static Permission perms = null;
    private String prefix = ChatColor.translateAlternateColorCodes('&', "&7[&bSk-Perm&7] ");

    @Override
    public void onEnable() {
        SkriptAddon addon = Skript.registerAddon(this);
        if ((Bukkit.getPluginManager().getPlugin("Skript") != null) && (Skript.isAcceptRegistrations())) {
            try {
                addon.loadClasses("tk.shanebee.skperm", "elements");
                sendConsoleMessage(prefix + ChatColor.GREEN + "[Skript] Dependency found");
            } catch (Exception e) {
                e.printStackTrace();
                Bukkit.getPluginManager().disablePlugin(this);
            }
            if (Bukkit.getPluginManager().getPlugin("Vault") != null) {
                setupPermissions();
                sendConsoleMessage(prefix + ChatColor.GREEN + "[Vault] Dependency found");
            } else {
                sendConsoleMessage(prefix + ChatColor.RED + "[Vault] Dependency not found, plugin disabling");
                Bukkit.getPluginManager().disablePlugin(this);
            }
            if (Bukkit.getPluginManager().getPlugin("PermissionsEX") != null) {
                try {
                    addon.loadClasses("tk.shanebee.skperm.pex", "elements");
                    sendConsoleMessage(prefix + ChatColor.GREEN + "[PEX] Dependency found, pex syntax loaded");
                } catch (Exception e) {
                    sendConsoleMessage(prefix + ChatColor.RED + "[PEX] Loading error, try restart your server");
                }
            } else {
                sendConsoleMessage(prefix + ChatColor.YELLOW + "[PEX] Dependency not found, ignoring pex syntaxes");
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

}
