package com.github.imdabigboss.superchatroom.bungee;

import com.github.imdabigboss.superchatroom.bungee.commands.CommandChatRoom;
import com.github.imdabigboss.superchatroom.bungee.commands.CommandShout;
import com.github.imdabigboss.superchatroom.connector.ChatRoom;
import com.github.imdabigboss.superchatroom.connector.ConnectorPlayer;
import com.github.imdabigboss.superchatroom.connector.ServerPlatform;
import com.github.imdabigboss.superchatroom.connector.Util;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SuperChatRoom extends Plugin implements com.github.imdabigboss.superchatroom.connector.SuperChatRoom {
    private static ChatRoom chatRoom;
    private static String chatFormat;

    @Override
    public void onEnable() {
        Util.platform = ServerPlatform.BUNGEECORD;
        chatRoom = new ChatRoom();

        if (!getDataFolder().exists()) {
            this.getDataFolder().mkdir();
        }

        File file = new File(getDataFolder(), "config.yml");

        if (!file.exists()) {
            try (InputStream in = getResourceAsStream("config.yml")) {
                Files.copy(in, file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            Configuration configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
            if (configuration.contains("chat_style")) {
                chatFormat = configuration.getString("chat_style");
            } else {
                chatFormat = "<%player%> %message%";
            }
        } catch (IOException ignored) {
        }

        ProxyServer.getInstance().getPluginManager().registerCommand(this, new CommandChatRoom(this));
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new CommandShout(this));

        this.getProxy().getPluginManager().registerListener(this, new EventListener(this));
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
        return new BungeePlayer(this.getProxy().getPlayer(player));
    }

    @Override
    public Collection<ConnectorPlayer> getOnlinePlayers() {
        Collection<ConnectorPlayer> out = new ArrayList<>();

        for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
            out.add(new BungeePlayer(player));
        }

        return out;
    }

    @Override
    public Collection<ConnectorPlayer> getOnlinePlayersOnServer(ConnectorPlayer player) {
        Collection<ConnectorPlayer> out = new ArrayList<>();
        ProxiedPlayer bungeePlayer = ((BungeePlayer) player).getPlayer();

        for (ProxiedPlayer p : bungeePlayer.getServer().getInfo().getPlayers()) {
            out.add(new BungeePlayer(p));
        }

        return out;
    }

    @Override
    public void messageToList(List<String> players, String message) {
        for (String playerName : players) {
            ProxiedPlayer player = this.getProxy().getPlayer(playerName);

            if (player != null) {
                player.sendMessage(TextComponent.fromLegacyText(message));
            }
        }
    }
}