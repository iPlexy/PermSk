package tk.shanebee.skperm.utils.api;

import org.bukkit.OfflinePlayer;
import org.bukkit.World;

/**
 * An interface to allow multiple permission plugins to use the same syntaxes via Skript
 */
public interface API {

    /** Add a permission to a player
     *
     * @param player The player to apply a permission to
     * @param permission The permission to apply to the player
     */
    void addPerm(OfflinePlayer player, String permission);

    /** Add a permission to a player in a specific world
     *
     * @param player The player to apply a permission to
     * @param permission The permission to apply to the player
     * @param world The world the permission will apply in
     */
    void addPerm(OfflinePlayer player, String permission, World world);

    /** Add a permission to a player in a specific world for a specific amount of time
     *
     * @param player The player to apply a permission to
     * @param permission The permission to apply to the player
     * @param world The world the permission will apply in
     * @param seconds The time the permission will last for (in seconds)
     */
    void addPerm(OfflinePlayer player, String permission, World world, int seconds);

    /** Add a permission to a player for a specific amount of time
     *
     * @param player The player to apply a permission to
     * @param permission The permission to apply to the player
     * @param seconds The time the permission will last for (in seconds)
     */
    void addPerm(OfflinePlayer player, String permission, int seconds);

    /** Add a permission to a group
     *
     * @param group The group to apply a permission to
     * @param permission The permission to apply to the group
     */
    void addPerm(String group, String permission);

    /** Add a permission to a group in a specific world
     *
     * @param group The group to apply a permission to
     * @param permission The permission to apply to the group
     * @param world The world the permission will apply in
     */
    void addPerm(String group, String permission, World world);

    /** Add a permission to a group in a specific world for a specific amount of time
     *
     * @param group The group to apply a permission to
     * @param permission The permission to apply to the group
     * @param world The world the permission will apply in
     * @param seconds The time the permission will last for (in seconds)
     */
    void addPerm(String group, String permission, World world, int seconds);

    /** Add a permission to a group for a specific amount of time
     *
     * @param group The group to apply a permission to
     * @param permission The permission to apply to the group
     * @param seconds The time the permission will last for (in seconds)
     */
    void addPerm(String group, String permission, int seconds);

    /** Remove a permission from a player
     *
     * @param player The player to have a permission removed from
     * @param permission The permission to remove from the player
     */
    void removePerm(OfflinePlayer player, String permission);

    /** Remove a permission from a player in a specific world
     *
     * @param player The player to have a permission removed from
     * @param permission The permission to remove from the player
     * @param world The world to remove the permission from
     */
    void removePerm(OfflinePlayer player, String permission, World world);

    /** Remove a permission from a group
     *
     * @param group The group to have a permission removed from
     * @param permission The permission to remove from the group
     */
    void removePerm(String group, String permission);

    /** Remove a permission from a group in a specific world
     *
     * @param group The group to have a permission removed from
     * @param permission The permission to remove from the group
     * @param world The world to remove the permission from
     */
    void removePerm(String group, String permission, World world);

    /** Get a list of permissions from a player
     *
     * @param player The player to get the permissions from
     * @return Returns a list of permissions the player has
     */
    String[] getPerm(OfflinePlayer player);

    /** Get a list of permissions from a player for a specific world
     *
     * @param player The player to get the permissions from
     * @param world The world to get the permissions from
     * @return Returns a list of permission the player has in that world
     */
    String[] getPerm(OfflinePlayer player, World world);

    /** Get a list of permissions from a group
     *
     * @param group The group to get permissions from
     * @return Returns a list of permissions the group has
     */
    String[] getPerm(String group);

    /** Get a list of permission from a group for a specific world
     *
     * @param group The group to get permissions from
     * @param world The world to get the permissions from
     * @return Returns a list of permissions the group has in that world
     */
    String[] getPerm(String group, World world);

    /** Create a new group
     *
     * @param group Name of the new group
     */
    void createGroup(String group);

    /** Create a new group with parents
     *
     * @param group Name of the new group
     * @param parents A list of parents this group will inherit permissions from
     */
    void createGroup(String group, String[] parents);

    /** Remove a group
     *
     * @param group Name of group to be remvoed
     */
    void removeGroup(String group);

    /** Add a player to a group
     *
     * @param player The player to be added to a group
     * @param group Name of group to add to the player
     */
    void addPlayerToGroup(OfflinePlayer player, String group);

