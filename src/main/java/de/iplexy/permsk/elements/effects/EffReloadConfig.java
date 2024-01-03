package de.iplexy.permsk.elements.effects;


import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;

@Name("Reload PermissionsEx Config")
@Since("2.0.0-pre1")
@Description("Reloads the PermissionsEx config.")
public class EffReloadConfig extends Effect {

    static {
        Skript.registerEffect(EffReloadConfig.class, "[permsk] reload pex [config]");
    }
    @Override
    protected void execute(Event event) {
        //Todo: Implement with pex
    }

    @Override
    public String toString(Event event, boolean b) {
        return "reload pex config";
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        return true;
    }
}
