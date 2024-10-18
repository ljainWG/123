package com.wg.dabms.service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.wg.dabms.dao.NotificationDAO;
import com.wg.dabms.model.Notification;

public class NotificationService {

    private static final Logger LOGGER = Logger.getLogger(NotificationService.class.getName());
    private NotificationDAO notificationDAO = new NotificationDAO();

    public NotificationService() {
        LOGGER.info("NotificationService instantiated.");
    }

    public Notification getNotificationById(String uuid) {
        LOGGER.log(Level.INFO, "Fetching notification by ID: {0}", uuid);
        return notificationDAO.getNotificationById(uuid);
    }

    public List<Notification> getAllNotifications() {
        LOGGER.info("Fetching all notifications.");
        return notificationDAO.getAllNotifications();
    }

    public boolean createNotification(Notification notification) {
        LOGGER.log(Level.INFO, "Creating notification: {0}", notification);
        boolean result = notificationDAO.createNotification(notification);
        LOGGER.log(Level.INFO, "Notification creation successful: {0}", result);
        return result;
    }

    public boolean updateNotification(Notification notification) {
        LOGGER.log(Level.INFO, "Updating notification: {0}", notification);
        boolean result = notificationDAO.updateNotification(notification);
        LOGGER.log(Level.INFO, "Notification update successful: {0}", result);
        return result;
    }

    public boolean deleteNotification(String uuid) {
        LOGGER.log(Level.INFO, "Deleting notification with ID: {0}", uuid);
        boolean result = notificationDAO.deleteNotification(uuid);
        LOGGER.log(Level.INFO, "Notification deletion successful: {0}", result);
        return result;
    }

    public List<Notification> getNotificationsByReceiverId(String receiverId) {
        LOGGER.log(Level.INFO, "Fetching notifications by receiver ID: {0}", receiverId);
        return notificationDAO.findNotificationsByReceiverId(receiverId);
    }

    public List<Notification> getNotificationsByGeneratorId(String generatorId) {
        LOGGER.log(Level.INFO, "Fetching notifications by generator ID: {0}", generatorId);
        return notificationDAO.findNotificationsByGeneratorId(generatorId);
    }
}
