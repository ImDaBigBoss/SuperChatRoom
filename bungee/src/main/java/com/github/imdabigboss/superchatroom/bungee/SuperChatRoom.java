package com.github.imdabigboss.superchatroom.bungee;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.github.imdabigboss.superchatroom.bungee.commands.*;

import com.github.imdabigboss.superchatroom.connector.ChatRoom;
import com.github.imdabigboss.superchatroom.connector.Player;
import com.github.imdabigboss.superchatroom.connector.Util;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class SuperChatRoom extends Plugin implements com.github.imdabigboss.superchatroom.connector.SuperChatRoom {
    private static Plugin plugin;
    private static ChatRoom chatRoom;
    private static String chatFormat = "<%player%> %message%";
    
    @Override
    public void onEnable() {
		Util.platform= "bungee";
        plugin = this;
        chatRoom = new ChatRoom();

		if (!getDataFolder().exists())
			getDataFolder().mkdir();

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
			if (configuration.contains("chat_style"))
				chatFormat = configuration.getString("chat_style");
		} catch (IOException e) {}

        ProxyServer.getInstance().getPluginManager().registerCommand(this, new CommandChatRoom(this));
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new CommandShout(this));

		getProxy().getPluginManager().registerListener(this, new EventListener(this)); //Enable the listener
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
			Player out = this.getPlayer(player);
			if (out != null)
				players.add(out);
		}
		return players;
    }

	@Override
	public void broadcastMessage(String message) {
		this.getProxy().broadcast(TextComponent.fromLegacyText(message));
	}

	@Override
	public com.github.imdabigboss.superchatroom.connector.Player getPlayer(String player) {
		return new BungeePlayer(this.getProxy().getPlayer(player));
	}
	
	@Override
	public Collection<com.github.imdabigboss.superchatroom.connector.Player> getOnlinePlayers() {
		Collection<com.github.imdabigboss.superchatroom.connector.Player> out = new ArrayList<com.github.imdabigboss.superchatroom.connector.Player>();
		for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
			out.add(new BungeePlayer(p));
		}
		return out;
	}
	
	@Override
	public Collection<com.github.imdabigboss.superchatroom.connector.Player> getOnlinePlayersOnServer(com.github.imdabigboss.superchatroom.connector.Player player) {
		Collection<com.github.imdabigboss.superchatroom.connector.Player> out = new ArrayList<com.github.imdabigboss.superchatroom.connector.Player>();
		ProxiedPlayer player1 = ((BungeePlayer) player).getPlayer();
		for (ProxiedPlayer p : player1.getServer().getInfo().getPlayers()) {
			out.add(new BungeePlayer(p));
		}
		return out;
	}

	@Override
	public void messageToList(List<String> players, String message) {
		for (String player : players) {
			this.getProxy().getPlayer(player).sendMessage(TextComponent.fromLegacyText(message));
		}
	}
}