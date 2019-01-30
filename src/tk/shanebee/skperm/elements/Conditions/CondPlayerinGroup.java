package tk.shanebee.skperm.elements.Conditions;

import ch.njol.skript.conditions.base.PropertyCondition;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.event.Event;
import tk.shanebee.skperm.SkPerm;

@Name("Permission: Player in Group")
@Description("Check if a player belongs to a group. Worlds are only supported if your permission plugin supports them.")
@Examples({"if player is in group \"moderator\":", "if player is a member of group \"admin\":",
        "if player is in group \"default\" in world \"world_nether\":"})
@RequiredPlugins("Vault")
@Since("1.0.0")
public class CondPlayerinGroup extends Condition {

    private Permission manager = SkPerm.perms;

    static {
        PropertyCondition.register(CondPlayerinGroup.class,
                "(in|[a] member of) group %string% [in [world]%-world%]",
                "offlineplayers");
    }

    @SuppressWarnings("null")
    private Expression<OfflinePlayer> player;
    private Expression<String> group;
    private Expression<World> world;

    @SuppressWarnings({"unchecked", "null"})
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        player = (Expression<OfflinePlayer>) exprs[0];
        group = (Expression<String>) exprs[1];
        world = (Expression<World>) exprs[2];
        return true;
    }

    @Override
    public boolean check(Event e) {
        if (world != null)
            return manager.playerInGroup(world.getSingle(e).getName(), player.getSingle(e), group.toString());
        else
            return manager.playerInGroup(null, player.getSingle(e), group.toString());
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "player " + player.toString(e, debug) + " is in group " + group.toString(e, debug) +
                (world != null ? " in world " + world.toString(e, debug) : "");
    }
}
