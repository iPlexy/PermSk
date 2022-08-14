package de.iplexy.permsk.misc.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;

@Name("Reload Whitelist")
@Description("Reload the server whitelist")
@Examples("reload whitelist")
@Since("2.3.0")
public class EffReloadWhitelist extends Effect {

	static {
		Skript.registerEffect(EffReloadWhitelist.class, "reload [server] whitelist");
	}

	@Override
	public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
		return true;
	}

	@Override
	protected void execute(Event event) {
		Bukkit.reloadWhitelist();
	}

	@Override
	public String toString(Event event, boolean b) {
		return "reload whitelist";
	}

}
