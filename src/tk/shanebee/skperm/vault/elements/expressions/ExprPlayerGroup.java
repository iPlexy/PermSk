package tk.shanebee.skperm.vault.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import tk.shanebee.skperm.SkPerm;

@Name("Vault Universal: Group's Players")
@Description("Add or remove player's to/from groups. Vault does not seem to allow getting players of a group. " +
        "Worlds are only supported if your permission plugin supports them.")
@Examples({"add player to group \"Admin\"", "remove player from group \"Moderator\"",
        "add player to group \"member\" in \"world\"", "remove player from group \"default\" in world \"world_nether\""})
@RequiredPlugins("Vault")
@Since("1.0.0")
public class ExprPlayerGroup extends SimpleExpression<OfflinePlayer> {

    private Permission manager = SkPerm.perms;

    static {
        Skript.registerExpression(ExprPlayerGroup.class, OfflinePlayer.class, ExpressionType.PROPERTY,
                "group %string% [in [world] %-world%]");
    }

    @SuppressWarnings("null")
    private Expression<World> world;
    private Expression<String> group;

    @SuppressWarnings({"null", "unchecked"})
    @Override
    public boolean init(Expression<?>[] exprs, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        group = (Expression<String>) exprs[0];
        world = (Expression<World>) exprs[1];
        return true;
    }

    @Override
    public Class<?>[] acceptChange(ChangeMode mode) {
        if (mode == ChangeMode.ADD || mode == ChangeMode.REMOVE) {
            return CollectionUtils.array(Player.class);
        }
        return null;
    }

    @Override
    public void change(Event e, Object[] delta, ChangeMode mode) {
        OfflinePlayer[] players = (OfflinePlayer[]) delta;
        String w = world == null ? null : world.getSingle(e).getName();
        for (OfflinePlayer player : players) {
            switch (mode) {
                case ADD:
                    manager.playerAddGroup(w, player, group.getSingle(e));
                    break;
                case REMOVE:
                    manager.playerRemoveGroup(w, player, group.getSingle(e));
                    break;
            }
        }
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends OfflinePlayer> getReturnType() {
        return OfflinePlayer.class;
    }

    @Override
    public String toString(Event event, boolean debug) {
        return "group " + group.toString(event, debug);
    }

    @Override
    protected Player[] get(Event event) {
        return null;
    }

}