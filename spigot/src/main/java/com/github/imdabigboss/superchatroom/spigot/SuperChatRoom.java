package com.github.imdabigboss.superchatroom.spigot;

import com.github.imdabigboss.superchatroom.connector.ChatRoom;
import com.github.imdabigboss.superchatroom.connector.ConnectorPlayer;
import com.github.imdabigboss.superchatroom.connector.ServerPlatform;
import com.github.imdabigboss.superchatroom.connector.Util;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SuperChatRoom extends JavaPlugin implements com.github.imdabigboss.superchatroom.connector.SuperChatRoom {
    private static ChatRoom chatRoom;
    private static String chatFormat;

    @Override
    public void onEnable() {
        Util.platform = ServerPlatform.SPIGOT;
        chatRoom = new ChatRoom();

        this.saveDefaultConfig();
        if (this.getConfig().contains("chat_style")) {
            chatFormat = this.getConfig().getString("chat_style");
        } else {
            chatFormat = "<%player%> %message%";
        }

        this.getCommand("chatroom").setExecutor(new CommandManager(this));
        this.getCommand("shout").setExecutor(new CommandManager(this));

        this.getServer().getPluginManager().registerEvents(new EventListener(this), this);
    }

    public ChatRoom getChatRoom() {
        return chatRoom;
    }

    @Override
    public String getChatFormat() {
        return chatFormat;
    }

    @Override
    public ConnectorPlayer getPlayer(String player) {
        return new SpigotPlayer(getServer().getPlayer(player));
    }

    @Override
    public Collection<ConnectorPlayer> getOnlinePlayers() {
        Collection<ConnectorPlayer> out = new ArrayList<>();

        for (Player player : this.getServer().getOnlinePlayers()) {
            out.add(new SpigotPlayer(player));
        }

        return out;
    }

    @Override
    public Collection<ConnectorPlayer> getOnlinePlayersOnServer(ConnectorPlayer player) {
        return this.getOnlinePlayers();
    }

    @Override
    public void messageToList(List<String> players, String message) {
        for (String playerName : players) {
            Player player = this.getServer().getPlayer(playerName);

            if (player != null) {
                player.sendMessage(message);
            }
        }
    }
}