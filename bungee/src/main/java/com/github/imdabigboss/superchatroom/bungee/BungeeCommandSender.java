package com.github.imdabigboss.superchatroom.bungee;

import com.github.imdabigboss.superchatroom.connector.Player;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ConnectedPlayer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class BungeeCommandSender implements com.github.imdabigboss.superchatroom.connector.CommandSender {
	private final CommandSender handle;
	
	public BungeeCommandSender(CommandSender handle) {
        this.handle = handle;
    }
	
	@Override
	public Player getPlayer() {
		if (isConsole())
			return null;
		
		return new BungeePlayer((ProxiedPlayer) handle);
	}
	
	@Override
    public void sendMessage(String message) {
        handle.sendMessage(TextComponent.fromLegacyText(message));
    }

    @Override
    public boolean isConsole() {
        return !(handle instanceof ProxiedPlayer || handle instanceof ConnectedPlayer);
    }
    
    @Override
    public String getName() {
        return handle.getName();
    }
}
