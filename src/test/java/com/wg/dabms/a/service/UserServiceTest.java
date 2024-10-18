package com.wg.dabms.a.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import com.wg.dabms.dao.UserDAO;
import com.wg.dabms.model.User;
import com.wg.dabms.model.enums.Department;
import com.wg.dabms.model.enums.Role;
import com.wg.dabms.service.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class UserServiceTest {

    @Mock
    private UserDAO userDAO;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testUpdateUserProfile() {
        User user = new User();
        when(userDAO.updateUser(user)).thenReturn(true);

        boolean result = userService.updateUserProfile(user);
        assertTrue(result, "User profile should be updated successfully");
        verify(userDAO).updateUser(user);
    }

    @Test
    public void testGetUserById() {
        String uuid = "1234";
        User user = new User();
        when(userDAO.getUserById(uuid)).thenReturn(user);

        User result = userService.getUserById(uuid);
        assertNotNull(result, "User should not be null");
        verify(userDAO).getUserById(uuid);
    }

    @Test
    public void testGetAllUsers() {
        List<User> users = new ArrayList<>();
        when(userDAO.getAllUsers()).thenReturn(users);

        List<User> result = userService.getAllUsers();
        assertEquals(users, result, "Should return the list of all users");
        verify(userDAO).getAllUsers();
    }

    @Test
    public void testCreateUser() {
        User user = new User();
        when(userDAO.createUser(user)).thenReturn(true);

        boolean result = userService.createUser(user);
        assertTrue(result, "User should be created successfully");
        verify(userDAO).createUser(user);
    }

    @Test
    public void testUpdateUser() {
        User user = new User();
        when(userDAO.updateUser(user)).thenReturn(true);

        boolean result = userService.updateUser(user);
        assertTrue(result, "User should be updated successfully");
        verify(userDAO).updateUser(user);
    }

    @Test
    public void testDeleteUser() {
        String uuid = "1234";
        when(userDAO.deleteUser(uuid)).thenReturn(true);

        boolean result = userService.deleteUser(uuid);
        assertTrue(result, "User should be deleted successfully");
        verify(userDAO).deleteUser(uuid);
    }

    @Test
    public void testGetUsersByRole() {
        Role role = Role.PATIENT;
        List<User> users = new ArrayList<>();
        when(userDAO.findUsersByRole(role)).thenReturn(users);

        List<User> result = userService.getUsersByRole(role);
        assertEquals(users, result, "Should return the list of users by role");
        verify(userDAO).findUsersByRole(role);
    }
    @Test
    public void testFindUsersByUsername() {
        List<User> users = new ArrayList<>();
        when(userDAO.findUsersByUsername("testuser")).thenReturn(users);
        List<User> result = userService.findUsersByUsername("testuser");
        assertEquals(users, result, "Should return the list of users by username");
        verify(userDAO, times(1)).findUsersByUsername("testuser");
    }

    @Test
    public void testFindByEmail() {
        String email = "test@example.com";
        User user = new User();
        when(userDAO.findByEmail(email)).thenReturn(user);

        User result = userService.findByEmail(email);
        assertNotNull(result, "User should not be null");
        verify(userDAO).findByEmail(email);
    }

    @Test
    public void testGetUsersByDepartment() {
        Department department = Department.CARDIOLOGY;
        List<User> users = new ArrayList<>();
        when(userDAO.findUsersByDepartment(department)).thenReturn(users);

        List<User> result = userService.getUsersByDepartment(department);
        assertEquals(users, result, "Should return the list of users by department");
        verify(userDAO).findUsersByDepartment(department);
    }

    @Test
    public void testFindUsersByUsernameAndRole() {
        String username = "testuser";
        Role role = Role.PATIENT;
        List<User> users = new ArrayList<>();
        when(userDAO.findUsersByUsernameAndRole(username, role)).thenReturn(users);

        List<User> result = userService.findUsersByUsernameAndRole(username, role);
        assertEquals(users, result, "Should return the list of users by username and role");
        verify(userDAO).findUsersByUsernameAndRole(username, role);
    }

    @Test
    public void testEmailExists() {
        String email = "test@example.com";
        User user = new User();
        when(userDAO.findByEmail(email)).thenReturn(user);

        boolean result = userService.emailExists(email);
        assertTrue(result, "Email should exist");
        verify(userDAO).findByEmail(email);
    }
}
