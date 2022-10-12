package de.iplexy.permsk.plugins.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import de.iplexy.permsk.SkPerm;
import de.iplexy.permsk.utils.api.API;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

@Name("Permission: Groups of player")
@Description("Returns a list of all the groups the player has. " +
    "If you just user group, you'll get their main group.")
@Examples({"set {_groups::*} to all groups of player",
    "set {_groups::*} to player's groups"})
@Since("1.1.1")
public class ExprPlayerGroups extends SimpleExpression<String> {
    
    static {
        Skript.registerExpression(ExprPlayerGroups.class, String.class, ExpressionType.PROPERTY,
            "[all] groups of [player] %offlineplayer%",
            "[player] %offlineplayer%'s groups");
    }
    
    private final API api = SkPerm.getAPI();
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
        if (parse == 0){
            groups = new String[]{api.getPrimaryGroup(player.getSingle(e))};
        }else{
            groups = api.getInheritedGroups(player.getSingle(e)).toArray(new String[0]);
        }
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
