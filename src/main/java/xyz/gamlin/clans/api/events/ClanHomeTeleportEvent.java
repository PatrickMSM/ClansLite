package xyz.gamlin.clans.api.events;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import xyz.gamlin.clans.models.Clan;

public class ClanHomeTeleportEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();
    private final Player createdBy;
    private final Clan clan;
    private final Location homeLocation;
    private final Location tpFromLocation;

    public ClanHomeTeleportEvent(Player createdBy, Clan clan, Location homeLocation, Location tpFromLocation) {
        this.createdBy = createdBy;
        this.clan = clan;
        this.homeLocation = homeLocation;
        this.tpFromLocation = tpFromLocation;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public Player getCreatedBy() {
        return createdBy;
    }

    public Clan getClan() {
        return clan;
    }

    public Location getHomeLocation() {
        return homeLocation;
    }

    public Location getTpFromLocation() {
        return tpFromLocation;
    }
}
