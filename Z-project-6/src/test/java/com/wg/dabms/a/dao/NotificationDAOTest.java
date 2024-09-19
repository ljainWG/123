package com.wg.dabms.a.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.wg.dabms.dao.NotificationDAO;
import com.wg.dabms.model.Notification;

public class NotificationDAOTest {

    @InjectMocks
    private NotificationDAO notificationDAO;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    @BeforeEach
    public void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
    }

    @Test
    public void testCreateNotification() throws SQLException {
        Notification notification = new Notification();
        notification.setUuid("1");
        notification.setGeneratorId("generator 1");
        notification.setReceiverId("reciver 1");
        notification.setTitle("Test Title");
        notification.setDescription("Test Description");
        notification.setGeneratedAt(LocalDateTime.now());
        notification.setUpdatedAt(LocalDateTime.now());

        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean result = notificationDAO.createNotification(notification);

        assertTrue(result);
        verify(preparedStatement).executeUpdate();
    }

    @Test
    public void testGetNotificationById() throws SQLException {
        Notification notification = new Notification();
        notification.setUuid("1");
        notification.setGeneratorId("generator 1");
        notification.setReceiverId("reciver 1");
        notification.setTitle("Test Title");
        notification.setDescription("Test Description");
        notification.setGeneratedAt(LocalDateTime.now());
        notification.setUpdatedAt(LocalDateTime.now());

        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getString("uuid")).thenReturn("1");
        when(resultSet.getString("generator_id")).thenReturn("generator 1");
        when(resultSet.getString("receiver_id")).thenReturn("reciver 1");
        when(resultSet.getString("title")).thenReturn("Test Title");
        when(resultSet.getString("description")).thenReturn("Test Description");
        when(resultSet.getTimestamp("generated_at")).thenReturn(java.sql.Timestamp.valueOf(notification.getGeneratedAt()));
        when(resultSet.getTimestamp("updated_at")).thenReturn(java.sql.Timestamp.valueOf(notification.getUpdatedAt()));

        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        Notification result = notificationDAO.getNotificationById("1");

        assertNotNull(result);
        assertEquals("1", result.getUuid());
        assertEquals("generator 1", result.getGeneratorId());
        assertEquals("reciver 1", result.getReceiverId());
        assertEquals("Test Title", result.getTitle());
        assertEquals("Test Description", result.getDescription());
    }

    @Test
    public void testUpdateNotification() throws SQLException {
        Notification notification = new Notification();
        notification.setUuid("1");
        notification.setGeneratorId("generator 1");
        notification.setReceiverId("reciver 1");
        notification.setTitle("Updated Title");
        notification.setDescription("Updated Description");
        notification.setGeneratedAt(LocalDateTime.now());
        notification.setUpdatedAt(LocalDateTime.now());

        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean result = notificationDAO.updateNotification(notification);

        assertTrue(result);
        verify(preparedStatement).executeUpdate();
    }

    @Test
    public void testDeleteNotification() throws SQLException {
        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean result = notificationDAO.deleteNotification("1");

        assertTrue(result);
        verify(preparedStatement).executeUpdate();
    }

    @Test
    public void testGetAllNotifications() throws SQLException {
        Notification notification1 = new Notification();
        notification1.setUuid("1");
        notification1.setGeneratorId("generator 1");
        notification1.setReceiverId("reciver 1");
        notification1.setTitle("Title 1");
        notification1.setDescription("Description 1");

        Notification notification2 = new Notification();
        notification2.setUuid("2");
        notification2.setGeneratorId("gen2");
        notification2.setReceiverId("rec2");
        notification2.setTitle("Title 2");
        notification2.setDescription("Description 2");

        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(resultSet.getString("uuid")).thenReturn("1").thenReturn("2").thenReturn(null);
        when(resultSet.getString("generator_id")).thenReturn("generator 1").thenReturn("gen2").thenReturn(null);
        when(resultSet.getString("receiver_id")).thenReturn("reciver 1").thenReturn("rec2").thenReturn(null);
        when(resultSet.getString("title")).thenReturn("Title 1").thenReturn("Title 2").thenReturn(null);
        when(resultSet.getString("description")).thenReturn("Description 1").thenReturn("Description 2").thenReturn(null);

        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        List<Notification> result = notificationDAO.getAllNotifications();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("1", result.get(0).getUuid());
        assertEquals("2", result.get(1).getUuid());
    }

    @Test
    public void testFindNotificationsByReceiverId() throws SQLException {
        Notification notification = new Notification();
        notification.setUuid("1");
        notification.setGeneratorId("generator 1");
        notification.setReceiverId("reciver 1");
        notification.setTitle("Title");
        notification.setDescription("Description");

        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getString("uuid")).thenReturn("1");
        when(resultSet.getString("generator_id")).thenReturn("generator 1");
        when(resultSet.getString("receiver_id")).thenReturn("reciver 1");
        when(resultSet.getString("title")).thenReturn("Title");
        when(resultSet.getString("description")).thenReturn("Description");

        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        List<Notification> result = notificationDAO.findNotificationsByReceiverId("reciver 1");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("1", result.get(0).getUuid());
    }

    @Test
    public void testFindNotificationsByGeneratorId() throws SQLException {
        Notification notification = new Notification();
        notification.setUuid("1");
        notification.setGeneratorId("generator 1");
        notification.setReceiverId("reciver 1");
        notification.setTitle("Title");
        notification.setDescription("Description");

        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getString("uuid")).thenReturn("1");
        when(resultSet.getString("generator_id")).thenReturn("generator 1");
        when(resultSet.getString("receiver_id")).thenReturn("reciver 1");
        when(resultSet.getString("title")).thenReturn("Title");
        when(resultSet.getString("description")).thenReturn("Description");

        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        List<Notification> result = notificationDAO.findNotificationsByGeneratorId("generator 1");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("1", result.get(0).getUuid());
    }
}
