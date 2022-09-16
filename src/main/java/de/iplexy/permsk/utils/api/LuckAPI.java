package de.iplexy.permsk.utils.api;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.cacheddata.CachedPermissionData;
import net.luckperms.api.context.DefaultContextKeys;
import net.luckperms.api.context.ImmutableContextSet;
import net.luckperms.api.model.data.DataMutateResult;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import net.luckperms.api.node.NodeType;
import net.luckperms.api.node.matcher.NodeMatcher;
import net.luckperms.api.node.types.InheritanceNode;
import net.luckperms.api.node.types.PermissionNode;
import net.luckperms.api.node.types.PrefixNode;
import net.luckperms.api.node.types.SuffixNode;
import net.luckperms.api.query.QueryMode;
import net.luckperms.api.query.QueryOptions;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;

import java.util.*;

public class LuckAPI implements API {
    
    private final LuckPerms api = LuckPermsProvider.get();
    
    public List<String> getInheritedGroups(OfflinePlayer p) {
        List<String> groups = new ArrayList<>();
        User user = api.getUserManager().getUser(p.getUniqueId());
        assert user != null;
        for (Group g : user.getInheritedGroups(QueryOptions.builder(QueryMode.CONTEXTUAL).build())) {
            groups.add(g.getName());
        }
        return groups;
    }    public void addPerm(OfflinePlayer player, String permission) {
        Node node = Node.builder(permission).build();
        api.getUserManager().modifyUser(player.getUniqueId(), (User user) -> {
            DataMutateResult result = user.data().add(node);
        });
    }
    
    public void addPerm(OfflinePlayer player, String permission, World world) {
        ImmutableContextSet set = ImmutableContextSet.of("world", world.getName());
        Node node = Node.builder(permission).withContext(set).build();
        api.getUserManager().modifyUser(player.getUniqueId(), (User user) -> {
            DataMutateResult result = user.data().add(node);
        });
    }
    
    public void addPerm(OfflinePlayer player, String permission, World world, int seconds) {
        ImmutableContextSet set = ImmutableContextSet.of("world", world.getName());
        Node node = Node.builder(permission).expiry(seconds).withContext(set).build();
        api.getUserManager().modifyUser(player.getUniqueId(), (User user) -> {
            DataMutateResult result = user.data().add(node);
        });
    }
    
    public void addPerm(OfflinePlayer player, String permission, int seconds) {
        Node node = Node.builder(permission).expiry(seconds).build();
        api.getUserManager().modifyUser(player.getUniqueId(), (User user) -> {
            DataMutateResult result = user.data().add(node);
        });
    }
    
    public void addPerm(String group, String permission) {
        Node node = Node.builder(permission).build();
        api.getGroupManager().modifyGroup(group, (Group theGroup) -> {
            DataMutateResult result = theGroup.data().add(node);
        });
    }
    
    public void addPerm(String group, String permission, World world) {
        ImmutableContextSet set = ImmutableContextSet.of("world", world.getName());
        Node node = Node.builder(permission).withContext(set).build();
        api.getGroupManager().modifyGroup(group, (Group theGroup) -> {
            DataMutateResult result = theGroup.data().add(node);
        });
    }
    
    public void addPerm(String group, String permission, World world, int seconds) {
        ImmutableContextSet set = ImmutableContextSet.of("world", world.getName());
        Node node = Node.builder(permission).expiry(seconds).withContext(set).build();
        api.getGroupManager().modifyGroup(group, (Group theGroup) -> {
            DataMutateResult result = theGroup.data().add(node);
        });
    }
    
    public void addPerm(String group, String permission, int seconds) {
        Node node = Node.builder(permission).expiry(seconds).build();
        api.getGroupManager().modifyGroup(group, (Group theGroup) -> {
            DataMutateResult result = theGroup.data().add(node);
        });
    }
    
    public void removePerm(OfflinePlayer player, String permission) {
        Node node = Node.builder(permission).build();
        api.getUserManager().modifyUser(player.getUniqueId(), (User user) -> {
            DataMutateResult result = user.data().remove(node);
            for (World worldB : Bukkit.getServer().getWorlds()) {
                Node nodeB = Node.builder(permission).withContext("world", worldB.getName()).build();
                DataMutateResult resultB = user.data().remove(nodeB);
            }
        });
    }
    
