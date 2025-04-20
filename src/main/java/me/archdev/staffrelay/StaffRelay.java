package me.archdev.staffrelay;

import lombok.Getter;
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
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        instance = null;
    }
}
