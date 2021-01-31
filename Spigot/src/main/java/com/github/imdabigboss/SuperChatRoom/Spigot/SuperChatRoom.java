package com.github.imdabigboss.SuperChatRoom.Spigot;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import com.github.imdabigboss.SuperChatRoom.connector.*;

public class SuperChatRoom extends JavaPlugin {
    private static Plugin plugin;
    private static ChatRoom chatRoom;
    
    // Fired when plugin is first enabled
    @Override
    public void onEnable() {    	
        plugin = this;
        chatRoom = new ChatRoom();
        
        getServer().getPluginManager().registerEvents(new EventListener(), this); //Enable the listener
        
        //Enable the commands
        this.getCommand("chatroom").setExecutor(new CommandChatRoom());
        this.getCommand("shout").setExecutor(new CommandShout());
    }
    
    // Fired when plugin is disabled
    @Override
    public void onDisable() {
        
    }
    
    public static Plugin getPlugin() {
        return plugin;
    }
    
    public static ChatRoom getChatRoom() {
        return chatRoom;
    }
    
    public static List<Player> stringsToPlayers(List<String> playerNames) {
    	List<Player> players = new ArrayList<Player>();
		for (String player : playerNames) {
			Player out = SuperChatRoom.getPlugin().getServer().getPlayer(player);
			if (out != null)
				players.add(out);
		}
		return players;
    }
}