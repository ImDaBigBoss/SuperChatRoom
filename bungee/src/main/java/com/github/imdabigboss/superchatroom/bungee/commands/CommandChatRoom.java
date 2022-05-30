package com.github.imdabigboss.superchatroom.bungee.commands;

import com.github.imdabigboss.superchatroom.bungee.BungeeCommandSender;
import com.github.imdabigboss.superchatroom.bungee.SuperChatRoom;
import com.github.imdabigboss.superchatroom.connector.ConnectorPlayer;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.ArrayList;

public class CommandChatRoom extends Command implements TabExecutor {
    private final SuperChatRoom plugin;

    public CommandChatRoom(SuperChatRoom plugin) {
        super("chatroom");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        new com.github.imdabigboss.superchatroom.connector.CommandChatRoom(plugin).runCommand(new BungeeCommandSender(sender), "chatroom", args);
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        ArrayList<String> cmds = new ArrayList<>();
        if (args.length == 1) {
            cmds.add("create");
            cmds.add("join");
            cmds.add("leave");
            cmds.add("invite");
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("invite")) {
                for (ConnectorPlayer player : plugin.getOnlinePlayers()) {
                    cmds.add(player.getName());
                }
            } else if (args[0].equalsIgnoreCase("general")) {
                cmds.add("show");
                cmds.add("hide");
            }
        }
        return cmds;
    }
}