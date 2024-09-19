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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.wg.dabms.dao.UserDAO;
import com.wg.dabms.model.User;
import com.wg.dabms.model.enums.Department;
import com.wg.dabms.model.enums.Gender;
import com.wg.dabms.model.enums.Role;

public class UserDAOTest {

    @InjectMocks
    private UserDAO userDAO;

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
    public void testCreateUser() throws SQLException {
        User user = new User();
        user.setUuid("1");
        user.setUsername("john_doe");
        user.setSalt("salt");
        user.setPassword("password");
        user.setEmail("john@example.com");
        user.setGender(Gender.MALE);
        user.setRole(Role.DOCTOR);
        user.setDepartment(Department.CARDIOLOGY);
        user.setPhoneNo("1234567890");
        user.setAddress("123 Street");
        user.setAvgRating(null);
        user.setNoOfReview(null);
        user.setExperience(null);
        user.setDob(LocalDate.now());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean result = userDAO.createUser(user);

        assertTrue(result);
        verify(preparedStatement).executeUpdate();
    }

    @Test
    public void testGetUserById() throws SQLException {
        User user = new User();
        user.setUuid("1");
        user.setUsername("john_doe");
        user.setSalt("salt");
        user.setPassword("password");
        user.setEmail("john@example.com");
        user.setGender(Gender.MALE);
        user.setRole(Role.DOCTOR);
        user.setDepartment(Department.CARDIOLOGY);
        user.setPhoneNo("1234567890");
        user.setAddress("123 Street");
        user.setAvgRating(null);
        user.setNoOfReview(null);
        user.setExperience(null);
        user.setDob(LocalDate.now());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getString("uuid")).thenReturn("1");
        when(resultSet.getString("username")).thenReturn("john_doe");
        // Add other getters as needed

        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        User result = userDAO.getUserById("1");

        assertNotNull(result);
        assertEquals("1", result.getUuid());
        assertEquals("john_doe", result.getUsername());
        // Add other assertions as needed
    }

    @Test
    public void testUpdateUser() throws SQLException {
        User user = new User();
        user.setUuid("1");
        user.setUsername("john_doe");

        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean result = userDAO.updateUser(user);

        assertTrue(result);
        verify(preparedStatement).executeUpdate();
    }

    @Test
    public void testDeleteUser() throws SQLException {
        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean result = userDAO.deleteUser("1");

        assertTrue(result);
        verify(preparedStatement).executeUpdate();
    }

    @Test
    public void testGetAllUsers() throws SQLException {
        User user1 = new User();
        user1.setUuid("1");
        user1.setUsername("john_doe");

        User user2 = new User();
        user2.setUuid("2");
        user2.setUsername("jane_doe");

        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(resultSet.getString("uuid")).thenReturn("1").thenReturn("2").thenReturn(null);
        when(resultSet.getString("username")).thenReturn("john_doe").thenReturn("jane_doe").thenReturn(null);

        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        List<User> result = userDAO.getAllUsers();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("1", result.get(0).getUuid());
        assertEquals("2", result.get(1).getUuid());
    }

    @Test
    public void testFindUsersByUsernameAndRole() throws SQLException {
        User user = new User();
        user.setUuid("1");
        user.setUsername("john_doe");

        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getString("uuid")).thenReturn("1");
        when(resultSet.getString("username")).thenReturn("john_doe");

        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        List<User> result = userDAO.findUsersByUsernameAndRole("john_doe", Role.DOCTOR);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("1", result.get(0).getUuid());
    }

    @Test
    public void testFindUsersByUsername() throws SQLException {
        User user1 = new User();
        user1.setUuid("1");
        user1.setUsername("john_doe");

        User user2 = new User();
        user2.setUuid("2");
        user2.setUsername("john_doe");

        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(resultSet.getString("uuid")).thenReturn("1").thenReturn("2").thenReturn(null);
        when(resultSet.getString("username")).thenReturn("john_doe").thenReturn("john_doe").thenReturn(null);

        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        List<User> result = userDAO.findUsersByUsername("john_doe");

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("1", result.get(0).getUuid());
        assertEquals("2", result.get(1).getUuid());
    }

    @Test
    public void testFindByEmail() throws SQLException {
        User user = new User();
        user.setUuid("1");
        user.setUsername("john_doe");
        user.setEmail("john@example.com");

        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getString("uuid")).thenReturn("1");
        when(resultSet.getString("username")).thenReturn("john_doe");
        when(resultSet.getString("email")).thenReturn("john@example.com");

        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        User result = userDAO.findByEmail("john@example.com");

        assertNotNull(result);
        assertEquals("1", result.getUuid());
        assertEquals("john@example.com", result.getEmail());
    }

    @Test
    public void testFindUsersByDepartment() throws SQLException {
        User user1 = new User();
        user1.setUuid("1");
        user1.setUsername("john_doe");
        user1.setDepartment(Department.CARDIOLOGY);

        User user2 = new User();
        user2.setUuid("2");
        user2.setUsername("jane_doe");
        user2.setDepartment(Department.CARDIOLOGY);

        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(resultSet.getString("uuid")).thenReturn("1").thenReturn("2").thenReturn(null);
        when(resultSet.getString("username")).thenReturn("john_doe").thenReturn("jane_doe").thenReturn(null);
        when(resultSet.getString("department")).thenReturn("CARDIOLOGY").thenReturn("CARDIOLOGY").thenReturn(null);

        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        List<User> result = userDAO.findUsersByDepartment(Department.CARDIOLOGY);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("1", result.get(0).getUuid());
        assertEquals("2", result.get(1).getUuid());
        assertEquals(Department.CARDIOLOGY, result.get(0).getDepartment());
        assertEquals(Department.CARDIOLOGY, result.get(1).getDepartment());
    }

}
