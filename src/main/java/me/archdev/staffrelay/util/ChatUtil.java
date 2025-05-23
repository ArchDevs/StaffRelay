package me.archdev.staffrelay.util;

import org.bukkit.Bukkit;

public class ChatUtil {
    public static void runConsoleCommand(String command) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
    }
}
