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
import de.iplexy.permsk.PermSk;
import de.iplexy.permsk.api.PermissionApi;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

@Name("Groups of Player")
@Since("2.0.0-pre1")
public class ExprPlayerGroups extends SimpleExpression<String> {

    static {
        Skript.registerExpression(ExprPlayerGroups.class, String.class, ExpressionType.PROPERTY,
                "[permsk] [all] groups of [player] %offlineplayer%",
                "[permsk] [player] %offlineplayer%'s groups");
    }

    private final PermissionApi api = PermSk.getPermissionApi();
    private Expression<Player> player;
    private int parse;

    @SuppressWarnings({"unchecked"})
    @Override
    public boolean init(Expression<?>[] exprs, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        player = (Expression<Player>) exprs[0];
        parse = parseResult.mark;
        return true;
    }

    @Override
    protected String[] get(Event e) {
        String[] groups = null;
        groups = api.getInheritedGroups(player.getSingle(e)).toArray(new String[0]);
        return groups;
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        return null;
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
        return "groups of "+player.getSingle(e).getName();
    }

}