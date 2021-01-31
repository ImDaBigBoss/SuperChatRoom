package com.github.imdabigboss.SuperChatRoom.Nukkit;

import com.github.imdabigboss.SuperChatRoom.connector.*;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;

public class CommandChatRoom {
	private final SuperChatRoom plugin;
    private final ChatRoom chatRoom;
	
	public CommandChatRoom(SuperChatRoom plugin) {
        this.plugin = plugin;
        this.chatRoom = plugin.getChatRoom();
    }
	
    public boolean runCommand(CommandSender sender, Command command, String label, String[] args) {
    	if (sender instanceof Player) { //Get if executing origin is a player
    	} else {
    		sender.sendMessage("You must be a player to use this command!");
    		return true;
    	}
    	
    	if (args.length != 1 && args.length != 2) {
    		SendHelp(sender);
    		return true;
    	}
    	
    	if (args[0].equalsIgnoreCase("create")) {
    		if (args.length != 2) {
    			SendHelp(sender);
    			return true;
    		}
    		if (chatRoom.isInRoom(sender.getName())) {
    			sender.sendMessage(TextFormat.RED + "You are already in a room!");
    			return true;
    		}
    		
    		int out = chatRoom.createRoom(args[1], sender.getName());
    		if (out == 1)
    			sender.sendMessage(TextFormat.RED + "There is already a room with that name!");
    		else 
    			sender.sendMessage(TextFormat.AQUA + "You created " + args[1] + "!");
    	} else if (args[0].equalsIgnoreCase("join")) {
    		if (args.length != 2) {
    			SendHelp(sender);
    			return true;
    		}
    		if (chatRoom.isInRoom(sender.getName())) {
    			sender.sendMessage(TextFormat.RED + "You are already in a room!");
    			return true;
    		}
    		
    		int out = chatRoom.joinRoom(args[1], sender.getName());
    		if (out == 1)
    			sender.sendMessage(TextFormat.RED + "No existing room has that name!");
    		else {
    			for (Player player :plugin.stringsToPlayers(chatRoom.getRoomPlayers(sender.getName()))) {
    				player.sendMessage(sender.getName() + " joined your chat room!");
    			}
    			sender.sendMessage(TextFormat.AQUA + "You joined " + args[1] + "!");
    		}
    	} else if (args[0].equalsIgnoreCase("leave")) {
    		if (args.length != 1) {
    			SendHelp(sender);
    			return true;
    		}
    		if (!chatRoom.isInRoom(sender.getName())) {
    			sender.sendMessage(TextFormat.RED + "You are not in a room!");
    			return true;
    		}
    		
    		int out = chatRoom.leaveRoom(sender.getName());
    		if (out == 1)
    			sender.sendMessage(TextFormat.RED + "An error occured");
    		else {
    			for (Player player : plugin.stringsToPlayers(chatRoom.getRoomPlayers(sender.getName()))) {
    				player.sendMessage(sender.getName() + " left your chat room!");
    			}
    			sender.sendMessage(TextFormat.AQUA + "You left the room!");
    		}
    	} else if (args[0].equalsIgnoreCase("invite")) {
    		if (args.length != 2) {
    			SendHelp(sender);
    			return true;
    		}
    		if (!chatRoom.isInRoom(sender.getName())) {
    			sender.sendMessage(TextFormat.RED + "You are not in a room!");
    			return true;
    		}
    		
    		Player target = null;
    		try {
    			target = plugin.getServer().getPlayer(args[1]);
    			if (!target.isOnline()) {
    				sender.sendMessage(TextFormat.RED + args[1] + " is not online!");
        			return true;
    			}
    		} catch (Exception e) {
    			sender.sendMessage(TextFormat.RED + args[1] + " is not online!");
    			return true;
    		}
    		
    		String name = chatRoom.playerRooms.get(sender.getName());
    		target.sendMessage(sender.getName() + " has invited you to their chatroom named " + name + " to join, enter " + TextFormat.AQUA + "/chatroom join " + name);
    		
    	} else {
    		SendHelp(sender);
    	}
    	
    	return true;
    }
    
    public void SendHelp(CommandSender sender) {
    	sender.sendMessage("The correct usage is :");
    	sender.sendMessage(" - /chatroom create <RoomName>");
    	sender.sendMessage(" - /chatroom join <RoomName>");
    	sender.sendMessage(" - /chatroom leave");
    	sender.sendMessage(" - /chatroom invite <player>");
    }
}
