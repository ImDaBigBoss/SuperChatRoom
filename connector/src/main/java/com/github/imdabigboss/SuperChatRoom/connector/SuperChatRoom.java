package com.github.imdabigboss.superchatroom.connector;

import java.util.Collection;
import java.util.List;

public interface SuperChatRoom {
	ChatRoom getChatRoom();
	
	default void broadcastMessage(String[] messages) {
		for (String message : messages) {
			broadcastMessage(message);
        }
	}

	void messageToList(List<String> players, String message);
	
	void broadcastMessage(String message);
	
	Player getPlayer(String player);

	Collection<Player> getOnlinePlayers();
	
	/**
	* This only makes a difference for BungeeCord.
	*/
	Collection<Player> getOnlinePlayersOnServer(com.github.imdabigboss.superchatroom.connector.Player player);

	String getChatFormat();
}
