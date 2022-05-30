package com.github.imdabigboss.superchatroom.bungee;

import com.github.imdabigboss.superchatroom.connector.ChatRoom;
import com.github.imdabigboss.superchatroom.connector.Util;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

import java.util.List;

public class EventListener implements Listener {
    private final SuperChatRoom plugin;
    private final ChatRoom chatRoom;

    public EventListener(SuperChatRoom plugin) {
        this.plugin = plugin;
        this.chatRoom = plugin.getChatRoom();
    }

    @EventHandler
    public void onPlayerQuit(PlayerDisconnectEvent event) {
        plugin.getChatRoom().leaveRoom(event.getPlayer().getName());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerChat(ChatEvent event) {
        String message = event.getMessage();

        if (message.startsWith("/")) {
            return;
        }

        ProxiedPlayer player = (ProxiedPlayer) event.getSender();
        String name = player.getName();
        String displayName = player.getDisplayName();

        if (chatRoom.isInRoom(name)) {
            event.setCancelled(true);
            message = ChatColor.BLUE + chatRoom.playerRooms.get(name) + "> " + ChatColor.RESET + Util.formatChat(displayName, message, plugin.getChatFormat());

            plugin.messageToList(chatRoom.getRoomPlayers(name), message);
        }
    }
}
