package tk.shanebee.skperm.pex.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import tk.shanebee.skperm.SkPerm;
import tk.shanebee.skperm.utils.api.API;

@Name("Permission: Create Group")
@Description("Create a new permission group. Optional parents can be added. Currently supports PEX")
@Examples({"create new group \"default\"",
        "create new group \"moderator\" with parent \"default\"", "create new group \"admin\" with parents \"moderator\" and \"default\"",
        "remove group \"moderator\""})
@Since("2.0.0")
public class EffCreateGroup extends Effect {

    API api = SkPerm.getAPI();

    static {
        Skript.registerEffect(EffCreateGroup.class,
                "(0¦create [new]|1¦(delete|remove)) group %string% [with parent[s] %-strings%]");
    }

    private Expression<String> group;
    private int parse;
    private Expression<String> parents;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, ParseResult parseResult) {
        group = (Expression<String>) expr[0];
        parse = parseResult.mark;
        parents = (Expression<String>) expr[1];
        return true;
    }

    @Override
    protected void execute(Event e) {
        if (parse == 0) {
            if (parents != null) {
                api.createGroup(this.group.getSingle(e), parents.getArray(e));
            } else {
                api.createGroup(this.group.getSingle(e));
            }
        } else {
            api.removeGroup(this.group.getSingle(e));
        }
    }

    @Override
    public String toString(Event e, boolean d) {
        return (parse == 0 ? "create new" : " delete") + " group " + group.toString(e, d) +
                (parents != null ? " with parents " + parents.toString(e, d) : "");
    }

}

