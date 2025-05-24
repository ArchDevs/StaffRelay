package me.archdev.staffrelay.manager;

import me.archdev.staffrelay.StaffRelay;
import me.archdev.staffrelay.jda.MessageReceiveListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.internal.utils.JDALogger;
import org.bukkit.plugin.java.JavaPlugin;

public class JDAManager {

    public static void init(JavaPlugin plugin) {
        JDALogger.setFallbackLoggerEnabled(false);

        try {
            JDA bot = createBot();
            if (bot != null) {
                StaffRelay.setJda(bot);
                plugin.getLogger().info("Discord bot started successfully.");
            } else {
                plugin.getLogger().info("No bot token provided. Discord bot will not be started.");
            }
        } catch (Exception e) {
            plugin.getLogger().severe("Failed to create Discord bot: " + e.getMessage());
            e.printStackTrace();
            plugin.getServer().getPluginManager().disablePlugin(plugin);
        }
    }


    private static JDA createBot() {
        if (ConfigManager.botToken == null || ConfigManager.botToken.isEmpty()) {
            return null;
        }

        return JDABuilder.createDefault(ConfigManager.botToken)
                .enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MESSAGE_TYPING, GatewayIntent.MESSAGE_CONTENT)
                .addEventListeners(new MessageReceiveListener())
                .setActivity(Activity.of(Activity.ActivityType.valueOf(ConfigManager.botActivity), ConfigManager.botActivityText))
                .setStatus(OnlineStatus.valueOf(ConfigManager.botStatus))
                .build();
    }

    public static void shutdown(JavaPlugin plugin, JDA jda) {
        try {
            jda.shutdownNow();
            jda.awaitShutdown();
        } catch (Exception e) {
            plugin.getLogger().warning("Error shutting down JDA: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
