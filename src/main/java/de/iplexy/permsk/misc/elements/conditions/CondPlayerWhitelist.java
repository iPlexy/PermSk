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
import org.bukkit.OfflinePlayer;
import org.bukkit.event.Event;

@Name("Player Whitelisted")
@Description("Check if an offline player is whitelisted")
@Examples({"if arg-1 is whitelisted:", "if arg-1 is not whitelisted:"})
@Since("2.3.0")
public class CondPlayerWhitelist extends Condition {

	private Expression<OfflinePlayer> player;

	static {
		Skript.registerCondition(CondPlayerWhitelist.class, "%offlineplayer% is whitelisted", "%offlineplayer% is(n'| no)t whitelisted");
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
		player = (Expression<OfflinePlayer>) exprs[0];
		setNegated(i == 1);
		return true;
	}

	@Override
	public boolean check(Event event) {
		boolean check = player.getSingle(event).isWhitelisted();
		return (isNegated() != check);
	}

	@Override
	public String toString(Event e, boolean b) {
		return player.toString(e, b) + " whitelisted";
	}

}
