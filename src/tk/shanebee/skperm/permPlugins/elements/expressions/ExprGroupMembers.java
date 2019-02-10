package tk.shanebee.skperm.permPlugins.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer.ChangeMode;
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
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.event.Event;
import tk.shanebee.skperm.SkPerm;
import tk.shanebee.skperm.utils.api.API;

import javax.annotation.Nullable;

@Name("Permission: Group Members")
@Description("Get all the users in a group. Also supports adding and removing players to/from groups. " +
        "[Requires a permission plugin, Currently supports PEX and LuckPerms]")
@Examples({"add player to group \"owner\"", "remove player from group \"moderator\""})
@Since("2.0.0")
public class ExprGroupMembers extends SimpleExpression<OfflinePlayer> {

    private API api = SkPerm.getAPI();

    static {
        Skript.registerExpression(ExprGroupMembers.class, OfflinePlayer.class, ExpressionType.PROPERTY,
                "[members of] group %string% [in [world] %-world%] [for %-timespan%])");
    }

    @SuppressWarnings("null")
    private Expression<World> world;
    private Expression<String> group;
    private Expression<Timespan> time;

    @SuppressWarnings({"null", "unchecked"})
    @Override
    public boolean init(Expression<?>[] exprs, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        group = (Expression<String>) exprs[0];
        world = (Expression<World>) exprs[1];
        time = (Expression<Timespan>) exprs[2];
        return true;
    }

    @Override
    public Class<?>[] acceptChange(ChangeMode mode) {
        if (mode == ChangeMode.ADD || mode == ChangeMode.REMOVE) {
            return CollectionUtils.array(OfflinePlayer.class);
        }
        return null;
    }

    @Override
    public void change(Event e, Object[] delta, ChangeMode mode) {
        OfflinePlayer[] players = (OfflinePlayer[]) delta;
        World world = this.world == null ? null : this.world.getSingle(e);
        String group = this.group.getSingle(e);
        int sec = time == null ? 0 : ((int) time.getSingle(e).getTicks_i() / 20);

        for (OfflinePlayer player : players) {
            switch (mode) {
                case ADD:
                    if (sec != 0)
                        if (world != null)
                            api.addPlayerToGroup(player, group, world, sec);
                        else
                            api.addPlayerToGroup(player, group, sec);
                    else
                        if (world != null)
                            api.addPlayerToGroup(player, group, world);
                        else
                            api.addPlayerToGroup(player, group);
                        break;
                case REMOVE:
                    if (sec != 0) return;
                    if (world != null)
                        api.removePlayerFromGroup(player, group, world);
                    else
                        api.removePlayerFromGroup(player, group);
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
    public String toString(Event e, boolean d) {
        return "group " + group.toString(e, d);
    }

    @Override
    @Nullable
    protected OfflinePlayer[] get(Event e) {
        return api.getPlayersInGroup(this.group.getSingle(e));
    }

}
