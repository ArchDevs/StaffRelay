package me.archdev.staffrelay.manager;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigManager {

    private static ConfigurationSection discordSection;

    public static boolean loadConfig(FileConfiguration config, JavaPlugin plugin) {
        plugin.getConfig().options().copyDefaults();
        plugin.saveDefaultConfig();

        discordSection = config.getConfigurationSection("discord-bot");

        if (discordSection == null) {
            plugin.getLogger().severe("discord-bot section was not found, plugin will disable");
            plugin.getServer().getPluginManager().disablePlugin(plugin);
            return false;
        }

        botToken = get("bot-token");
        botActivity = get("bot-activity", "PLAYING").toUpperCase();
        botActivityText = get("bot-activity-text", "Minecraft");
        botStatus = get("bot-status", "ONLINE").toUpperCase();
        channelId = get("channel-id");

        return true;
    }

    // Discord Section
    public static String botToken;
    public static String botActivity;
    public static String botActivityText;
    public static String botStatus;
    public static String channelId;

    private static String get(String key) {
        return discordSection.getString(key, "");
    }

    private static String get(String key, String def) {
        return discordSection.getString(key, def);
    }

}
