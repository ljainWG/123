package com.wg.dabms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.wg.dabms.model.Notification;

public class NotificationDAO extends GenericDAO<Notification> {

    private static final Logger LOGGER = Logger.getLogger(NotificationDAO.class.getName());

    @Override
    protected Notification mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        Notification notification = new Notification();
        notification.setUuid(resultSet.getString("uuid"));
        notification.setGeneratorId(resultSet.getString("generator_id"));
        notification.setReceiverId(resultSet.getString("receiver_id"));
        notification.setTitle(resultSet.getString("title"));
        notification.setDescription(resultSet.getString("description"));
        java.sql.Timestamp generatedAt = resultSet.getTimestamp("generated_at");
        notification.setGeneratedAt(generatedAt != null ? generatedAt.toLocalDateTime() : null);
        java.sql.Timestamp updatedAt = resultSet.getTimestamp("updated_at");
        notification.setUpdatedAt(updatedAt != null ? updatedAt.toLocalDateTime() : null);
        
        LOGGER.log(Level.INFO, "Mapped ResultSet to Notification entity: {0}", notification);
        return notification;
    }

    public Notification getNotificationById(String uuid) {
        LOGGER.log(Level.INFO, "Getting notification by ID: {0}", uuid);
        String query = "SELECT * FROM notification WHERE uuid = ?";
        return executeGetQuery(query, uuid);
    }

    public List<Notification> getAllNotifications() {
        LOGGER.info("Getting all notifications");
        String query = "SELECT * FROM notification";
        return executeGetAllQuery(query);
    }

    public boolean createNotification(Notification notification) {
        LOGGER.log(Level.INFO, "Creating notification: {0}", notification);
        String query = "INSERT INTO notification (uuid, generator_id, receiver_id, title, description, generated_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?)";
        boolean result = executeUpdate(query, notification.getUuid(), notification.getGeneratorId(), notification.getReceiverId(),
                notification.getTitle(), notification.getDescription(), notification.getGeneratedAt(),
                notification.getUpdatedAt());
        LOGGER.log(Level.INFO, "Notification creation successful: {0}", result);
        return result;
    }

    public boolean updateNotification(Notification notification) {
        LOGGER.log(Level.INFO, "Updating notification: {0}", notification);
        String query = "UPDATE notification SET generator_id = ?, receiver_id = ?, title = ?, description = ?, generated_at = ?, updated_at = ? WHERE uuid = ?";
        boolean result = executeUpdate(query, notification.getGeneratorId(), notification.getReceiverId(),
                notification.getTitle(), notification.getDescription(), notification.getGeneratedAt(),
                notification.getUpdatedAt(), notification.getUuid());
        LOGGER.log(Level.INFO, "Notification update successful: {0}", result);
        return result;
    }

    public boolean deleteNotification(String uuid) {
        LOGGER.log(Level.INFO, "Deleting notification with ID: {0}", uuid);
        String query = "DELETE FROM notification WHERE uuid = ?";
        boolean result = executeDelete(query, uuid);
        LOGGER.log(Level.INFO, "Notification deletion successful: {0}", result);
        return result;
    }

    public List<Notification> findNotificationsByReceiverId(String receiverId) {
        LOGGER.log(Level.INFO, "Finding notifications by receiver ID: {0}", receiverId);
        String query = "SELECT * FROM notification WHERE receiver_id = ?";
        return executeGetAllQuery(query, receiverId);
    }

    public List<Notification> findNotificationsByGeneratorId(String generatorId) {
        LOGGER.log(Level.INFO, "Finding notifications by generator ID: {0}", generatorId);
        String query = "SELECT * FROM notification WHERE generator_id = ?";
        return executeGetAllQuery(query, generatorId);
    }
}
