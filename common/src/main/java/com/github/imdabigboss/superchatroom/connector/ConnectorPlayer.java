package com.github.imdabigboss.superchatroom.connector;

public interface ConnectorPlayer {
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
