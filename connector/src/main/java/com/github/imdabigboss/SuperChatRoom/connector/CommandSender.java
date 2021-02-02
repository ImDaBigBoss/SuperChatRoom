package com.github.imdabigboss.SuperChatRoom.connector;

public interface CommandSender {
	String getName();
	Player getPlayer();
	
	default void sendMessage(String[] messages) {
		for (String message : messages) {
            sendMessage(message);
        }
	}
	
	void sendMessage(String message);
	
	boolean isConsole();
}
