package com.github.imdabigboss.superchatroom.nukkit;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerChatEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.utils.TextFormat;
import com.github.imdabigboss.superchatroom.connector.ChatRoom;
import com.github.imdabigboss.superchatroom.connector.Util;

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
    public void onChat(PlayerChatEvent event) {
        String message = event.getMessage();
        String name = event.getPlayer().getName();
        String displayName = event.getPlayer().getDisplayName();

        if (chatRoom.isInRoom(name)) {
            event.setCancelled(true);
            message = TextFormat.BLUE + chatRoom.playerRooms.get(name) + "> " + TextFormat.RESET + Util.formatChat(displayName, message, plugin.getChatFormat());

            plugin.messageToList(chatRoom.getRoomPlayers(name), message);
        }

        for (String player : chatRoom.showGeneral.keySet()) {
            if (!chatRoom.showGeneral.get(player)) {
                event.getRecipients().remove(plugin.getServer().getPlayer(player));
            }
        }
    }
}
