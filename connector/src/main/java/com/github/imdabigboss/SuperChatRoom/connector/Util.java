package com.github.imdabigboss.superchatroom.connector;

import java.util.List;

public class Util {
    public static String formatChat(String name, String message, String chatFormat) {
        return formatChat(name, message, chatFormat, true);
    }

    public static String formatChat(String name, String message, String chatFormat, boolean Color) {
        String text = chatFormat;
        text = text.replace("%player%", name);
        if (Color)
            text = text.replace("%message%", ChatColor.GREEN + message + ChatColor.RESET);
        else
            text = text.replace("%message%", message);
        return text;
    }
}
