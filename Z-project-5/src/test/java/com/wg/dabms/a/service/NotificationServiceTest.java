package com.wg.dabms.a.service;

import com.wg.dabms.a.dao.NotificationDAO;
import com.wg.dabms.model.Notification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class NotificationServiceTest {

    @Mock
    private NotificationDAO notificationDAO;

    @InjectMocks
    private NotificationService notificationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetNotificationById() {
        String uuid = "test-uuid";
        Notification notification = new Notification();
        when(notificationDAO.getNotificationById(uuid)).thenReturn(notification);

        Notification result = notificationService.getNotificationById(uuid);
        assertEquals(notification, result, "Should return the notification by UUID");
        verify(notificationDAO).getNotificationById(uuid);
    }

    @Test
    public void testGetAllNotifications() {
        List<Notification> notifications = new ArrayList<>();
        when(notificationDAO.getAllNotifications()).thenReturn(notifications);

        List<Notification> result = notificationService.getAllNotifications();
        assertEquals(notifications, result, "Should return the list of all notifications");
        verify(notificationDAO).getAllNotifications();
    }

    @Test
    public void testCreateNotification() {
        Notification notification = new Notification();
        when(notificationDAO.createNotification(notification)).thenReturn(true);

        boolean result = notificationService.createNotification(notification);
        assertTrue(result, "Should return true when notification is created");
        verify(notificationDAO).createNotification(notification);
    }

    @Test
    public void testUpdateNotification() {
        Notification notification = new Notification();
        when(notificationDAO.updateNotification(notification)).thenReturn(true);

        boolean result = notificationService.updateNotification(notification);
        assertTrue(result, "Should return true when notification is updated");
        verify(notificationDAO).updateNotification(notification);
    }

    @Test
    public void testDeleteNotification() {
        String uuid = "test-uuid";
        when(notificationDAO.deleteNotification(uuid)).thenReturn(true);

        boolean result = notificationService.deleteNotification(uuid);
        assertTrue(result, "Should return true when notification is deleted");
        verify(notificationDAO).deleteNotification(uuid);
    }

    @Test
    public void testGetNotificationsByReceiverId() {
        String receiverId = "receiver-1";
        List<Notification> notifications = new ArrayList<>();
        when(notificationDAO.findNotificationsByReceiverId(receiverId)).thenReturn(notifications);

        List<Notification> result = notificationService.getNotificationsByReceiverId(receiverId);
        assertEquals(notifications, result, "Should return notifications by receiver ID");
        verify(notificationDAO).findNotificationsByReceiverId(receiverId);
    }

    @Test
    public void testGetNotificationsByGeneratorId() {
        String generatorId = "generator-1";
        List<Notification> notifications = new ArrayList<>();
        when(notificationDAO.findNotificationsByGeneratorId(generatorId)).thenReturn(notifications);

        List<Notification> result = notificationService.getNotificationsByGeneratorId(generatorId);
        assertEquals(notifications, result, "Should return notifications by generator ID");
        verify(notificationDAO).findNotificationsByGeneratorId(generatorId);
    }
}
