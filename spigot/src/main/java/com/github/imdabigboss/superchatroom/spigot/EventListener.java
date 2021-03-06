package com.github.imdabigboss.superchatroom.spigot;

import java.util.List;

import com.github.imdabigboss.superchatroom.connector.ChatRoom;

import com.github.imdabigboss.superchatroom.connector.Util;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class EventListener implements Listener {	
	private final SuperChatRoom plugin;
    private final ChatRoom chatRoom;

    public EventListener(SuperChatRoom plugin) {
        this.plugin = plugin;
        this.chatRoom = plugin.getChatRoom();
    }
	
	@EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) //when a player quits the game
    {
		Player player = e.getPlayer();
		plugin.getChatRoom().leaveRoom(player.getName());
    }

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void playerChat(AsyncPlayerChatEvent e){
		String message = e.getMessage();
		String name = e.getPlayer().getName();
		String displayName = e.getPlayer().getDisplayName();

		if (chatRoom.isInRoom(name)) {
			e.setFormat(ChatColor.BLUE + chatRoom.playerRooms.get(name) + "> " + ChatColor.RESET + Util.formatChat(displayName, message, plugin.getChatFormat()));
			
			e.getRecipients().clear();
			List<String> playerNames = chatRoom.getRoomPlayers(name);
			e.getRecipients().addAll(plugin.stringsToPlayers(playerNames));
		}

		for (String player : chatRoom.showGeneral.keySet()) {
			if (chatRoom.showGeneral.get(player).equalsIgnoreCase("hide")) {
				e.getRecipients().remove(player);
			}
		}
	}
}
