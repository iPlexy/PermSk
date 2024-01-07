package de.iplexy.permsk.api;

import me.TechsCode.UltraPermissions.UltraPermissions;
import me.TechsCode.UltraPermissions.UltraPermissionsAPI;
import me.TechsCode.UltraPermissions.storage.objects.Group;
import me.TechsCode.UltraPermissions.storage.objects.Permission;
import me.TechsCode.UltraPermissions.storage.objects.User;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class UltraApi implements PermissionApi {
    private final UltraPermissionsAPI api = UltraPermissions.getAPI();


    @Override
    public void addPerm(OfflinePlayer player, String permission) {
        User user = api.getUsers().uuid(player.getUniqueId()).orElseThrow();
        user.newPermission(permission)
                .create();
    }

    @Override
    public void addPerm(OfflinePlayer player, String permission, World world) {
        User user = api.getUsers().uuid(player.getUniqueId()).orElseThrow();
        user.newPermission(permission)
                .setWorld(world.getName())
                .create();
    }

    @Override
    public void addPerm(OfflinePlayer player, String permission, World world, int seconds) {
        User user = api.getUsers().uuid(player.getUniqueId()).orElseThrow();
        Timestamp time = Timestamp.from(Instant.now().plusSeconds(seconds));
        user.newPermission(permission)
                .setWorld(world.getName())
                .setExpiration(time.getTime())
                .create();
    }

    @Override
    public void addPerm(OfflinePlayer player, String permission, int seconds) {
        User user = api.getUsers().uuid(player.getUniqueId()).orElseThrow();
        Timestamp time = Timestamp.from(Instant.now().plusSeconds(seconds));
        user.newPermission(permission)
                .setExpiration(time.getTime())
                .create();
    }

    @Override
    public void addPerm(String groupName, String permission) {
        Group group = api.getGroups().name(groupName).orElseThrow();
        group.newPermission(permission)
                .create();
    }

    @Override
    public void addPerm(String groupName, String permission, World world) {
        Group group = api.getGroups().name(groupName).orElseThrow();
        group.newPermission(permission)
                .setWorld(world.getName())
                .create();
    }

    @Override
    public void addPerm(String groupName, String permission, World world, int seconds) {
        Group group = api.getGroups().name(groupName).orElseThrow();
        Timestamp time = Timestamp.from(Instant.now().plusSeconds(seconds));
        group.newPermission(permission)
                .setWorld(world.getName())
                .setExpiration(time.getTime())
                .create();
    }

    @Override
    public void addPerm(String groupName, String permission, int seconds) {
        Group group = api.getGroups().name(groupName).orElseThrow();
        Timestamp time = Timestamp.from(Instant.now().plusSeconds(seconds));
        group.newPermission(permission)
                .setExpiration(time.getTime())
                .create();
    }

    @Override
    public void removePerm(OfflinePlayer player, String permission) {
        User user = api.getUsers().uuid(player.getUniqueId()).orElseThrow();
        user.getPermissions().stream()
                .filter(perm -> perm.getName().equalsIgnoreCase(permission))
                .forEach(Permission::remove);
    }

    @Override
    public void removePerm(OfflinePlayer player, String permission, World world) {
        User user = api.getUsers().uuid(player.getUniqueId()).orElseThrow();
        user.getPermissions().stream()
                .filter(perm -> perm.getName().equalsIgnoreCase(permission))
                .filter(perm -> perm.getWorld().orElseThrow().equalsIgnoreCase(world.getName()))
                .forEach(Permission::remove);
    }

    @Override
    public void removePerm(String groupName, String permission) {
        Group group = api.getGroups().name(groupName).orElseThrow();
        group.getPermissions().stream()
                .filter(perm -> perm.getName().equalsIgnoreCase(permission))
                .forEach(Permission::remove);
    }

    @Override
    public void removePerm(String groupName, String permission, World world) {
        Group group = api.getGroups().name(groupName).orElseThrow();
        group.getPermissions().stream()
                .filter(perm -> perm.getName().equalsIgnoreCase(permission))
                .filter(perm -> perm.getWorld().orElseThrow().equalsIgnoreCase(world.getName()))
                .forEach(Permission::remove);
    }

    @Override
    public List<String> getPerms(OfflinePlayer player) {
        User user = api.getUsers().uuid(player.getUniqueId()).orElseThrow();
        return user.getPermissions().stream()
                .map(Permission::getName)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getPerms(OfflinePlayer player, World world) {
        User user = api.getUsers().uuid(player.getUniqueId()).orElseThrow();
        return user.getPermissions().stream()
                .filter(perm -> perm.getWorld().orElseThrow().equalsIgnoreCase(world.getName()))
                .map(Permission::getName)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getPerms(String groupName) {
        Group group = api.getGroups().name(groupName).orElseThrow();
        return group.getPermissions().stream()
                .map(Permission::getName)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getPerms(String groupName, World world) {
        Group group = api.getGroups().name(groupName).orElseThrow();
        return group.getPermissions().stream()
                .filter(perm -> perm.getWorld().orElseThrow().equalsIgnoreCase(world.getName()))
                .map(Permission::getName)
                .collect(Collectors.toList());
    }

    @Override
    public void createGroup(String groupName) {
        api.newGroup(groupName).create();
    }

    @Override
    public void createGroup(String groupName, List<String> parentGroups) {
        api.newGroup(groupName).create();
        Group group = api.getGroups().name(groupName).orElseThrow();
        parentGroups.forEach(parent -> {
            Group parentGroup = api.getGroups().name(groupName).orElseThrow();
            group.addInheritance(parentGroup);
        });
    }

    @Override
    public void deleteGroup(String groupName) {
        api.getGroups().name(groupName).orElseThrow().remove();
    }

    @Override
    public void addPlayerToGroup(OfflinePlayer player, String groupName) {
        User user = api.getUsers().uuid(player.getUniqueId()).orElseThrow();
        Group group = api.getGroups().name(groupName).orElseThrow();
        user.addGroup(group);
        Bukkit.broadcastMessage(group.getName());
    }

    @Override
    public void addPlayerToGroup(OfflinePlayer player, String groupName, World world) {
        //NOT SUPPORTED
    }

    @Override
    public void addPlayerToGroup(OfflinePlayer player, String groupName, World world, int seconds) {
        //NOT SUPPORTED
    }

    @Override
    public void addPlayerToGroup(OfflinePlayer player, String groupName, int seconds) {
        User user = api.getUsers().uuid(player.getUniqueId()).orElseThrow();
        Group group = api.getGroups().name(groupName).orElseThrow();
        Timestamp time = Timestamp.from(Instant.now().plusSeconds(seconds));
        user.addGroup(group, time.getTime());
    }

    @Override
    public void removePlayerFromGroup(OfflinePlayer player, String groupName) {
        User user = api.getUsers().uuid(player.getUniqueId()).orElseThrow();
        Group group = api.getGroups().name(groupName).orElseThrow();
        user.removeGroup(group);
    }

    @Override
    public void removePlayerFromGroup(OfflinePlayer player, String groupName, World world) {
        //NOT SUPPORTED
    }

    @Override
    public List<OfflinePlayer> getPlayersInGroup(String groupName) {
        Group group = api.getGroups().name(groupName).orElseThrow();
        return group.getUsers().stream()
                .map(user -> Bukkit.getOfflinePlayer(user.getUuid()))
                .collect(Collectors.toList());
    }

    @Override
    public List<OfflinePlayer> getPlayersInGroup(String groupName, World world) {
        //NOT SUPPORTED
        return null;
    }

    @Override
    public void setGroupWeight(String groupName, int weight) {
        Group group = api.getGroups().name(groupName).orElseThrow();
        group.setPriority(weight);
    }

    @Override
    public void setGroupRank(String groupName, int rank) {
        setGroupWeight(groupName, rank);
    }

    @Override
    public int getGroupWeight(String groupName) {
        Group group = api.getGroups().name(groupName).orElseThrow();
        return group.getPriority();
    }

    @Override
    public int getGroupRank(String groupName) {
        return getGroupWeight(groupName);
    }

    @Override
    public void setGroupPrefix(String groupName, String prefix) {
        Group group = api.getGroups().name(groupName).orElseThrow();
        group.setPrefix(prefix);
    }

    @Override
    public String getGroupPrefix(String groupName) {
        Group group = api.getGroups().name(groupName).orElseThrow();
        return group.getPrefix().orElseThrow();
    }

    @Override
    public void setGroupSuffix(String groupName, String suffix) {
        Group group = api.getGroups().name(groupName).orElseThrow();
        group.setSuffix(suffix);
    }

    @Override
    public String getGroupSuffix(String groupName) {
        Group group = api.getGroups().name(groupName).orElseThrow();
        return group.getSuffix().orElseThrow();
    }

    @Override
    public void setGroupPrefix(String groupName, String prefix, World world) {
        //NOT SUPPORTED
    }

    @Override
    public String getGroupPrefix(String groupName, World world) {
        //NOT SUPPORTED
        return null;
    }

    @Override
    public void setGroupSuffix(String groupName, String suffix, World world) {
        //NOT SUPPORTED
    }

    @Override
    public String getGroupSuffix(String groupName, World world) {
        //NOT SUPPORTED
        return null;
    }

    @Override
    public void setPlayerPrefix(OfflinePlayer player, String prefix) {
        User user = api.getUsers().uuid(player.getUniqueId()).orElseThrow();
        user.setPrefix(prefix);
    }

    @Override
    public String getPlayerPrefix(OfflinePlayer player) {
        User user = api.getUsers().uuid(player.getUniqueId()).orElseThrow();
        return user.getPrefix().orElseThrow();
    }

    @Override
    public void setPlayerSuffix(OfflinePlayer player, String suffix) {
        User user = api.getUsers().uuid(player.getUniqueId()).orElseThrow();
        user.setSuffix(suffix);
    }

    @Override
    public String getPlayerSuffix(OfflinePlayer player) {
        User user = api.getUsers().uuid(player.getUniqueId()).orElseThrow();
        return user.getSuffix().orElseThrow();
    }

    @Override
    public void setPlayerPrefix(OfflinePlayer player, String prefix, World world) {
        //NOT SUPPORTED
    }

    @Override
    public String getPlayerPrefix(OfflinePlayer player, World world) {
        //NOT SUPPORTED
        return null;
    }

    @Override
    public void setPlayerSuffix(OfflinePlayer player, String suffix, World world) {
        //NOT SUPPORTED
    }

    @Override
    public String getPlayerSuffix(OfflinePlayer player, World world) {
        //NOT SUPPORTED
        return null;
    }

    @Override
    public List<String> getInheritedGroups(String groupName) {
        Group group = api.getGroups().name(groupName).orElseThrow();
        return group.getInheritedGroups()
                .stream()
                .map(storedGroup -> storedGroup.get().orElseThrow().getName())
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getInheritedGroups(OfflinePlayer player) {
        User user = api.getUsers().uuid(player.getUniqueId()).orElseThrow();
        return user.getActiveGroups()
                .stream()
                .map(Group::getName)
                .collect(Collectors.toList());
    }

    @Override
    public Date getGroupExpireDate(OfflinePlayer player, String groupName) {
        User user = api.getUsers().uuid(player.getUniqueId()).orElseThrow();
        return new Date(user.getGroupExpiry(user.getGroups()
                .stream()
                .filter(group -> group.get().orElseThrow().getName().equalsIgnoreCase(groupName))
                .findFirst().orElseThrow()));
    }

    @Override
    public String getPrimaryGroup(OfflinePlayer player) {
        //NOT SUPPORTED
        return null;
    }

    @Override
    public void setPrimaryGroup(OfflinePlayer player, String groupName) {
        addPlayerToGroup(player, groupName);
    }

    @Override
    public List<String> getAllGroups() {
        return api.getGroups().stream()
                .map(Group::getName)
                .collect(Collectors.toList());
    }
}
