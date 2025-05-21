package me.archdev.staffrelay;

import lombok.Getter;
import me.archdev.staffrelay.config.ConfigValues;
import me.archdev.staffrelay.manager.CommandManager;
import me.archdev.staffrelay.util.ColorUtil;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class StaffRelay extends JavaPlugin {

    @Getter
    private static StaffRelay instance;

    @Getter
    private static JDA jda;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        final long startTime = System.currentTimeMillis();

        ConfigValues.loadConfig(getConfig(), instance);
        createJdaBot();

        getCommand("staffrelay").setExecutor(new CommandManager());

        final long endTime = System.currentTimeMillis() - startTime;
        Bukkit.getLogger().info(ColorUtil.formatLegacy("&aPlugin was loaded in: " + endTime));
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    private void createJdaBot() {
        if (ConfigValues.botToken == null) return;

        jda = JDABuilder.createDefault(ConfigValues.botToken)
                .enableIntents(GatewayIntent.GUILD_MESSAGES)
                .addEventListeners()
                .setActivity(Activity.of(Activity.ActivityType.valueOf(ConfigValues.botActivity), ConfigValues.botActivityText))
                .setStatus(OnlineStatus.valueOf(ConfigValues.botStatus))
                .build();
    }
}
