package de.iplexy.permsk.vault.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.permissions.PermissionAttachmentInfo;
import de.iplexy.permsk.SkPerm;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

@Name("Vault Universal: Player's Permissions")
@Description("Add or remove permissions of a player. Worlds are only supported if your permission plugin supports them." +
        "Getting permissions for offline players is not supported.")
@Examples({"add \"essentials.home\" to permission of player", "add \"essentials.teleport\" to permission of player in world \"world\"",
        "remove \"essentials.tp\" from permission of player"})
@RequiredPlugins("Vault")
@Since("1.0.0")
public class ExprPlayerPerm extends SimpleExpression<String> {

    private final Permission manager = SkPerm.getPerms();

    static {
        Skript.registerExpression(ExprPlayerPerm.class, String.class, ExpressionType.PROPERTY,
                "perm[ission][s] of %offlineplayers% [in [world]%-world%]",
                "%offlineplayers%'s perm[ission][s] [in [world]%-world%]");
    }

    @SuppressWarnings("null")
    private Expression<OfflinePlayer> players;
    private Expression<World> world;

    @SuppressWarnings({"null", "unchecked"})
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        players = (Expression< OfflinePlayer>) exprs[0];
        world = (Expression<World>) exprs[1];
        return true;
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        return switch (mode) {
            case ADD, REMOVE -> new Class[]{String.class};
            default -> null;
        };
    }

    @Override
    public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
        if (delta == null) return; // should never happen but just in case
        String w = world == null ? null : world.getSingle(e).getName();
        for (Object value : delta) { // casting arrays isn't always safe
            if (value == null) continue;
            for (OfflinePlayer player : players.getArray(e))
                switch(mode) {
                    case ADD -> manager.playerAdd(w, player, value.toString());
                    case REMOVE -> manager.playerRemove(w, player, value.toString());
                }
        }
    }

    @Override
    protected String[] get(Event e) {
        final Set<String> permissions = new HashSet<>();
        for (OfflinePlayer player : players.getArray(e)) {
            if (player.isOnline()) {
                for (final PermissionAttachmentInfo permission : player.getPlayer().getEffectivePermissions())
                    permissions.add(permission.getPermission());
            } else {
                permissions.add(ChatColor.RED + player.getName() + " is not online");
            }
        }
        return permissions.toArray(new String[0]);
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
        return "permissions of " + players.toString(event, debug);
    }
}
