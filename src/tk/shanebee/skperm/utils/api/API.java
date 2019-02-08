package tk.shanebee.skperm.utils.api;

import org.bukkit.OfflinePlayer;
import org.bukkit.World;

public interface API {

    void addPerm(OfflinePlayer player, String permission);

    void addPerm(OfflinePlayer player, String permission, World world);

    void addPerm(OfflinePlayer player, String permission, World world, int seconds);

    void addPerm(String group, String permission);

    void addPerm(String group, String permission, World world);

    void addPerm(String group, String permission, World world, int seconds);

    void removePerm(OfflinePlayer player, String permission);

    void removePerm(OfflinePlayer player, String permission, World world);

    void removePerm(String group, String permission);

    void removePerm(String group, String permission, World world);

    String[] getPerm(OfflinePlayer player);

    String[] getPerm(OfflinePlayer player, World world);

    String[] getPerm(String group);

    String[] getPerm(String group, World world);

    void createGroup(String group);

    void createGroup(String group, String[] parents);

    void removeGroup(String group);

    void addPlayerToGroup(OfflinePlayer player, String group);

    void addPlayerToGroup(OfflinePlayer player, String group, World world);

    void addPlayerToGroup(OfflinePlayer player, String group, World world, int seconds);

    void removePlayerFromGroup(OfflinePlayer player, String group);

    void removePlayerFromGroup(OfflinePlayer player, String group, World world);

    void setGroupWeight(String group, int weight);

    void setGroupRank(String group, int rank);

}