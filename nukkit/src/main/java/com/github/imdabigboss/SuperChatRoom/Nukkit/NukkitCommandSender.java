package com.github.imdabigboss.superchatroom.nukkit;

import com.github.imdabigboss.superchatroom.connector.Player;

import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;

public class NukkitCommandSender implements com.github.imdabigboss.superchatroom.connector.CommandSender {
	private final CommandSender handle;
	
	public NukkitCommandSender(CommandSender handle) {
        this.handle = handle;
    }
	
	@Override
	public Player getPlayer() {
		if (isConsole())
			return null;
		
		return new NukkitPlayer((cn.nukkit.Player) handle);
	}
	
	@Override
	public String getName() {
		return handle.getName();
	}

	@Override
	public void sendMessage(String message) {
		handle.sendMessage(message);
	}

	@Override
	public boolean isConsole() {
		return handle instanceof ConsoleCommandSender;
	}
	
}
