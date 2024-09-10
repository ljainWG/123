package com.wg.dabms.a.service;

import java.util.List;

import com.wg.dabms.a.dao.NotificationDAO;
import com.wg.dabms.model.Notification;

public class NotificationService {

    private final NotificationDAO notificationDAO = new NotificationDAO();
    
    public NotificationService() {
    }

    public Notification getNotificationById(String uuid) {
        return notificationDAO.getNotificationById(uuid);
    }

    public List<Notification> getAllNotifications() {
        return notificationDAO.getAllNotifications();
    }

    public boolean createNotification(Notification notification) {
        return notificationDAO.createNotification(notification);
    }

    public boolean updateNotification(Notification notification) {
        return notificationDAO.updateNotification(notification);
    }

    public boolean deleteNotification(String uuid) {
        return notificationDAO.deleteNotification(uuid);
    }
    public List<Notification> getNotificationsByReceiverId(String receiverId) {
        return notificationDAO.findNotificationsByReceiverId(receiverId);
    }

    public List<Notification> getNotificationsByGeneratorId(String generatorId) {
        return notificationDAO.findNotificationsByGeneratorId(generatorId);
    }

}
