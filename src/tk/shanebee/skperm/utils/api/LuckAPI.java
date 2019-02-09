package tk.shanebee.skperm.utils.api;

import me.lucko.luckperms.LuckPerms;
import me.lucko.luckperms.api.Group;
import me.lucko.luckperms.api.LuckPermsApi;
import me.lucko.luckperms.api.Node;
import me.lucko.luckperms.api.User;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;

import java.util.concurrent.TimeUnit;

public class LuckAPI implements API {

    private LuckPermsApi api = LuckPerms.getApi();

    public void addPerm(OfflinePlayer player, String permission) {
        Node node = api.getNodeFactory().newBuilder(permission).build();
        User user = api.getUserManager().loadUser(player.getUniqueId()).join();
        if (user != null) {
            user.setPermission(node);
            api.getUserManager().saveUser(user);
            Bukkit.getConsoleSender().sendMessage("Player Saved");
        }
    }

    public void addPerm(OfflinePlayer player, String permission, World world) {
        Node node = api.getNodeFactory().newBuilder(permission).setWorld(world.getName()).build();
        User user = api.getUserManager().loadUser(player.getUniqueId()).join();
        if (user != null) {
            user.setPermission(node);
            api.getUserManager().saveUser(user);
        }
    }

    public void addPerm(OfflinePlayer player, String permission, World world, int seconds) {
        Node node = api.getNodeFactory().newBuilder(permission).setWorld(world.getName()).setExpiry(seconds, TimeUnit.SECONDS).build();
        User user = api.getUserManager().loadUser(player.getUniqueId()).join();
        if (user != null) {
            user.setPermission(node);
            api.getUserManager().saveUser(user);
        }
    }


    public void addPerm(OfflinePlayer player, String permission, int seconds) {
        Node node = api.getNodeFactory().newBuilder(permission).setExpiry(seconds, TimeUnit.SECONDS).build();
        User user = api.getUserManager().loadUser(player.getUniqueId()).join();
        if (user != null) {
            user.setPermission(node);
            api.getUserManager().saveUser(user);
        }
    }

    public void addPerm(String group, String permission) {
        Node node = api.getNodeFactory().newBuilder(permission).build();
        Group groupT = api.getGroup(group);
        if (groupT != null) {
            groupT.setPermission(node);
            api.getGroupManager().saveGroup(groupT);
        }
    }

    public void addPerm(String group, String permission, World world) {
        Node node = api.getNodeFactory().newBuilder(permission).setWorld(world.getName()).build();
        Group groupT = api.getGroup(group);
        if (groupT != null) {
            groupT.setPermission(node);
            api.getGroupManager().saveGroup(groupT);
        }
    }

    public void addPerm(String group, String permission, World world, int seconds) {
        Node node = api.getNodeFactory().newBuilder(permission).setWorld(world.getName()).setExpiry(seconds, TimeUnit.SECONDS).build();
        Group groupT = api.getGroup(group);
        if (groupT != null) {
            groupT.setPermission(node);
            api.getGroupManager().saveGroup(groupT);
        }
    }

    public void addPerm(String group, String permission, int seconds) {
        Node node = api.getNodeFactory().newBuilder(permission).setExpiry(seconds, TimeUnit.SECONDS).build();
        Group groupT = api.getGroup(group);
        if (groupT != null) {
            groupT.setPermission(node);
            api.getGroupManager().saveGroup(groupT);
        }
    }

    public void removePerm(OfflinePlayer player, String permission) {
        Node node = api.getNodeFactory().newBuilder(permission).build();
        User user = api.getUserManager().loadUser(player.getUniqueId()).join();
        if (user != null) {
            user.unsetPermission(node);
            api.getUserManager().saveUser(user);
        }
    }

    public void removePerm(OfflinePlayer player, String permission, World world) {
        Node node = api.getNodeFactory().newBuilder(permission).setWorld(world.getName()).build();
        User user = api.getUserManager().loadUser(player.getUniqueId()).join();
        if (user != null) {
            user.unsetPermission(node);
            api.getUserManager().saveUser(user);
        }
    }

    public void removePerm(String group, String permission) {
        Node node = api.getNodeFactory().newBuilder(permission).build();
        Group groupT = api.getGroup(group);
        if (groupT != null) {
            groupT.unsetPermission(node);
            api.getGroupManager().saveGroup(groupT);
        }
    }

    public void removePerm(String group, String permission, World world) {
        Node node = api.getNodeFactory().newBuilder(permission).setWorld(world.getName()).build();
        Group groupT = api.getGroup(group);
        if (groupT != null) {
            groupT.unsetPermission(node);
            api.getGroupManager().saveGroup(groupT);
        }
    }

    public String[] getPerm(OfflinePlayer player) { // TODO need to figure this out
        return null;
    }

    public String[] getPerm(OfflinePlayer player, World world) { // TODO need to figure this out
        return null;
    }

    public String[] getPerm(String group) { // TODO need to figure this out
        return null;
    }

    public String[] getPerm(String group, World world) { // TODO need to figure this out
        return null;
    }

    public void createGroup(String group) {
        api.getGroupManager().createAndLoadGroup(group);
    }