    public void removePerm(OfflinePlayer player, String permission, World world) {
        Node node = Node.builder(permission).withContext("world", world.getName()).build();
        api.getUserManager().modifyUser(player.getUniqueId(), (User user) -> {
            DataMutateResult result = user.data().remove(node);
            for (World worldB : Bukkit.getServer().getWorlds()) {
                Node nodeB = Node.builder(permission).withContext("world", worldB.getName()).build();
                DataMutateResult resultB = user.data().remove(nodeB);
            }
        });
    }
    
    public void removePerm(String group, String permission) {
        Node node = Node.builder(permission).build();
        api.getGroupManager().modifyGroup(group, (Group groupB) -> {
            DataMutateResult result = groupB.data().remove(node);
            for (World worldB : Bukkit.getServer().getWorlds()) {
                Node nodeB = Node.builder(permission).withContext("world", worldB.getName()).build();
                DataMutateResult resultB = groupB.data().remove(nodeB);
            }
        });
    }
    
    public void removePerm(String group, String permission, World world) {
        Node node = Node.builder(permission).withContext("world", world.getName()).build();
        api.getGroupManager().modifyGroup(group, (Group groupB) -> {
            DataMutateResult result = groupB.data().remove(node);
            for (World worldB : Bukkit.getServer().getWorlds()) {
                Node nodeB = Node.builder(permission).withContext("world", worldB.getName()).build();
                DataMutateResult resultB = groupB.data().remove(nodeB);
            }
        });
    }
    
    public String[] getPerm(OfflinePlayer player) {
        ArrayList<String> perms = new ArrayList<>();
        User user = api.getUserManager().loadUser(player.getUniqueId()).join();
        CachedPermissionData permissionData = user.getCachedData().getPermissionData();
        for (String perm : permissionData.getPermissionMap().keySet()) {
            PermissionNode node = PermissionNode.builder(perm).build();
            if (permissionData.getPermissionMap().get(perm)) {
                if (!perm.startsWith("group.")) {
                    perms.add(perm);
                }
            }
        }
        return perms.toArray(new String[0]);
    }
    
    public String[] getPerm(OfflinePlayer player, World world) {
        ArrayList<String> perms = new ArrayList<>();
        User user = api.getUserManager().loadUser(player.getUniqueId()).join();
        for (String perm : user.getCachedData().getPermissionData().getPermissionMap().keySet()) {
            Node node = PermissionNode.builder(perm).build();
            if (node.getContexts().contains("world", world.getName())) {
                perms.add(perm);
            }
        }
        return perms.toArray(new String[0]);
    }
    
    public String[] getPerm(String group) {
        ArrayList<String> perms = new ArrayList<>();
        Group group1 = api.getGroupManager().getGroup(group);
        CachedPermissionData permissionData = group1.getCachedData().getPermissionData();
        for (String perm : permissionData.getPermissionMap().keySet()) {
            PermissionNode node = PermissionNode.builder(perm).build();
            if (permissionData.getPermissionMap().get(perm)) {
                if (!perm.startsWith("group.")) {
                    perms.add(perm);
                }
            }
        }
        return perms.toArray(new String[0]);
    }
    
    public String[] getPerm(String group, World world) {
        ArrayList<String> perms = new ArrayList<>();
        Group group1 = api.getGroupManager().getGroup(group);
        for (String perm : group1.getCachedData().getPermissionData().getPermissionMap().keySet()) {
            Node node = PermissionNode.builder(perm).build();
            if (node.getContexts().contains("world", world.getName())) {
                perms.add(perm);
            }
        }
        return perms.toArray(new String[0]);
    }
    
    public void createGroup(String group) {
        api.getGroupManager().createAndLoadGroup(group);
    }
    
    public void createGroup(String group, String[] parents) {
        api.getGroupManager().createAndLoadGroup(group);
        Group groupT = api.getGroupManager().getGroup(group);
        
        if (groupT == null) return;
        Node node;
        for (String parent : parents) {
            addPerm(group, "group." + parent);
        }
    }
    
