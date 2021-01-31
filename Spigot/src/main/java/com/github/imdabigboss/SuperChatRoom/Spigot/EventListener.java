package com.github.imdabigboss.SuperChatRoom.Spigot;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.github.imdabigboss.SuperChatRoom.connector.*;

public class EventListener implements Listener {	
	private ChatRoom chatRoom = SuperChatRoom.getChatRoom();
	
	@EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) //when a player quits the game
    {
		Player player = event.getPlayer();
		SuperChatRoom.getChatRoom().leaveRoom(player.getName());
    }
	
	@EventHandler
	public void playerChat(AsyncPlayerChatEvent event){
		Player p = event.getPlayer();
		if (chatRoom.isInRoom(p.getName())) {
			event.setFormat(ChatColor.BLUE + chatRoom.playerRooms.get(p.getName()) + "> " + ChatColor.RESET + p.getDisplayName() + ChatColor.RESET + ": " + event.getMessage());
			
			event.getRecipients().clear();
			List<String> playerNames = chatRoom.getRoomPlayers(p.getName());
			event.getRecipients().addAll(SuperChatRoom.stringsToPlayers(playerNames));
		} else {
			event.setFormat("<" + p.getDisplayName() + ChatColor.RESET + "> " + event.getMessage());
		}
	}
}
