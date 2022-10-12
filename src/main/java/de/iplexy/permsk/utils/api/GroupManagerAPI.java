package de.iplexy.permsk.utils.api;

import de.iplexy.permsk.SkPerm;
import org.anjocaido.groupmanager.GroupManager;
import org.anjocaido.groupmanager.data.Group;
import org.anjocaido.groupmanager.data.User;
import org.anjocaido.groupmanager.dataholder.OverloadedWorldHolder;
import org.anjocaido.groupmanager.permissions.AnjoPermissionsHandler;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import java.lang.reflect.Array;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class GroupManagerAPI implements API{

    private static GroupManager groupManager;


    public static boolean hasGroupManager() {

        if (groupManager != null) return true;

        final PluginManager pluginManager = SkPerm.getInstance().getServer().getPluginManager();
        final Plugin GMplugin = pluginManager.getPlugin("GroupManager");

        if (GMplugin != null && GMplugin.isEnabled()) {
            groupManager = (GroupManager) GMplugin;
            return true;
        }
        return false;
    }

    @Override
    public void addPerm(OfflinePlayer player, String permission) {
        String uuid = player.getUniqueId().toString();
        final OverloadedWorldHolder handler = groupManager.getWorldsHolder().getDefaultWorld();
        User user = handler.getUser(uuid);
        user.addPermission(permission);
    }

    @Override
    public void addPerm(OfflinePlayer player, String permission, World world) {
        String uuid = player.getUniqueId().toString();
        final OverloadedWorldHolder handler = groupManager.getWorldsHolder().getWorldData(world.getName());
        User user = handler.getUser(uuid);
        user.addPermission(permission);
    }

    @Override
    public void addPerm(OfflinePlayer player, String permission, World world, int seconds) {
        String uuid = player.getUniqueId().toString();
        final OverloadedWorldHolder handler = groupManager.getWorldsHolder().getWorldData(world.getName());
        User user = handler.getUser(uuid);
        Instant timed = Instant.now().plus(seconds, ChronoUnit.SECONDS);
        user.addTimedPermission(permission,timed.getEpochSecond());
    }

    @Override
    public void addPerm(OfflinePlayer player, String permission, int seconds) {
        String uuid = player.getUniqueId().toString();
        final OverloadedWorldHolder handler = groupManager.getWorldsHolder().getDefaultWorld();
        User user = handler.getUser(uuid);
        Instant timed = Instant.now().plus(seconds, ChronoUnit.SECONDS);
        user.addTimedPermission(permission,timed.getEpochSecond());
    }

    @Override
    public void addPerm(String group, String permission) {
        final OverloadedWorldHolder handler = groupManager.getWorldsHolder().getDefaultWorld();
        Group group1 = handler.getGroup(group);
        group1.addPermission(permission);
    }

    @Override
    public void addPerm(String group, String permission, World world) {
        final OverloadedWorldHolder handler = groupManager.getWorldsHolder().getWorldData(world.getName());
        Group group1 = handler.getGroup(group);
        group1.addPermission(permission);
    }

    @Override
    public void addPerm(String group, String permission, World world, int seconds) {
        final OverloadedWorldHolder handler = groupManager.getWorldsHolder().getWorldData(world.getName());
        Group group1 = handler.getGroup(group);
        Instant timed = Instant.now().plus(seconds, ChronoUnit.SECONDS);
        group1.addTimedPermission(permission,timed.getEpochSecond());
    }

    @Override
    public void addPerm(String group, String permission, int seconds) {
        final OverloadedWorldHolder handler = groupManager.getWorldsHolder().getDefaultWorld();
        Group group1 = handler.getGroup(group);
        Instant timed = Instant.now().plus(seconds, ChronoUnit.SECONDS);
        group1.addTimedPermission(permission,timed.getEpochSecond());
    }

    @Override
    public void removePerm(OfflinePlayer player, String permission) {
        String uuid = player.getUniqueId().toString();
        final OverloadedWorldHolder handler = groupManager.getWorldsHolder().getDefaultWorld();
        User user = handler.getUser(uuid);
        user.removePermission(permission);
    }

    @Override
    public void removePerm(OfflinePlayer player, String permission, World world) {
        String uuid = player.getUniqueId().toString();
        final OverloadedWorldHolder handler = groupManager.getWorldsHolder().getWorldData(world.getName());
        User user = handler.getUser(uuid);
        user.removePermission(permission);
    }

    @Override
    public void removePerm(String group, String permission) {
        final OverloadedWorldHolder handler = groupManager.getWorldsHolder().getDefaultWorld();
        Group group1 = handler.getGroup(group);
        group1.removePermission(permission);
    }

    @Override
    public void removePerm(String group, String permission, World world) {
        final OverloadedWorldHolder handler = groupManager.getWorldsHolder().getWorldData(world.getName());
        Group group1 = handler.getGroup(group);
        group1.removePermission(permission);
    }

    @Override
    public String[] getPerm(OfflinePlayer player) {
        String uuid = player.getUniqueId().toString();
        final OverloadedWorldHolder handler = groupManager.getWorldsHolder().getDefaultWorld();
        User user = handler.getUser(uuid);
        return user.getPermissionList().toArray(new String[0]);
    }

    @Override
    public String[] getPerm(OfflinePlayer player, World world) {
        String uuid = player.getUniqueId().toString();
        final OverloadedWorldHolder handler = groupManager.getWorldsHolder().getWorldData(world.getName());
        User user = handler.getUser(uuid);
        return user.getPermissionList().toArray(new String[0]);
    }

    @Override
    public String[] getPerm(String group) {
        //TODO Make more efficient
        final OverloadedWorldHolder handler = groupManager.getWorldsHolder().getDefaultWorld();
        Group group1 = handler.getGroup(group);
        return group1.getPermissionList().toArray(new String[0]);
    }

    @Override
    public String[] getPerm(String group, World world) {
        final OverloadedWorldHolder handler = groupManager.getWorldsHolder().getWorldData(world.getName());
        Group group1 = handler.getGroup(group);
        return group1.getPermissionList().toArray(new String[0]);
    }

    @Override
    public void createGroup(String group) {
        final OverloadedWorldHolder handler = groupManager.getWorldsHolder().getDefaultWorld();
        handler.createGroup(group);
    }

    @Override
    public void createGroup(String group, String[] parents) {
        final OverloadedWorldHolder handler = groupManager.getWorldsHolder().getDefaultWorld();
        Group group1 = handler.createGroup(group);
        for(String parent : parents){
            if(handler.groupExists(parent))
                group1.addInherits(handler.getGroup(parent));
        }
    }

    @Override
    public void removeGroup(String group) {
        final OverloadedWorldHolder handler = groupManager.getWorldsHolder().getDefaultWorld();
        handler.removeGroup(group);
    }

    @Override
    public void addPlayerToGroup(OfflinePlayer player, String group) {
        String uuid = player.getUniqueId().toString();
        final OverloadedWorldHolder handler = groupManager.getWorldsHolder().getDefaultWorld();
        User user = handler.getUser(uuid);
        Group group1 = handler.getGroup(group);
        user.addSubGroup(group1);
    }

    @Override
    public void addPlayerToGroup(OfflinePlayer player, String group, World world) {
        String uuid = player.getUniqueId().toString();
        final OverloadedWorldHolder handler = groupManager.getWorldsHolder().getWorldData(world.getName());
        User user = handler.getUser(uuid);
        Group group1 = handler.getGroup(group);
        user.addSubGroup(group1);
    }

    @Override
    public void addPlayerToGroup(OfflinePlayer player, String group, World world, int seconds) {
        String uuid = player.getUniqueId().toString();
        final OverloadedWorldHolder handler = groupManager.getWorldsHolder().getWorldData(world.getName());
        User user = handler.getUser(uuid);
        Group group1 = handler.getGroup(group);
        Instant timed = Instant.now().plus(seconds, ChronoUnit.SECONDS);
        user.addTimedSubGroup(group1,timed.getEpochSecond());
    }

    @Override
    public void addPlayerToGroup(OfflinePlayer player, String group, int seconds) {
        String uuid = player.getUniqueId().toString();
        final OverloadedWorldHolder handler = groupManager.getWorldsHolder().getDefaultWorld();
        User user = handler.getUser(uuid);
        Group group1 = handler.getGroup(group);
        Instant timed = Instant.now().plus(seconds, ChronoUnit.SECONDS);
        user.addTimedSubGroup(group1,timed.getEpochSecond());
    }

    @Override
    public void removePlayerFromGroup(OfflinePlayer player, String group) {
        String uuid = player.getUniqueId().toString();
        final OverloadedWorldHolder handler = groupManager.getWorldsHolder().getDefaultWorld();
        User user = handler.getUser(uuid);
        Group group1 = handler.getGroup(group);
        user.removeSubGroup(group1);
    }

    @Override
    public void removePlayerFromGroup(OfflinePlayer player, String group, World world) {
        String uuid = player.getUniqueId().toString();
        final OverloadedWorldHolder handler = groupManager.getWorldsHolder().getWorldData(world.getName());
        User user = handler.getUser(uuid);
        Group group1 = handler.getGroup(group);
        user.removeSubGroup(group1);
    }

    @Override
    public OfflinePlayer[] getPlayersInGroup(String group) {
        //TODO NOT SUPPORTED
        return new OfflinePlayer[0];
    }

    @Override
    public void setGroupWeight(String group, int weight) {
        //TODO NOT SUPPORTED
    }

    @Override
    public void setGroupRank(String group, int rank) {
        //TODO NOT SUPPORTED
    }

    @Override
    public int getGroupWeight(String group) {
        //TODO NOT SUPPORTED
        return 0;
    }

    @Override
    public int getGroupRank(String group) {
        //TODO NOT SUPPORTED
        return 0;
    }

    @Override
    public void setGroupPrefix(String group, String prefix) {
        final OverloadedWorldHolder handler = groupManager.getWorldsHolder().getDefaultWorld();
        Group group1 = handler.getGroup(group);
        group1.getVariables().addVar("prefix", prefix);

    }

    @Override
    public void setGroupPrefix(String group, String prefix, World world) {
        final OverloadedWorldHolder handler = groupManager.getWorldsHolder().getWorldData(world.getName());
        Group group1 = handler.getGroup(group);
        group1.getVariables().addVar("prefix", prefix);
    }

    @Override
    public String getGroupPrefix(String group) {
        final AnjoPermissionsHandler handler = groupManager.getWorldsHolder().getDefaultWorld().getPermissionsHandler();
        return handler.getGroupPrefix(group);
    }

    @Override
    public String getGroupPrefix(String group, World world) {
        final AnjoPermissionsHandler handler = groupManager.getWorldsHolder().getWorldPermissions(world.getName());
        return handler.getGroupPrefix(group);
    }

    @Override
    public void setGroupSuffix(String group, String suffix) {
        final OverloadedWorldHolder handler = groupManager.getWorldsHolder().getDefaultWorld();
        Group group1 = handler.getGroup(group);
        group1.getVariables().addVar("suffix", suffix);
    }

    @Override
    public void setGroupSuffix(String group, String suffix, World world) {
        final OverloadedWorldHolder handler = groupManager.getWorldsHolder().getWorldData(world.getName());
        Group group1 = handler.getGroup(group);
        group1.getVariables().addVar("suffix", suffix);
    }

    @Override
    public String getGroupSuffix(String group) {
        final AnjoPermissionsHandler handler = groupManager.getWorldsHolder().getDefaultWorld().getPermissionsHandler();
        return handler.getGroupSuffix(group);
    }

    @Override
    public String getGroupSuffix(String group, World world) {
        final AnjoPermissionsHandler handler = groupManager.getWorldsHolder().getWorldPermissions(world.getName());
        return handler.getGroupSuffix(group);
    }

    @Override
    public void setPlayerPrefix(OfflinePlayer player, String prefix) {
        String uuid = player.getUniqueId().toString();
        final OverloadedWorldHolder handler = groupManager.getWorldsHolder().getDefaultWorld();
        User user = handler.getUser(uuid);
        user.getVariables().addVar("prefix", prefix);
    }

    @Override
    public void setPlayerPrefix(OfflinePlayer player, String prefix, World world) {
        String uuid = player.getUniqueId().toString();
        final OverloadedWorldHolder handler = groupManager.getWorldsHolder().getWorldData(world.getName());
        User user = handler.getUser(uuid);
        user.getVariables().addVar("prefix", prefix);
    }

    @Override
    public String getPlayerPrefix(OfflinePlayer player) {
        final AnjoPermissionsHandler handler = groupManager.getWorldsHolder().getWorldPermissionsByPlayerName(player.getName());
        return handler.getUserPrefix(player.getName());
    }

    @Override
    public String getPlayerPrefix(OfflinePlayer player, World world) {
        final AnjoPermissionsHandler handler = groupManager.getWorldsHolder().getWorldPermissions(world.getName());
        return handler.getUserPrefix(player.getName());

    }

    @Override
    public void setPlayerSuffix(OfflinePlayer player, String suffix) {
        String uuid = player.getUniqueId().toString();
        final OverloadedWorldHolder handler = groupManager.getWorldsHolder().getDefaultWorld();
        User user = handler.getUser(uuid);
        user.getVariables().addVar("suffix", suffix);
    }

    @Override
    public void setPlayerSuffix(OfflinePlayer player, String suffix, World world) {
        String uuid = player.getUniqueId().toString();
        final OverloadedWorldHolder handler = groupManager.getWorldsHolder().getWorldData(world.getName());
        User user = handler.getUser(uuid);
        user.getVariables().addVar("suffix", suffix);
    }

    @Override
    public String getPlayerSuffix(OfflinePlayer player) {
        final AnjoPermissionsHandler handler = groupManager.getWorldsHolder().getWorldPermissionsByPlayerName(player.getName());
        return handler.getUserPrefix(player.getName());
    }

    @Override
    public String getPlayerSuffix(OfflinePlayer player, World world) {
        final AnjoPermissionsHandler handler = groupManager.getWorldsHolder().getWorldPermissions(world.getName());
        return handler.getUserPrefix(player.getName());
    }

    @Override
    public String getPrimaryGroup(OfflinePlayer player) {
        //TODO ADD FUNCTION
        return null;
    }


    @Override
    public void setPrimaryGroup(OfflinePlayer player, String group) {

    }

    @Override
    public ArrayList<String> getInheritedGroups(OfflinePlayer player) {
        //Todo Test
        String uuid = player.getUniqueId().toString();
        final OverloadedWorldHolder handler = groupManager.getWorldsHolder().getDefaultWorld();
        User user = handler.getUser(uuid);
        return user.subGroupListStringCopy();
    }
}
