package com.github.imdabigboss.superchatroom.nukkit;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.plugin.PluginBase;
import com.github.imdabigboss.superchatroom.connector.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SuperChatRoom extends PluginBase implements com.github.imdabigboss.superchatroom.connector.SuperChatRoom {
    private static ChatRoom chatRoom;
    private static String chatFormat;

    @Override
    public void onEnable() {
        Util.platform = ServerPlatform.NUKKIT;
        chatRoom = new ChatRoom();

        this.saveDefaultConfig();
        if (this.getConfig().exists("chat_style")) {
            chatFormat = this.getConfig().getString("chat_style");
        } else {
            chatFormat = "<%player%> %message%";
        }

        this.getServer().getPluginManager().registerEvents(new EventListener(this), this);
    }

    @Override
    public ChatRoom getChatRoom() {
        return chatRoom;
    }

    public String getChatFormat() {
        return chatFormat;
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
    public ConnectorPlayer getPlayer(String player) {
        return new NukkitPlayer(this.getServer().getPlayer(player));
    }

    @Override
    public Collection<ConnectorPlayer> getOnlinePlayers() {
        Collection<ConnectorPlayer> out = new ArrayList<>();

        for (Player player : this.getServer().getOnlinePlayers().values()) {
            out.add(new NukkitPlayer(player));
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