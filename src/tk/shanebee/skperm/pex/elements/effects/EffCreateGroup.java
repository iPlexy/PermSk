package tk.shanebee.skperm.pex.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.util.LinkedList;
import java.util.List;

@Name("Pex: Create Group")
@Description("Create a new PEX group. Optional parents can be added.")
@Examples({"pex create new group \"default\"",
        "pex create new group \"moderator\" with parent \"default\"",
        "pex remove group \"moderator\""})
@Since("1.1.0")
public class EffCreateGroup extends Effect {

    static {
        Skript.registerEffect(EffCreateGroup.class,
                "[pex] (0¦create [new]|1¦(delete|remove)) group %string% [with parent[s] %-strings%]");
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
        PermissionGroup group = PermissionsEx.getPermissionManager().getGroup(this.group.getSingle(e));
        List<PermissionGroup> groups = new LinkedList<>();
        if (parse == 0) {
            if (parents != null) {
                for (String parent : parents.getAll(e)) {
                    groups.add(PermissionsEx.getPermissionManager().getGroup(parent));
                }
                group.setParents(groups, null);
            }
            group.save();
        } else {
            group.remove();
        }
    }

    @Override
    public String toString(Event e, boolean d) {
        return "pex " + (parse == 0 ? "create new " : " delete") + "group " + group.toString(e, d) +
                (parents != null ? "with parents " + parents.toString(e, d) : "");
    }

}

