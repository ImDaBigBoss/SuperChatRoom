package com.github.imdabigboss.SuperChatRoom.Nukkit;

import java.util.List;

import com.github.imdabigboss.SuperChatRoom.connector.*;

import cn.nukkit.utils.TextFormat;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerChatEvent;

public class EventListener implements Listener {
    private final SuperChatRoom plugin;
    private final ChatRoom chatRoom;

    public EventListener(SuperChatRoom plugin) {
        this.plugin = plugin;
        this.chatRoom = plugin.getChatRoom();
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onChat(PlayerChatEvent e) {
    	String message = e.getMessage();
        String name = e.getPlayer().getName();
    	
    	if (chatRoom.isInRoom(name)) {
    		e.setMessage(TextFormat.BLUE + chatRoom.playerRooms.get(name) + ">" + TextFormat.RESET + " <" + name + "> " + message);
    		e.setRecipients(plugin.stringsToSet(chatRoom.getRoomPlayers(name)));
    	}
    }
}
