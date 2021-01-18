package com.github.imdabigboss.superchatroom;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class EventListener implements Listener {	
	private ChatRoom chatRoom = SuperChatRoom.getChatRoom();
	
	@EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) //when a player quits
    {
		Player player = event.getPlayer();
		SuperChatRoom.getChatRoom().leaveRoom(player);
    }
	
	@EventHandler
	public void playerChat(AsyncPlayerChatEvent event){
		Player p = event.getPlayer();
		if (chatRoom.isInRoom(p.getName())) {
			event.setFormat(ChatColor.BLUE + chatRoom.playerRooms.get(p.getName()) + "> " + ChatColor.RESET + p.getDisplayName() + ChatColor.RESET + ": " + event.getMessage());
			
			event.getRecipients().clear();
			event.getRecipients().addAll(chatRoom.getRoomPlayers(p.getName()));
		} else {
			event.setFormat(p.getDisplayName() + ChatColor.RESET + ": " + event.getMessage());
		}
	}
}
