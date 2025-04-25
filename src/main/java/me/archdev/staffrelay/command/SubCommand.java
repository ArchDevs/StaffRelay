package me.archdev.staffrelay.command;

import org.bukkit.entity.Player;

public interface SubCommand {
    public abstract String getName();
    public abstract void perform(Player player, String[] args);
}
