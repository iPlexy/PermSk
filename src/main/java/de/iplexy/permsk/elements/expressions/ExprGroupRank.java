package de.iplexy.permsk.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import de.iplexy.permsk.PermSk;
import de.iplexy.permsk.api.PermissionApi;
import org.bukkit.event.Event;

@Name("Rank of Group")
@Since("2.0.0-pre1")
public class ExprGroupRank extends SimpleExpression<Number> {

    static {
        Skript.registerExpression(ExprGroupRank.class, Number.class, ExpressionType.PROPERTY,
                "[permsk] [the] rank of group %string%",
                "[permsk] group %string%'s rank");
    }

    private final PermissionApi api = PermSk.getPermissionApi();
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
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.REMOVE || mode == Changer.ChangeMode.RESET || mode == Changer.ChangeMode.ADD) {
            return CollectionUtils.array(Number.class);
        }
        return null;
    }

    @Override
    public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
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
