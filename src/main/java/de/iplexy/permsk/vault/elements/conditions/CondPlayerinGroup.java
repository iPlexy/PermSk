package de.iplexy.permsk.vault.elements.conditions;

import ch.njol.skript.conditions.base.PropertyCondition;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import de.iplexy.permsk.SkPerm;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.event.Event;

@Name("Vault Universal: Player in Group")
@Description("Check if a player belongs to a group. Worlds are only supported if your permission plugin supports them.")
@Examples({"if player is in group \"moderator\":", "if player is a member of group \"admin\":",
    "if player is in group \"default\" in world \"world_nether\":"})
@RequiredPlugins("Vault")
@Since("1.0.0")
public class CondPlayerinGroup extends Condition {
    
    static {
        PropertyCondition.register(CondPlayerinGroup.class,
            "(in|[a] member of) group %string% [in [world]%-world%]",
            "offlineplayers");
    }
    
    private final Permission manager = SkPerm.getPerms();
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
        String w = world == null ? null : world.getSingle(e).getName();
        return manager.playerInGroup(w, player.getSingle(e), group.getSingle(e));
    }
    
    @Override
    public String toString(Event e, boolean debug) {
        return "player " + player.toString(e, debug) + " is in group " + group.toString(e, debug) +
            (world != null ? " in world " + world.toString(e, debug) : "");
    }
}
