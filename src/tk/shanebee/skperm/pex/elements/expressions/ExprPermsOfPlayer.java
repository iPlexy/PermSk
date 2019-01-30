package tk.shanebee.skperm.pex.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.event.Event;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

@Name("PEX: Permissions of Player")
@Description("Returns a list of all the player's permissions with an option to get the permissions for a specific world.")
@Examples({"set {_perms::*} to permissions of player",
        "set {_perms::*} to permissions of player in \"world\"",
        "send \"Perms in %world of player%: %permissions of player in world of player%\""})
@RequiredPlugins({"PermissionsEX", "Vault"})
@Since("1.1.0")
public class ExprPermsOfPlayer extends SimpleExpression<String> {

    static {
        Skript.registerExpression(ExprPermsOfPlayer.class, String.class, ExpressionType.PROPERTY,
                "[all] permission[s] of %offlineplayer% [in [world] %-world%]",
                "%offlineplayer%'s permission[s] [in [world] %-world%]");
    }

    private Expression<OfflinePlayer> player;
    private Expression<World> world;

    @SuppressWarnings({"unchecked"})
    @Override
    public boolean init(Expression<?>[] exprs, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        player = (Expression<OfflinePlayer>) exprs[0];
        world = (Expression<World>) exprs[1];
        return true;
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        return null;
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
