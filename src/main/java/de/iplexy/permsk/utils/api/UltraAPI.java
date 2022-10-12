package de.iplexy.permsk.utils.api;

import me.TechsCode.UltraPermissions.UltraPermissions;
import me.TechsCode.UltraPermissions.UltraPermissionsAPI;
import me.TechsCode.UltraPermissions.storage.objects.Group;
import me.TechsCode.UltraPermissions.storage.objects.Permission;
import me.TechsCode.UltraPermissions.storage.objects.User;
import me.TechsCode.base.storage.Stored;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class UltraAPI implements API {
    
    //
    // Load API
    //
    private final UltraPermissionsAPI api = UltraPermissions.getAPI();
    
    public void addPerm(OfflinePlayer player, String permission) { //TODO come back to this
        User user = api.getUsers().uuid(player.getUniqueId()).orElseThrow();
        user.newPermission(permission).create();
    }
    
    public void addPerm(OfflinePlayer player, String permission, World world) {
        User user = api.getUsers().uuid(player.getUniqueId()).orElseThrow();
        user.newPermission(permission).setWorld(world.getName()).create();
    }
    
    public void addPerm(OfflinePlayer player, String permission, World world, int seconds) {
        User user = api.getUsers().uuid(player.getUniqueId()).orElseThrow();
        Timestamp time = Timestamp.from(Instant.now().plusSeconds(seconds));
        user.newPermission(permission).setWorld(world.getName()).setExpiration(time.getTime()).create();
    }
    
    public void addPerm(OfflinePlayer player, String permission, int seconds) {
        User user = api.getUsers().uuid(player.getUniqueId()).orElseThrow();
        Timestamp time = Timestamp.from(Instant.now().plusSeconds(seconds));
        user.newPermission(permission).setExpiration(time.getTime()).create();
    }
    
    public void addPerm(String group, String permission) {
        Group groupT = api.getGroups().name(group).orElseThrow();
        groupT.newPermission(permission).create();
    }
    
    public void addPerm(String group, String permission, World world) {
        Group groupT = api.getGroups().name(group).orElseThrow();
        groupT.newPermission(permission).setWorld(world.getName()).create();
    }
    
    public void addPerm(String group, String permission, World world, int seconds) {
        Group groupT = api.getGroups().name(group).orElseThrow();
        Timestamp time = Timestamp.from(Instant.now().plusSeconds(seconds));
        groupT.newPermission(permission).setWorld(world.getName()).setExpiration(time.getTime()).create();
    }
    
    public void addPerm(String group, String permission, int seconds) {
        Group groupT = api.getGroups().name(group).orElseThrow();
        Timestamp time = Timestamp.from(Instant.now().plusSeconds(seconds));
        groupT.newPermission(permission).setExpiration(time.getTime()).create();
    }
    
    public void removePerm(OfflinePlayer player, String permission) {
        User user = api.getUsers().uuid(player.getUniqueId()).orElseThrow();
        for (Permission perm : user.getPermissions()) {
            if (perm.getName().equalsIgnoreCase(permission)) {
                perm.remove();
            }
        }
    }
    
    public void removePerm(OfflinePlayer player, String permission, World world) {
        User user = api.getUsers().uuid(player.getUniqueId()).orElseThrow();
        for (Permission perm : user.getPermissions().worlds(true, world.getName())) {
            if (perm.getName().equalsIgnoreCase(permission)) {
                perm.remove();
            }
        }
    }
    
    public void removePerm(String group, String permission) {
        Group groupT = api.getGroups().name(group).orElseThrow();
        for (Permission perm : groupT.getPermissions()) {
            if (perm.getName().equalsIgnoreCase(permission)) {
                perm.remove();
            }
        }
    }
    
    public void removePerm(String group, String permission, World world) {
        Group groupT = api.getGroups().name(group).orElseThrow();
        for (Permission perm : groupT.getPermissions().worlds(true, world.getName())) {
            if (perm.getName().equalsIgnoreCase(permission)) {
                perm.remove();
            }
        }
    }
    
    public String[] getPerm(OfflinePlayer player) {
        User user = api.getUsers().uuid(player.getUniqueId()).orElseThrow();
        ArrayList<String> list = new ArrayList<>();
        for (Permission perm : user.getPermissions()) {
            list.add(perm.getName());
        }
        return list.toArray(new String[0]);
    }
    
    public String[] getPerm(OfflinePlayer player, World world) {
        User user = api.getUsers().uuid(player.getUniqueId()).orElseThrow();
        ArrayList<String> list = new ArrayList<>();
        for (Permission perm : user.getPermissions().worlds(true, world.getName())) {
            list.add(perm.getName());
        }
        return list.toArray(new String[0]);
    }
    
    public String[] getPerm(String group) {
        Group groupT = api.getGroups().name(group).orElseThrow();
        ArrayList<String> list = new ArrayList<>();
        for (Permission perm : groupT.getPermissions()) {
            list.add(perm.getName());
        }
        return list.toArray(new String[0]);
    }
    
    public String[] getPerm(String group, World world) {
        Group groupT = api.getGroups().name(group).orElseThrow();
        ArrayList<String> list = new ArrayList<>();
        for (Permission perm : groupT.getPermissions().worlds(true, world.getName())) {
            list.add(perm.getName());
        }
        return list.toArray(new String[0]);
    }
    
    public void createGroup(String group) {
        if (api.getGroups().name(group).isEmpty()) {
            api.newGroup(group).create();
        }
    }
    
    public void createGroup(String group, String[] parents) {
        if (api.getGroups().name(group).isEmpty()) {
            api.newGroup(group).create();
            Group groupT = api.getGroups().name(group).orElseThrow();
            for (String par : parents) {
                Group parent = api.getGroups().name(par).orElseThrow();
                groupT.addInheritance(parent);
            }
            //groupT.save();
        }
    }
    
    public void removeGroup(String group) {
        Group group1 = api.getGroups().name(group).orElseThrow();
        group1._justDestroy();
    }
    
    public void addPlayerToGroup(OfflinePlayer player, String group) {
        User user = api.getUsers().uuid(player.getUniqueId()).orElseThrow();
        Group groupT = api.getGroups().name(group).orElseThrow();
        user.addGroup(groupT);
        //user.save();
    }
    
    public void addPlayerToGroup(OfflinePlayer player, String group, World world) {
        // TODO NOT SUPPORTED
    }
    
    public void addPlayerToGroup(OfflinePlayer player, String group, World world, int seconds) {
        // TODO NOT SUPPORTED
    }
    
    public void addPlayerToGroup(OfflinePlayer player, String group, int seconds) {
        User user = api.getUsers().uuid(player.getUniqueId()).orElseThrow();
        Group groupT = api.getGroups().name(group).orElseThrow();
        Timestamp time = Timestamp.from(Instant.now().plusSeconds(seconds));
        user.addGroup(groupT, time.getTime());
        //user.save();
    }
    
    public void removePlayerFromGroup(OfflinePlayer player, String group) {
        User user = api.getUsers().uuid(player.getUniqueId()).orElseThrow();
        Group groupT = api.getGroups().name(group).orElseThrow();
        if (groupT == null) return;
        user.removeGroup(groupT);
        //user.save();
    }
    
    public void removePlayerFromGroup(OfflinePlayer player, String group, World world) {
        // TODO NOT SUPPORTED
    }
    
    public OfflinePlayer[] getPlayersInGroup(String group) {
        User user;
        ArrayList<OfflinePlayer> list = new ArrayList<>();
        for (OfflinePlayer player : Bukkit.getServer().getOfflinePlayers()) {
            user = api.getUsers().uuid(player.getUniqueId()).orElseThrow();
            if (user == null) continue;
            list.add(player);
        }
        return list.toArray(new OfflinePlayer[0]);
    }
    
    public void setGroupWeight(String group, int weight) {
        Group groupT = api.getGroups().name(group).orElseThrow();
        groupT.setPriority(weight);
        //groupT.save();
    }
    
    public void setGroupRank(String group, int rank) {
        // NOT SUPPORTED
    }
    
    public int getGroupWeight(String group) {
        Group groupT = api.getGroups().name(group).orElseThrow();
        return groupT.getPriority();
    }
    
    public int getGroupRank(String group) {
        return 0;
        // NOT SUPPORTED
    }
    
    public void setGroupPrefix(String group, String prefix) {
        Group groupT = api.getGroups().name(group).orElseThrow();
        groupT.setPrefix(prefix);
        //groupT.save();
    }
    
    public void setGroupPrefix(String group, String prefix, World world) {
        Group groupT = api.getGroups().name(group).orElseThrow();
        groupT.setPrefix(prefix);
        //groupT.save();
        // WORLD NOT SUPPORTED
    }
    
    public String getGroupPrefix(String group) {
        Group group1 = api.getGroups().name(group).orElseThrow();
        return group1.getPrefix().orElse("");
    }
    
    public String getGroupPrefix(String group, World world) {
        Group group1 = api.getGroups().name(group).orElseThrow();
        return group1.getPrefix().orElse("");
        // WORLDS NOT SUPPORTED
    }
    
    public void setGroupSuffix(String group, String suffix) {
        Group groupT = api.getGroups().name(group).orElseThrow();
        groupT.setSuffix(suffix);
        //groupT.save();
    }
    
    public void setGroupSuffix(String group, String suffix, World world) {
        Group groupT = api.getGroups().name(group).orElseThrow();
        groupT.setSuffix(suffix);
        //groupT.save();
        // WORLDS NOT SUPPORTED
    }
    
    public String getGroupSuffix(String group) {
        Group group1 = api.getGroups().name(group).orElseThrow();
        return group1.getSuffix().orElse("");
    }
    
    public String getGroupSuffix(String group, World world) {
        Group group1 = api.getGroups().name(group).orElseThrow();
        return group1.getSuffix().orElse("");
        // WORLDS NOT SUPPORTED
    }
    
    public void setPlayerPrefix(OfflinePlayer player, String prefix) {
        User user = api.getUsers().uuid(player.getUniqueId()).orElseThrow();
        user.setPrefix(prefix);
        //user.save();
    }
    
    public void setPlayerPrefix(OfflinePlayer player, String prefix, World world) {
        User user = api.getUsers().uuid(player.getUniqueId()).orElseThrow();
        user.setPrefix(prefix);
        //user.save();
    }
    
    public String getPlayerPrefix(OfflinePlayer player) {
        User user = api.getUsers().uuid(player.getUniqueId()).orElseThrow();
        return user.getPrefix().orElse("");
    }
    
    public String getPlayerPrefix(OfflinePlayer player, World world) {
        User user = api.getUsers().uuid(player.getUniqueId()).orElseThrow();
        return user.getSuffix().orElse("");
        // Does not support worlds
    }
    
    public void setPlayerSuffix(OfflinePlayer player, String suffix) {
        User user = api.getUsers().uuid(player.getUniqueId()).orElseThrow();
        user.setSuffix(suffix);
        //user.save();
    }
    
    public void setPlayerSuffix(OfflinePlayer player, String suffix, World world) {
        User user = api.getUsers().uuid(player.getUniqueId()).orElseThrow();
        user.setSuffix(suffix);
        //user.save();
        // Does not support worlds
    }
    
    public String getPlayerSuffix(OfflinePlayer player) {
        User user = api.getUsers().uuid(player.getUniqueId()).orElseThrow();
        return user.getSuffix().orElse("");
    }
    
    public String getPlayerSuffix(OfflinePlayer player, World world) {
        User user = api.getUsers().uuid(player.getUniqueId()).orElseThrow();
        return user.getSuffix().orElse("");
        // Does not support worlds
    }

    @Override
    public String getPrimaryGroup(OfflinePlayer player) {
        //TODO ADD FUNCTION
        return null;
    }


    @Override
    public void setPrimaryGroup(OfflinePlayer player, String group) {
        //TODO ADD FUNCTION
    }

    @Override
    public ArrayList<String> getInheritedGroups(OfflinePlayer player) {
        //TODO Test
        User user = api.getUsers().uuid(player.getUniqueId()).orElseThrow();
        ArrayList<String> groups = new ArrayList<>();
        for(Stored<Group> group : user.getGroups()){
            groups.add(group.get().orElseThrow().getName());
        }
        return groups;
    }

}
