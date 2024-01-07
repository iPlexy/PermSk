package de.iplexy.permsk.api;

import lombok.AccessLevel;
import lombok.Getter;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.context.DefaultContextKeys;
import net.luckperms.api.context.ImmutableContextSet;
import net.luckperms.api.model.data.DataMutateResult;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.luckperms.api.model.user.UserManager;
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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class LuckApi implements PermissionApi {

    private final LuckPerms api = LuckPermsProvider.get();
    @Getter(AccessLevel.PUBLIC)
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    @Override
    public void addPerm(OfflinePlayer player, String permission) {
        executorService.submit(() -> {
            Node node = Node.builder(permission).build();
            api.getUserManager().modifyUser(player.getUniqueId(), (User user) -> {
                DataMutateResult result = user.data().add(node);
            });
        });
    }

    @Override
    public void addPerm(OfflinePlayer player, String permission, World world) {
        executorService.submit(() -> {
            ImmutableContextSet set = ImmutableContextSet.of("world", world.getName());
            Node node = Node.builder(permission).withContext(set).build();
            api.getUserManager().modifyUser(player.getUniqueId(), (User user) -> {
                DataMutateResult result = user.data().add(node);
            });
        });
    }

    @Override
    public void addPerm(OfflinePlayer player, String permission, World world, int seconds) {
        executorService.submit(() -> {
            ImmutableContextSet set = ImmutableContextSet.of("world", world.getName());
            Node node = Node.builder(permission).expiry(seconds).withContext(set).build();
            api.getUserManager().modifyUser(player.getUniqueId(), (User user) -> {
                DataMutateResult result = user.data().add(node);
            });
        });
    }

    @Override
    public void addPerm(OfflinePlayer player, String permission, int seconds) {
        executorService.submit(() -> {
            Node node = Node.builder(permission).expiry(seconds).build();
            api.getUserManager().modifyUser(player.getUniqueId(), (User user) -> {
                DataMutateResult result = user.data().add(node);
            });
        });
    }

    @Override
    public void addPerm(String groupName, String permission) {
        executorService.submit(() -> {
            Node node = Node.builder(permission).build();
            api.getGroupManager().modifyGroup(groupName, (Group group) -> group.data().add(node));
        });
    }

    @Override
    public void addPerm(String groupName, String permission, World world) {
        executorService.submit(() -> {
            ImmutableContextSet set = ImmutableContextSet.of("world", world.getName());
            Node node = Node.builder(permission).withContext(set).build();
            api.getGroupManager().modifyGroup(groupName, (Group group) -> group.data().add(node));
        });
    }

    @Override
    public void addPerm(String groupName, String permission, World world, int seconds) {
        executorService.submit(() -> {
            ImmutableContextSet set = ImmutableContextSet.of("world", world.getName());
            Node node = Node.builder(permission).expiry(seconds).withContext(set).build();
            api.getGroupManager().modifyGroup(groupName, (Group group) -> group.data().add(node));
        });
    }

    @Override
    public void addPerm(String groupName, String permission, int seconds) {
        executorService.submit(() -> {
            Node node = Node.builder(permission).expiry(seconds).build();
            api.getGroupManager().modifyGroup(groupName, (Group group) -> group.data().add(node));
        });
    }

    @Override
    public void removePerm(OfflinePlayer player, String permission) {
        executorService.submit(() -> {
            Node node = Node.builder(permission).build();
            api.getUserManager().modifyUser(player.getUniqueId(), (User user) -> {
                DataMutateResult result = user.data().remove(node);
            });
        });
    }

    @Override
    public void removePerm(OfflinePlayer player, String permission, World world) {
        executorService.submit(() -> {
            Node node = Node.builder(permission).withContext("world", world.getName()).build();
            api.getUserManager().modifyUser(player.getUniqueId(), (User user) -> user.data().remove(node));
        });
    }

    @Override
    public void removePerm(String groupName, String permission) {
        executorService.submit(() -> {
            Node node = Node.builder(permission).build();
            api.getGroupManager().modifyGroup(groupName, (Group group) -> {
                DataMutateResult result = group.data().remove(node);
            });
        });
    }

    @Override
    public void removePerm(String groupName, String permission, World world) {
        executorService.submit(() -> {
            Node node = Node.builder(permission).withContext("world", world.getName()).build();
            api.getGroupManager().modifyGroup(groupName, (Group group) -> group.data().remove(node));
        });
    }

    @Override
    public List<String> getPerms(OfflinePlayer player) {
        try {
            return executorService.submit(() -> {
                User user = getUser(player);
                return user.getCachedData().getPermissionData().getPermissionMap()
                        .entrySet().stream()
                        .filter(entry -> entry.getValue() && !entry.getKey().startsWith("group."))
                        .map(Map.Entry::getKey)
                        .collect(Collectors.toList());
            }).get();
        } catch (InterruptedException | ExecutionException e) {
            return null;
        }
    }

    @Override
    public List<String> getPerms(OfflinePlayer player, World world) {
        try {
            return executorService.submit(() -> {
                User user = getUser(player);
                return user.getCachedData().getPermissionData().getPermissionMap()
                        .entrySet().stream()
                        .filter(perm -> PermissionNode.builder(perm.getKey()).build().getContexts().contains("world", world.getName()))
                        .filter(entry -> entry.getValue() && !entry.getKey().startsWith("group."))
                        .map(Map.Entry::getKey)
                        .collect(Collectors.toList());
            }).get();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<String> getPerms(String groupName) {
        try {
            return executorService.submit(() -> {
                Group group = api.getGroupManager().getGroup(groupName);
                return group.getCachedData().getPermissionData().getPermissionMap()
                        .entrySet().stream()
                        .filter(entry -> entry.getValue() && !entry.getKey().startsWith("group."))
                        .map(Map.Entry::getKey)
                        .collect(Collectors.toList());
            }).get();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<String> getPerms(String groupName, World world) {
        try {
            return executorService.submit(() -> {
                Group group = api.getGroupManager().getGroup(groupName);
                return group.getCachedData().getPermissionData().getPermissionMap()
                        .entrySet().stream()
                        .filter(perm -> PermissionNode.builder(perm.getKey()).build().getContexts().contains("world", world.getName()))
                        .filter(entry -> entry.getValue() && !entry.getKey().startsWith("group."))
                        .map(Map.Entry::getKey)
                        .collect(Collectors.toList());
            }).get();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void createGroup(String groupName) {
        executorService.submit(() -> {
            api.getGroupManager().createAndLoadGroup(groupName);
        });
    }

    @Override
    public void createGroup(String groupName, List<String> parentGroups) {
        executorService.submit(() -> {
            api.getGroupManager().createAndLoadGroup(groupName);
            parentGroups.forEach(parent -> addPerm(groupName, "group." + parent));
        });
    }

    @Override
    public void deleteGroup(String groupName) {
        executorService.submit(() -> {
            Group group = api.getGroupManager().getGroup(groupName);
            api.getGroupManager().deleteGroup(group);
        });
    }

    @Override
    public void addPlayerToGroup(OfflinePlayer player, String groupName) {
        executorService.submit(() -> {
            addPerm(player, "group." + groupName);
        });
    }

    @Override
    public void addPlayerToGroup(OfflinePlayer player, String groupName, World world) {
        executorService.submit(() -> {
            addPerm(player, "group." + groupName, world);
        });
    }

    @Override
    public void addPlayerToGroup(OfflinePlayer player, String groupName, World world, int seconds) {
        executorService.submit(() -> {
            addPerm(player, "group." + groupName, world, seconds);
        });
    }

    @Override
    public void addPlayerToGroup(OfflinePlayer player, String groupName, int seconds) {
        executorService.submit(() -> {
            addPerm(player, "group." + groupName, seconds);
        });
    }

    @Override
    public void removePlayerFromGroup(OfflinePlayer player, String groupName) {
        executorService.submit(() -> {
            removePerm(player, "group." + groupName);
        });
    }

    @Override
    public void removePlayerFromGroup(OfflinePlayer player, String groupName, World world) {
        executorService.submit(() -> {
            removePerm(player, "group." + groupName, world);
        });
    }

    @Override
    public List<OfflinePlayer> getPlayersInGroup(String groupName) {
        try {
            return executorService.submit(() -> {
                LuckPerms api = LuckPermsProvider.get();
                Group group = api.getGroupManager().getGroup(groupName);
                if (group == null) throw new IllegalArgumentException("Group " + groupName + " not found");
                UserManager userManager = api.getUserManager();
                List<OfflinePlayer> users = new ArrayList<>();
                for (UUID uuid : userManager.searchAll(NodeMatcher.key(InheritanceNode.builder(group).build())).join().keySet()) {
                    User user = userManager.isLoaded(uuid) ? userManager.getUser(uuid) : userManager.loadUser(uuid).join();
                    if (user == null) throw new IllegalStateException("Could not load data of " + uuid);
                    users.add(Bukkit.getOfflinePlayer(uuid));
                }
                return users;
            }).get();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<OfflinePlayer> getPlayersInGroup(String groupName, World world) {
        try {
            return executorService.submit(() -> {
                Group group = api.getGroupManager().getGroup(groupName);
                if (group == null) throw new IllegalArgumentException("Group " + groupName + " not found");
                UserManager userManager = api.getUserManager();
                List<OfflinePlayer> users = new ArrayList<>();
                return userManager.searchAll(NodeMatcher.key(InheritanceNode.builder(group).build()))
                        .join()
                        .keySet()
                        .stream()
                        .map(uuid -> {
                            User user = userManager.isLoaded(uuid) ? userManager.getUser(uuid) : userManager.loadUser(uuid).join();
                            return user != null ? Bukkit.getOfflinePlayer(user.getUniqueId()) : null;
                        })
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());
            }).get();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void setGroupWeight(String groupName, int weight) {
        executorService.submit(() -> {
            Group group = api.getGroupManager().getGroup(groupName);
            group.getCachedData().getPermissionData().getPermissionMap().keySet().stream()
                    .filter(perm -> perm.startsWith("weight."))
                    .forEach(perm -> removePerm(groupName, perm));
            addPerm(groupName, "weight." + weight);
        });
    }

    @Override
    public void setGroupRank(String groupName, int rank) {
        //LuckPerms doesn't have a rank system, so we use the weight system
        executorService.submit(() -> {
            setGroupWeight(groupName, rank);
        });
    }

    @Override
    public int getGroupWeight(String groupName) {
        try {
            return executorService.submit(() -> {
                Group group = api.getGroupManager().getGroup(groupName);
                if (group == null) return 0;
                if (group.getWeight().isPresent()) {
                    return group.getWeight().getAsInt();
                }
                return 0;
            }).get();
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public int getGroupRank(String groupName) {
        //LuckPerms doesn't have a rank system, so we use the weight system
        try {
            return executorService.submit(() -> getGroupWeight(groupName)).get();
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public void setGroupPrefix(String groupName, String prefix) {
        executorService.submit(() -> {
            Group group = api.getGroupManager().getGroup(groupName);
            group.getCachedData().getMetaData().getPrefixes().values().stream()
                    .filter(perm -> perm.startsWith("prefix."))
                    .forEach(perm -> removePerm(groupName, perm));
            addPerm(groupName, "prefix." + getGroupWeight(groupName) + "." + prefix);
        });
    }

    @Override
    public String getGroupPrefix(String groupName) {
        try {
            return executorService.submit(() -> {
                Group group = api.getGroupManager().getGroup(groupName);
                if (group == null) return "";
                return group.getCachedData().getMetaData().getPrefix();
            }).get();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void setGroupSuffix(String groupName, String suffix) {
        executorService.submit(() -> {
            Group group = api.getGroupManager().getGroup(groupName);
            group.getCachedData().getMetaData().getSuffixes().values().stream()
                    .filter(perm -> perm.startsWith("prefix."))
                    .forEach(perm -> removePerm(groupName, perm));
            addPerm(groupName, "suffix." + getGroupWeight(groupName) + "." + suffix);
        });
    }

    @Override
    public String getGroupSuffix(String groupName) {
        try {
            return executorService.submit(() -> {
                Group group = api.getGroupManager().getGroup(groupName);
                if (group == null) return "";
                return group.getCachedData().getMetaData().getSuffix();
            }).get();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void setGroupPrefix(String groupName, String prefix, World world) {
        executorService.submit(() -> {
            PrefixNode node = PrefixNode.builder(prefix, getGroupWeight(groupName)).withContext(DefaultContextKeys.WORLD_KEY, world.getName()).build();
            api.getGroupManager().modifyGroup(groupName, (Group theGroup) -> {
                DataMutateResult result = theGroup.data().add(node);
            });
        });
    }

    @Override
    public String getGroupPrefix(String groupName, World world) {
        try {
            return executorService.submit(() -> {
                Group group = api.getGroupManager().getGroup(groupName);
                return group.getNodes(NodeType.PREFIX)
                        .stream()
                        .filter(node -> node.getContexts().contains(DefaultContextKeys.WORLD_KEY, world.getName()))
                        .findFirst()
                        .map(PrefixNode::getMetaValue)
                        .orElse("null");
            }).get();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void setGroupSuffix(String groupName, String suffix, World world) {
        executorService.submit(() -> {
            SuffixNode node = SuffixNode.builder(suffix, getGroupWeight(groupName)).withContext(DefaultContextKeys.WORLD_KEY, world.getName()).build();
            api.getGroupManager().modifyGroup(groupName, (Group theGroup) -> {
                DataMutateResult result = theGroup.data().add(node);
            });
        });
    }

    @Override
    public String getGroupSuffix(String groupName, World world) {
        try {
            return executorService.submit(() -> {
                Group group = api.getGroupManager().getGroup(groupName);
                return group.getNodes(NodeType.SUFFIX)
                        .stream()
                        .filter(node -> node.getContexts().contains(DefaultContextKeys.WORLD_KEY, world.getName()))
                        .findFirst()
                        .map(SuffixNode::getMetaValue)
                        .orElse("null");
            }).get();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void setPlayerPrefix(OfflinePlayer player, String prefix) {
        executorService.submit(() -> {
            User user = getUser(player);
            user.getCachedData().getMetaData().getSuffixes().values().stream()
                    .filter(perm -> perm.startsWith("prefix."))
                    .forEach(perm -> removePerm(player, perm));
            addPerm(player, "prefix." + getGroupWeight(user.getPrimaryGroup()) + "." + prefix);
        });
    }

    @Override
    public String getPlayerPrefix(OfflinePlayer player) {
        try {
            return executorService.submit(() -> {
                User user = getUser(player);
                return user.getCachedData().getMetaData().getPrefix();
            }).get();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void setPlayerSuffix(OfflinePlayer player, String suffix) {
        executorService.submit(() -> {
            User user = getUser(player);
            user.getCachedData().getMetaData().getSuffixes().values().stream()
                    .filter(perm -> perm.startsWith("suffix."))
                    .forEach(perm -> removePerm(player, perm));
            addPerm(player, "suffix." + getGroupWeight(user.getPrimaryGroup()) + "." + suffix);
        });
    }

    @Override
    public String getPlayerSuffix(OfflinePlayer player) {
        try {
            return executorService.submit(() -> {
                User user = getUser(player);
                return user.getCachedData().getMetaData().getSuffix();
            }).get();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void setPlayerPrefix(OfflinePlayer player, String prefix, World world) {
        executorService.submit(() -> {
            PrefixNode node = PrefixNode.builder(player.getName(), getGroupWeight(getPrimaryGroup(player))).withContext(DefaultContextKeys.WORLD_KEY, world.getName()).build();
            api.getUserManager().modifyUser(player.getUniqueId(), (User user) -> {
                DataMutateResult result = user.data().add(node);
            });
        });
    }

    @Override
    public String getPlayerPrefix(OfflinePlayer player, World world) {
        try {
            return executorService.submit(() -> {
                User user = api.getUserManager().getUser(player.getUniqueId());
                return user.getNodes(NodeType.PREFIX)
                        .stream()
                        .filter(node -> node.getContexts().contains(DefaultContextKeys.WORLD_KEY, world.getName()))
                        .findFirst()
                        .map(PrefixNode::getMetaValue)
                        .orElse("null");
            }).get();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void setPlayerSuffix(OfflinePlayer player, String suffix, World world) {
        executorService.submit(() -> {
            SuffixNode node = SuffixNode.builder(player.getName(), getGroupWeight(getPrimaryGroup(player))).withContext(DefaultContextKeys.WORLD_KEY, world.getName()).build();
            api.getUserManager().modifyUser(player.getUniqueId(), (User user) -> {
                DataMutateResult result = user.data().add(node);
            });
        });
    }

    @Override
    public String getPlayerSuffix(OfflinePlayer player, World world) {
        try {
            return executorService.submit(() -> {
                User user = api.getUserManager().getUser(player.getUniqueId());
                return user.getNodes(NodeType.SUFFIX)
                        .stream()
                        .filter(node -> node.getContexts().contains(DefaultContextKeys.WORLD_KEY, world.getName()))
                        .findFirst()
                        .map(SuffixNode::getMetaValue)
                        .orElse("null");
            }).get();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<String> getInheritedGroups(String groupName) {
        try {
            return executorService.submit(() -> {
                Group group = api.getGroupManager().getGroup(groupName);
                return group.resolveInheritedNodes(QueryOptions.nonContextual()).stream()
                        .filter(NodeType.INHERITANCE::matches)
                        .map(NodeType.INHERITANCE::cast)
                        .map(InheritanceNode::getGroupName)
                        .collect(Collectors.toList());
            }).get();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<String> getInheritedGroups(OfflinePlayer player) {
        try {
            return executorService.submit(() -> {
                User user = getUser(player);
                return user.getInheritedGroups(QueryOptions.builder(QueryMode.CONTEXTUAL).build()).stream()
                        .map(Group::getName)
                        .collect(Collectors.toList());
            }).get();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Date getGroupExpireDate(OfflinePlayer player, String groupName) {
        try {
            return executorService.submit(() -> {
                User user = getUser(player);
                return user.getNodes().stream()
                        .filter(node -> node instanceof InheritanceNode)
                        .filter(node -> ((InheritanceNode) node).getGroupName().equalsIgnoreCase(groupName))
                        .filter(Node::hasExpiry)
                        .map(node -> Date.from(node.getExpiry()))
                        .findFirst()
                        .orElse(null);
            }).get();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String getPrimaryGroup(OfflinePlayer player) {
        try {
            return executorService.submit(() -> {
                User user = getUser(player);
                return user.getPrimaryGroup();
            }).get();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void setPrimaryGroup(OfflinePlayer player, String group) {
        executorService.submit(() -> {
            User user = getUser(player);
            user.setPrimaryGroup(group);
            api.getUserManager().saveUser(user);
        });
    }

    @Override
    public List<String> getAllGroups() {
        try {
            return executorService.submit(() -> {
                return api.getGroupManager().getLoadedGroups().stream()
                        .map(Group::getName)
                        .collect(Collectors.toList());
            }).get();
        } catch (Exception e) {
            return null;
        }
    }

    private User getUser(OfflinePlayer player) {
        try {
            return executorService.submit(() -> api.getUserManager().isLoaded(player.getUniqueId()) ?
                    api.getUserManager().getUser(player.getUniqueId()) :
                    api.getUserManager().loadUser(player.getUniqueId()).join()).get();
        } catch (Exception e) {
            return null;
        }
    }

}
