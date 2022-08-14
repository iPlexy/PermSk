package de.iplexy.permsk.permPlugins.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import de.iplexy.permsk.SkPerm;
import de.iplexy.permsk.utils.api.API;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.event.Event;

@Name("Permission: Player Prefix/Suffix")
@Description("Get/Set prefix/suffix of players. IF you have issues with it clashing with Skript's expression, " +
        "make sure to add \"perm\" or \"permission\" to the front of your code" +
        "[Requires a permission plugin, Currently supports PEX, LuckPerms and UltraPermissions]")
@Examples({"set perm prefix of player to \"[MrBob]\"", "set {_pre} to prefix of player"})
@Since("2.2.0")
public class ExprPlayerPrefixSuffix  extends SimpleExpression<String> {

    private API api = SkPerm.getAPI();

    static {
        Skript.registerExpression(ExprPlayerPrefixSuffix.class, String.class, ExpressionType.PROPERTY,
                "[the] [perm[ission]] (0¦prefix|1¦suffix) of %offlineplayer% [in [world] %-world%]",
                "%offlineplayer%'s [the] [perm[ission]] (0¦prefix|1¦suffix) [in [world] %-world%]");
    }

    private Expression<OfflinePlayer> player;
    private Expression<World> world;
    private int parse;

    @SuppressWarnings({"unchecked"})
    @Override
    public boolean init(Expression<?>[] exprs, int i, Kleenean kleenean, ParseResult parseResult) {
        player = (Expression<OfflinePlayer>) exprs[0];
        world = (Expression<World>) exprs[1];
        parse = parseResult.mark;
        return true;
    }

    @Override
    public Class<?>[] acceptChange(ChangeMode mode) {
        if (mode == ChangeMode.SET) {
            return CollectionUtils.array(String.class);
        }
        return null;
    }

    @Override
    protected String[] get(Event e) {
        if (parse == 0) {
            if (world == null)
                return CollectionUtils.array(api.getPlayerPrefix(player.getSingle(e)));
            else
                return CollectionUtils.array(api.getPlayerPrefix(player.getSingle(e), world.getSingle(e)));
        } else {
            if (world == null)
                return CollectionUtils.array(api.getPlayerSuffix(player.getSingle(e)));
            else
                return CollectionUtils.array(api.getPlayerSuffix(player.getSingle(e), world.getSingle(e)));
        }
    }

    @Override
    public void change(Event e, Object[] delta, ChangeMode mode) {
        OfflinePlayer player = this.player.getSingle(e);
        String value = (String) delta[0];
        switch (mode) {
            case SET:
                if (parse == 0) {
                    if (world == null)
                        api.setPlayerPrefix(player, value);
                    else
                        api.setPlayerPrefix(player, value, world.getSingle(e));
                } else {
                    if (world == null)
                        api.setPlayerSuffix(player, value);
                    else
                        api.setPlayerSuffix(player, value, world.getSingle(e));
                }
        }
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
    public String toString(Event e, boolean d) {
        return (parse == 0 ? "Prefix " : "Suffix ") + " of player " + player.toString(e, d);
    }

}