    public void removeGroup(String group) {
        Group groupT = api.getGroupManager().getGroup(group);
        if (groupT != null)
            api.getGroupManager().deleteGroup(groupT);
    }
    
    public void addPlayerToGroup(OfflinePlayer player, String group) {
        addPerm(player, "group." + group);
    }
    
    public void addPlayerToGroup(OfflinePlayer player, String group, World world) {
        addPerm(player, "group." + group, world);
    }
    
    public void addPlayerToGroup(OfflinePlayer player, String group, World world, int seconds) {
        addPerm(player, "group." + group, world, seconds);
    }
    
    public void addPlayerToGroup(OfflinePlayer player, String group, int seconds) {
        addPerm(player, "group." + group, seconds);
    }
    
    public void removePlayerFromGroup(OfflinePlayer player, String group) {
        removePerm(player, "group." + group);
    }
    
    public void removePlayerFromGroup(OfflinePlayer player, String group, World world) {
        removePerm(player, "group." + group, world);
    }
    
    public OfflinePlayer[] getPlayersInGroup(String group) {
        ArrayList<OfflinePlayer> list = new ArrayList<>();
        Group group1 = api.getGroupManager().getGroup(group);
        NodeMatcher<InheritanceNode> matcher = NodeMatcher.key(InheritanceNode.builder(group1).build());
        api.getUserManager().searchAll(matcher).thenAccept((Map<UUID, Collection<InheritanceNode>> map) -> {
            Set<UUID> memberUUIDS = map.keySet();
            for (UUID uuid : memberUUIDS) {
                list.add(Bukkit.getOfflinePlayer(uuid));
            }
        });
        return list.toArray(new OfflinePlayer[0]);
    }
    
    public void setGroupWeight(String group, int weight) {
        Group groupT = api.getGroupManager().getGroup(group);
        if (groupT == null) return;
        for (String perm : groupT.getCachedData().getPermissionData().getPermissionMap().keySet()) {
            if (perm.startsWith("weight.")) {
                removePerm(group, perm);
            }
        }
        addPerm(group, "weight." + weight);
    }
    
    public void setGroupRank(String group, int rank) {
        // Not supported in luck perms
    }
    
    public int getGroupWeight(String group) {
        Group groupT = api.getGroupManager().getGroup(group);
        if (groupT == null) return 0;
        if (groupT.getWeight().isPresent()) {
            return groupT.getWeight().getAsInt();
        }
        return 0;
    }
    
    public int getGroupRank(String group) {
        return 0;
        // Not supported in LuckPerms
    }
    
    public void setGroupPrefix(String group, String prefix) {
        Group groupT = api.getGroupManager().getGroup(group);
        if (groupT == null) return;
        for (String perm : api.getGroupManager().getGroup(group).getCachedData().getPermissionData().getPermissionMap()
            .keySet()) {
            if (perm.startsWith("prefix."))
                removePerm(group, perm);
        }
        addPerm(group, "prefix." + getWeight(group) + "." + prefix);
    }
    
    public void setGroupPrefix(String group, String prefix, World world) {
        PrefixNode node = PrefixNode.builder(prefix, getGroupWeight(group)).withContext(DefaultContextKeys.WORLD_KEY, world.getName()).build();
        api.getGroupManager().modifyGroup(group, (Group theGroup) -> {
            DataMutateResult result = theGroup.data().add(node);
        });
    }
    
    public String getGroupPrefix(String group) {
        Group groupT = api.getGroupManager().getGroup(group);
        return groupT.getCachedData().getMetaData().getPrefix();
    }
    
    public String getGroupPrefix(String group, World world) {
        //TODO TEST
        for(PrefixNode node : api.getGroupManager().getGroup(group).getNodes(NodeType.PREFIX)){
            if(node.getContexts().contains(DefaultContextKeys.WORLD_KEY, world.getName())){
                return node.getMetaValue();
            }
        }
        return "null";
    }
    
    
    public void setGroupSuffix(String group, String suffix) {
        Group groupT = api.getGroupManager().getGroup(group);
        if (groupT == null) return;
        for (String perm : api.getGroupManager().getGroup(group).getCachedData().getPermissionData().getPermissionMap()
            .keySet()) {
            if (perm.startsWith("suffix."))
                removePerm(group, perm);
        }
        addPerm(group, "prefix." + getWeight(group) + "." + suffix);
    }
    
