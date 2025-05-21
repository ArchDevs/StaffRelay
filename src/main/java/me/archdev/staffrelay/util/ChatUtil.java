package me.archdev.staffrelay.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ChatUtil {
    public static void sendMessageToAll(Player player, String[] args) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.sendMessage(String.format("%s: %s", player.getName(), args[0]));
        }
    }
}
