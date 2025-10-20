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
import de.iplexy.permsk.api.events.GroupRemoveEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import javax.annotation.Nullable;

@Name("Remove Group")
@Since("2.1")
@Description("")
@Examples({"on group remove:", "\tbroadcast \"%player% remove the group %event-group%!\""})
public class EvtGroupRemove extends SkriptEvent {

    static {
        Skript.registerEvent("Remove Group", EvtGroupRemove.class, GroupRemoveEvent.class, "[permsk] [(user|player)] group remove");

        EventValues.registerEventValue(GroupRemoveEvent.class, Player.class, new Getter<Player, GroupRemoveEvent>() {
            @Override
            public Player get(GroupRemoveEvent e) {
                return e.getPlayer();
            }
        }, 0);

        EventValues.registerEventValue(GroupRemoveEvent.class, String.class, new Getter<String, GroupRemoveEvent>() {
            @Override
            public String get(GroupRemoveEvent e) {
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
        return e instanceof GroupRemoveEvent;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "Group remove";
    }




}
