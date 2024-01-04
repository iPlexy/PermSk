package de.iplexy.permsk.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.util.Timespan;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import de.iplexy.permsk.PermSk;
import de.iplexy.permsk.api.PermissionApi;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.event.Event;

import java.util.Arrays;

@Name("Members of group")
@Since("2.0.0-pre1")
@Description("Returns the members of a group.")
public class ExprGroupMembers extends SimpleExpression<String> {
    static {
        Skript.registerExpression(ExprGroupMembers.class, String.class, ExpressionType.PROPERTY,
                "[permsk] [all] members of group %string% [in [world] %-world%]",
                "[permsk] group %string%'s members [in [world] %-world%]");
    }

    private final PermissionApi api = PermSk.getPermissionApi();
    private Expression<String> group;
    private Expression<World> world;
    private Expression<Timespan> time;

    @SuppressWarnings({"unchecked"})
    @Override
    public boolean init(Expression<?>[] exprs, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        group = (Expression<String>) exprs[0];
        world = (Expression<World>) exprs[1];
        return true;
    }

    @Override
    protected String[] get(Event e) {
        if (this.world == null) {
            return api.getPlayersInGroup(this.group.getSingle(e)).toArray(new String[0]);
        } else {
            return api.getPlayersInGroup(this.group.getSingle(e), this.world.getSingle(e)).toArray(new String[0]);
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
        OfflinePlayer[] players = delta == null ? null : Arrays.copyOf(delta, delta.length, OfflinePlayer[].class);
        String group = this.group.getSingle(e);
        World world = this.world == null ? null : this.world.getSingle(e);
        int sec = time == null ? 0 : ((int) time.getSingle(e).getTicks_i() / 20);
        if (players == null) return;
        for (OfflinePlayer player : players) {
            switch (mode) {
                case ADD:
                    if (world != null)
                        api.addPlayerToGroup(player, group, world);
                    else
                        api.addPlayerToGroup(player, group);
                    break;
                case REMOVE:
                    if (world != null)
                        api.removePlayerFromGroup(player, group, world);
                    else
                        api.removePerm(player, group);
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
        return "Members of " + group.toString(e, d) + (world == null ? "" : " in world " + world.toString(e, d));
    }
}
