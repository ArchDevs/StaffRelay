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
    private static ConfigurationSection databaseSection;

    private static List<ConfigurationSection> sections;

    public static boolean loadConfig(FileConfiguration config, JavaPlugin plugin) {
        plugin.getConfig().options().copyDefaults();
        plugin.saveDefaultConfig();

        sections = new ArrayList<>();

        discordSection = config.getConfigurationSection("discord-bot");
        messagesSection = config.getConfigurationSection("messages");
        databaseSection = config.getConfigurationSection("database");

        sections.clear();
        sections.addAll(Arrays.asList(discordSection, messagesSection, databaseSection));

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

        // DataBase Section
        dbType = ConfigUtil.get(databaseSection, "db-type", "sqlite").toLowerCase();
        // H2
        h2File = ConfigUtil.get(databaseSection, "h2.file", "./plugins/StaffRelay/staffrelay");
        // MySQL
        mysqlHost = ConfigUtil.get(databaseSection, "mysql.host", "localhost");
        mysqlPort = ConfigUtil.getInt(databaseSection, "mysql.port", 3306);
        mysqlDatabase = ConfigUtil.get(databaseSection, "mysql.database", "staffrelay");
        mysqlUsername = ConfigUtil.get(databaseSection, "mysql.username", "root");
        mysqlPassword = ConfigUtil.get(databaseSection, "mysql.password", "password");
        // SQLite
        sqliteFile = ConfigUtil.get(databaseSection, "sqlite.file", "./plugins/StaffRelay/staffrelay.db");
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

    // DataBase Section
    public static String dbType;
    // H2
    public static String h2File;
    // MySQL
    public static String mysqlHost;
    public static int mysqlPort;
    public static String mysqlDatabase;
    public static String mysqlUsername;
    public static String mysqlPassword;
    // SQLite
    public static String sqliteFile;
}