    /** Add a player to a group in a specific world
     *
     * @param player The player to be added to a group
     * @param group Name of group to add to the player
     * @param world The world that the player will be added to the group
     */
    void addPlayerToGroup(OfflinePlayer player, String group, World world);

    /** Add a player to a group in a specific world for a specific amount of time
     *
     * @param player The player to be added to a group
     * @param group Name of group to add to the player
     * @param world The world that the player will be added to the group
     * @param seconds The time the player will be added to this group for (in seconds)
     */
    void addPlayerToGroup(OfflinePlayer player, String group, World world, int seconds);

    /** Add a player to a group for a specific amount of time
     *
     * @param player The player to be added to a group
     * @param group Name of group to add to the player
     * @param seconds The time the player will be added to this group for (in seconds)
     */
    void addPlayerToGroup(OfflinePlayer player, String group, int seconds);

    /** Remove a player from a group
     *
     * @param player The player to remove from a group
     * @param group The group the player will be removed from
     */
    void removePlayerFromGroup(OfflinePlayer player, String group);

    /** Remove a player from a group
     *
     * @param player The player to remove from a group
     * @param group The group the player will be removed from
     * @param world The world which the player will be removed from that group
     */
    void removePlayerFromGroup(OfflinePlayer player, String group, World world);

    /** Get a list of players in a group
     *
     * @param group The group to get players from
     * @return Returns a list of players from that group
     */
    OfflinePlayer[] getPlayersInGroup(String group);

    /** Set a group's weight/priority
     *
     * @param group The group to set weight/priority
     * @param weight The weight/priority of a group to be set
     */
    void setGroupWeight(String group, int weight);

    /** Set a group's rank
     *
     * @param group The group to set weight/priority
     * @param rank The rank of a group to be set
     */
    void setGroupRank(String group, int rank);

    /** Get a group's weight/priority
     *
     * @param group The group to get weight/priority from
     * @return Returns the weight/priority of a group
     */
    int getGroupWeight(String group);

    /** Get a group's rank
     *
     * @param group The group to get rank from
     * @return Returns the rank of a group
     */
    int getGroupRank(String group);

    /** Set a group's prefix
     *
     * @param group The group to set a prefix for
     * @param prefix The prefix to set for a group
     */
    void setGroupPrefix(String group, String prefix);

    /** Set a group's prefix in a specific world
     *
     * @param group The group to set a prefix for
     * @param prefix The prefix to set for a group
     * @param world The world in which the prefix will be set
     */
    void setGroupPrefix(String group, String prefix, World world);

    /** Get a group's prefix
     *
     * @param group The group to get a prefix from
     * @return Returns the prefix of the group
     */
    String getGroupPrefix(String group);

    /** Get a group's prefix
     *
     * @param group The group to get a prefix from
     * @param world The world in which to get a prefix from
     * @return Returns the prefix of the group in a specific world
     */
    String getGroupPrefix(String group, World world);

    /** Set a group's suffix
     *
     * @param group The group to set a suffix for
     * @param suffix The suffix to set for the group
     */
    void setGroupSuffix(String group, String suffix);

    /** Set a group's suffix in a specific world
     *
     * @param group The group to set a suffix for
     * @param suffix The suffix to set for the group
     * @param world The world for which the suffix will be applied to
     */
    void setGroupSuffix(String group, String suffix, World world);

    /** Get a group's suffix
     *
     * @param group The group to get a suffix from
     * @return Returns the suffix of the group
     */
    String getGroupSuffix(String group);

    /** Get a group's suffix from a specific world
     *
     * @param group The group to get a suffix from
     * @param world The world in which to get the suffix from
     * @return Returns the suffix of the group in a specific world
     */
    String getGroupSuffix(String group, World world);

    void setPlayerPrefix(OfflinePlayer player, String prefix);

    void setPlayerPrefix(OfflinePlayer player, String prefix, World world);

    String getPlayerPrefix(OfflinePlayer player);

    String getPlayerPrefix(OfflinePlayer player, World world);

    void setPlayerSuffix(OfflinePlayer player, String suffix);

    void setPlayerSuffix(OfflinePlayer player, String suffix, World world);

    String getPlayerSuffix(OfflinePlayer player);

    String getPlayerSuffix(OfflinePlayer player, World world);

}