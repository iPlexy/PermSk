package de.iplexy.permsk.api.events;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
public class PermissionAddEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    private final Player player;
    private final String group;
    private final String permission;

    public PermissionAddEvent(Player player,String group, String permission) {
        super(true);
        this.player = player;
        this.group = group;
        this.permission = permission;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

}
