package de.iplexy.permsk.plugins.elements.expressions;

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
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import de.iplexy.permsk.SkPerm;
import de.iplexy.permsk.utils.api.API;
import org.bukkit.event.Event;

@Name("Permission: Group Weight")
@Description("Set the weight of a group, also supports add, remove, reset and get. " +
    "[Requires a permission plugin, Currently supports PEX, LuckPerms and UltraPermissions]")
@Examples({"set weight of group \"owner\" to 1", "set weight of group \"admin\" to 100",
    "reset weight of group \"default\"", "set {_weight} to weight of group \"admin\""})
@Since("2.0.0")
public class ExprGroupWeight extends SimpleExpression<Number> {
    
    static {
        Skript.registerExpression(ExprGroupWeight.class, Number.class, ExpressionType.PROPERTY,
            "weight of group %string%", "group %string%'s weight");
    }
    
    private final API api = SkPerm.getAPI();
    private Expression<String> group;
    
    @SuppressWarnings({"unchecked"})
    @Override
    public boolean init(Expression<?>[] exprs, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        group = (Expression<String>) exprs[0];
        return true;
    }
    
    @Override
    protected Number[] get(Event e) {
        return CollectionUtils.array(api.getGroupWeight(this.group.getSingle(e)));
    }
    
    @Override
    public Class<?>[] acceptChange(ChangeMode mode) {
        if (mode == ChangeMode.SET || mode == ChangeMode.REMOVE || mode == ChangeMode.RESET || mode == ChangeMode.ADD) {
            return CollectionUtils.array(Number.class);
        }
        return null;
    }
    
    @Override
    public void change(Event e, Object[] delta, ChangeMode mode) {
        String group = this.group.getSingle(e);
        Number weight = delta != null ? (Number) delta[0] : 0;
        int oldWeight;
        switch (mode) {
            case SET:
                api.setGroupWeight(group, weight.intValue());
                break;
            case ADD:
                oldWeight = api.getGroupWeight(group);
                api.setGroupWeight(group, (oldWeight + weight.intValue()));
                break;
            case REMOVE:
                oldWeight = api.getGroupWeight(group);
                api.setGroupWeight(group, (oldWeight - weight.intValue()));
                break;
            case RESET:
                api.setGroupWeight(group, 0);
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
