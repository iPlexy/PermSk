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
import de.iplexy.permsk.api.events.PermissionAddEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

@Name("Add permission")
@Since("2.1")
@Description("")
@Examples({"on permission add:", "\tbroadcast \"%player% got the permission %event-permission%!\""})
public class EvtPermissionAdd extends SkriptEvent {

    static {
        Skript.registerEvent("Add Group", EvtPermissionAdd.class, PermissionAddEvent.class, "[permsk] permission add");

        EventValues.registerEventValue(PermissionAddEvent.class, Player.class, new Getter<Player, PermissionAddEvent>() {
            @Override
            public Player get(PermissionAddEvent e) {
                return e.getPlayer();
            }
        }, 0);

        EventValues.registerEventValue(PermissionAddEvent.class, String.class, new Getter<String, PermissionAddEvent>() {
            @Override
            public String get(PermissionAddEvent e) {
                return e.getGroup();
            }
        }, 0);

        EventValues.registerEventValue(PermissionAddEvent.class, String.class, new Getter<String, PermissionAddEvent>() {
            @Override
            public String get(PermissionAddEvent e) {
                return e.getPermission();
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
        return e instanceof PermissionAddEvent;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "Permission Add";
    }


}
