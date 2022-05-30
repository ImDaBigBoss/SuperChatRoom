package com.github.imdabigboss.superchatroom.connector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatRoom {
    public Map<String, ArrayList<String>> chatRooms = new HashMap<>();
    public Map<String, String> playerRooms = new HashMap<>();
    public Map<String, Boolean> showGeneral = new HashMap<>();

    public int createRoom(String roomName, String player) {
        if (chatRooms.containsKey(roomName))
            return 1;

        ArrayList<String> players = new ArrayList<>();
        players.add(player);

        chatRooms.put(roomName, players);
        this.setPlayerRoom(roomName, player);
        return 0;
    }

    public int joinRoom(String roomName, String player) {
        if (!chatRooms.containsKey(roomName)) {
            return 1;
        }

        chatRooms.get(roomName).add(player);

        this.setPlayerRoom(roomName, player);
        return 0;
    }

    public int leaveRoom(String player) {
        if (!playerRooms.containsKey(player)) {
            return 1;
        }

        String currentRoom = playerRooms.get(player);
        chatRooms.get(currentRoom).remove(player);

        if (chatRooms.get(currentRoom).isEmpty()) {
            chatRooms.remove(currentRoom);
            playerRooms.remove(player);
            return 2;
        } else {
            playerRooms.remove(player);
        }
        return 0;
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
