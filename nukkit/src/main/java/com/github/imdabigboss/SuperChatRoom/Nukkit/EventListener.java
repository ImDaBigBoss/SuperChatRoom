package com.github.imdabigboss.superchatroom.nukkit;

import cn.nukkit.Player;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.utils.TextFormat;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerChatEvent;

import com.github.imdabigboss.superchatroom.connector.ChatRoom;
import com.github.imdabigboss.superchatroom.connector.Util;

import java.util.List;

public class EventListener implements Listener {
    private final SuperChatRoom plugin;
    private final ChatRoom chatRoom;

    public EventListener(SuperChatRoom plugin) {
        this.plugin = plugin;
        this.chatRoom = plugin.getChatRoom();
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) //when a player quits the game
    {
        Player player = e.getPlayer();
        plugin.getChatRoom().leaveRoom(player.getName());
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onChat(PlayerChatEvent e) {
    	String message = e.getMessage();
        String name = e.getPlayer().getName();
        String displayName = e.getPlayer().getDisplayName();
    	
    	if (chatRoom.isInRoom(name)) {
    		e.setMessage(TextFormat.BLUE + chatRoom.playerRooms.get(name) + "> " + TextFormat.RESET + Util.formatChat(displayName, message, plugin.getChatFormat()));

    		e.getRecipients().clear();
            List<String> playerNames = chatRoom.getRoomPlayers(name);
    		e.setRecipients(plugin.stringsToSet(playerNames));
    	}

        for (String player : chatRoom.showGeneral.keySet()) {
            if (chatRoom.showGeneral.get(player).equalsIgnoreCase("hide")) {
                e.getRecipients().remove(player);
            }
        }
    }
}
