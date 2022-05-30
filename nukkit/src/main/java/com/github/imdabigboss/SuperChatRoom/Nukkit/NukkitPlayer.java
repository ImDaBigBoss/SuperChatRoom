package com.github.imdabigboss.superchatroom.nukkit;

import cn.nukkit.Player;
import com.github.imdabigboss.superchatroom.connector.ConnectorPlayer;

public class NukkitPlayer implements ConnectorPlayer {
    private final Player handle;

    public NukkitPlayer(Player handle) {
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
