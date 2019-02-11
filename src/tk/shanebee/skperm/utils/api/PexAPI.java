package tk.shanebee.skperm.utils.api;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PexAPI implements API {
    
    PermissionManager api = PermissionsEx.getPermissionManager();

    public void addPerm(OfflinePlayer player, String permission) {
        api.getUser(player.getName()).addPermission(permission);
    }

    public void addPerm(OfflinePlayer player, String permission, World world) {
        api.getUser(player.getName()).addPermission(permission, world.getName());
    }

    public void addPerm(OfflinePlayer player, String permission, World world, int seconds) {
        api.getUser(player.getName()).addTimedPermission(permission, world.getName(), seconds);
    }

    public void addPerm(OfflinePlayer player, String permission, int seconds) {
        api.getUser(player.getName()).addTimedPermission(permission, null, seconds);
    }

    public void addPerm(String group, String permission) {
        api.getGroup(group).addPermission(permission);
    }

    public void addPerm(String group, String permission, World world) {
        api.getGroup(group).addPermission(permission, world.getName());
    }

    public void addPerm(String group, String permission, World world, int seconds) {
        api.getGroup(group).addTimedPermission(permission, world.getName(), seconds);
    }

    public void addPerm(String group, String permission, int seconds) {
        api.getGroup(group).addTimedPermission(permission, null, seconds);
    }

    public void removePerm(OfflinePlayer player, String permission) {
        api.getUser(player.getName()).removePermission(permission);
    }

    public void removePerm(OfflinePlayer player, String permission, World world) {
        api.getUser(player.getName()).removePermission(permission, world.getName());
        
    }

    public void removePerm(String group, String permission) {
        api.getGroup(group).removePermission(permission);
    }

    public void removePerm(String group, String permission, World world) {
        api.getGroup(group).removePermission(permission, world.getName());
    }

    public String[] getPerm(OfflinePlayer player) {
        PermissionUser user = api.getUser(player.getName());
        return user.getPermissions(null).toArray(new String[0]);
    }

    public String[] getPerm(OfflinePlayer player, World world) {
        PermissionUser user = api.getUser(player.getName());
        return user.getPermissions(world.getName()).toArray(new String[0]);
    }

    public String[] getPerm(String group) {
        PermissionGroup groupT = api.getGroup(group);
        return groupT.getPermissions(null).toArray(new String[0]);
    }

    public String[] getPerm(String group, World world) {
        PermissionGroup groupT = api.getGroup(group);
        return groupT.getPermissions(world.getName()).toArray(new String[0]);
    }

    public void createGroup(String group) {
        PermissionGroup pexGroup = api.getGroup(group);
        pexGroup.save();
    }

    public void createGroup(String group, String[] parents) {
        PermissionGroup pexGroup = api.getGroup(group);
        List<PermissionGroup> groups = new LinkedList<>();
        for (String parent : parents) {
            groups.add(api.getGroup(parent));
        }
        pexGroup.setParents(groups, null);
        pexGroup.save();
    }

    public void removeGroup(String group) {
        api.getGroup(group).remove();
    }

    public void addPlayerToGroup(OfflinePlayer player, String group) {
        api.getUser(player.getName()).addGroup(group, null);
    }

    public void addPlayerToGroup(OfflinePlayer player, String group, World world) {
        api.getUser(player.getName()).addGroup(group, world.getName());
    }

    public void addPlayerToGroup(OfflinePlayer player, String group, World world, int seconds) {
        api.getUser(player.getName()).addGroup(group, world.getName(), seconds);
    }

    public void addPlayerToGroup(OfflinePlayer player, String group, int seconds) {
        api.getUser(player.getName()).addGroup(group, null, seconds);
    }

    public void removePlayerFromGroup(OfflinePlayer player, String group) {
        api.getUser(player.getName()).removeGroup(group);
    }

    public void removePlayerFromGroup(OfflinePlayer player, String group, World world) {
        api.getUser(player.getName()).removeGroup(group, world.getName());
    }

    @SuppressWarnings("deprecation")
    public OfflinePlayer[] getPlayersInGroup(String group) {
        ArrayList<OfflinePlayer> users = new ArrayList<>();
        for (PermissionUser user : api.getGroup(group).getUsers()) {
            users.add(Bukkit.getOfflinePlayer(user.getName()));
        }
        return users.toArray(new OfflinePlayer[0]);
    }

    public void setGroupWeight(String group, int weight) {
        api.getGroup(group).setWeight(weight);
    }

    public void setGroupRank(String group, int rank) {
        api.getGroup(group).setRank(rank);
    }

    public int getGroupWeight(String group) {
        return api.getGroup(group).getWeight();
    }

    public int getGroupRank(String group) {
        return api.getGroup(group).getRank();
    }

    public void setGroupPrefix(String group, String prefix) {
        api.getGroup(group).setPrefix(prefix, null);
    }

    public void setGroupPrefix(String group, String prefix, World world) {
        api.getGroup(group).setPrefix(prefix, world.getName());
    }

    public String getGroupPrefix(String group) {
        return api.getGroup(group).getPrefix();
    }

    public String getGroupPrefix(String group, World world) {
        return api.getGroup(group).getPrefix(world.getName());
    }

    public void setGroupSuffix(String group, String suffix) {
        api.getGroup(group).setSuffix(suffix, null);
    }

    public void setGroupSuffix(String group, String suffix, World world) {
        api.getGroup(group).setSuffix(suffix, world.getName());
    }

    public String getGroupSuffix(String group) {
        return api.getGroup(group).getSuffix();
    }

    public String getGroupSuffix(String group, World world) {
        return api.getGroup(group).getSuffix(world.getName());
    }

    public void setPlayerPrefix(OfflinePlayer player, String prefix) {
        api.getUser(player.getUniqueId()).setPrefix(prefix, null);
    }

    public void setPlayerPrefix(OfflinePlayer player, String prefix, World world) {
        api.getUser(player.getUniqueId()).setPrefix(prefix, world.getName());
    }

    public String getPlayerPrefix(OfflinePlayer player) {
        return api.getUser(player.getUniqueId()).getPrefix(null);
    }

    public String getPlayerPrefix(OfflinePlayer player, World world) {
        return api.getUser(player.getUniqueId()).getPrefix(world.getName());
    }

    public void setPlayerSuffix(OfflinePlayer player, String suffix) {
        api.getUser(player.getUniqueId()).setSuffix(suffix, null);
    }

    public void setPlayerSuffix(OfflinePlayer player, String suffix, World world) {
        api.getUser(player.getUniqueId()).setSuffix(suffix, world.getName());
    }

    public String getPlayerSuffix(OfflinePlayer player) {
        return api.getUser(player.getUniqueId()).getSuffix(null);
    }

    public String getPlayerSuffix(OfflinePlayer player, World world) {
        return api.getUser(player.getUniqueId()).getSuffix(world.getName());
    }

}