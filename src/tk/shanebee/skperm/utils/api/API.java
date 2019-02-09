package tk.shanebee.skperm.utils.api;

import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public interface API {

    void addPerm(OfflinePlayer player, String permission);

    void addPerm(OfflinePlayer player, String permission, World world);

    void addPerm(OfflinePlayer player, String permission, World world, int seconds);

    void addPerm(OfflinePlayer player, String permission, int seconds);

    void addPerm(String group, String permission);

    void addPerm(String group, String permission, World world);

    void addPerm(String group, String permission, World world, int seconds);

    void addPerm(String group, String permission, int seconds);

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

    void addPlayerToGroup(OfflinePlayer player, String group, int seconds);

    void removePlayerFromGroup(OfflinePlayer player, String group);

    void removePlayerFromGroup(OfflinePlayer player, String group, World world);

    OfflinePlayer[] getPlayersInGroup(String group);

    void setGroupWeight(String group, int weight);

    void setGroupRank(String group, int rank);

    int getGroupWeight(String group);

    int getGroupRank(String group);

    void setGroupPrefix(String group, String prefix);

    void setGroupPrefix(String group, String prefix, World world);

    String getGroupPrefix(String group);

    String getGroupPrefix(String group, World world);

    void setGroupSuffix(String group, String suffix);

    void setGroupSuffix(String group, String suffix, World world);

    String getGroupSuffix(String group);

    String getGroupSuffix(String group, World world);

}