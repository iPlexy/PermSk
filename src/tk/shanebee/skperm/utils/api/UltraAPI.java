package tk.shanebee.skperm.utils.api;

import me.TechsCode.UltraPermissions.UltraPermissions;
import me.TechsCode.UltraPermissions.UltraPermissionsAPI;
import me.TechsCode.UltraPermissions.storage.objects.Group;
import me.TechsCode.UltraPermissions.storage.objects.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;

import java.sql.Timestamp;
import java.time.Instant;

public class UltraAPI implements API {

    /**
     * Load API
     */
    private UltraPermissionsAPI api = UltraPermissions.getAPI();

    public void addPerm(OfflinePlayer player, String permission) { // TODO come back to this
        User user = api.getUsers().uuid(player.getUniqueId());
        if (user.getPermissions().has(permission)) return;
        user.newPermission(permission).create();
    }

    public void addPerm(OfflinePlayer player, String permission, World world) {
        User user = api.getUsers().uuid(player.getUniqueId());
        if (user.getPermissions().worlds(true, world.getName()).has(permission)) {
            return;
        }
        user.newPermission(permission).setWorld(world.getName()).create();
    }

    public void addPerm(OfflinePlayer player, String permission, World world, int seconds) {
        User user = api.getUsers().uuid(player.getUniqueId());
        if (user.getPermissions().worlds(true, world.getName()).has(permission)) return;
        Timestamp time = Timestamp.from(Instant.now().plusSeconds(seconds));
        user.newPermission(permission).setWorld(world.getName()).setExpiration(time.getTime()).create();
    }

    public void addPerm(OfflinePlayer player, String permission, int seconds) {
        User user = api.getUsers().uuid(player.getUniqueId());
        if (user.getPermissions().has(permission)) return;
        Timestamp time = Timestamp.from(Instant.now().plusSeconds(seconds));
        user.newPermission(permission).setExpiration(time.getTime()).create();
    }

    public void addPerm(String group, String permission) {
        Group groupT = api.getGroups().name(group);
        if (groupT.getPermissions().has(permission)) return;
        groupT.newPermission(permission).create();
    }

    public void addPerm(String group, String permission, World world) {
        Group groupT = api.getGroups().name(group);
        if (groupT.getPermissions().worlds(true, world.getName()).has(permission)) return;
        groupT.newPermission(permission).setWorld(world.getName()).create();
    }

    public void addPerm(String group, String permission, World world, int seconds) {
        Group groupT = api.getGroups().name(group);
        if (groupT.getPermissions().worlds(true, world.getName()).has(permission)) return;
        Timestamp time = Timestamp.from(Instant.now().plusSeconds(seconds));
        groupT.newPermission(permission).setWorld(world.getName()).setExpiration(time.getTime()).create();
    }

    public void addPerm(String group, String permission, int seconds) {
        Group groupT = api.getGroups().name(group);
        if (groupT.getPermissions().has(permission)) return;
        Timestamp time = Timestamp.from(Instant.now().plusSeconds(seconds));
        groupT.newPermission(permission).setExpiration(time.getTime()).create();
    }

    public void removePerm(OfflinePlayer player, String permission) {}

    public void removePerm(OfflinePlayer player, String permission, World world) {}

    public void removePerm(String group, String permission) {}

    public void removePerm(String group, String permission, World world) {}

    public String[] getPerm(OfflinePlayer player) { return null;}

    public String[] getPerm(OfflinePlayer player, World world) {return null;}

    public String[] getPerm(String group) {return null;}

    public String[] getPerm(String group, World world) {return null;}

    public void createGroup(String group) {}

    public void createGroup(String group, String[] parents) {}

    public void removeGroup(String group) {}

    public void addPlayerToGroup(OfflinePlayer player, String group) {}

    public void addPlayerToGroup(OfflinePlayer player, String group, World world) {}

    public void addPlayerToGroup(OfflinePlayer player, String group, World world, int seconds) {}

    public void addPlayerToGroup(OfflinePlayer player, String group, int seconds) {}

    public void removePlayerFromGroup(OfflinePlayer player, String group) {}

    public void removePlayerFromGroup(OfflinePlayer player, String group, World world) {}

    public OfflinePlayer[] getPlayersInGroup(String group) {return null;}

    public void setGroupWeight(String group, int weight) {}

    public void setGroupRank(String group, int rank) {}

    public int getGroupWeight(String group) {return 0;}

    public int getGroupRank(String group) {return 0;}

    public void setGroupPrefix(String group, String prefix) {}

    public void setGroupPrefix(String group, String prefix, World world) {}

    public String getGroupPrefix(String group) {return null;}

    public String getGroupPrefix(String group, World world) {return null;}

    public void setGroupSuffix(String group, String suffix) {}

    public void setGroupSuffix(String group, String suffix, World world) {}

    public String getGroupSuffix(String group) {return null;}

    public String getGroupSuffix(String group, World world) {return null;}

    private void sendDebug(String msg) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
    }
}
