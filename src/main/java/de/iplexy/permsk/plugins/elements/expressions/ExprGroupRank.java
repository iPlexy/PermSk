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

@Name("Permission: Group Rank")
@Description("Set the rank of a group, also supports add, remove, reset and get. " +
    "[Requires a permission plugin, Currently only supports PEX]")
@Examples({"set rank of group \"mod\" to 100", "reset rank of group \"admin\"",
    "set {_rank} to rank of group \"owner\""})
@Since("2.0.0")
public class ExprGroupRank extends SimpleExpression<Number> {
    
    static {
        Skript.registerExpression(ExprGroupRank.class, Number.class, ExpressionType.PROPERTY,
            "[the] rank of group %string%", "group %string%'s rank");
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
        return CollectionUtils.array(api.getGroupRank(this.group.getSingle(e)));
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
        int oldRank;
        Number rank = delta != null ? (Number) delta[0] : 0;
        switch (mode) {
            case SET:
                api.setGroupRank(group, rank.intValue());
                break;
            case ADD:
                oldRank = api.getGroupRank(group);
                api.setGroupRank(group, (rank.intValue() + oldRank));
                break;
            case REMOVE:
                oldRank = api.getGroupRank(group);
                api.setGroupRank(group, (oldRank - rank.intValue()));
                break;
            case RESET:
                api.setGroupRank(group, 0);
                break;
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
