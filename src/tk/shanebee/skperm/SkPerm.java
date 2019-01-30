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
                Bukkit.getConsoleSender().sendMessage(prefix + ChatColor.GREEN + "[Skript] Dependency found");
            } catch (Exception e) {
                e.printStackTrace();
                Bukkit.getPluginManager().disablePlugin(this);
            }
            if (Bukkit.getPluginManager().getPlugin("Vault") != null) {
                setupPermissions();
                Bukkit.getConsoleSender().sendMessage(prefix + ChatColor.GREEN + "[Vault] Dependency found");
                Bukkit.getConsoleSender().sendMessage(prefix + ChatColor.GREEN + "Loaded successfully");
            } else {
                Bukkit.getConsoleSender().sendMessage(prefix + ChatColor.RED + "[Vault] Dependency not found, plugin disabling");
                Bukkit.getPluginManager().disablePlugin(this);
            }
        } else {
            getLogger().info(ChatColor.RED + "[Skript] Dependency was not found, plugin disabling");
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(prefix + "Unloaded successfully");
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }

}
