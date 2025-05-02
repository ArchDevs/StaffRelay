package me.archdev.staffrelay.manager;

import lombok.Getter;
import me.archdev.staffrelay.command.SubCommand;
import me.archdev.staffrelay.command.subcommands.ReloadSubcommand;
import me.archdev.staffrelay.util.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CommandManager implements CommandExecutor {

    private final List<SubCommand> subCommands = new ArrayList<>();

    public CommandManager() {
        subCommands.add(new ReloadSubcommand());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;

        if (!player.hasPermission("staffrelay.access")) {
            player.sendMessage(ChatUtil.formatLegacy("&cYou do not have permission to do that"));
            return true;
        }

        for (int i = 0; i < getSubCommands().size(); i++) {
            if (args[0].equalsIgnoreCase(getSubCommands().get(i).getName()) && player.hasPermission("staffrelay.admin")) {
                getSubCommands().get(i).perform(player, args);
                return true;
            }
        }

        for (Player p : Bukkit.getOnlinePlayers()) {
            p.sendMessage(String.format("%s: %s", player.getName(), args[0]));
        }

        return true;
    }
}