package com.github.imdabigboss.SuperChatRoom.connector;

public interface Player {
	String getName();
	String getDisplayName();
	
	boolean isOnline();
	
	default void sendMessage(String[] messages) {
		for (String message : messages) {
            sendMessage(message);
        }
	}
	
	void sendMessage(String message);
}
