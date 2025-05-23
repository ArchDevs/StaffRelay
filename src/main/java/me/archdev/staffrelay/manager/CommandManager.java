package me.archdev.staffrelay.manager;

import lombok.Getter;
import me.archdev.staffrelay.command.SubCommand;
import me.archdev.staffrelay.command.subcommands.ReloadSubcommand;
import me.archdev.staffrelay.dao.StaffMessageDAO;
import me.archdev.staffrelay.util.ChatUtil;
import me.archdev.staffrelay.util.ColorUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.Timestamp;
import java.time.Instant;
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
            player.sendMessage(ColorUtil.formatLegacy(ConfigManager.noPerm));
            return true;
        }

        for (int i = 0; i < getSubCommands().size(); i++) {
            if (args[0].equalsIgnoreCase(getSubCommands().get(i).getName()) && player.hasPermission("staffrelay.admin")) {
                getSubCommands().get(i).perform(player, args);
                return true;
            }
        }

        String message = String.join(" ", args);

        StaffMessageDAO.saveMessageToDB(player.getName(), message, Timestamp.from(Instant.now()));
        ChatUtil.sendMessageToAll(player, message);

        return true;
    }
}