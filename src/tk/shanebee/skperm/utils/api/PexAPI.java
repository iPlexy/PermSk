package tk.shanebee.skperm.utils.api;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PexAPI implements API {

    public void addPerm(OfflinePlayer player, String permission) {
        PermissionsEx.getUser(player.getName()).addPermission(permission);
    }

    public void addPerm(OfflinePlayer player, String permission, World world) {
        PermissionsEx.getUser(player.getName()).addPermission(permission, world.getName());
    }

    public void addPerm(OfflinePlayer player, String permission, World world, int seconds) {
        PermissionsEx.getUser(player.getName()).addTimedPermission(permission, world.getName(), seconds);
    }

    public void addPerm(OfflinePlayer player, String permission, int seconds) {
        PermissionsEx.getUser(player.getName()).addTimedPermission(permission, null, seconds);
    }

    public void addPerm(String group, String permission) {
        PermissionsEx.getPermissionManager().getGroup(group).addPermission(permission);
    }

    public void addPerm(String group, String permission, World world) {
        PermissionsEx.getPermissionManager().getGroup(group).addPermission(permission, world.getName());
    }

    public void addPerm(String group, String permission, World world, int seconds) {
        PermissionsEx.getPermissionManager().getGroup(group).addTimedPermission(permission, world.getName(), seconds);
    }

    public void addPerm(String group, String permission, int seconds) {
        PermissionsEx.getPermissionManager().getGroup(group).addTimedPermission(permission, null, seconds);
    }

    public void removePerm(OfflinePlayer player, String permission) {
        PermissionsEx.getUser(player.getName()).removePermission(permission);
    }

    public void removePerm(OfflinePlayer player, String permission, World world) {
        PermissionsEx.getUser(player.getName()).removePermission(permission, world.getName());
        
    }

    public void removePerm(String group, String permission) {
        PermissionsEx.getPermissionManager().getGroup(group).removePermission(permission);
    }

    public void removePerm(String group, String permission, World world) {
        PermissionsEx.getPermissionManager().getGroup(group).removePermission(permission, world.getName());
    }

    public String[] getPerm(OfflinePlayer player) {
        PermissionUser user = PermissionsEx.getUser(player.getName());
        return user.getPermissions(null).toArray(new String[0]);
    }

    public String[] getPerm(OfflinePlayer player, World world) {
        PermissionUser user = PermissionsEx.getUser(player.getName());
        return user.getPermissions(world.getName()).toArray(new String[0]);
    }

    public String[] getPerm(String group) {
        PermissionGroup groupT = PermissionsEx.getPermissionManager().getGroup(group);
        return groupT.getPermissions(null).toArray(new String[0]);
    }

    public String[] getPerm(String group, World world) {
        PermissionGroup groupT = PermissionsEx.getPermissionManager().getGroup(group);
        return groupT.getPermissions(world.getName()).toArray(new String[0]);
    }

    public void createGroup(String group) {
        PermissionGroup pexGroup = PermissionsEx.getPermissionManager().getGroup(group);
        pexGroup.save();
    }

    public void createGroup(String group, String[] parents) {
        PermissionGroup pexGroup = PermissionsEx.getPermissionManager().getGroup(group);
        List<PermissionGroup> groups = new LinkedList<>();
        for (String parent : parents) {
            groups.add(PermissionsEx.getPermissionManager().getGroup(parent));
        }
        pexGroup.setParents(groups, null);
        pexGroup.save();
    }

    public void removeGroup(String group) {
        PermissionsEx.getPermissionManager().getGroup(group).remove();
    }

    public void addPlayerToGroup(OfflinePlayer player, String group) {
        PermissionsEx.getUser(player.getName()).addGroup(group, null);
    }

    public void addPlayerToGroup(OfflinePlayer player, String group, World world) {
        PermissionsEx.getUser(player.getName()).addGroup(group, world.getName());
    }

    public void addPlayerToGroup(OfflinePlayer player, String group, World world, int seconds) {
        PermissionsEx.getUser(player.getName()).addGroup(group, world.getName(), seconds);
    }

    public void addPlayerToGroup(OfflinePlayer player, String group, int seconds) {
        PermissionsEx.getUser(player.getName()).addGroup(group, null, seconds);
    }

    public void removePlayerFromGroup(OfflinePlayer player, String group) {
        PermissionsEx.getUser(player.getName()).removeGroup(group);
    }

    public void removePlayerFromGroup(OfflinePlayer player, String group, World world) {
        PermissionsEx.getUser(player.getName()).removeGroup(group, world.getName());
    }

    @SuppressWarnings("deprecation")
    public OfflinePlayer[] getPlayersInGroup(String group) {
        ArrayList<OfflinePlayer> users = new ArrayList<>();
        for (PermissionUser user : PermissionsEx.getPermissionManager().getGroup(group).getUsers()) {
            users.add(Bukkit.getOfflinePlayer(user.getName()));
        }
        return users.toArray(new OfflinePlayer[0]);
    }

    public void setGroupWeight(String group, int weight) {
        PermissionsEx.getPermissionManager().getGroup(group).setWeight(weight);
    }

    public void setGroupRank(String group, int rank) {
        PermissionsEx.getPermissionManager().getGroup(group).setRank(rank);
    }

    public int getGroupWeight(String group) {
        return PermissionsEx.getPermissionManager().getGroup(group).getWeight();
    }

    public int getGroupRank(String group) {
        return PermissionsEx.getPermissionManager().getGroup(group).getRank();
    }

}