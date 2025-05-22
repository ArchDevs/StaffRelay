package me.archdev.staffrelay.util;

import me.archdev.staffrelay.StaffRelay;
import me.archdev.staffrelay.manager.DatabaseManager;
import net.dv8tion.jda.api.entities.User;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.sql.Timestamp;

public class DBUtil {
    public static void saveMessageToDB(String username, String message, Timestamp sendTime) {
        try {
            DatabaseManager.saveMessage(username, message, sendTime);
        } catch (SQLException e) {
            StaffRelay.getInstance().getLogger().severe("Failed to save message: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
