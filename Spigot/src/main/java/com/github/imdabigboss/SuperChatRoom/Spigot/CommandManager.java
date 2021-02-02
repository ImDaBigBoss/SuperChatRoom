package com.github.imdabigboss.SuperChatRoom.Spigot;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import com.github.imdabigboss.SuperChatRoom.connector.ChatRoom;
import com.github.imdabigboss.SuperChatRoom.connector.CommandChatRoom;
import com.github.imdabigboss.SuperChatRoom.connector.CommandShout;
import com.github.imdabigboss.SuperChatRoom.connector.SuperChatRoom;

public class CommandManager implements CommandExecutor, TabExecutor {
	private final SuperChatRoom plugin;
	
	public CommandManager(SuperChatRoom plugin) {
        this.plugin = plugin;
    }
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("shout")) {
        	return new CommandShout(plugin).runCommand(new SpigotCommandSender(sender), label, args);
        }
        if (command.getName().equalsIgnoreCase("chatroom")) {
        	return new CommandChatRoom(plugin).runCommand(new SpigotCommandSender(sender), label, args);
        }
        sender.sendMessage(command.getName());
        return false;
    }
	
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("chatroom")) {
        	if (args.length == 1) {
        		ArrayList<String> cmds = new ArrayList<String>();
        		cmds.add("create");
        		cmds.add("join");
        		cmds.add("leave");
        		cmds.add("invite");
        		return cmds;
        	}
        	if (args.length == 2 && args[0].equalsIgnoreCase("invite")) {
        		ArrayList<String> cmds = new ArrayList<String>();
        		for (com.github.imdabigboss.SuperChatRoom.connector.Player player : plugin.getOnlinePlayers()) {
        			cmds.add(player.getName());
        		}
        		return cmds;
        	}
        	
        	return new ArrayList<>();
        }
        return new ArrayList<>();
    }
}
