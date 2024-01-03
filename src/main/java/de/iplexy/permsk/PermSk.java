package de.iplexy.permsk;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import de.iplexy.permsk.api.PermissionApi;
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

    private static final String[] permissionPlugins = {"LuckPerms", "PermissionsEx", "GroupManager", "UltimatePermissions"};


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

        for(String plugin : permissionPlugins){
            if(Bukkit.getPluginManager().getPlugin(plugin) != null){
                loadApi("LuckPerms", "LuckApi");
                break;
            }
        }
    }

    private void loadApi(String permPlugin, String apiClass) {
        try {
            permissionApi = (PermissionApi) Class.forName("de.iplexy.permsk.api." + apiClass).getConstructor().newInstance();
            sendConsoleMessage("<green>Loaded dependency: " + permPlugin + "</green>");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
