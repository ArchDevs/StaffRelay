package me.archdev.staffrelay.jda;

import me.archdev.staffrelay.StaffRelay;
import me.archdev.staffrelay.manager.ConfigManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.internal.utils.JDALogger;
import org.bukkit.plugin.java.JavaPlugin;

public class JDAInitializer {

    public static void init(JavaPlugin plugin) {
        JDALogger.setFallbackLoggerEnabled(false);

        if (ConfigManager.botToken == null) {
            plugin.getLogger().severe("Bot token is missing in config. Disabling plugin.");
            plugin.getServer().getPluginManager().disablePlugin(plugin);
            return;
        }

        try {
            StaffRelay.setJda(createBot());
        } catch (Exception e) {
            plugin.getLogger().severe("Failed to create Discord bot: " + e.getMessage());
            e.printStackTrace();
            plugin.getServer().getPluginManager().disablePlugin(plugin);
            return;
        }

        plugin.getLogger().info("Discord bot started successfully.");
    }

    private static JDA createBot() {
        return JDABuilder.createDefault(ConfigManager.botToken)
                .enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MESSAGE_TYPING, GatewayIntent.MESSAGE_CONTENT)
                .addEventListeners(new MessageReceiveListener())
                .setActivity(Activity.of(Activity.ActivityType.valueOf(ConfigManager.botActivity), ConfigManager.botActivityText))
                .setStatus(OnlineStatus.valueOf(ConfigManager.botStatus))
                .build();
    }
}
