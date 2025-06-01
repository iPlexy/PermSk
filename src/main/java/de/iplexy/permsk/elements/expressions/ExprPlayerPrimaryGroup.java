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

@Name("Primary Group of Player")
@Since("2.0.0-pre1")
public class ExprPlayerPrimaryGroup extends SimpleExpression<String> {

    static {
        Skript.registerExpression(ExprPlayerPrimaryGroup.class, String.class, ExpressionType.COMBINED,
                "[permsk] [primary] group of %offlineplayer%");
    }

    private final PermissionApi api = PermSk.getPermissionApi();
    private Expression<OfflinePlayer> player;

    @Override
    protected String[] get(Event event) {
        return new String[]{api.getPrimaryGroup(player.getSingle(event))};
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
    public String toString(Event event, boolean b) {
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        player = (Expression<OfflinePlayer>) exprs[0];
        return true;
    }

    @Override
    public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET) {
            return CollectionUtils.array(String.class);
        }
        return null;
    }

    @Override
    public void change(Event event, Object[] delta, Changer.ChangeMode mode) {
        OfflinePlayer p = player.getSingle(event);
        if (p != null) {
            if (mode == Changer.ChangeMode.SET) {
                System.out.println("Test 1 " + delta[0]);
                api.setPrimaryGroup(p, (String) delta[0]);
            }
        }
    }
}