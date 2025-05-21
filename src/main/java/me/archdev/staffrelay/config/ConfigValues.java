package me.archdev.staffrelay.config;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigValues {

    private static ConfigurationSection discordSection;

    public static boolean loadConfig(FileConfiguration config, JavaPlugin plugin) {
        if (discordSection == null) {
            plugin.getLogger().severe("discord-bot section was not found, plugin will disable");
            plugin.getServer().getPluginManager().disablePlugin(plugin);
            return false;
        }

        discordSection = config.getConfigurationSection("discord-bot");

        botToken = get("bot-token");
        botActivity = get("bot-activity", "playing");
        botActivityText = get("bot-activity-text", "Minecraft");
        botStatus = get("bot-status", "ONLINE").toUpperCase();

        return true;
    }

    public static String botToken;
    public static String botActivity;
    public static String botActivityText;
    public static String botStatus;

    private static String get(String key) {
        return discordSection.getString(key, "");
    }

    private static String get(String key, String def) {
        return discordSection.getString(key, def);
    }

}
