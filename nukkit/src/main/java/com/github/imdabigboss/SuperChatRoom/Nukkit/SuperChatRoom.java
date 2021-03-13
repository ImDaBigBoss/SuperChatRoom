package com.github.imdabigboss.superchatroom.nukkit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.github.imdabigboss.superchatroom.connector.*;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.plugin.PluginBase;

public class SuperChatRoom extends PluginBase  implements com.github.imdabigboss.superchatroom.connector.SuperChatRoom {
    private static ChatRoom chatRoom;
	private static String chatFormat;
    
    @Override
    public void onEnable() {
		Util.platform= "nukkit";
        chatRoom = new ChatRoom();

		this.saveDefaultConfig();
		if (this.getConfig().exists("chat_style"))
			chatFormat = this.getConfig().getString("chat_style");
		else
			chatFormat = "<%player%> %message%";
        
        this.getServer().getPluginManager().registerEvents(new EventListener(this), this);
    }
    
    @Override
    public void onDisable() {
        
    }

	@Override
    public ChatRoom getChatRoom() {
        return chatRoom;
    }

	public String getChatFormat() {
		return chatFormat;
	}
    
    public Set<CommandSender> stringsToSet(List<String> playerNames) {
    	if (playerNames.isEmpty())
    		return null;
    	
    	Set<CommandSender> players = new HashSet<CommandSender>(); 
		for (String player : playerNames) {
			Player out = this.getServer().getPlayer(player);
			if (out != null)
				players.add(out);
		}
		return players;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("shout")) {
        	return new CommandShout(this).runCommand(new NukkitCommandSender(sender), label, args);
        }
        if (command.getName().equalsIgnoreCase("chatroom")) {
        	return new CommandChatRoom(this).runCommand(new NukkitCommandSender(sender), label, args);
        }
        return false;
    }
    
    @Override
	public void broadcastMessage(String message) {
		getServer().broadcastMessage(message);
	}

	@Override
	public com.github.imdabigboss.superchatroom.connector.Player getPlayer(String player) {
		return new NukkitPlayer(getServer().getPlayer(player));
	}
	
	@Override
	public Collection<com.github.imdabigboss.superchatroom.connector.Player> getOnlinePlayers() {
		Collection<com.github.imdabigboss.superchatroom.connector.Player> out = new ArrayList<com.github.imdabigboss.superchatroom.connector.Player>();
		for (Player p : this.getServer().getOnlinePlayers().values()) {
			out.add(new NukkitPlayer(p));
		}
		return out;
	}
	
	@Override
	public Collection<com.github.imdabigboss.superchatroom.connector.Player> getOnlinePlayersOnServer(com.github.imdabigboss.superchatroom.connector.Player player) {
		return getOnlinePlayers();
	}

	@Override
	public void messageToList(List<String> players, String message) {
		for (String player : players) {
			this.getServer().getPlayer(player).sendMessage(message);
		}
	}
}