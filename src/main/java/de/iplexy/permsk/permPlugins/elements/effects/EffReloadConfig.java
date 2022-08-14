package de.iplexy.permsk.permPlugins.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import ru.tehkode.permissions.bukkit.PermissionsEx;

@Name("PEX: Reload Config")
@Description("This allows you to reload the pex config. Often when you change permissions they won't take effect " +
        "until the pex config has reloaded.")
@Examples("reload pex config")
@RequiredPlugins({"Vault", "PermissionsEX"})
@Since("1.1.0")
public class EffReloadConfig extends Effect {

    static {
        Skript.registerEffect(EffReloadConfig.class, "reload pex [config]");
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        return true;
    }

    @Override
    protected void execute(Event event) {
        PermissionsEx.getPlugin().reloadConfig();
    }

    @Override
    public String toString(Event event, boolean b) {
        return "reload pex config";
    }

}
