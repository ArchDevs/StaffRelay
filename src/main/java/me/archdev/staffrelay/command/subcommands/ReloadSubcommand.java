package me.archdev.staffrelay.command.subcommands;

import me.archdev.staffrelay.StaffRelay;
import me.archdev.staffrelay.command.SubCommand;
import me.archdev.staffrelay.util.ColorUtil;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.time.Instant;

public class ReloadSubcommand implements SubCommand {
    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public void perform(Player player, String[] args) {
        Instant start = Instant.now();

        StaffRelay.getInstance().reloadConfig();

        Duration timeElapsed = Duration.between(start, Instant.now());
        player.sendMessage(ColorUtil.formatLegacy(String.format("&aConfig was reloaded in: %s ms", timeElapsed.toMillis())));
    }
}
