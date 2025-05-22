package me.archdev.staffrelay.manager;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import me.archdev.staffrelay.StaffRelay;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class DatabaseManager {
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
            case "mysql":
                hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
                hikariConfig.setJdbcUrl("jdbc:mysql://" + ConfigManager.mysqlHost + ":" + ConfigManager.mysqlPort + "/" + ConfigManager.mysqlDatabase);
                hikariConfig.setUsername(ConfigManager.mysqlUsername);
                hikariConfig.setPassword(ConfigManager.mysqlPassword);
                break;
            case "h2":
                hikariConfig.setDriverClassName("org.h2.Driver");
                hikariConfig.setJdbcUrl("jdbc:h2:" + ConfigManager.h2File + ";MODE=MySQL");
                hikariConfig.setUsername("sa");
                hikariConfig.setPassword("");
                break;
            case "sqlite":
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

        createTable();
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

    public static void saveMessage(String username, String message, Timestamp messageSendTime) throws SQLException {
        String sql = "INSERT INTO staff_messages (username, message, messageSendTime) VALUES (?, ?, ?)";

        try (Connection connection = dataSource.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, message);
            stmt.setTimestamp(3, messageSendTime);
            stmt.executeUpdate();
        }
    }

    public static void closeConnection() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }
}
