package com.github.imdabigboss.superchatroom.bungee.commands;

import com.github.imdabigboss.superchatroom.bungee.BungeeCommandSender;
import com.github.imdabigboss.superchatroom.bungee.SuperChatRoom;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class CommandShout extends Command {
    private final SuperChatRoom plugin;

    public CommandShout(SuperChatRoom plugin) {
        super("shout");
        this.plugin = plugin;
    }

    public void execute(CommandSender sender, String[] args) {
        new com.github.imdabigboss.superchatroom.connector.CommandShout(plugin).runCommand(new BungeeCommandSender(sender), "chatroom", args);
    }
}