package com.wg.dabms.a.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.wg.dabms.model.Notification;

public class NotificationDAO extends GenericDAO<Notification> {

    @Override
    protected Notification mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        Notification notification = new Notification();
        notification.setUuid(resultSet.getString("uuid"));
        notification.setGeneratorId(resultSet.getString("generator_id"));
        notification.setReceiverId(resultSet.getString("receiver_id"));
        notification.setTitle(resultSet.getString("title"));
        notification.setDescription(resultSet.getString("description"));
        notification.setGeneratedAt(resultSet.getTimestamp("generated_at").toLocalDateTime());
        notification.setUpdatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime());
        return notification;
    }
    public Notification getNotificationById(String uuid) {
        String query = "SELECT * FROM notification WHERE uuid = ?";
        return executeGetQuery(query, uuid);
    }

    public List<Notification> getAllNotifications() {
        String query = "SELECT * FROM notification";
        return executeGetAllQuery(query);
    }

    public boolean createNotification(Notification notification) {
        String query = "INSERT INTO notification (uuid, generator_id, receiver_id, title, description, generated_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?)";
        return executeUpdate(query, notification.getUuid(), notification.getGeneratorId(), notification.getReceiverId(), notification.getTitle(), notification.getDescription(), notification.getGeneratedAt(), notification.getUpdatedAt());
    }

    public boolean updateNotification(Notification notification) {
        String query = "UPDATE notification SET generator_id = ?, receiver_id = ?, title = ?, description = ?, generated_at = ?, updated_at = ? WHERE uuid = ?";
        return executeUpdate(query, notification.getGeneratorId(), notification.getReceiverId(), notification.getTitle(), notification.getDescription(), notification.getGeneratedAt(), notification.getUpdatedAt(), notification.getUuid());
    }

    public boolean deleteNotification(String uuid) {
        String query = "DELETE FROM notification WHERE uuid = ?";
        return executeDelete(query, uuid);
    }
}
