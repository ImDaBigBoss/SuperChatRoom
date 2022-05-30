package com.github.imdabigboss.superchatroom.nukkit;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;
import com.github.imdabigboss.superchatroom.connector.ConnectorPlayer;

public class NukkitCommandSender implements com.github.imdabigboss.superchatroom.connector.CommandSender {
    private final CommandSender handle;

    public NukkitCommandSender(CommandSender handle) {
        this.handle = handle;
    }

    @Override
    public ConnectorPlayer getPlayer() {
        if (this.isConsole()) {
            return null;
        }

        return new NukkitPlayer((Player) handle);
    }

    @Override
    public String getName() {
        return handle.getName();
    }

    @Override
    public void sendMessage(String message) {
        handle.sendMessage(message);
    }

    @Override
    public boolean isConsole() {
        return handle instanceof ConsoleCommandSender;
    }

}
