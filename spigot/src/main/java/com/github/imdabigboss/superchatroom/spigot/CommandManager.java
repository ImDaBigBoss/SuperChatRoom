package com.github.imdabigboss.superchatroom.spigot;

import com.github.imdabigboss.superchatroom.connector.CommandChatRoom;
import com.github.imdabigboss.superchatroom.connector.CommandShout;
import com.github.imdabigboss.superchatroom.connector.ConnectorPlayer;
import com.github.imdabigboss.superchatroom.connector.SuperChatRoom;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.ArrayList;
import java.util.List;

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
        ArrayList<String> cmds = new ArrayList<>();
        if (command.getName().equalsIgnoreCase("chatroom")) {
            if (args.length == 1) {
                cmds.add("create");
                cmds.add("join");
                cmds.add("leave");
                cmds.add("invite");
                cmds.add("general");
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
        }
        return cmds;
    }
}
