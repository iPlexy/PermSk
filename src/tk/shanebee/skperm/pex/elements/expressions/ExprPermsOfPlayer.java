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
import net.milkbowl.vault.permission.Permission;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.event.Event;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;
import tk.shanebee.skperm.SkPerm;

import java.util.Arrays;

@Name("PEX: Permissions of Player")
@Description("Returns a list of all the player's permissions with an option to get the permissions for a specific world. " +
        "You can also add/remove permissions to/from a player.")
@Examples({"set {_perms::*} to all permissions of player",
        "set {_perms::*} to all permissions of player in \"world\"",
        "send \"Perms in %world of player%: %all permissions of player in world of player%\"",
        "add \"essentials.chat\" to permissions of player",
        "add \"essentials.fly\" to permissions of player in world of player",
        "remove \"essentials.fly\" from permissions of player"})
@RequiredPlugins({"PermissionsEX", "Vault"})
@Since("1.1.0")
public class ExprPermsOfPlayer extends SimpleExpression<String> {

    private Permission manager = SkPerm.perms;

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
        String w = world == null ? null : world.getSingle(e).getName();
        int sec = time == null ? 0 : ((int) time.getSingle(e).getTicks_i() / 20);
        switch (mode) {
            case ADD:
                if (perms == null) return;
                for (String perm : perms) {
                    if (sec == 0) {
                        manager.playerAdd(w, player.getSingle(e), perm);
                    }
                    else {
                        PermissionsEx.getUser(player.getSingle(e).getName()).addTimedPermission(perm, w, sec);
                    }
                }
                break;
            case REMOVE:
                if (perms == null) return;
                for (String perm : perms) {
                    manager.playerRemove(w, player.getSingle(e), perm);
                }
                break;
        }
    }

    @Override
    protected String[] get(Event e) {
        String world = this.world == null ? null : this.world.getSingle(e).getName();
        PermissionUser user = PermissionsEx.getPermissionManager().getUser(player.getSingle(e).getUniqueId());
        return user.getPermissions(world).toArray(new String[0]);
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
