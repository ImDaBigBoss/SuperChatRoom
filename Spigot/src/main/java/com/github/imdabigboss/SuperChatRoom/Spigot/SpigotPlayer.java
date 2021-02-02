package com.github.imdabigboss.SuperChatRoom.Spigot;

import org.bukkit.entity.Player;

public class SpigotPlayer implements com.github.imdabigboss.SuperChatRoom.connector.Player {
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
