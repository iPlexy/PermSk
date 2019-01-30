package tk.shanebee.skperm.pex.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import org.bukkit.event.Event;
import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.bukkit.PermissionsEx;

@Name("PEX: Group Rank")
@Description("Set the rank of a group")
@Examples({"set rank of group \"mod\" to 100", "reset rank of group \"admin\"",
        "set {_rank} to rank of group \"owner\""})
@RequiredPlugins({"Vault", "PermissionsEX"})
@Since("1.1.0")
public class ExprGroupRank extends SimpleExpression<Number> {

    static {
        Skript.registerExpression(ExprGroupRank.class, Number.class, ExpressionType.PROPERTY,
                "[the] rank of group %string%", "group %string%'s rank");
    }

    private Expression<String> group;

    @SuppressWarnings({"unchecked"})
    @Override
    public boolean init(Expression<?>[] exprs, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        group = (Expression<String>) exprs[0];
        return true;
    }

    @Override
    public Class<?>[] acceptChange(ChangeMode mode) {
        if (mode == ChangeMode.SET || mode == ChangeMode.REMOVE || mode == ChangeMode.RESET) {
            return CollectionUtils.array(Number.class);
        }
        return null;
    }

    @Override
    protected Number[] get(Event e) {
        PermissionGroup group = PermissionsEx.getPermissionManager().getGroup(this.group.getSingle(e));
        return CollectionUtils.array(group.getRank());
    }

    @Override
    public void change(Event e, Object[] delta, ChangeMode mode) {
        PermissionGroup group = PermissionsEx.getPermissionManager().getGroup(this.group.getSingle(e));
        Number rank = (Number) delta[0];
        switch (mode) {
            case SET:
                group.setRank(rank.intValue());
                break;
            case REMOVE:
            case RESET:
                group.getRank();
        }
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Number> getReturnType() {
        return Number.class;
    }

    @Override
    public String toString(Event e, boolean d) {
        return "rank of group " + group.toString(e, d);
    }

}
