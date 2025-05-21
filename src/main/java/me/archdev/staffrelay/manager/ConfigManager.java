package me.archdev.staffrelay.manager;

import me.archdev.staffrelay.util.ConfigUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ConfigManager {

    private static ConfigurationSection discordSection;
    private static ConfigurationSection messagesSection;

    private static List<ConfigurationSection> sections;

    public static boolean loadConfig(FileConfiguration config, JavaPlugin plugin) {
        plugin.getConfig().options().copyDefaults();
        plugin.saveDefaultConfig();

        sections = new ArrayList<>();

        discordSection = config.getConfigurationSection("discord-bot");
        messagesSection = config.getConfigurationSection("messages");

        sections.clear();
        sections.addAll(Arrays.asList(discordSection, messagesSection));

        // Check for nulls
        if (sections.stream().anyMatch(Objects::isNull)) {
            plugin.getLogger().severe("Config file is corrupted, plugin will disable");
            plugin.getServer().getPluginManager().disablePlugin(plugin);
            return false;
        }

        // Discord Section
        botToken = ConfigUtil.get(discordSection, "bot-token");
        botActivity = ConfigUtil.get(discordSection, "bot-activity", "PLAYING").toUpperCase();
        botActivityText = ConfigUtil.get(discordSection, "bot-activity-text", "Minecraft");
        botStatus = ConfigUtil.get(discordSection, "bot-status", "ONLINE").toUpperCase();
        channelId = ConfigUtil.get(discordSection, "channel-id");

        // Messages Section
        noPerm = ConfigUtil.get(messagesSection, "no-permission", "&cYou do not have permission to do that");
        configReload = ConfigUtil.get(messagesSection, "config-reload", "&aConfig was reloaded in: %s ms");


        return true;
    }

    // Discord Section
    public static String botToken;
    public static String botActivity;
    public static String botActivityText;
    public static String botStatus;
    public static String channelId;

    // Messages Section
    public static String noPerm;
    public static String configReload;

}
