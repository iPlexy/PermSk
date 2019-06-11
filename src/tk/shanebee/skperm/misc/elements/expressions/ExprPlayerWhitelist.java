package tk.shanebee.skperm.misc.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.Event;

import java.util.ArrayList;

@Name("Server Whitelist")
@Description("Add/remove players from the server's whitelist. Get all the players in the server's whitelist.")
@Examples({"add arg-1 to whitelist", "remove arg-1 from whitelist",
		"reset server whitelist", "set {_wl::*} to server whitelist"})
@Since("2.3.0")
public class ExprPlayerWhitelist extends SimpleExpression<OfflinePlayer> {

	static {
		Skript.registerExpression(ExprPlayerWhitelist.class, OfflinePlayer.class, ExpressionType.PROPERTY, "[server] whitelist");
	}

	@Override
	public boolean init(Expression<?>[] exprs, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
		return true;
	}

	@Override
	protected OfflinePlayer[] get(Event event) {
		ArrayList<OfflinePlayer> wl = new ArrayList<>(Bukkit.getWhitelistedPlayers());
		return wl.toArray(new OfflinePlayer[0]);
	}

	@Override
	public Class<?>[] acceptChange(ChangeMode mode) {
		if (mode == ChangeMode.ADD || mode == ChangeMode.REMOVE) {
			return CollectionUtils.array(OfflinePlayer.class);
		} else if (mode == ChangeMode.RESET || mode == ChangeMode.SET) {
			return CollectionUtils.array(Boolean.class);
		}
		return null;
	}

	@Override
	public void change(Event e, Object[] delta, ChangeMode mode) {
		if (mode == ChangeMode.ADD) {
			((OfflinePlayer) delta[0]).setWhitelisted(true);
		} else if (mode == ChangeMode.REMOVE) {
			((OfflinePlayer) delta[0]).setWhitelisted(false);
		} else if (mode == ChangeMode.RESET) {
			for (OfflinePlayer player : Bukkit.getWhitelistedPlayers()) {
				player.setWhitelisted(false);
			}
		} else if (mode == ChangeMode.SET) {
			Bukkit.setWhitelist(((Boolean) delta[0]));
		}
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public Class<? extends OfflinePlayer> getReturnType() {
		return OfflinePlayer.class;
	}

	@Override
	public String toString(Event e, boolean b) {
		return "whitelist";
	}

}
