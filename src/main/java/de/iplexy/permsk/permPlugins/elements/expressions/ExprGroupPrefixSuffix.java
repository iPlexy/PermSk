package de.iplexy.permsk.permPlugins.elements.expressions;

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
import de.iplexy.permsk.SkPerm;
import de.iplexy.permsk.utils.api.API;
import org.bukkit.World;
import org.bukkit.event.Event;

@Name("Permission: Group Prefix/Suffix")
@Description("Get/Set prefix/suffix of groups. IF you have issues with it clashing with Skript's expression, " +
        "make sure to add \"perm\" or \"permission\" to the front of your code" +
        "[Requires a permission plugin, Currently supports PEX, LuckPerms and UltraPermissions]")
@Examples({"set {_pre} to perm prefix of group \"owner\"", "set perm prefix of group \"owner\" to \"[OWNER]\"",
        "set perm prefix of group \"member\" in world \"world\" to \"[MEMBER-WORLD]\"",
        "set perm suffix of group \"player\" in world \"world_nether\" to \"[HELL]\""})
@Since("2.1.0")
public class ExprGroupPrefixSuffix extends SimpleExpression<String> {

    private API api = SkPerm.getAPI();

    static {
        Skript.registerExpression(ExprGroupPrefixSuffix.class, String.class, ExpressionType.PROPERTY,
                "[the] [perm[ission]] (0¦prefix|1¦suffix) of group %string% [in [world] %-world%]",
                "group %string%'s [the] [perm[ission]] (0¦prefix|1¦suffix) [in [world] %-world%]");
    }

    private Expression<String> group;
    private Expression<World> world;
    private int parse;

    @SuppressWarnings({"unchecked"})
    @Override
    public boolean init(Expression<?>[] exprs, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        group = (Expression<String>) exprs[0];
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
                return CollectionUtils.array(api.getGroupPrefix(group.getSingle(e)));
            else
                return CollectionUtils.array(api.getGroupPrefix(group.getSingle(e), world.getSingle(e)));
        } else {
            if (world == null)
                return CollectionUtils.array(api.getGroupSuffix(group.getSingle(e)));
            else
                return CollectionUtils.array(api.getGroupSuffix(group.getSingle(e), world.getSingle(e)));
        }
    }

    @Override
    public void change(Event e, Object[] delta, ChangeMode mode) {
        String group = this.group.getSingle(e);
        String value = (String) delta[0];
        switch (mode) {
            case SET:
                if (parse == 0) {
                    if (world == null)
                        api.setGroupPrefix(group, value);
                    else
                        api.setGroupPrefix(group, value, world.getSingle(e));
                } else {
                    if (world == null)
                        api.setGroupSuffix(group, value);
                    else
                        api.setGroupSuffix(group, value, world.getSingle(e));
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
        return (parse == 0 ? "Prefix " : "Suffix ") + " of group " + group.toString(e, d);
    }

}