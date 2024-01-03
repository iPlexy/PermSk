package de.iplexy.permsk.api;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.context.ImmutableContextSet;
import net.luckperms.api.model.data.DataMutateResult;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import net.luckperms.api.node.matcher.NodeMatcher;
import net.luckperms.api.node.types.InheritanceNode;
import net.luckperms.api.node.types.PermissionNode;
import net.luckperms.api.query.QueryMode;
import net.luckperms.api.query.QueryOptions;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;

import java.util.*;
import java.util.stream.Collectors;

public class LuckApi implements PermissionApi {

    private final LuckPerms api = LuckPermsProvider.get();

    @Override
    public void addPerm(OfflinePlayer player, String permission) {
        Node node = Node.builder(permission).build();
        api.getUserManager().modifyUser(player.getUniqueId(), (User user) -> {
            DataMutateResult result = user.data().add(node);
        });
    }

    @Override
    public void addPerm(OfflinePlayer player, String permission, World world) {
        ImmutableContextSet set = ImmutableContextSet.of("world", world.getName());
        Node node = Node.builder(permission).withContext(set).build();
        api.getUserManager().modifyUser(player.getUniqueId(), (User user) -> {
            DataMutateResult result = user.data().add(node);
        });
    }

    @Override
    public void addPerm(OfflinePlayer player, String permission, World world, int seconds) {
        ImmutableContextSet set = ImmutableContextSet.of("world", world.getName());
        Node node = Node.builder(permission).expiry(seconds).withContext(set).build();
        api.getUserManager().modifyUser(player.getUniqueId(), (User user) -> {
            DataMutateResult result = user.data().add(node);
        });
    }

    @Override
    public void addPerm(OfflinePlayer player, String permission, int seconds) {
        Node node = Node.builder(permission).expiry(seconds).build();
        api.getUserManager().modifyUser(player.getUniqueId(), (User user) -> {
            DataMutateResult result = user.data().add(node);
        });
    }

    @Override
    public void addPerm(String groupName, String permission) {
        Node node = Node.builder(permission).build();
        api.getGroupManager().modifyGroup(groupName, (Group group) -> group.data().add(node));
    }

    @Override
    public void addPerm(String groupName, String permission, World world) {
        ImmutableContextSet set = ImmutableContextSet.of("world", world.getName());
        Node node = Node.builder(permission).withContext(set).build();
        api.getGroupManager().modifyGroup(groupName, (Group group) -> group.data().add(node));
    }

    @Override
    public void addPerm(String groupName, String permission, World world, int seconds) {
        ImmutableContextSet set = ImmutableContextSet.of("world", world.getName());
        Node node = Node.builder(permission).expiry(seconds).withContext(set).build();
        api.getGroupManager().modifyGroup(groupName, (Group group) -> group.data().add(node));
    }

    @Override
    public void addPerm(String groupName, String permission, int seconds) {
        Node node = Node.builder(permission).expiry(seconds).build();
        api.getGroupManager().modifyGroup(groupName, (Group group) -> group.data().add(node));
    }

    @Override
    public void removePerm(OfflinePlayer player, String permission) {
        Node node = Node.builder(permission).build();
        api.getUserManager().modifyUser(player.getUniqueId(), (User user) -> {
            DataMutateResult result = user.data().remove(node);
        });
    }

    @Override
    public void removePerm(OfflinePlayer player, String permission, World world) {
        Node node = Node.builder(permission).withContext("world", world.getName()).build();
        api.getUserManager().modifyUser(player.getUniqueId(), (User user) -> user.data().remove(node));
    }

    @Override
    public void removePerm(String groupName, String permission) {
        Node node = Node.builder(permission).build();
        api.getGroupManager().modifyGroup(groupName, (Group group) -> {
            DataMutateResult result = group.data().remove(node);
        });
    }

    @Override
    public void removePerm(String groupName, String permission, World world) {
        Node node = Node.builder(permission).withContext("world", world.getName()).build();
        api.getGroupManager().modifyGroup(groupName, (Group group) -> group.data().remove(node));
    }

