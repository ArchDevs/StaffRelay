package me.archdev.staffrelay.listener;

import me.archdev.staffrelay.StaffRelay;
import me.archdev.staffrelay.manager.ConfigManager;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player player = e.getPlayer();

        if (!player.hasPermission("staffrelay.access")) return;

        MessageChannel channel = StaffRelay.getJda().getTextChannelById(ConfigManager.channelId);

        if (channel == null) {
            StaffRelay.getInstance().getLogger().warning("Could not find channel by that ID");
            return;
        }

        channel.sendMessage(player.getName() + ": " + e.getMessage()).queue();
    }
}
