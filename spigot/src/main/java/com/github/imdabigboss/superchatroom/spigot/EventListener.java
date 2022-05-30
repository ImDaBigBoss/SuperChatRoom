package com.github.imdabigboss.superchatroom.spigot;

import com.github.imdabigboss.superchatroom.connector.ChatRoom;
import com.github.imdabigboss.superchatroom.connector.Util;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class EventListener implements Listener {
    private final SuperChatRoom plugin;
    private final ChatRoom chatRoom;

    public EventListener(SuperChatRoom plugin) {
        this.plugin = plugin;
        this.chatRoom = plugin.getChatRoom();
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        plugin.getChatRoom().leaveRoom(event.getPlayer().getName());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerChat(AsyncPlayerChatEvent event) {
        String message = event.getMessage();
        String name = event.getPlayer().getName();
        String displayName = event.getPlayer().getDisplayName();

        if (chatRoom.isInRoom(name)) {
            event.setCancelled(true);
            message = ChatColor.BLUE + chatRoom.playerRooms.get(name) + "> " + ChatColor.RESET + Util.formatChat(displayName, message, plugin.getChatFormat());

            plugin.messageToList(chatRoom.getRoomPlayers(name), message);
        }

        for (String player : chatRoom.showGeneral.keySet()) {
            if (!chatRoom.showGeneral.get(player)) {
                event.getRecipients().remove(plugin.getServer().getPlayer(player));
            }
        }
    }
}
