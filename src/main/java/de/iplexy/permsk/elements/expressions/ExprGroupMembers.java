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
public class ExprGroupMembers extends SimpleExpression<OfflinePlayer> {
    static {
        Skript.registerExpression(ExprGroupMembers.class, OfflinePlayer.class, ExpressionType.PROPERTY,
                "[permsk] [all] members of group %string% [in [world] %-world%]",
                "[permsk] group %string%'s members [in [world] %-world%]");
    }

    private final PermissionApi api = PermSk.getPermissionApi();
    private Expression<String> group;
    private Expression<World> world;

    @SuppressWarnings({"unchecked"})
    @Override
    public boolean init(Expression<?>[] exprs, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        group = (Expression<String>) exprs[0];
        world = (Expression<World>) exprs[1];
        return true;
    }

    @Override
    protected OfflinePlayer[] get(Event e) {
        if (this.world == null) {
            return api.getPlayersInGroup(this.group.getSingle(e)).toArray(new OfflinePlayer[0]);
        } else {
            return api.getPlayersInGroup(this.group.getSingle(e), this.world.getSingle(e)).toArray(new OfflinePlayer[0]);
        }
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.ADD ||
                mode == Changer.ChangeMode.REMOVE ||
                mode == Changer.ChangeMode.REMOVE_ALL) {
            return CollectionUtils.array(OfflinePlayer[].class);
        }
        return null;
    }

    @Override
    public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
        OfflinePlayer[] players = Arrays.copyOf(delta, delta.length, OfflinePlayer[].class);
        String group = this.group.getSingle(e);
        World world = this.world == null ? null : this.world.getSingle(e);
        for (OfflinePlayer player : players) {
            if (mode == Changer.ChangeMode.ADD) {
                if (world != null) {
                    api.addPlayerToGroup(player, group, world);
                } else {
                    api.addPlayerToGroup(player, group);
                }
            } else if (mode == Changer.ChangeMode.REMOVE) {
                if (world != null) {
                    api.removePlayerFromGroup(player, group, world);
                } else {
                    api.removePlayerFromGroup(player, group);
                }
            }
        }
        if (mode == Changer.ChangeMode.REMOVE_ALL) {
            if (world != null) {
                api.getPlayersInGroup(group, world)
                        .forEach(p -> api.removePlayerFromGroup(p, group));
            } else {
                api.getPlayersInGroup(group)
                        .forEach(p -> api.removePlayerFromGroup(p, group));
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
    public String toString(Event e, boolean d) {
        return "Members of " + group.toString(e, d) + (world == null ? "" : " in world " + world.toString(e, d));
    }
}
