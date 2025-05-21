package me.archdev.staffrelay.util;

import org.bukkit.ChatColor;

public class ColorUtil {
    public static String formatLegacy(String text) {
        if (text == null) return "";
        return ChatColor.translateAlternateColorCodes('&', text);
    }
}