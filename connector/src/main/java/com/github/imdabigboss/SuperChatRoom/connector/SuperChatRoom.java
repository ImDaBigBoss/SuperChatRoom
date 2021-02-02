package com.github.imdabigboss.SuperChatRoom.connector;

import java.util.Collection;

public interface SuperChatRoom {
	ChatRoom getChatRoom();
	
	default void broadcastMessage(String[] messages) {
		for (String message : messages) {
			broadcastMessage(message);
        }
	}
	
	void broadcastMessage(String message);
	
	Player getPlayer(String player);

	Collection<Player> getOnlinePlayers();
}
