package com.github.imdabigboss.superchatroom.bungee;

import java.util.List;

import com.github.imdabigboss.superchatroom.connector.ChatRoom;
import com.github.imdabigboss.superchatroom.connector.Util;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.event.EventPriority;

public class EventListener implements Listener {
	private final SuperChatRoom plugin;
    private final ChatRoom chatRoom;

    public EventListener(SuperChatRoom plugin) {
        this.plugin = plugin;
        this.chatRoom = plugin.getChatRoom();
    }
	
	@EventHandler
    public void onPlayerQuit(PlayerDisconnectEvent e) //when a player quits the game
    {
		ProxiedPlayer player = e.getPlayer();
		plugin.getChatRoom().leaveRoom(player.getName());
    }

	@EventHandler(priority = EventPriority.HIGHEST)
	public void playerChat(ChatEvent e){
		String message = e.getMessage();
		
		if (message.startsWith("/"))
			return;
		
		ProxiedPlayer player = (ProxiedPlayer) e.getSender();
		String name = player.getName();
		String displayName = player.getDisplayName();

		if (chatRoom.isInRoom(name)) {
			e.setCancelled(true);
			message = ChatColor.BLUE + chatRoom.playerRooms.get(name) + "> " + ChatColor.RESET + Util.formatChat(displayName, message, plugin.getChatFormat());

			List<String> playerNames = chatRoom.getRoomPlayers(name);
			plugin.messageToList(playerNames, message);
		}
	}
}
