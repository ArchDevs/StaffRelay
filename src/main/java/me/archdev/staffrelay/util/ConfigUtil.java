package me.archdev.staffrelay.util;

import me.archdev.staffrelay.StaffRelay;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigUtil {

    private static FileConfiguration config = StaffRelay.getInstance().getConfig();

    public static String get(String key) {
        return config.getString(key, "");
    }

    public static String get(String key, String def) {
        return config.getString(key, def);
    }

    public static String get(ConfigurationSection section, String key) {
        return section.getString(key, "");
    }

    public static String get(ConfigurationSection section, String key, String def) {
        return section.getString(key, def);
    }

    public static int getInt(ConfigurationSection section, String key, int def) {
        return section.getInt(key, def);
    }
}