    public void setGroupSuffix(String group, String suffix, World world) {
        SuffixNode node = SuffixNode.builder(suffix, getGroupWeight(group)).withContext(DefaultContextKeys.WORLD_KEY, world.getName()).build();
        api.getGroupManager().modifyGroup(group, (Group theGroup) -> {
            DataMutateResult result = theGroup.data().add(node);
        });
    }
    
    
    public String getGroupSuffix(String group) {
        Group groupT = api.getGroupManager().getGroup(group);
        return groupT.getCachedData().getMetaData().getSuffix();
    }
    
    public String getGroupSuffix(String group, World world) {
        //TODO TEST
        for(SuffixNode node : api.getGroupManager().getGroup(group).getNodes(NodeType.SUFFIX)){
            if(node.getContexts().contains(DefaultContextKeys.WORLD_KEY, world.getName())){
                return node.getMetaValue();
            }
        }
        return "null";
    }
    
    
    public void setPlayerPrefix(OfflinePlayer player, String prefix) {
        User user = api.getUserManager().getUser(player.getUniqueId());
        if (user == null) return;
        for (String perm : user.getCachedData().getPermissionData().getPermissionMap().keySet()) {
            if (perm.startsWith("prefix."))
                removePerm(player, perm);
        }
        addPerm(player, "prefix." + getWeight(getPrimaryGroup(player)) + "." + prefix);
    }
    
    
    public void setPlayerPrefix(OfflinePlayer player, String prefix, World world) {
        PrefixNode node = PrefixNode.builder(prefix, 100).withContext(DefaultContextKeys.WORLD_KEY, world.getName()).build();
        api.getUserManager().modifyUser(player.getUniqueId(), (User user) -> {
            DataMutateResult result = user.data().add(node);
        });
    }
    
    
    public String getPlayerPrefix(OfflinePlayer player) {
        User user = api.getUserManager().getUser(player.getUniqueId());
        return user.getCachedData().getMetaData().getPrefix();
    }
    
    public String getPlayerPrefix(OfflinePlayer player, World world) {
        //TODO TEST
        for(PrefixNode node : api.getUserManager().getUser(player.getUniqueId()).getNodes(NodeType.PREFIX)){
            if(node.getContexts().contains(DefaultContextKeys.WORLD_KEY, world.getName())){
                return node.getMetaValue();
            }
        }
        return "null";
    }
    
    
    public void setPlayerSuffix(OfflinePlayer player, String suffix) {
        User user = api.getUserManager().getUser(player.getUniqueId());
        if (user == null) return;
        for (String perm : user.getCachedData().getPermissionData().getPermissionMap().keySet()) {
            if (perm.startsWith("suffix."))
                removePerm(player, perm);
        }
        addPerm(player, "suffix." + getWeight(getPrimaryGroup(player)) + "." + suffix);
    }
    
    
    public void setPlayerSuffix(OfflinePlayer player, String suffix, World world) {
        SuffixNode node = SuffixNode.builder(suffix, 100).withContext(DefaultContextKeys.WORLD_KEY, world.getName()).build();
        api.getUserManager().modifyUser(player.getUniqueId(), (User user) -> {
            DataMutateResult result = user.data().add(node);
        });
    }
    
    public String getPlayerSuffix(OfflinePlayer player) {
        User user = api.getUserManager().getUser(player.getUniqueId());
        return user.getCachedData().getMetaData().getSuffix();
    }
    
    
    public String getPlayerSuffix(OfflinePlayer player, World world) {
        //TODO TEST
        for(SuffixNode node : api.getUserManager().getUser(player.getUniqueId()).getNodes(NodeType.SUFFIX)){
            if(node.getContexts().contains(DefaultContextKeys.WORLD_KEY, world.getName())){
                return node.getMetaValue();
            }
        }
        return "null";
    }
    
    public String getPrimaryGroup(OfflinePlayer p) {
        User user = api.getUserManager().getUser(p.getUniqueId());
        return user.getPrimaryGroup();
    }
    

    
    public int getWeight(String g) {
        return api.getGroupManager().getGroup(g).getWeight().orElse(-1);
    }
}
