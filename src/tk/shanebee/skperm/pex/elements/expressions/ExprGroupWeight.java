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

@Name("PEX: Group Weight")
@Description("Set the weight of a group.")
@Examples({"set weight of group \"owner\" to 1", "set weight of group \"admin\" to 100",
        "reset weight of group \"default\"", "set {_weight} to weight of group \"admin\""})
@RequiredPlugins({"Vault", "PermissionsEX"})
@Since("1.1.0")
public class ExprGroupWeight extends SimpleExpression<Number> {

    static {
        Skript.registerExpression(ExprGroupWeight.class, Number.class, ExpressionType.PROPERTY,
                "weight of group %string%", "group %string%'s weight");
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
        return CollectionUtils.array(group.getWeight());
    }

    @Override
    public void change(Event e, Object[] delta, ChangeMode mode) {
        PermissionGroup group = PermissionsEx.getPermissionManager().getGroup(this.group.getSingle(e));
        Number weight = (Number) delta[0];
        switch (mode) {
            case SET:
                group.setWeight(weight.intValue());
                break;
            case REMOVE:
            case RESET:
                group.setWeight(0);
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
        return "weight of group " + group.toString(e, d);
    }
}
