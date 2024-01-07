package de.iplexy.permsk.api;

import org.bukkit.OfflinePlayer;
import org.bukkit.World;

import java.util.Date;
import java.util.List;

public interface PermissionApi {

    /**
     * Add a permission to a player
     *
     * @param player     The player to add the permission to
     * @param permission The permission to add
     */
    void addPerm(OfflinePlayer player, String permission);

    /**
     * Add a permission to a player for a specific world
     *
     * @param player     The player to add the permission to
     * @param permission The permission to add
     * @param world      The world to add the permission to
     */
    void addPerm(OfflinePlayer player, String permission, World world);

    /**
     * Add a permission to a player for a specific world for a specific amount of time
     *
     * @param player     The player to add the permission to
     * @param permission The permission to add
     * @param world      The world to add the permission to
     * @param seconds    The amount of seconds the permission should be added
     */
    void addPerm(OfflinePlayer player, String permission, World world, int seconds);

    /**
     * Add a permission to a player for a specific amount of time
     *
     * @param player     The player to add the permission to
     * @param permission The permission to add
     * @param seconds    The amount of seconds the permission should be added
     */
    void addPerm(OfflinePlayer player, String permission, int seconds);

    /**
     * Add a permission to a group
     *
     * @param groupName  The group to add the permission to
     * @param permission The permission to add
     */
    void addPerm(String groupName, String permission);

    /**
     * Add a permission to a group for a specific world
     *
     * @param groupName  The group to add the permission to
     * @param permission The permission to add
     * @param world      The world to add the permission to
     */
    void addPerm(String groupName, String permission, World world);

    /**
     * Add a permission to a group for a specific world for a specific amount of time
     *
     * @param groupName  The group to add the permission to
     * @param permission The permission to add
     * @param world      The world to add the permission to
     * @param seconds    The amount of seconds the permission should be added
     */
    void addPerm(String groupName, String permission, World world, int seconds);

    /**
     * Add a permission to a group for a specific amount of time
     *
     * @param groupName  The group to add the permission to
     * @param permission The permission to add
     * @param seconds    The amount of seconds the permission should be added
     */
    void addPerm(String groupName, String permission, int seconds);

    /**
     * Remove a permission from a player
     *
     * @param player     The player to remove the permission from
     * @param permission The permission to remove
     */
    void removePerm(OfflinePlayer player, String permission);

    /**
     * Remove a permission from a player for a specific world
     *
     * @param player     The player to remove the permission from
     * @param permission The permission to remove
     * @param world      The world to remove the permission from
     */
    void removePerm(OfflinePlayer player, String permission, World world);

    /**
     * Remove a permission from a group
     *
     * @param groupName  The group to remove the permission from
     * @param permission The permission to remove
     */
    void removePerm(String groupName, String permission);

    /**
     * Remove a permission from a group for a specific world
     *
     * @param groupName  The group to remove the permission from
     * @param permission The permission to remove
     * @param world      The world to remove the permission from
     */
    void removePerm(String groupName, String permission, World world);

    /**
     * Get all permissions from a player
     *
     * @param player The player to get the permissions from
     * @return A list of all permissions from the player
     */
    List<String> getPerms(OfflinePlayer player);

    /**
     * Get all permissions from a player for a specific world
     *
     * @param player The player to get the permissions from
     * @param world  The world to get the permissions from
     * @return A list of all permissions from the player for the specific world
     */
    List<String> getPerms(OfflinePlayer player, World world);

    /**
     * Get all permissions from a group
     *
     * @param groupName The group to get the permissions from
     * @return A list of all permissions from the group
     */
    List<String> getPerms(String groupName);

    /**
     * Get all permissions from a group for a specific world
     *
     * @param groupName The group to get the permissions from
     * @param world     The world to get the permissions from
     * @return A list of all permissions from the group for the specific world
     */
    List<String> getPerms(String groupName, World world);

