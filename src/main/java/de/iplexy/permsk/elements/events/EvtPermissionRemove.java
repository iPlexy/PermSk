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
import de.iplexy.permsk.api.events.PermissionAddEvent;
import de.iplexy.permsk.api.events.PermissionRemoveEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

@Name("Remove permission")
@Since("2.1")
@Description("")
@Examples({"on permission remove:", "\tbroadcast \"%player% remove the permission %event-permission%!\""})
public class EvtPermissionRemove extends SkriptEvent {

    static {
        Skript.registerEvent("Remove Group", EvtPermissionRemove.class, PermissionRemoveEvent.class, "[permsk] permission remove");

        EventValues.registerEventValue(PermissionRemoveEvent.class, Player.class, new Getter<Player, PermissionRemoveEvent>() {
            @Override
            public Player get(PermissionRemoveEvent e) {
                return e.getPlayer();
            }
        }, 0);

        EventValues.registerEventValue(PermissionRemoveEvent.class, String.class, new Getter<String, PermissionRemoveEvent>() {
            @Override
            public String get(PermissionRemoveEvent e) {
                return e.getGroup();
            }
        }, 0);

        EventValues.registerEventValue(PermissionRemoveEvent.class, String.class, new Getter<String, PermissionRemoveEvent>() {
            @Override
            public String get(PermissionRemoveEvent e) {
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
        return e instanceof PermissionRemoveEvent;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "Permission remove";
    }


}
