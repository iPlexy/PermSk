package de.iplexy.permsk.api;

import de.iplexy.permsk.PermSk;
import org.anjocaido.groupmanager.GroupManager;
import org.anjocaido.groupmanager.data.Group;
import org.anjocaido.groupmanager.data.User;
import org.anjocaido.groupmanager.dataholder.OverloadedWorldHolder;
import org.anjocaido.groupmanager.permissions.AnjoPermissionsHandler;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class GroupManagerApi implements PermissionApi {

    private final GroupManager groupManager = (GroupManager) PermSk.getInstance().getServer().getPluginManager().getPlugin("GroupManager");

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
        user.addTimedPermission(permission, timed.getEpochSecond());
    }

    @Override
    public void addPerm(OfflinePlayer player, String permission, int seconds) {
        String uuid = player.getUniqueId().toString();
        final OverloadedWorldHolder handler = groupManager.getWorldsHolder().getDefaultWorld();
        User user = handler.getUser(uuid);
        Instant timed = Instant.now().plus(seconds, ChronoUnit.SECONDS);
        user.addTimedPermission(permission, timed.getEpochSecond());
    }

    @Override
    public void addPerm(String groupName, String permission) {
        final OverloadedWorldHolder handler = groupManager.getWorldsHolder().getDefaultWorld();
        Group group = handler.getGroup(groupName);
        group.addPermission(permission);
    }

    @Override
    public void addPerm(String groupName, String permission, World world) {
        final OverloadedWorldHolder handler = groupManager.getWorldsHolder().getWorldData(world.getName());
        Group group = handler.getGroup(groupName);
        group.addPermission(permission);
    }

    @Override
    public void addPerm(String groupName, String permission, World world, int seconds) {
        final OverloadedWorldHolder handler = groupManager.getWorldsHolder().getWorldData(world.getName());
        Group group = handler.getGroup(groupName);
        Instant timed = Instant.now().plus(seconds, ChronoUnit.SECONDS);
        group.addTimedPermission(permission, timed.getEpochSecond());
    }

    @Override
    public void addPerm(String groupName, String permission, int seconds) {
        final OverloadedWorldHolder handler = groupManager.getWorldsHolder().getDefaultWorld();
        Group group = handler.getGroup(groupName);
        Instant timed = Instant.now().plus(seconds, ChronoUnit.SECONDS);
        group.addTimedPermission(permission, timed.getEpochSecond());
    }

    @Override
    public void removePerm(OfflinePlayer player, String permission) {
        final OverloadedWorldHolder handler = groupManager.getWorldsHolder().getDefaultWorld();
        User user = handler.getUser(player.getUniqueId().toString());
        user.removePermission(permission);
    }

    @Override
    public void removePerm(OfflinePlayer player, String permission, World world) {
        final OverloadedWorldHolder handler = groupManager.getWorldsHolder().getWorldData(world.getName());
        User user = handler.getUser(player.getUniqueId().toString());
        user.removePermission(permission);
    }

    @Override
    public void removePerm(String groupName, String permission) {
        final OverloadedWorldHolder handler = groupManager.getWorldsHolder().getDefaultWorld();
        Group group = handler.getGroup(groupName);
        group.removePermission(permission);
    }

    @Override
    public void removePerm(String groupName, String permission, World world) {
        final OverloadedWorldHolder handler = groupManager.getWorldsHolder().getWorldData(world.getName());
        Group group = handler.getGroup(groupName);
        group.removePermission(permission);
    }

    @Override
    public List<String> getPerms(OfflinePlayer player) {
        final OverloadedWorldHolder handler = groupManager.getWorldsHolder().getDefaultWorld();
        User user = handler.getUser(player.getUniqueId().toString());
        return user.getPermissionList();
    }

    @Override
    public List<String> getPerms(OfflinePlayer player, World world) {
        final OverloadedWorldHolder handler = groupManager.getWorldsHolder().getWorldData(world.getName());
        User user = handler.getUser(player.getUniqueId().toString());
        return user.getPermissionList();
    }

    @Override
    public List<String> getPerms(String groupName) {
        final OverloadedWorldHolder handler = groupManager.getWorldsHolder().getDefaultWorld();
        Group group = handler.getGroup(groupName);
        return group.getPermissionList();
    }

    @Override
    public List<String> getPerms(String groupName, World world) {
        final OverloadedWorldHolder handler = groupManager.getWorldsHolder().getWorldData(world.getName());
        Group group = handler.getGroup(groupName);
        return group.getPermissionList();
    }

    @Override
    public void createGroup(String groupName) {
        final OverloadedWorldHolder handler = groupManager.getWorldsHolder().getDefaultWorld();
        handler.createGroup(groupName);
    }

    @Override
    public void createGroup(String groupName, List<String> parentGroups) {
        final OverloadedWorldHolder handler = groupManager.getWorldsHolder().getDefaultWorld();
        Group group = handler.createGroup(groupName);
        parentGroups.forEach(parent -> {
            if (handler.groupExists(parent))
                group.addInherits(handler.getGroup(parent));
        });
    }

    @Override
    public void deleteGroup(String groupName) {
        final OverloadedWorldHolder handler = groupManager.getWorldsHolder().getDefaultWorld();
        handler.removeGroup(groupName);
    }

    @Override
    public void addPlayerToGroup(OfflinePlayer player, String groupName) {
        String uuid = player.getUniqueId().toString();
        final OverloadedWorldHolder handler = groupManager.getWorldsHolder().getDefaultWorld();
        User user = handler.getUser(uuid);
        Group group1 = handler.getGroup(groupName);
        user.addSubGroup(group1);
    }

    @Override
    public void addPlayerToGroup(OfflinePlayer player, String groupName, World world) {
        String uuid = player.getUniqueId().toString();
        final OverloadedWorldHolder handler = groupManager.getWorldsHolder().getWorldData(world.getName());
        User user = handler.getUser(uuid);
        Group group1 = handler.getGroup(groupName);
        user.addSubGroup(group1);
    }

    @Override
    public void addPlayerToGroup(OfflinePlayer player, String groupName, World world, int seconds) {
        String uuid = player.getUniqueId().toString();
        final OverloadedWorldHolder handler = groupManager.getWorldsHolder().getWorldData(world.getName());
        User user = handler.getUser(uuid);
        Group group1 = handler.getGroup(groupName);
        Instant timed = Instant.now().plus(seconds, ChronoUnit.SECONDS);
        user.addTimedSubGroup(group1, timed.getEpochSecond());
    }

    @Override
    public void addPlayerToGroup(OfflinePlayer player, String groupName, int seconds) {
        String uuid = player.getUniqueId().toString();
        final OverloadedWorldHolder handler = groupManager.getWorldsHolder().getDefaultWorld();
        User user = handler.getUser(uuid);
        Group group1 = handler.getGroup(groupName);
        Instant timed = Instant.now().plus(seconds, ChronoUnit.SECONDS);
        user.addTimedSubGroup(group1, timed.getEpochSecond());
    }

    @Override
    public void removePlayerFromGroup(OfflinePlayer player, String groupName) {
        String uuid = player.getUniqueId().toString();
        final OverloadedWorldHolder handler = groupManager.getWorldsHolder().getDefaultWorld();
        User user = handler.getUser(uuid);
        Group group1 = handler.getGroup(groupName);
        user.removeSubGroup(group1);
    }

    @Override
    public void removePlayerFromGroup(OfflinePlayer player, String groupName, World world) {
        String uuid = player.getUniqueId().toString();
        final OverloadedWorldHolder handler = groupManager.getWorldsHolder().getWorldData(world.getName());
        User user = handler.getUser(uuid);
        Group group1 = handler.getGroup(groupName);
        user.removeSubGroup(group1);
    }

    @Override
    public List<OfflinePlayer> getPlayersInGroup(String groupName) {
        final OverloadedWorldHolder handler = groupManager.getWorldsHolder().getDefaultWorld();
        Group group = handler.getGroup(groupName);
        return handler.getUserList().stream()
                .filter(user -> user.getSaveSubGroupsList().contains(groupName)
                        || user.getGroupName().equalsIgnoreCase(groupName))
                .map(user -> Bukkit.getOfflinePlayer(user.getUUID()))
                .collect(Collectors.toList());
    }

    @Override
    public List<OfflinePlayer> getPlayersInGroup(String groupName, World world) {
        final OverloadedWorldHolder handler = groupManager.getWorldsHolder().getWorldData(world.getName());
        Group group = handler.getGroup(groupName);
        return handler.getUserList().stream()
                .filter(user -> user.getSaveSubGroupsList().contains(groupName)
                        || user.getGroupName().equalsIgnoreCase(groupName))
                .map(user -> Bukkit.getOfflinePlayer(user.getUUID()))
                .collect(Collectors.toList());
    }

    @Override
    public void setGroupWeight(String groupName, int weight) {
        //NOT SUPPORTED
    }

    @Override
    public void setGroupRank(String groupName, int rank) {
//NOT SUPPORTED
    }

    @Override
    public int getGroupWeight(String groupName) {
        //NOT SUPPORTED
        return 0;
    }

    @Override
    public int getGroupRank(String groupName) {
        //NOT SUPPORTED
        return 0;
    }

    @Override
    public void setGroupPrefix(String groupName, String prefix) {
        final OverloadedWorldHolder handler = groupManager.getWorldsHolder().getDefaultWorld();
        Group group = handler.getGroup(groupName);
        group.getVariables().addVar("prefix", prefix);
    }

    @Override
    public String getGroupPrefix(String groupName) {
        final AnjoPermissionsHandler handler = groupManager.getWorldsHolder().getDefaultWorld().getPermissionsHandler();
        return handler.getGroupPrefix(groupName);
    }

    @Override
    public void setGroupSuffix(String groupName, String suffix) {
        final OverloadedWorldHolder handler = groupManager.getWorldsHolder().getDefaultWorld();
        Group group = handler.getGroup(groupName);
        group.getVariables().addVar("suffix", suffix);
    }

    @Override
    public String getGroupSuffix(String groupName) {
        final AnjoPermissionsHandler handler = groupManager.getWorldsHolder().getDefaultWorld().getPermissionsHandler();
        return handler.getGroupSuffix(groupName);
    }

    @Override
    public void setGroupPrefix(String groupName, String prefix, World world) {
        final OverloadedWorldHolder handler = groupManager.getWorldsHolder().getWorldData(world.getName());
        Group group = handler.getGroup(groupName);
        group.getVariables().addVar("prefix", prefix);
    }

    @Override
    public String getGroupPrefix(String groupName, World world) {
        final AnjoPermissionsHandler handler = groupManager.getWorldsHolder().getWorldPermissions(world.getName());
        return handler.getGroupPrefix(groupName);
    }

    @Override
    public void setGroupSuffix(String groupName, String suffix, World world) {
        final OverloadedWorldHolder handler = groupManager.getWorldsHolder().getWorldData(world.getName());
        Group group = handler.getGroup(groupName);
        group.getVariables().addVar("suffix", suffix);
    }

    @Override
    public String getGroupSuffix(String groupName, World world) {
        final AnjoPermissionsHandler handler = groupManager.getWorldsHolder().getWorldPermissions(world.getName());
        return handler.getGroupSuffix(groupName);
    }

    @Override
    public void setPlayerPrefix(OfflinePlayer player, String prefix) {
        String uuid = player.getUniqueId().toString();
        final OverloadedWorldHolder handler = groupManager.getWorldsHolder().getDefaultWorld();
        User user = handler.getUser(uuid);
        user.getVariables().addVar("prefix", prefix);
    }

    @Override
    public String getPlayerPrefix(OfflinePlayer player) {
        final AnjoPermissionsHandler handler = groupManager.getWorldsHolder().getWorldPermissionsByPlayerName(player.getName());
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
    public String getPlayerSuffix(OfflinePlayer player) {
        final AnjoPermissionsHandler handler = groupManager.getWorldsHolder().getWorldPermissionsByPlayerName(player.getName());
        return handler.getUserPrefix(player.getName());
    }

    @Override
    public void setPlayerPrefix(OfflinePlayer player, String prefix, World world) {
        String uuid = player.getUniqueId().toString();
        final OverloadedWorldHolder handler = groupManager.getWorldsHolder().getWorldData(world.getName());
        User user = handler.getUser(uuid);
        user.getVariables().addVar("prefix", prefix);
    }

    @Override
    public String getPlayerPrefix(OfflinePlayer player, World world) {
        final AnjoPermissionsHandler handler = groupManager.getWorldsHolder().getWorldPermissions(world.getName());
        return handler.getUserPrefix(player.getName());
    }

    @Override
    public void setPlayerSuffix(OfflinePlayer player, String suffix, World world) {
        String uuid = player.getUniqueId().toString();
        final OverloadedWorldHolder handler = groupManager.getWorldsHolder().getWorldData(world.getName());
        User user = handler.getUser(uuid);
        user.getVariables().addVar("suffix", suffix);
    }

    @Override
    public String getPlayerSuffix(OfflinePlayer player, World world) {
        final AnjoPermissionsHandler handler = groupManager.getWorldsHolder().getWorldPermissions(world.getName());
        return handler.getUserPrefix(player.getName());
    }

    @Override
    public List<String> getInheritedGroups(String groupName) {
        final OverloadedWorldHolder handler = groupManager.getWorldsHolder().getDefaultWorld();
        Group group = handler.getGroup(groupName);
        return group.getInherits();
    }

    @Override
    public List<String> getInheritedGroups(OfflinePlayer player) {
        final OverloadedWorldHolder handler = groupManager.getWorldsHolder().getDefaultWorld();
        User user = handler.getUser(player.getUniqueId().toString());
        List<String> groups = new java.util.ArrayList<>(user.getSaveSubGroupsList());
        groups.add(user.getGroupName());
        return groups;
    }

    @Override
    public Date getGroupExpireDate(OfflinePlayer player, String groupName) {
        //TODO: Implement
        return null;
    }

    @Override
    public String getPrimaryGroup(OfflinePlayer player) {
        final OverloadedWorldHolder handler = groupManager.getWorldsHolder().getDefaultWorld();
        User user = handler.getUser(player.getUniqueId().toString());
        return user.getGroupName();
    }

    @Override
    public void setPrimaryGroup(OfflinePlayer player, String groupName) {
        final OverloadedWorldHolder handler = groupManager.getWorldsHolder().getDefaultWorld();
        User user = handler.getUser(player.getUniqueId().toString());
        Group group = handler.getGroup(groupName);
        user.setGroup(group);
    }

    @Override
    public List<String> getAllGroups() {
        final OverloadedWorldHolder handler = groupManager.getWorldsHolder().getDefaultWorld();
        return handler.getGroupList().stream()
                .map(Group::getName)
                .collect(Collectors.toList());
    }
}
