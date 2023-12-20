package de.iplexy.permsk;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import de.iplexy.permsk.permissionApi.PermissionApi;
import lombok.AccessLevel;
import lombok.Getter;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class PermSk extends JavaPlugin {

    @Getter(AccessLevel.PUBLIC)
    private static PermSk instance;

    @Getter(AccessLevel.PUBLIC)
    private static PermissionApi permissionApi;


    @Getter(AccessLevel.PUBLIC)
    private static SkriptAddon addon;

    public static void sendConsoleMessage(String message) {
        Bukkit.getConsoleSender().sendMessage(MiniMessage.miniMessage().deserialize("<gray>[<green>PermSk<gray>] <white>" + message + "</white>"));
    }

    @Override
    public void onEnable() {

        //Init variables
        addon = Skript.registerAddon(this);
        instance = this;
        loadDependencies();
        sendConsoleMessage("Plugin enabled!");
    }

    @Override
    public void onDisable() {
        sendConsoleMessage("Plugin disabled!");
    }

    private void loadDependencies() {
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            sendConsoleMessage("<gold>Vault not found, ignoring syntaxes</gold>");
        }else{
            sendConsoleMessage("<green>Loaded dependency: Vault</green>");
        }

        if (Bukkit.getPluginManager().getPlugin("LuckPerms") != null) {
            loadApi("LuckPerms", "LuckApi");
        } else if (Bukkit.getPluginManager().getPlugin("PermissionsEx") != null) {
            loadApi("PermissionsEx", "PexApi");
        } else if (Bukkit.getPluginManager().getPlugin("GroupManager") != null) {
            loadApi("GroupManager", "GroupManagerApi");
        } else if (Bukkit.getPluginManager().getPlugin("UltimatePermisions") != null) {
            loadApi("UltimatePermisions", "UltimateApi");
        }
    }

    private void loadApi(String permPlugin, String apiClass) {
        try {
            permissionApi = (PermissionApi) Class.forName("de.iplexy.permsk.permissionApi." + apiClass).getConstructor().newInstance();
            sendConsoleMessage("<green>Loaded dependency: " + permPlugin + "</green>");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
