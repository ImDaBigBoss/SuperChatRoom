package com.github.imdabigboss.superchatroom.connector;

import java.util.Collection;
import java.util.List;

public interface SuperChatRoom {
    ChatRoom getChatRoom();

    void messageToList(List<String> players, String message);

    ConnectorPlayer getPlayer(String player);

    Collection<ConnectorPlayer> getOnlinePlayers();

    Collection<ConnectorPlayer> getOnlinePlayersOnServer(ConnectorPlayer player);

    String getChatFormat();
}
