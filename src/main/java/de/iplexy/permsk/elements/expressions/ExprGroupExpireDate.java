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
import org.bukkit.OfflinePlayer;
import org.bukkit.event.Event;

import java.util.Date;

@Name("Expire Date of Group")
@Since("2.0.0-pre1")
public class ExprGroupExpireDate extends SimpleExpression<Date> {

    static {
        Skript.registerExpression(ExprGroupExpireDate.class, Date.class, ExpressionType.PROPERTY,
                "[permsk] expire date of group %string% for [player] %offlineplayer%",
                "[permsk] [player] %offlineplayer%'s expire date of group %string%");
    }

    private final PermissionApi api = PermSk.getPermissionApi();
    private Expression<String> group;
    private Expression<OfflinePlayer> player;

    @SuppressWarnings({"unchecked"})
    @Override
    public boolean init(Expression<?>[] exprs, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        group = (Expression<String>) exprs[0];
        player = (Expression<OfflinePlayer>) exprs[1];
        return true;
    }

    @Override
    protected Date[] get(Event e) {
        return CollectionUtils.array(api.getGroupExpireDate(player.getSingle(e), group.getSingle(e)));
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
        return true;
    }

    @Override
    public Class<? extends Date> getReturnType() {
        return Date.class;
    }

    @Override
    public String toString(Event e, boolean d) {
        return "Expire date of group " + group.toString(e, d);
    }
}