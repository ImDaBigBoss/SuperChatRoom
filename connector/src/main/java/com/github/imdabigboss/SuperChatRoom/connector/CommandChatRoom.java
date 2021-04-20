package com.github.imdabigboss.superchatroom.connector;

import java.util.ArrayList;
import java.util.List;

public class CommandChatRoom {
	private final SuperChatRoom plugin;
	private final ChatRoom chatRoom;
	
	public CommandChatRoom(SuperChatRoom plugin) {
		this.plugin = plugin;
		this.chatRoom = plugin.getChatRoom();
	}
	
	public boolean runCommand(CommandSender sender, String label, String[] args) {
		if (sender.isConsole()) { //Get if executing origin is a player
			sender.sendMessage(ChatColor.RED + "You must be a player to use this command!");
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
				sender.sendMessage(ChatColor.RED + "You are already in a room!");
				return true;
			}
			
			int out = chatRoom.createRoom(args[1], sender.getName());
			if (out == 1)
				sender.sendMessage(ChatColor.RED + "There is already a room with that name!");
			else 
				sender.sendMessage(ChatColor.AQUA + "You created " + args[1] + "!");
		} else if (args[0].equalsIgnoreCase("join")) {
			if (args.length != 2) {
				SendHelp(sender);
				return true;
			}
			if (chatRoom.isInRoom(sender.getName())) {
				sender.sendMessage(ChatColor.RED + "You are already in a room!");
				return true;
			}
			
			int out = chatRoom.joinRoom(args[1], sender.getName());
			if (out == 1)
				sender.sendMessage(ChatColor.RED + "No existing room has that name!");
			else {
				for (Player player : stringsToPlayers(chatRoom.getRoomPlayers(sender.getName()))) {
					if (player.getName() != sender.getName()) {
						player.sendMessage(sender.getName() + " joined your chat room!");
					}
				}
				sender.sendMessage(ChatColor.AQUA + "You joined " + args[1] + "!");
			}
		} else if (args[0].equalsIgnoreCase("leave")) {
			if (args.length != 1) {
				SendHelp(sender);
				return true;
			}
			if (!chatRoom.isInRoom(sender.getName())) {
				sender.sendMessage(ChatColor.RED + "You are not in a room!");
				return true;
			}
			
			List<String> roomPlayers = chatRoom.getRoomPlayers(sender.getName());
			int out = chatRoom.leaveRoom(sender.getName());
			if (out == 1)
				sender.sendMessage(ChatColor.RED + "An error occured");
			else if (out == 2) { //No players in room
				sender.sendMessage(ChatColor.AQUA + "You left the room!");
			}
			else {
				for (Player player : stringsToPlayers(roomPlayers)) {
					player.sendMessage(sender.getName() + " left the chat room!");
				}
				sender.sendMessage(ChatColor.AQUA + "You left the room!");
			}
		} else if (args[0].equalsIgnoreCase("invite")) {
			if (args.length != 2) {
				SendHelp(sender);
				return true;
			}
			if (!chatRoom.isInRoom(sender.getName())) {
				sender.sendMessage(ChatColor.RED + "You are not in a room!");
				return true;
			}
			
			Player target = null;
			try {
				target = plugin.getPlayer(args[1]);
				if (!target.isOnline()) {
					sender.sendMessage(ChatColor.RED + args[1] + " is not online!");
					return true;
				}
			} catch (Exception e) {
				sender.sendMessage(ChatColor.RED + args[1] + " is not online!");
				return true;
			}

			sender.sendMessage("You invited " + args[1] + " to your chatroom!");
			String name = chatRoom.playerRooms.get(sender.getName());
			target.sendMessage(sender.getName() + " has invited you to their chatroom named " + name + " to join, enter " + ChatColor.AQUA + "/chatroom join " + name);
		} else if (args[0].equalsIgnoreCase("general")) {
			if (Util.platform == "bungee") {
				sender.sendMessage("This feature is currently not available on BungeeCord.");
				return true;
			}

			if (args.length != 2) {
				SendHelp(sender);
				return true;
			}
			if (!chatRoom.isInRoom(sender.getName())) {
				sender.sendMessage(ChatColor.RED + "You are not in a room!");
				return true;
			}

			if (args[1].equalsIgnoreCase("show")) {
				if (chatRoom.showGeneral.containsKey(sender.getName())) {
					chatRoom.showGeneral.replace(sender.getName(), "show");
				} else {
					chatRoom.showGeneral.put(sender.getName(), "show");
				}
			} else if (args[1].equalsIgnoreCase("hide")) {
				if (chatRoom.showGeneral.containsKey(sender.getName())) {
					chatRoom.showGeneral.replace(sender.getName(), "hide");
				} else {
					chatRoom.showGeneral.put(sender.getName(), "hide");
				}
			} else {
				SendHelp(sender);
				return true;
			}
		} else {
			SendHelp(sender);
		}
		
		return true;
	}
	
	public List<Player> stringsToPlayers(List<String> playerNames) {
		List<Player> players = new ArrayList<Player>();
		for (String player : playerNames) {
			Player out = plugin.getPlayer(player);
			if (out != null)
				players.add(out);
		}
		return players;
	}
	
	public void SendHelp(CommandSender sender) {
		sender.sendMessage("The correct usage is :");
		sender.sendMessage(" - /chatroom create <RoomName>");
		sender.sendMessage(" - /chatroom join <RoomName>");
		sender.sendMessage(" - /chatroom leave");
		sender.sendMessage(" - /chatroom invite <player>");
		if (Util.platform != "bungee")
			sender.sendMessage(" - /chatroom general show/hide");
	}
}
