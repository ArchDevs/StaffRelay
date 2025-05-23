package me.archdev.staffrelay.manager;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import me.archdev.staffrelay.StaffRelay;
import me.archdev.staffrelay.dao.StaffMessageDAO;
import me.archdev.staffrelay.model.StaffMessage;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class DatabaseManager {
    @Getter
    private static HikariDataSource dataSource;

    public static void init(JavaPlugin plugin) {
        try {
            preInit();
            plugin.getLogger().info("Database initialized successfully.");
        } catch (SQLException e) {
            plugin.getLogger().severe("Failed to initialize database: " + e.getMessage());
            e.printStackTrace();
            plugin.getServer().getPluginManager().disablePlugin(plugin);
        }
    }

    private static void preInit() throws SQLException {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setPoolName("StaffRelayPool");

        switch (ConfigManager.dbType) {
            case MYSQL:
                hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
                hikariConfig.setJdbcUrl("jdbc:mysql://" + ConfigManager.mysqlHost + ":" + ConfigManager.mysqlPort + "/" + ConfigManager.mysqlDatabase);
                hikariConfig.setUsername(ConfigManager.mysqlUsername);
                hikariConfig.setPassword(ConfigManager.mysqlPassword);
                break;
            case H2:
                hikariConfig.setDriverClassName("org.h2.Driver");
                hikariConfig.setJdbcUrl("jdbc:h2:" + ConfigManager.h2File + ";MODE=MySQL");
                hikariConfig.setUsername("sa");
                hikariConfig.setPassword("");
                break;
            case SQLITE:
            default:
                hikariConfig.setDriverClassName("org.sqlite.JDBC");
                hikariConfig.setJdbcUrl("jdbc:sqlite:" + ConfigManager.sqliteFile);
                break;
        }

        hikariConfig.setMaximumPoolSize(10);
        hikariConfig.setMinimumIdle(2);
        hikariConfig.setConnectionTimeout(30000);
        hikariConfig.setIdleTimeout(600000);
        hikariConfig.setMaxLifetime(1800000);

        dataSource = new HikariDataSource(hikariConfig);

        Bukkit.getScheduler().runTaskAsynchronously(StaffRelay.getInstance(), () -> {
            try {
                createTable();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private static void createTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS staff_messages (" +
                "id BIGINT AUTO_INCREMENT PRIMARY KEY," +
                "username VARCHAR(48) NOT NULL," + // Discord's username limit is 32
                "message TEXT NOT NULL," +
                "messageSendTime TIMESTAMP NOT NULL" +
                ")";

        try (Connection connection = dataSource.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.executeUpdate();
        }
    }

    public static void saveMessageAsync(String username, String message, Timestamp sendTime) {
        Bukkit.getScheduler().runTaskAsynchronously(StaffRelay.getInstance(), () -> {
            try {
                StaffMessageDAO.saveMessage(new StaffMessage(username, message, sendTime));
            } catch (SQLException e) {
                StaffRelay.getInstance().getLogger().severe("Failed to save message: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }

    public static void closeConnection() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }
}
