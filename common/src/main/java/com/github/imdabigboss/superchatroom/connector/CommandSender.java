package com.github.imdabigboss.superchatroom.connector;

public interface CommandSender {
    String getName();

    ConnectorPlayer getPlayer();

    default void sendMessage(String[] messages) {
        for (String message : messages) {
            sendMessage(message);
        }
    }

    void sendMessage(String message);

    boolean isConsole();
}
