package de.iplexy.permsk.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.util.Timespan;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import de.iplexy.permsk.PermSk;
import de.iplexy.permsk.api.PermissionApi;
import org.bukkit.World;
import org.bukkit.event.Event;

import java.util.Arrays;

@Name("Inherited Groups of Group")
@Since("2.0.0-pre1")
public class ExprGroupInheritedGroups extends SimpleExpression<String> {

    static {
        Skript.registerExpression(ExprGroupInheritedGroups.class, String.class, ExpressionType.PROPERTY,
                "[permsk] [all] [group] inheritances of group %string%",
                "[permsk] group %string%'s [group] inheritances");
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
    protected String[] get(Event e) {
        return api.getInheritedGroups(this.group.getSingle(e)).toArray(new String[0]);
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        return null;
    }

    @Override
    public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
        return;
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
        return "Inherited groups of " + group.toString(e, d);
    }

}
