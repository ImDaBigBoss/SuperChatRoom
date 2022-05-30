package com.github.imdabigboss.superchatroom.spigot;

import com.github.imdabigboss.superchatroom.connector.ConnectorPlayer;
import org.bukkit.entity.Player;

public class SpigotPlayer implements ConnectorPlayer {
    private final Player handle;

    public SpigotPlayer(Player handle) {
        this.handle = handle;
    }

    @Override
    public String getName() {
        return handle.getName();
    }

    @Override
    public String getDisplayName() {
        return handle.getDisplayName();
    }

    @Override
    public boolean isOnline() {
        return handle.isOnline();
    }

    @Override
    public void sendMessage(String message) {
        handle.sendMessage(message);
    }

    public Player getPlayer() {
        return handle;
    }
}
