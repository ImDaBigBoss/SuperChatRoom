package com.github.imdabigboss.superchatroom;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class SuperChatRoom extends JavaPlugin {
    private static Plugin plugin;
    private static ChatRoom chatRoom;
    
    public static String serverName = "My Server!";
    
    // Fired when plugin is first enabled
    @Override
    public void onEnable() {
    	if (this.getConfig().contains("serverName"))
    		serverName = this.getConfig().getString("serverName");
    	
        plugin = this;
        chatRoom = new ChatRoom();
        
        getServer().getPluginManager().registerEvents(new EventListener(), this); //Enable the listener
        
        //Enable the commands
        this.getCommand("chatroom").setExecutor(new CommandChatRoom());
        this.getCommand("shout").setExecutor(new CommandShout());
    }
    
    // Fired when plugin is disabled
    @Override
    public void onDisable() {
        
    }
    
    public static Plugin getPlugin() {
        return plugin;
    }
    
    public static ChatRoom getChatRoom() {
        return chatRoom;
    }
}