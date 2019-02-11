package tk.shanebee.skperm.utils.api;

import me.lucko.luckperms.LuckPerms;
import me.lucko.luckperms.api.Group;
import me.lucko.luckperms.api.LuckPermsApi;
import me.lucko.luckperms.api.Node;
import me.lucko.luckperms.api.User;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;

import java.util.ArrayList;
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
            for (World worldB : Bukkit.getServer().getWorlds()) {
                node = api.getNodeFactory().newBuilder(permission).setWorld(worldB.getName()).build();
                user.unsetPermission(node);
            }
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
            for (World world : Bukkit.getServer().getWorlds()) {
                node = api.getNodeFactory().newBuilder(permission).setWorld(world.getName()).build();
                groupT.unsetPermission(node);
            }
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

    public String[] getPerm(OfflinePlayer player) {
        User user = api.getUserManager().loadUser(player.getUniqueId()).join();
        ArrayList<String> perms = new ArrayList<>();
        for (Node perm : user.getPermissions()) {
            if (!perm.getPermission().startsWith("group."))
                perms.add(perm.getPermission());
        }
        return perms.toArray(new String[0]);
    }

    public String[] getPerm(OfflinePlayer player, World world) {
        User user = api.getUserManager().loadUser(player.getUniqueId()).join();
        ArrayList<String> perms = new ArrayList<>();
        for (Node perm : user.getPermissions()) {
            if (!perm.getPermission().startsWith("group.")) {
                if (perm.getWorld().isPresent() && perm.getWorld().toString().contains("[" + world.getName() + "]")) {
                    perms.add(perm.getPermission());
                }
            }
        }
        return perms.toArray(new String[0]);
    }

    public String[] getPerm(String group) {
        Group groupT = api.getGroup(group);
        if (groupT == null) return null;
        ArrayList<String> perms = new ArrayList<>();
        for (Node perm : groupT.getPermissions()) {
            if (!perm.getPermission().startsWith("prefix.") &&
                    !perm.getPermission().startsWith("weight.") &&
                    !perm.getPermission().startsWith("suffix.")) {
                perms.add(perm.getPermission());
            }
        }
        return perms.toArray(new String[0]);
    }

    public String[] getPerm(String group, World world) {
        Group groupT = api.getGroup(group);
        if (groupT == null) return null;
        ArrayList<String> perms = new ArrayList<>();
        for (Node perm : groupT.getPermissions()) {
            if (!perm.getPermission().startsWith("prefix.") &&
                    !perm.getPermission().startsWith("weight.") &&
                    !perm.getPermission().startsWith("suffix.")) {
                if (perm.getWorld().toString().contains("[" + world.getName() + "]"))
                perms.add(perm.getPermission());
            }
        }
        return perms.toArray(new String[0]);
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
        for (World worldB : Bukkit.getServer().getWorlds()) {
            node = api.getNodeFactory().makeGroupNode(group).setWorld(worldB.getName()).build();
            user.unsetPermission(node);
        }
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
        ArrayList<OfflinePlayer> list = new ArrayList<>();
        for (OfflinePlayer player : Bukkit.getServer().getOfflinePlayers()) {
            api.getUserManager().loadUser(player.getUniqueId()).join();
            if (!api.getUserManager().isLoaded(player.getUniqueId())) continue;
            for (Node node : api.getUser(player.getUniqueId()).getAllNodes()) {
                if (!node.getPermission().contains("group." + group)) continue;
                list.add(player);
            }
        }
        return list.toArray(new OfflinePlayer[0]);
    } // TODO NEEDS SOME WORK

    public void setGroupWeight(String group, int weight) {
        Group groupT = api.getGroup(group);
        if (groupT == null) return;
        for (Node perm : groupT.getPermissions()) {
            if (perm.getPermission().startsWith("weight.")) {
                groupT.unsetPermission(perm);
            }
        }
        Node newWeight = api.getNodeFactory().newBuilder("weight." + weight).build();
        groupT.setPermission(newWeight);
        api.getGroupManager().saveGroup(groupT);
    }

    public void setGroupRank(String group, int rank) {
    } // TODO not supported by LuckPerms?

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
    } // TODO not supported by LuckPerms?

    public void setGroupPrefix(String group, String prefix) {
        Group groupT = api.getGroup(group);
        if (groupT == null) return;
        for (Node nodeD : api.getGroup(group).getPermissions()) {
            if (nodeD.getPermission().startsWith("prefix."))
                groupT.unsetPermission(nodeD);
        }
        Node node = api.getNodeFactory().makePrefixNode(0, prefix).build();
        groupT.setPermission(node);
        api.getGroupManager().saveGroup(groupT);
    }

    public void setGroupPrefix(String group, String prefix, World world) {
        Group groupT = api.getGroup(group);
        Node node = api.getNodeFactory().makePrefixNode(0, prefix).setWorld(world.getName()).build();
        if (groupT == null) return;
        for (Node nodeD : api.getGroup(group).getPermissions()) {
            if (!nodeD.getPermission().startsWith("prefix.")) continue;
            if (!nodeD.getWorld().toString().startsWith("Optional[" + world.getName() + "]")) continue;
            groupT.unsetPermission(nodeD);
        }
        groupT.setPermission(node);
        api.getGroupManager().saveGroup(groupT);
    }

    public String getGroupPrefix(String group) {
        Group groupT = api.getGroupManager().getGroup(group);
        String prefix = null;
        if (groupT == null) return null;
        for (Node node : groupT.getPermissions()) {
            if (!node.getPermission().startsWith("prefix.")) continue;
            prefix = node.getPrefix().getValue();
        }
        return prefix;
    }

    public String getGroupPrefix(String group, World world) {
        Group groupT = api.getGroupManager().getGroup(group);
        String prefix = null;
        if (groupT == null) return null;
        for (Node node : groupT.getPermissions()) {
            if (!node.getPermission().startsWith("prefix.")) continue;
            if (!node.getWorld().toString().startsWith("Optional[" + world.getName() + "]")) continue;
            prefix = node.getPrefix().getValue();
        }
        return prefix;
    }

    public void setGroupSuffix(String group, String suffix) {
        Group groupT = api.getGroup(group);
        if (groupT == null) return;
        for (Node nodeD : api.getGroup(group).getPermissions()) {
            if (nodeD.getPermission().startsWith("suffix."))
                groupT.unsetPermission(nodeD);
        }
        Node node = api.getNodeFactory().makeSuffixNode(0, suffix).build();
        groupT.setPermission(node);
        api.getGroupManager().saveGroup(groupT);
    }

    public void setGroupSuffix(String group, String suffix, World world) {
        Group groupT = api.getGroup(group);
        Node node = api.getNodeFactory().makeSuffixNode(0, suffix).setWorld(world.getName()).build();
        if (groupT == null) return;
        for (Node nodeD : api.getGroup(group).getPermissions()) {
            if (!nodeD.getPermission().startsWith("suffix.")) continue;
            if (!nodeD.getWorld().toString().startsWith("Optional[" + world.getName() + "]")) continue;
            groupT.unsetPermission(nodeD);
        }
        groupT.setPermission(node);
        api.getGroupManager().saveGroup(groupT);
    }

    public String getGroupSuffix(String group) {
        Group groupT = api.getGroupManager().getGroup(group);
        String suffix = null;
        if (groupT == null) return null;
        for (Node node : groupT.getPermissions()) {
            if (!node.getPermission().startsWith("suffix.")) continue;
            suffix = node.getSuffix().getValue();
        }
        return suffix;
    }

    public String getGroupSuffix(String group, World world) {
        Group groupT = api.getGroupManager().getGroup(group);
        String suffix = null;
        if (groupT == null) return null;
        for (Node node : groupT.getPermissions()) {
            if (!node.getPermission().startsWith("suffix.")) continue;
            if (!node.getWorld().toString().startsWith("Optional[" + world.getName() + "]")) continue;
            suffix = node.getSuffix().getValue();
        }
        return suffix;
    }

    public void setPlayerPrefix(OfflinePlayer player, String prefix) {
        api.getUserManager().loadUser(player.getUniqueId()).join();
        User user = api.getUser(player.getUniqueId());
        if (user == null) return;
        for (Node node : user.getPermissions()) {
            if (node.getPermission().startsWith("prefix."))
                user.unsetPermission(node);
        }
        Node node = api.getNodeFactory().makePrefixNode(0, prefix).build();
        user.setPermission(node);
        api.getUserManager().saveUser(user);
    }

    public void setPlayerPrefix(OfflinePlayer player, String prefix, World world) {
        api.getUserManager().loadUser(player.getUniqueId()).join();
        User user = api.getUser(player.getUniqueId());
        if (user == null) return;
        for (Node node :user.getPermissions()) {
            if (!node.getPermission().startsWith("prefix.")) continue;
            if (!node.getWorld().toString().startsWith("Optional[" + world.getName() + "]")) continue;
            user.unsetPermission(node);
        }
        Node node = api.getNodeFactory().makePrefixNode(0, prefix).setWorld(world.getName()).build();
        user.setPermission(node);
        api.getUserManager().saveUser(user);
    }

    public String getPlayerPrefix(OfflinePlayer player) {
        api.getUserManager().loadUser(player.getUniqueId()).join();
        User user = api.getUser(player.getUniqueId());

        String prefix = null;
        if (user == null) return null;
        for (Node node : user.getPermissions()) {
            if (!node.getPermission().startsWith("prefix.")) continue;
            prefix = node.getPrefix().getValue();
        }
        return prefix;
    }

    public String getPlayerPrefix(OfflinePlayer player, World world) {
        api.getUserManager().loadUser(player.getUniqueId()).join();
        User user = api.getUser(player.getUniqueId());
        String prefix = null;
        if (user == null) return null;
        for (Node node : user.getPermissions()) {
            if (!node.getPermission().startsWith("prefix.")) continue;
            if (!node.getWorld().toString().startsWith("Optional[" + world.getName() + "]")) continue;
            prefix = node.getPrefix().getValue();
        }
        return prefix;
    }

    public void setPlayerSuffix(OfflinePlayer player, String suffix) {
        api.getUserManager().loadUser(player.getUniqueId()).join();
        User user = api.getUser(player.getUniqueId());
        if (user == null) return;
        for (Node node : user.getPermissions()) {
            if (node.getPermission().startsWith("suffix."))
                user.unsetPermission(node);
        }
        Node node = api.getNodeFactory().makeSuffixNode(0, suffix).build();
        user.setPermission(node);
        api.getUserManager().saveUser(user);
    }

    public void setPlayerSuffix(OfflinePlayer player, String suffix, World world) {
        api.getUserManager().loadUser(player.getUniqueId()).join();
        User user = api.getUser(player.getUniqueId());
        if (user == null) return;
        for (Node node :user.getPermissions()) {
            if (!node.getPermission().startsWith("suffix.")) continue;
            if (!node.getWorld().toString().startsWith("Optional[" + world.getName() + "]")) continue;
            user.unsetPermission(node);
        }
        Node node = api.getNodeFactory().makeSuffixNode(0, suffix).setWorld(world.getName()).build();
        user.setPermission(node);
        api.getUserManager().saveUser(user);
    }

    public String getPlayerSuffix(OfflinePlayer player) {
        api.getUserManager().loadUser(player.getUniqueId()).join();
        User user = api.getUser(player.getUniqueId());
        String suffix = null;
        if (user == null) return null;
        for (Node node : user.getPermissions()) {
            if (!node.getPermission().startsWith("suffix.")) continue;
            suffix = node.getSuffix().getValue();
        }
        return suffix;
    }

    public String getPlayerSuffix(OfflinePlayer player, World world) {
        api.getUserManager().loadUser(player.getUniqueId()).join();
        User user = api.getUser(player.getUniqueId());
        String suffix = null;
        if (user == null) return null;
        for (Node node : user.getPermissions()) {
            if (!node.getPermission().startsWith("suffix.")) continue;
            if (!node.getWorld().toString().startsWith("Optional[" + world.getName() + "]")) continue;
            suffix = node.getSuffix().getValue();
        }
        return suffix;
    }

}
