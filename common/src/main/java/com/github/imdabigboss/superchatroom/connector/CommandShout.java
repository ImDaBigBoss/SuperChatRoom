package com.github.imdabigboss.superchatroom.connector;

public class CommandShout {
    private final SuperChatRoom plugin;
    private ChatRoom chatRoom;

    public CommandShout(SuperChatRoom plugin) {
        this.plugin = plugin;
        this.chatRoom = plugin.getChatRoom();
    }

    public boolean runCommand(CommandSender sender, String label, String[] args) {
        if (!sender.isConsole()) {
            if (chatRoom.isInRoom(sender.getName())) {
                if (args.length == 0) {
                    sender.sendMessage("Correct usage is /shout <message>");
                    return true;
                }

                String message = String.join(" ", args);
                for (ConnectorPlayer player : plugin.getOnlinePlayersOnServer(sender.getPlayer())) {
                    if (chatRoom.showGeneral.containsKey(player.getName())) {
                        if (chatRoom.showGeneral.get(player.getName())) {
                            player.sendMessage(Util.formatChat(sender.getPlayer().getDisplayName(), message, plugin.getChatFormat(), false));
                        }
                    } else {
                        player.sendMessage(Util.formatChat(sender.getPlayer().getDisplayName(), message, plugin.getChatFormat(), false));
                    }
                }
            } else {
                sender.sendMessage("You must be in a room to use this command!");
            }
        } else {
            sender.sendMessage("You need to be a player to use this command!");
            return true;
        }
        return true;
    }
}