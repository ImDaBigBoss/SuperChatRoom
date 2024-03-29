package com.github.imdabigboss.superchatroom.spigot;

import com.github.imdabigboss.superchatroom.connector.ConnectorPlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class SpigotCommandSender implements com.github.imdabigboss.superchatroom.connector.CommandSender {
    private final CommandSender handle;

    public SpigotCommandSender(CommandSender handle) {
        this.handle = handle;
    }

    @Override
    public ConnectorPlayer getPlayer() {
        if (this.isConsole()) {
            return null;
        }

        return new SpigotPlayer((Player) handle);
    }

    @Override
    public void sendMessage(String message) {
        handle.sendMessage(message);
    }

    @Override
    public boolean isConsole() {
        return handle instanceof ConsoleCommandSender;
    }

    @Override
    public String getName() {
        return handle.getName();
    }
}
