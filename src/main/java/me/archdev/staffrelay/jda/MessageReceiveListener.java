package me.archdev.staffrelay.jda;

import me.archdev.staffrelay.manager.ConfigManager;
import me.archdev.staffrelay.manager.DatabaseManager;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.Timestamp;
import java.time.Instant;

public class MessageReceiveListener extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (!(event.getChannelType() == ChannelType.TEXT) && !event.getChannel().getId().equals(ConfigManager.channelId)) return;

        if (event.getAuthor().isBot() || event.getAuthor().isSystem()) return;

        DatabaseManager.saveMessageAsync("DS_Message_" + event.getAuthor().getName(), event.getMessage().getContentRaw(), Timestamp.from(Instant.now()));

        for (Player p : Bukkit.getOnlinePlayers()) {
            if (!p.hasPermission("staffrelay.access")) return;
            p.sendMessage(String.format("[Discord] | %s: %s", event.getAuthor().getName(), event.getMessage().getContentRaw()));
        }
    }
}