    @Override
    public List<String> getPerms(OfflinePlayer player) {
        User user = getUser(player);
        return user.getCachedData().getPermissionData().getPermissionMap()
                .entrySet().stream()
                .filter(entry -> entry.getValue() && !entry.getKey().startsWith("group."))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getPerms(OfflinePlayer player, World world) {
        User user = getUser(player);
        return user.getCachedData().getPermissionData().getPermissionMap()
                .entrySet().stream()
                .filter(perm -> PermissionNode.builder(perm.getKey()).build().getContexts().contains("world", world.getName()))
                .filter(entry -> entry.getValue() && !entry.getKey().startsWith("group."))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getPerms(String groupName) {
        Group group = api.getGroupManager().getGroup(groupName);
        return group.getCachedData().getPermissionData().getPermissionMap()
                .entrySet().stream()
                .filter(entry -> entry.getValue() && !entry.getKey().startsWith("group."))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getPerms(String groupName, World world) {
        Group group = api.getGroupManager().getGroup(groupName);
        return group.getCachedData().getPermissionData().getPermissionMap()
                .entrySet().stream()
                .filter(perm -> PermissionNode.builder(perm.getKey()).build().getContexts().contains("world", world.getName()))
                .filter(entry -> entry.getValue() && !entry.getKey().startsWith("group."))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    @Override
    public void createGroup(String groupName) {
        api.getGroupManager().createAndLoadGroup(groupName);
    }

    @Override
    public void createGroup(String groupName, List<String> parentGroups) {
        api.getGroupManager().createAndLoadGroup(groupName);
        parentGroups.forEach(parent -> addPerm(groupName, "group." + parent));
    }

    @Override
    public void deleteGroup(String groupName) {
        Group group = api.getGroupManager().getGroup(groupName);
        api.getGroupManager().deleteGroup(group);
    }

    @Override
    public void addPlayerToGroup(OfflinePlayer player, String groupName) {
        addPerm(player, "group." + groupName);
    }

    @Override
    public void addPlayerToGroup(OfflinePlayer player, String groupName, World world) {
        addPerm(player, "group." + groupName, world);
    }

    @Override
    public void addPlayerToGroup(OfflinePlayer player, String groupName, World world, int seconds) {
        addPerm(player, "group." + groupName, world, seconds);
    }

    @Override
    public void addPlayerToGroup(OfflinePlayer player, String groupName, int seconds) {
        addPerm(player, "group." + groupName, seconds);
    }

    @Override
    public void removePlayerFromGroup(OfflinePlayer player, String groupName) {
        removePerm(player, "group." + groupName);
    }

    @Override
    public void removePlayerFromGroup(OfflinePlayer player, String groupName, World world) {
        removePerm(player, "group." + groupName, world);
    }

    @Override
    public List<OfflinePlayer> getPlayersInGroup(String groupName) {
        List<OfflinePlayer> players = new ArrayList<>();
        Group group = api.getGroupManager().getGroup(groupName);
        NodeMatcher<InheritanceNode> matcher = NodeMatcher.key(InheritanceNode.builder(group).build());
        api.getUserManager().searchAll(matcher).thenAccept((Map<UUID, Collection<InheritanceNode>> map) -> {
            map.keySet().forEach(uuid -> players.add(Bukkit.getOfflinePlayer(uuid)));
        });
        return players;
    }

    @Override
    public List<OfflinePlayer> getPlayersInGroup(String groupName, World world) {
        //TODO: Implement
        return null;
    }

    @Override
    public void setGroupWeight(String groupName, int weight) {
        Group group = api.getGroupManager().getGroup(groupName);
        group.getCachedData().getPermissionData().getPermissionMap().keySet().stream()
                .filter(perm -> perm.startsWith("weight."))
                .forEach(perm -> removePerm(groupName, perm));
        addPerm(groupName, "weight." + weight);
    }

    @Override
    public void setGroupRank(String groupName, int rank) {
        //LuckPerms doesn't have a rank system, so we use the weight system
        setGroupWeight(groupName, rank);
    }

    @Override
    public int getGroupWeight(String groupName) {
        Group group = api.getGroupManager().getGroup(groupName);
        if (group == null) return 0;
        if (group.getWeight().isPresent()) {
            return group.getWeight().getAsInt();
        }
        return 0;
    }

    @Override
    public int getGroupRank(String groupName) {
        //LuckPerms doesn't have a rank system, so we use the weight system
        return getGroupWeight(groupName);
    }

    @Override
    public void setGroupPrefix(String groupName, String prefix) {
        Group group = api.getGroupManager().getGroup(groupName);
        group.getCachedData().getMetaData().getPrefixes().values().stream()
                .filter(perm -> perm.startsWith("prefix."))
                .forEach(perm -> removePerm(groupName, perm));
        addPerm(groupName, "prefix." + getGroupWeight(groupName) + "." + prefix);
    }

    @Override
    public String getGroupPrefix(String groupName) {
        Group group = api.getGroupManager().getGroup(groupName);
        if (group == null) return "";
        return group.getCachedData().getMetaData().getPrefix();
    }

    @Override
    public void setGroupSuffix(String groupName, String suffix) {
        Group group = api.getGroupManager().getGroup(groupName);
        group.getCachedData().getMetaData().getSuffixes().values().stream()
                .filter(perm -> perm.startsWith("prefix."))
                .forEach(perm -> removePerm(groupName, perm));
        addPerm(groupName, "suffix." + getGroupWeight(groupName) + "." + suffix);
    }

    @Override
    public String getGroupSuffix(String groupName) {
        Group group = api.getGroupManager().getGroup(groupName);
        if (group == null) return "";
        return group.getCachedData().getMetaData().getSuffix();
    }

    @Override
    public void setGroupPrefix(String groupName, String prefix, World world) {
        //TODO: Implement
    }

    @Override
    public String getGroupPrefix(String groupName, World world) {
        //TODO: Implement
        return null;
    }

    @Override
    public void setGroupSuffix(String groupName, String suffix, World world) {
        //TODO: Implement

    }

    @Override
    public String getGroupSuffix(String groupName, World world) {
        //TODO: Implement
        return null;
    }

    @Override
    public void setPlayerPrefix(OfflinePlayer player, String prefix) {
        User user = getUser(player);
        user.getCachedData().getMetaData().getSuffixes().values().stream()
                .filter(perm -> perm.startsWith("prefix."))
                .forEach(perm -> removePerm(player, perm));
        addPerm(player, "prefix." + getGroupWeight(user.getPrimaryGroup()) + "." + prefix);
    }

    @Override
    public String getPlayerPrefix(OfflinePlayer player) {
        User user = getUser(player);
        return user.getCachedData().getMetaData().getPrefix();
    }

    @Override
    public void setPlayerSuffix(OfflinePlayer player, String suffix) {
        User user = getUser(player);
        user.getCachedData().getMetaData().getSuffixes().values().stream()
                .filter(perm -> perm.startsWith("suffix."))
                .forEach(perm -> removePerm(player, perm));
        addPerm(player, "suffix." + getGroupWeight(user.getPrimaryGroup()) + "." + suffix);
    }

    @Override
    public String getPlayerSuffix(OfflinePlayer player) {
        User user = getUser(player);
        return user.getCachedData().getMetaData().getSuffix();
    }

    @Override
    public void setPlayerPrefix(OfflinePlayer player, String prefix, World world) {
        //TODO: Implement
    }

    @Override
    public String getPlayerPrefix(OfflinePlayer player, World world) {
        //TODO: Implement
        return null;
    }

    @Override
    public void setPlayerSuffix(OfflinePlayer player, String suffix, World world) {
        //TODO: Implement
    }

    @Override
    public String getPlayerSuffix(OfflinePlayer player, World world) {
        //TODO: Implement
        return null;
    }

    @Override
    public List<String> getInheritedGroups(String groupName) {
        return null;
    }

    @Override
    public List<String> getInheritedGroups(OfflinePlayer player) {
        User user = getUser(player);
        return user.getInheritedGroups(QueryOptions.builder(QueryMode.CONTEXTUAL).build()).stream()
                .map(Group::getName)
                .collect(Collectors.toList());
    }

    @Override
    public Date getGroupExpireDate(OfflinePlayer player, String groupName) {
        User user = getUser(player);

        return user.getNodes().stream()
                .filter(node -> node instanceof InheritanceNode)
                .filter(node -> ((InheritanceNode) node).getGroupName().equalsIgnoreCase(groupName))
                .filter(Node::hasExpiry)
                .map(node -> Date.from(node.getExpiry()))
                .findFirst()
                .orElseThrow(NullPointerException::new);
    }

    @Override
    public String getPrimaryGroup(OfflinePlayer player) {
        User user = getUser(player);
        return user.getPrimaryGroup();
    }

    private User getUser(OfflinePlayer player) {
        return api.getUserManager().isLoaded(player.getUniqueId()) ?
                api.getUserManager().getUser(player.getUniqueId()) :
                api.getUserManager().loadUser(player.getUniqueId()).join();
    }
}