    /**
     * Create a group
     *
     * @param groupName The name of the group
     */
    void createGroup(String groupName);

    /**
     * Create a group with parent groups
     *
     * @param groupName    The name of the group
     * @param parentGroups The parent groups
     */
    void createGroup(String groupName, List<String> parentGroups);

    /**
     * Delete a group
     *
     * @param groupName The name of the group
     */
    void deleteGroup(String groupName);

    /**
     * Add a player to a group
     *
     * @param player    The player to add to the group
     * @param groupName The group to add the player to
     */
    void addPlayerToGroup(OfflinePlayer player, String groupName);

    /**
     * Add a player to a group for a specific world
     *
     * @param player    The player to add to the group
     * @param groupName The group to add the player to
     * @param world     The world to add the player to the group
     */
    void addPlayerToGroup(OfflinePlayer player, String groupName, World world);

    /**
     * Add a player to a group for a specific world for a specific amount of time
     *
     * @param player    The player to add to the group
     * @param groupName The group to add the player to
     * @param world     The world to add the player to the group
     * @param seconds   The amount of seconds the player should be added to the group
     */
    void addPlayerToGroup(OfflinePlayer player, String groupName, World world, int seconds);

    /**
     * Add a player to a group for a specific amount of time
     *
     * @param player    The player to add to the group
     * @param groupName The group to add the player to
     * @param seconds   The amount of seconds the player should be added to the group
     */
    void addPlayerToGroup(OfflinePlayer player, String groupName, int seconds);

    /**
     * Remove a player from a group
     *
     * @param player    The player to remove from the group
     * @param groupName The group to remove the player from
     */
    void removePlayerFromGroup(OfflinePlayer player, String groupName);

    /**
     * Remove a player from a group for a specific world
     *
     * @param player    The player to remove from the group
     * @param groupName The group to remove the player from
     * @param world     The world to remove the player from the group
     */
    void removePlayerFromGroup(OfflinePlayer player, String groupName, World world);

    /**
     * Get all players in a group
     *
     * @param groupName The group to get the players from
     * @return A list of all players in the group
     */
    List<OfflinePlayer> getPlayersInGroup(String groupName);

    /**
     * Get all players in a group for a specific world
     *
     * @param groupName The group to get the players from
     * @param world     The world to get the players from
     * @return A list of all players in the group for the specific world
     */
    List<OfflinePlayer> getPlayersInGroup(String groupName, World world);


    /**
     * Set the weight of a group
     *
     * @param groupName The group to set the weight of
     * @param weight    The weight to set
     */
    void setGroupWeight(String groupName, int weight);

    /**
     * Set the rank of a group
     *
     * @param groupName The group to set the rank of
     * @param rank      The rank to set
     */
    void setGroupRank(String groupName, int rank);

    /**
     * Get the weight of a group
     *
     * @param groupName The group to get the weight from
     * @return The weight of the group
     */
    int getGroupWeight(String groupName);

    /**
     * Get the rank of a group
     *
     * @param groupName The group to get the rank from
     * @return The rank of the group
     */
    int getGroupRank(String groupName);

    /**
     * Set the prefix of a group
     *
     * @param groupName The group to set the prefix of
     * @param prefix    The prefix to set
     */
    void setGroupPrefix(String groupName, String prefix);

    /**
     * Get the prefix of a group
     *
     * @param groupName The group to get the prefix from
     * @return The prefix of the group
     */
    String getGroupPrefix(String groupName);

    /**
     * Set the suffix of a group
     *
     * @param groupName The group to set the suffix of
     * @param suffix    The suffix to set
     */
    void setGroupSuffix(String groupName, String suffix);

    /**
     * Get the suffix of a group
     *
     * @param groupName The group to get the suffix from
     * @return The suffix of the group
     */
    String getGroupSuffix(String groupName);

