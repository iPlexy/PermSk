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
import org.bukkit.World;
import org.bukkit.event.Event;
import tk.shanebee.skperm.SkPerm;

@Name("Permission: Group's Permissions")
@Description("Add or remove permissions to/from a group. Worlds are only supported if your permission plugin supports them.")
@Examples({"add \"essentials.chat\" to permissions of group \"members\"",
        "add \"essentials.home\" to permissions of group \"knight\" in world \"world\"",
        "remove \"essentials.gamemode\" from permissions of group \"moderator\""})
@RequiredPlugins("Vault")
@Since("1.0.0")
public class ExprGroupPerm extends SimpleExpression<String> {

    private Permission manager = SkPerm.perms;

    static {
        Skript.registerExpression(ExprGroupPerm.class, String.class, ExpressionType.PROPERTY,
                "perm[ission][s] of group %string% [in [world]%-world%]",
                "group %string%'s perm[ission][s] [in [world]%-world%]");
    }

    @SuppressWarnings("null")
    private Expression<String> group;
    private Expression<World> world;

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
            return CollectionUtils.array(String.class);
        }
        return null;
    }

    @Override
    public void change(Event e, Object[] delta, ChangeMode mode) {
        String[] perms = (String[]) delta;
        String w = world == null ? null : world.getSingle(e).getName();
        for (String perm : perms) {
            switch (mode) {
                case ADD:
                    manager.groupAdd(w, group.getSingle(e), perm);
                    break;
                case REMOVE:
                    manager.groupRemove(w, group.getSingle(e), perm);
                    break;
            }
        }
    }

    @Override
    protected String[] get(Event event) {
        return null;
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public String toString(Event event, boolean debug) {
        return "permission of group " + group.toString(event, debug);
    }

}

