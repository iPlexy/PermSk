package de.iplexy.permsk.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import de.iplexy.permsk.PermSk;
import de.iplexy.permsk.api.PermissionApi;
import org.bukkit.event.Event;

import java.util.Arrays;

@Name("Create Group")
@Since("2.0.0-pre1")
@Description("Creates a Group. (Optional: With parent groups)")
public class EffCreateGroup extends Effect {

    static {
        Skript.registerEffect(EffCreateGroup.class,
                "[permsk] create [new] group %string% [with parent[s] %-strings%]");
    }

    private final PermissionApi api = PermSk.getPermissionApi();
    private Expression<String> group;
    private Expression<String> parents;

    @Override
    protected void execute(Event event) {
        if (parents != null) {
            api.createGroup(this.group.getSingle(event), Arrays.asList(parents.getArray(event)));
        } else {
            api.createGroup(this.group.getSingle(event));
        }
    }

    @Override
    public String toString(Event event, boolean b) {
        return "create new group " + group.toString(event, b) +
                (parents != null ? " with parents " + parents.toString(event, b) : "");
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        group = (Expression<String>) expressions[0];
        parents = (Expression<String>) expressions[1];
        return true;
    }
}
