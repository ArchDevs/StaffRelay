package me.archdev.staffrelay;

import lombok.Getter;
import lombok.Setter;
import me.archdev.staffrelay.manager.JDAManager;
import me.archdev.staffrelay.listener.PlayerChatListener;
import me.archdev.staffrelay.manager.CommandManager;
import me.archdev.staffrelay.manager.ConfigManager;
import me.archdev.staffrelay.manager.DatabaseManager;
import net.dv8tion.jda.api.JDA;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.time.Duration;
import java.time.Instant;

public final class StaffRelay extends JavaPlugin {

    @Getter
    private static StaffRelay instance;

    @Getter
    @Setter
    private static JDA jda;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        Instant start = Instant.now();

        ConfigManager.loadConfig(getConfig(), instance);

        JDAManager.init(instance);
        DatabaseManager.init(instance);

        getCommand("staffrelay").setExecutor(new CommandManager());
        getServer().getPluginManager().registerEvents(new PlayerChatListener(), instance);

        Duration timeElapsed = Duration.between(start, Instant.now());
        Bukkit.getLogger().info("Plugin enabled in " + timeElapsed.toMillis() + " ms");
    }

    @Override
    public void onDisable() {
        JDAManager.shutdown(instance, jda);
        DatabaseManager.closeConnection();

        instance = null;
    }
}
