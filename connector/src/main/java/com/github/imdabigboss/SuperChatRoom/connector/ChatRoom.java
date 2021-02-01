package com.github.imdabigboss.SuperChatRoom.connector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatRoom {	
	public Map<String, ArrayList<String>> chatRooms = new HashMap<String, ArrayList<String>>();
	public Map<String, String> playerRooms = new HashMap<String, String>();
	public Map<String, String> playerSeeGeneral = new HashMap<String, String>();
	
	public int createRoom(String roomName, String player) {
		if (chatRooms.containsKey(roomName))
			return 1;
		
		ArrayList<String> players = new ArrayList<String>();
		players.add(player);
		chatRooms.put(roomName, players);
		playerSeeGeneral.put(player, "on");
		setPlayerRoom(roomName, player);
		return 0;
	}
	
	public int joinRoom(String roomName, String player) {
		if (!chatRooms.containsKey(roomName))
			return 1;
		
		ArrayList<String> players = chatRooms.get(roomName);
		players.add(player);
		
		chatRooms.replace(roomName, players);
		playerSeeGeneral.put(player, "on");
		setPlayerRoom(roomName, player);
		return 0;
	}
	
	public int leaveRoom(String player) {
		if (!playerRooms.containsKey(player))
			return 1;
		
		String currentRoom = playerRooms.get(player);
		ArrayList<String> roomplayers = chatRooms.get(currentRoom);
		roomplayers.remove(player);
		
		if (roomplayers.isEmpty())
			chatRooms.remove(currentRoom);
		else {
			chatRooms.replace(currentRoom, roomplayers);
		}
		
		playerRooms.remove(player);
		playerSeeGeneral.remove(player);
		return 0;
	}
	
	public int playerSeeGeneral(String player, String onORoff) {
		if (!playerRooms.containsKey(player))
			return 1;
		
		if (onORoff.equalsIgnoreCase("on") || onORoff.equalsIgnoreCase("off")) {
			playerSeeGeneral.replace(player, onORoff);
		} else
			return 2;
		
		return 0;
	}
	
	public boolean getPlayerSeeGeneral(String player) {
		if (playerSeeGeneral.containsKey(player)) {
			if (playerSeeGeneral.get(player).equalsIgnoreCase("on")) {
				return true;
			} else if (playerSeeGeneral.get(player).equalsIgnoreCase("off")) {
				return false;
			}
		}
		return true;
	}
	
	public boolean isInRoom(String player) {
		return playerRooms.containsKey(player);
	}
	
	public List<String> getRoomPlayers(String player) {
		return chatRooms.get(playerRooms.get(player));
	}
	
	private void setPlayerRoom(String roomName, String player) {
		if (playerRooms.containsKey(player)) {
			playerRooms.replace(player, roomName);
		} else {
			playerRooms.put(player, roomName);
		}
	}
}
