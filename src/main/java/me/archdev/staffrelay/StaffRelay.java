package me.archdev.staffrelay;

import lombok.Getter;
import lombok.Setter;
import me.archdev.staffrelay.jda.JDAInitializer;
import me.archdev.staffrelay.jda.MessageReceiveListener;
import me.archdev.staffrelay.listener.PlayerChatListener;
import me.archdev.staffrelay.manager.ConfigManager;
import me.archdev.staffrelay.manager.CommandManager;
import me.archdev.staffrelay.util.ColorUtil;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.internal.utils.JDALogger;
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

        JDAInitializer.init(instance);

        getCommand("staffrelay").setExecutor(new CommandManager());
        getServer().getPluginManager().registerEvents(new PlayerChatListener(), instance);

        Duration timeElapsed = Duration.between(start, Instant.now());
        Bukkit.getLogger().info("Plugin enabled in " + timeElapsed.toMillis() + " ms");
    }

    @Override
    public void onDisable() {
        try {
            jda.shutdownNow(); // Use shutdownNow for immediate shutdown
            jda.awaitShutdown(); // Waits for complete shutdown
        } catch (Exception e) {
            getLogger().warning("Error shutting down JDA: " + e.getMessage());
            e.printStackTrace();
        }
        instance = null;
    }
}
