package com.github.imdabigboss.SuperChatRoom.Nukkit;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.github.imdabigboss.SuperChatRoom.connector.*;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.plugin.PluginBase;

public class SuperChatRoom extends PluginBase {
    private static ChatRoom chatRoom;
    
    @Override
    public void onEnable() {
        chatRoom = new ChatRoom();
        
        this.getServer().getPluginManager().registerEvents(new EventListener(this), this);
    }
    
    @Override
    public void onDisable() {
        
    }
    
    public ChatRoom getChatRoom() {
        return chatRoom;
    }
    
    public List<Player> stringsToPlayers(List<String> playerNames) {
    	List<Player> players = new ArrayList<Player>();
		for (String player : playerNames) {
			Player out = this.getServer().getPlayer(player);
			if (out != null)
				players.add(out);
		}
		return players;
    }
    
    public Set<CommandSender> stringsToSet(List<String> playerNames) {
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
        	if (sender instanceof Player) { //Get if executing origin is a player
        	} else {
        		sender.sendMessage("You must be a player to use this command!");
        		return true;
        	}
        	
        	if (args.length == 0) {
				sender.sendMessage("Correct usage is /shout <message>");
				return true;
			}
        	
        	String message = String.join(" ", args);
            this.getServer().broadcastMessage("<" + sender.getName() + "> " + message);
            return true;
        }
        if (command.getName().equalsIgnoreCase("chatroom")) {
        	return new CommandChatRoom(this).runCommand(sender, command, label, args);
        }
        return false;
    }
}