    /**
     * Set the prefix of a group for a specific world
     *
     * @param groupName The group to set the prefix of
     * @param prefix    The prefix to set
     * @param world     The world to set the prefix of
     */
    void setGroupPrefix(String groupName, String prefix, World world);

    /**
     * Get the prefix of a group for a specific world
     *
     * @param groupName The group to get the prefix from
     * @param world     The world to get the prefix from
     * @return The prefix of the group for the specific world
     */
    String getGroupPrefix(String groupName, World world);

    /**
     * Set the suffix of a group for a specific world
     *
     * @param groupName The group to set the suffix of
     * @param suffix    The suffix to set
     * @param world     The world to set the suffix of
     */
    void setGroupSuffix(String groupName, String suffix, World world);

    /**
     * Get the suffix of a group for a specific world
     *
     * @param groupName The group to get the suffix from
     * @param world     The world to get the suffix from
     * @return The suffix of the group for the specific world
     */
    String getGroupSuffix(String groupName, World world);

    /**
     * Set the prefix of a player
     *
     * @param player The player to set the prefix of
     * @param prefix The prefix to set
     */
    void setPlayerPrefix(OfflinePlayer player, String prefix);

    /**
     * Get the prefix of a player
     *
     * @param player The player to get the prefix from
     * @return The prefix of the player
     */
    String getPlayerPrefix(OfflinePlayer player);

    /**
     * Set the suffix of a player
     *
     * @param player The player to set the suffix of
     * @param suffix The suffix to set
     */
    void setPlayerSuffix(OfflinePlayer player, String suffix);

    /**
     * Get the suffix of a player
     *
     * @param player The player to get the suffix from
     * @return The suffix of the player
     */
    String getPlayerSuffix(OfflinePlayer player);

    /**
     * Set the prefix of a player for a specific world
     *
     * @param player The player to set the prefix of
     * @param prefix The prefix to set
     * @param world  The world to set the prefix of
     */
    void setPlayerPrefix(OfflinePlayer player, String prefix, World world);

    /**
     * Get the prefix of a player for a specific world
     *
     * @param player The player to get the prefix from
     * @param world  The world to get the prefix from
     * @return The prefix of the player for the specific world
     */
    String getPlayerPrefix(OfflinePlayer player, World world);

    /**
     * Set the suffix of a player for a specific world
     *
     * @param player The player to set the suffix of
     * @param suffix The suffix to set
     * @param world  The world to set the suffix of
     */
    void setPlayerSuffix(OfflinePlayer player, String suffix, World world);

    /**
     * Get the suffix of a player for a specific world
     *
     * @param player The player to get the suffix from
     * @param world  The world to get the suffix from
     * @return The suffix of the player for the specific world
     */
    String getPlayerSuffix(OfflinePlayer player, World world);

    /**
     * Get all groups a group is inheriting
     *
     * @param groupName The group to get the inherited groups from
     * @return A list of all groups the group is inheriting
     */
    List<String> getInheritedGroups(String groupName);

    /**
     * Get all groups a player is in
     *
     * @param player The player to get the groups from
     * @return A list of all groups the player is in
     */
    List<String> getInheritedGroups(OfflinePlayer player);


    /**
     * Get the expiry date of a group
     *
     * @param player    The player to get the expiry date from
     * @param groupName The group to get the expiry date from
     * @return The expiry date of the group
     */
    Date getGroupExpireDate(OfflinePlayer player, String groupName);

    /**
     * Get the primary group of a player
     *
     * @param player The player to get the expiry date from
     * @return The primary group of the player
     */
    String getPrimaryGroup(OfflinePlayer player);

    /**
     * Sets the primary group of a player
     *
     * @param player The player to get the expiry date from
     * @param groupName  The group to set as primary group
     */
    void setPrimaryGroup(OfflinePlayer player, String groupName);

    /**
     * Gets all groups of the server
     *
     * @return List of all groups
     */
    List<String> getAllGroups();


}
