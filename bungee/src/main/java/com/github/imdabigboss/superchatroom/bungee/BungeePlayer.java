package com.github.imdabigboss.superchatroom.bungee;

import com.github.imdabigboss.superchatroom.connector.ConnectorPlayer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class BungeePlayer implements ConnectorPlayer {
    private final ProxiedPlayer handle;

    public BungeePlayer(ProxiedPlayer handle) {
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
        return handle.isConnected();
    }

    @Override
    public void sendMessage(String message) {
        handle.sendMessage(TextComponent.fromLegacyText(message));
    }

    public ProxiedPlayer getPlayer() {
        return handle;
    }
}
