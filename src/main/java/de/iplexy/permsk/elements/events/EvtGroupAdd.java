package de.iplexy.permsk.elements.events;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import de.iplexy.permsk.api.events.GroupAddEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

@Name("Add Group")
@Since("2.1")
@Description("")
@Examples({""})
public class EvtGroupAdd extends SkriptEvent {

    static {
        Skript.registerEvent("Add Group", EvtGroupAdd.class, GroupAddEvent.class, "[permsk] [(user|player)] group add");

        EventValues.registerEventValue(GroupAddEvent.class, Player.class, new Getter<Player, GroupAddEvent>() {
            @Override
            public Player get(GroupAddEvent e) {
                return e.getPlayer();
            }
        }, 0);

        EventValues.registerEventValue(GroupAddEvent.class, String.class, new Getter<String, GroupAddEvent>() {
            @Override
            public String get(GroupAddEvent e) {
                return e.getGroup();
            }
        }, 0);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Literal<?>[] args, int matchedPattern, SkriptParser.ParseResult parseResult) {
        return true;
    }

    @Override
    public boolean check(Event e) {
        return e instanceof GroupAddEvent;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "Group Add";
    }


}
