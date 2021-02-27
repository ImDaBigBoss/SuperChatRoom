package com.github.imdabigboss.superchatroom.spigot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.imdabigboss.superchatroom.connector.*;

public class SuperChatRoom extends JavaPlugin implements com.github.imdabigboss.superchatroom.connector.SuperChatRoom {
    private static Plugin plugin;
    private static ChatRoom chatRoom;
    private static String chatFormat;
    
    @Override
    public void onEnable() {    	
        plugin = this;
        chatRoom = new ChatRoom();

        this.saveDefaultConfig();
        if (this.getConfig().contains("chat_style"))
			chatFormat = this.getConfig().getString("chat_style");
        else
			chatFormat = "<%player%> %message%";

        this.getCommand("chatroom").setExecutor(new CommandManager(this));
        this.getCommand("shout").setExecutor(new CommandManager(this));
        
        getServer().getPluginManager().registerEvents(new EventListener(this), this); //Enable the listener
    }
    
    @Override
    public void onDisable() {
        
    }
    
    public Plugin getPlugin() {
        return plugin;
    }
    
    public ChatRoom getChatRoom() {
        return chatRoom;
    }

	@Override
    public String getChatFormat() {
    	return chatFormat;
	}
    
    public List<Player> stringsToPlayers(List<String> playerNames) {
    	if (playerNames.isEmpty())
    		return null;
    	
    	List<Player> players = new ArrayList<Player>();
		for (String player : playerNames) {
			Player out = getPlugin().getServer().getPlayer(player);
			if (out != null)
				players.add(out);
		}
		return players;
    }

	@Override
	public void broadcastMessage(String message) {
		getServer().broadcastMessage(message);
	}

	@Override
	public com.github.imdabigboss.superchatroom.connector.Player getPlayer(String player) {
		return new SpigotPlayer(getServer().getPlayer(player));
	}
	
	@Override
	public Collection<com.github.imdabigboss.superchatroom.connector.Player> getOnlinePlayers() {
		Collection<com.github.imdabigboss.superchatroom.connector.Player> out = new ArrayList<com.github.imdabigboss.superchatroom.connector.Player>();
		for (Player p : getServer().getOnlinePlayers()) {
			out.add(new SpigotPlayer(p));
		}
		return out;
	}
}