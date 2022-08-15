package de.iplexy.permsk.misc.elements.conditions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;

@Name("Server Whitelisted")
@Description("Check if the server is white listed or not")
@Examples({"if server is whitelisted:", "if server is not whitelisted:"})
@Since("2.3.0")
public class CondServerWhitelist extends Condition {
    
    
    static {
        Skript.registerCondition(CondServerWhitelist.class, "server is whitelisted", "server is(n'| no)t whitelisted");
    }
    
    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        setNegated(i == 1);
        return true;
    }
    
    @Override
    public boolean check(Event event) {
        boolean check = Bukkit.hasWhitelist();
        return (isNegated() != check);
    }
    
    @Override
    public String toString(Event e, boolean b) {
        return "server whitelisted";
    }
    
}
