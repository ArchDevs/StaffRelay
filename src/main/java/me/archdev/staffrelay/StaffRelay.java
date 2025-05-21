package me.archdev.staffrelay;

import lombok.Getter;
import me.archdev.staffrelay.manager.CommandManager;
import me.archdev.staffrelay.util.ColorUtil;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class StaffRelay extends JavaPlugin {

    @Getter
    private static StaffRelay instance;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        final long startTime = System.currentTimeMillis();

        getCommand("staffrelay").setExecutor(new CommandManager());

        final long endTime = System.currentTimeMillis() - startTime;
        Bukkit.getLogger().info(ColorUtil.formatLegacy("&aPlugin was loaded in: " + endTime));
    }

    @Override
    public void onDisable() {
        instance = null;
    }
}
