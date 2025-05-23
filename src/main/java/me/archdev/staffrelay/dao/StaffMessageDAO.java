package me.archdev.staffrelay.dao;

import me.archdev.staffrelay.manager.DatabaseManager;
import me.archdev.staffrelay.model.StaffMessage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class StaffMessageDAO {
    public static void saveMessage(StaffMessage message) throws SQLException {
        String sql = "INSERT INTO staff_messages (username, message, messageSendTime) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseManager.getDataSource().getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, message.getUsername());
            stmt.setString(2, message.getMessage());
            stmt.setTimestamp(3, message.getMessageSendTime());
            stmt.executeUpdate();
        }
    }

    public static void saveMessagesBatch(List<StaffMessage> messages) throws SQLException {
        String sql = "INSERT INTO staff_messages (username, message, messageSendTime) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseManager.getDataSource().getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            connection.setAutoCommit(false);

            for (StaffMessage msg : messages) {
                stmt.setString(1, msg.getUsername());
                stmt.setString(2, msg.getMessage());
                stmt.setTimestamp(3, msg.getMessageSendTime());
                stmt.addBatch();
            }

            stmt.executeBatch();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
