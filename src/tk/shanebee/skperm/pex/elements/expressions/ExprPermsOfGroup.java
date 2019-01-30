package tk.shanebee.skperm.pex.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.World;
import org.bukkit.event.Event;
import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.bukkit.PermissionsEx;

@Name("PEX: Permissions of Group")
@Description("Returns a list of all the group's permissions with an option to get the permissions for a specific world.")
@Examples({"set {_perms::*} to all permissions of group \"mod\"",
        "set {_perms::*} to all permissions of group \"builder\" in world \"world\"",
        "send \"Perms in %world of player%: %permissions of group \"\"admin\"\" in world of player%\""})
@RequiredPlugins({"Vault", "PermissionsEX"})
@Since("1.1.0")
public class ExprPermsOfGroup extends SimpleExpression<String> {

    static {
        Skript.registerExpression(ExprPermsOfGroup.class, String.class, ExpressionType.PROPERTY,
                "[all] permission[s] of group %string% [in [world] %-world%]",
                "group %string%'s permission[s] [in [world] %-world%]");
    }

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
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        return null;
    }

    @Override
    protected String[] get(Event e) {
        String world = this.world == null ? null : this.world.getSingle(e).getName();
        PermissionGroup group = PermissionsEx.getPermissionManager().getGroup(this.group.getSingle(e));
        return group.getPermissions(world).toArray(new String[0]);
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