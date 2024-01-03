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
import org.bukkit.World;
import org.bukkit.event.Event;

@Name("Suffix of Group")
@Since("2.0.0-pre1")
public class ExprGroupSuffix extends SimpleExpression<String> {
    static {
        Skript.registerExpression(ExprGroupSuffix.class, String.class, ExpressionType.PROPERTY,
                "[permsk] suffix of group %string% [in [world] %-world%]",
                "[permsk] group %string%'s suffix [in [world] %-world%]");
    }

    private final PermissionApi api = PermSk.getPermissionApi();
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
    protected String[] get(Event e) {
        if (world == null)
            return CollectionUtils.array(api.getGroupSuffix(group.getSingle(e)));
        else
            return CollectionUtils.array(api.getGroupSuffix(group.getSingle(e), world.getSingle(e)));
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET) {
            return CollectionUtils.array(String.class);
        }
        return null;
    }

    @Override
    public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
        String group = this.group.getSingle(e);
        String value = (String) delta[0];
        if (mode == Changer.ChangeMode.SET) {
            if (world == null)
                api.setGroupSuffix(group, value);
            else
                api.setGroupSuffix(group, value, world.getSingle(e));
        }
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public String toString(Event e, boolean d) {
        return "Suffix of group " + group.toString(e, d);
    }
}
