package me.archdev.staffrelay.command.subcommands;

import me.archdev.staffrelay.StaffRelay;
import me.archdev.staffrelay.command.SubCommand;
import me.archdev.staffrelay.util.ColorUtil;
import org.bukkit.entity.Player;

public class ReloadSubcommand implements SubCommand {
    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public void perform(Player player, String[] args) {
        long startTime = System.currentTimeMillis();

        StaffRelay.getInstance().reloadConfig();
        long endTime = System.currentTimeMillis() - startTime;
        player.sendMessage(ColorUtil.formatLegacy("&aConfig was reloaded in: " + endTime));
    }
}
