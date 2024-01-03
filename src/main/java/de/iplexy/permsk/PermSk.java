package de.iplexy.permsk;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import de.iplexy.permsk.api.PermissionApi;
import de.iplexy.permsk.enums.PermissionPlugin;
import de.iplexy.permsk.utils.BStats;
import de.iplexy.permsk.utils.UpdateChecker;
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

    @Getter(AccessLevel.PUBLIC)
    private static String permissionPlugin;

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
        BStats.loadStats();
        UpdateChecker.checkForUpdate(this.getPluginMeta().getVersion());
        registerListener();
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

        for(PermissionPlugin plugin : PermissionPlugin.values()){
            if(Bukkit.getPluginManager().getPlugin(plugin.getName()) != null){
                loadApi(plugin.getName(), plugin.getApiClass());
                break;
            }
        }
    }

    private void loadApi(String permPlugin, String apiClass) {
        try {
            addon.loadClasses("de.iplexy.permsk","elements");
            permissionApi = (PermissionApi) Class.forName("de.iplexy.permsk.api." + apiClass).getConstructor().newInstance();
            permissionPlugin = permPlugin;
            sendConsoleMessage("<green>Loaded dependency: " + permPlugin + "</green>");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void registerListener() {
        Bukkit.getPluginManager().registerEvents(new UpdateChecker(this), this);
    }


}
