package tk.shanebee.skperm.pex.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.*;
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

import java.util.Arrays;

@Name("Permission: Permissions of Player")
@Description("Returns a list of all the player's permissions with an option to get the permissions for a specific world. " +
        "You can also add/remove permissions to/from a player (Supports lists of permissions/strings) " +
        "[Support for timed permissions does not seem to be working in PEX. May or may not work]" +
        " Currently only supported in PEX")
@Examples({"set {_perms::*} to all permissions of player",
        "set {_perms::*} to all permissions of player in \"world\"",
        "send \"Perms in %world of player%: %all permissions of player in world of player%\"",
        "add \"essentials.chat\" to permissions of player",
        "add \"essentials.fly\" to permissions of player in world of player",
        "remove \"essentials.fly\" from permissions of player"})
@Since("2.0.0")
public class ExprPermsOfPlayer extends SimpleExpression<String> {

    private API api = SkPerm.getAPI();

    static {
        Skript.registerExpression(ExprPermsOfPlayer.class, String.class, ExpressionType.PROPERTY,
                "perm[ission][s] of %offlineplayer% [in [world] %-world%] [for %-timespan%]",
                "%offlineplayer%'s perm[ission][s] [in [world] %-world%] [for %-timespan%]");
    }

    private Expression<OfflinePlayer> player;
    private Expression<World> world;
    private Expression<Timespan> time;

    @SuppressWarnings({"unchecked"})
    @Override
    public boolean init(Expression<?>[] exprs, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        player = (Expression<OfflinePlayer>) exprs[0];
        world = (Expression<World>) exprs[1];
        time = (Expression<Timespan>) exprs[2];
        return true;
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
        World world = this.world == null ? null : this.world.getSingle(e);
        int sec = time == null ? 0 : ((int) time.getSingle(e).getTicks_i() / 20);
        OfflinePlayer player = this.player.getSingle(e);

        switch (mode) {
            case ADD:
                if (perms == null) return;
                for (String perm : perms) {
                    if (sec == 0) {
                        if (world != null)
                            api.addPerm(player, perm, world);
                        else
                            api.addPerm(player, perm);
                    }
                    else {
                        if (world != null)
                            api.addPerm(player, perm, world, sec);
                        else
                            api.addPerm(player, perm, sec);
                    }
                }
                break;
            case REMOVE:
                if (perms == null) return;
                for (String perm : perms) {
                    if (world != null)
                        api.removePerm(player, perm, world);
                    else
                        api.removePerm(player, perm);
                }
                break;
        }
    }

    @Override
    protected String[] get(Event e) {
        if (this.world != null)
            return api.getPerm(player.getSingle(e), this.world.getSingle(e));
        else
            return api.getPerm(player.getSingle(e));
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
        return "permissions of " + player.toString(e, d) + (world == null ? "" : " in world " + world.toString(e, d));
    }
}