    public void createGroup(String group, String[] parents) {
        api.getGroupManager().createAndLoadGroup(group);
        Group groupT = api.getGroup(group);
        if (groupT == null) return;
        Node node;
        for (String parent : parents) {
            node = api.getNodeFactory().makeGroupNode(parent).build();
            groupT.setPermission(node);
            api.getGroupManager().saveGroup(groupT);
        }
    }

    public void removeGroup(String group) {
        Group groupT = api.getGroup(group);
        if (groupT != null)
            api.getGroupManager().deleteGroup(groupT);
    }

    public void addPlayerToGroup(OfflinePlayer player, String group) {
        if (!api.getGroupManager().isLoaded(group)) return;
        User user = api.getUserManager().loadUser(player.getUniqueId()).join();
        Node node = api.getNodeFactory().makeGroupNode(group).build();
        user.setPermission(node);
        api.getUserManager().saveUser(user);

    }

    public void addPlayerToGroup(OfflinePlayer player, String group, World world) {
        if (!api.getGroupManager().isLoaded(group)) return;
        User user = api.getUserManager().loadUser(player.getUniqueId()).join();
        Node node = api.getNodeFactory().makeGroupNode(group).setWorld(world.getName()).build();
        user.setPermission(node);
        api.getUserManager().saveUser(user);
    }

    public void addPlayerToGroup(OfflinePlayer player, String group, World world, int seconds) {
        if (!api.getGroupManager().isLoaded(group)) return;
        User user = api.getUserManager().loadUser(player.getUniqueId()).join();
        Node node = api.getNodeFactory().makeGroupNode(group).setWorld(world.getName()).setExpiry(seconds, TimeUnit.SECONDS).build();
        user.setPermission(node);
        api.getUserManager().saveUser(user);
    }

    public void addPlayerToGroup(OfflinePlayer player, String group, int seconds) {
        if (!api.getGroupManager().isLoaded(group)) return;
        User user = api.getUserManager().loadUser(player.getUniqueId()).join();
        Node node = api.getNodeFactory().makeGroupNode(group).setExpiry(seconds, TimeUnit.SECONDS).build();
        user.setPermission(node);
        api.getUserManager().saveUser(user);
    }

    public void removePlayerFromGroup(OfflinePlayer player, String group) {
        if (!api.getGroupManager().isLoaded(group)) return;
        User user = api.getUserManager().loadUser(player.getUniqueId()).join();
        Node node = api.getNodeFactory().makeGroupNode(group).build();
        user.unsetPermission(node);
        api.getUserManager().saveUser(user);
    }

    public void removePlayerFromGroup(OfflinePlayer player, String group, World world) {
        if (!api.getGroupManager().isLoaded(group)) return;
        User user = api.getUserManager().loadUser(player.getUniqueId()).join();
        Node node = api.getNodeFactory().makeGroupNode(group).setWorld(world.getName()).build();
        user.unsetPermission(node);
        api.getUserManager().saveUser(user);
    }

    public OfflinePlayer[] getPlayersInGroup(String group) {
        return null; //TODO figure this out
    }

    public void setGroupWeight(String group, int weight) {
        //TODO figure this out
    }

    public void setGroupRank(String group, int rank) {
        //TODO figure this out
    }

    public int getGroupWeight(String group) {
        Group groupT = api.getGroup(group);
        if (groupT == null) return 0;
        if (groupT.getWeight().isPresent()) {
            return groupT.getWeight().getAsInt();
        }
        return 0;
    }

    public int getGroupRank(String group) {
        return 0;
    }

    public void setGroupPrefix(String group, String prefix) {
        Group groupT = api.getGroup(group);
        Node node = api.getNodeFactory().makePrefixNode(1, prefix).build();
        if (groupT == null) return;
        groupT.setPermission(node);
        api.getGroupManager().saveGroup(groupT);
    }

    public void setGroupPrefix(String group, String prefix, World world) {
        Group groupT = api.getGroup(group);
        Node node = api.getNodeFactory().makePrefixNode(1, prefix).setWorld(world.getName()).build();
        if (groupT == null) return;
        groupT.setPermission(node);
        api.getGroupManager().saveGroup(groupT);
    }

    public String getGroupPrefix(String group) {
        Group groupT = api.getGroup(group);
        return null; //TODO figure this out
    }

    public String getGroupPrefix(String group, World world) {
        return null; //TODO figure this out
    }

    public void setGroupSuffix(String group, String suffix) {
        Group groupT = api.getGroup(group);
        Node node = api.getNodeFactory().makeSuffixNode(1, suffix).build();
        if (groupT == null) return;
        groupT.setPermission(node);
        api.getGroupManager().saveGroup(groupT);
    }

    public void setGroupSuffix(String group, String suffix, World world) {
        Group groupT = api.getGroup(group);
        Node node = api.getNodeFactory().makeSuffixNode(1, suffix).setWorld(world.getName()).build();
        if (groupT == null) return;
        groupT.setPermission(node);
        api.getGroupManager().saveGroup(groupT);
    }

    public String getGroupSuffix(String group) {
        return null; //TODO figure this out
    }

    public String getGroupSuffix(String group, World world) {
        return null; //TODO figure this out
    }

}
