package de.iplexy.permsk.plugins.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.util.Timespan;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import de.iplexy.permsk.SkPerm;
import de.iplexy.permsk.utils.api.API;
import org.bukkit.World;
import org.bukkit.event.Event;

import java.util.Arrays;

@Name("Permission: Permissions of Group")
@Description("Returns a list of all the group's permissions with an option to get the permissions for a specific world. " +
    "You can also add/remove permissions to/from a group (Supports lists of permissions/strings) " +
    "[Support for timed permissions does not seem to be working. This is an issue with PEX. May or may not work]" +
    " [Requires a permission plugin, Currently supports PEX, LuckPerms and UltraPermissions]")
@Examples({"set {_perms::*} to all permissions of group \"mod\"",
    "set {_perms::*} to all permissions of group \"builder\" in world \"world\"",
    "send \"Perms in %world of player%: %all permissions of group \"\"admin\"\" in world of player%\"",
    "add \"essentials.fly\" to permissions of group \"builder\" in world \"world\"",
    "remove \"essentials.fly\" from permissions of group \"mod\""})
@Since("2.0.0")
public class ExprGroupPerms extends SimpleExpression<String> {
    
    static {
        Skript.registerExpression(ExprGroupPerms.class, String.class, ExpressionType.PROPERTY,
            "[all] perm[ission][s] of group %string% [in [world] %-world%] [for %-timespan%]",
            "group %string%'s perm[ission][s] [in [world] %-world%] [for %-timespan%]");
    }
    
    private final API api = SkPerm.getAPI();
    private Expression<String> group;
    private Expression<World> world;
    private Expression<Timespan> time;
    
    @SuppressWarnings({"unchecked"})
    @Override
    public boolean init(Expression<?>[] exprs, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        group = (Expression<String>) exprs[0];
        world = (Expression<World>) exprs[1];
        time = (Expression<Timespan>) exprs[2];
        return true;
    }
    
    @Override
    protected String[] get(Event e) {
        if (this.world == null) {
            return api.getPerm(this.group.getSingle(e));
        } else {
            return api.getPerm(this.group.getSingle(e), this.world.getSingle(e));
        }
    }
    
    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.ADD || mode == Changer.ChangeMode.REMOVE) {
            return CollectionUtils.array(String[].class);
        }
        return null;
    }
    
    @Override
    public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
        String[] perms = delta == null ? null : Arrays.copyOf(delta, delta.length, String[].class);
        String group = this.group.getSingle(e);
        World world = this.world == null ? null : this.world.getSingle(e);
        int sec = time == null ? 0 : ((int) time.getSingle(e).getTicks_i() / 20);
        if (perms == null) return;
        for (String perm : perms) {
            switch (mode) {
                case ADD:
                    if (sec == 0)
                        if (world != null)
                            api.addPerm(group, perm, world);
                        else
                            api.addPerm(group, perm);
                    else if (world != null)
                        api.addPerm(group, perm, world, sec);
                    else
                        api.addPerm(group, perm, sec);
                    break;
                case REMOVE:
                    if (world != null)
                        api.removePerm(group, perm, world);
                    else
                        api.removePerm(group, perm);
                    break;
            }
        }
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
    public String toString(Event e, boolean d) {
        return "permissions of " + group.toString(e, d) + (world == null ? "" : " in world " + world.toString(e, d));
    }
    
}
