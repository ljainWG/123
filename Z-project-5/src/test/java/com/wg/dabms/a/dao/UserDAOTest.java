package com.wg.dabms.a.dao;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.wg.dabms.model.User;
import com.wg.dabms.model.enums.Department;
import com.wg.dabms.model.enums.Gender;
import com.wg.dabms.model.enums.Role;

public class UserDAOTest {

	private UserDAO userDAO;
	private GenericDAO<User> genericDAO;

	@BeforeEach
	public void setUp() {
		userDAO = spy(new UserDAO()); // Using spy to test real methods with mocked dependencies
		genericDAO = mock(GenericDAO.class);
	}

	@Test
	public void testFindUsersByUsernameAndRole() {
		Role role = Role.DOCTOR;
		String username = "john";
		List<User> users = new ArrayList<>();

		when(userDAO.executeGetAllQuery(anyString(), anyString(), anyString())).thenReturn(users);

		List<User> result = userDAO.findUsersByUsernameAndRole(username, role);

		assertEquals(users, result, "Should return users by username and role");
		verify(userDAO).executeGetAllQuery("SELECT * FROM user WHERE username LIKE ? AND role = ?",
				"%" + username + "%", role.name());
	}

	@Test
	public void testGetUserById() {
		String userId = "123";
		User user = new User();

		when(userDAO.executeGetQuery(anyString(), anyString())).thenReturn(user);

		User result = userDAO.getUserById(userId);

		assertEquals(user, result, "Should return the user by ID");
		verify(userDAO).executeGetQuery("SELECT * FROM user WHERE uuid = ?", userId);
	}

	@Test
	public void testFindUsersByUsername() {
		String username = "john";
		List<User> users = new ArrayList<>();

		when(userDAO.executeGetAllQuery(anyString(), anyString())).thenReturn(users);

		List<User> result = userDAO.findUsersByUsername(username);

		assertEquals(users, result, "Should return users by username");
		verify(userDAO).executeGetAllQuery("SELECT * FROM user WHERE username = ?", username);
	}

	@Test
	public void testGetAllUsers() {
		List<User> users = new ArrayList<>();

		when(userDAO.executeGetAllQuery(anyString())).thenReturn(users);

		List<User> result = userDAO.getAllUsers();

		assertEquals(users, result, "Should return all users");
		verify(userDAO).executeGetAllQuery("SELECT * FROM user");
	}

	@Test
	public void testCreateUser() {
		User user = createUserStub(); // Use a method to create a stub User object

		// Mock the executeUpdate method
		when(userDAO.executeUpdate(anyString(), // SQL query
				any(), // uuid
				any(), // username
				any(), // salt
				any(), // password
				any(), // email
				any(), // gender
				any(), // role
				any(), // department
				any(), // phone_no
				any(), // address
				any(), // avg_rating
				any(), // no_of_review
				any(), // experience
				any(), // dob
				any(), // created_at
				any() // updated_at
		)).thenReturn(true);

		// Perform the create operation
		boolean result = userDAO.createUser(user);

		// Verify and assert the results
		assertTrue(result, "User should be created successfully");
		verify(userDAO).executeUpdate(eq(
				"INSERT INTO user (uuid, username, salt, password, email, gender, role, department, phone_no, address, avg_rating, no_of_review, experience, dob, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"),
				any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(),
				any());
	}

	@Test
    public void testUpdateUser() {
        User user = createUserStub();
        user.setUuid("uuid-123"); // Ensure UUID is set for update

        // Mock the executeUpdate method
        when(userDAO.executeUpdate(
            anyString(), // SQL query
            any(),       // username
            any(),       // salt
            any(),       // password
            any(),       // email
            any(),       // gender
            any(),       // role
            any(),       // department
            any(),       // phone_no
            any(),       // address
            any(),       // avg_rating
            any(),       // no_of_review
            any(),       // experience
            any(),       // dob
            any(),       // created_at
            any()        // updated_at
        )).thenReturn(true);

        // Perform the update operation
        boolean result = userDAO.updateUser(user);

        // Verify and assert the results
        assertTrue(result, "User should be updated successfully");

        // Verify interaction with executeUpdate
        verify(userDAO).executeUpdate(
            eq("UPDATE user SET username = ?, salt = ?, password = ?, email = ?, gender = ?, role = ?, department = ?, phone_no = ?, address = ?, avg_rating = ?, no_of_review = ?, experience = ?, dob = ?, created_at = ?, updated_at = ? WHERE uuid = ?"),
            eq(user.getUsername()), 
            eq(user.getSalt()), 
            eq(user.getPassword()), 
            eq(user.getEmail()), 
            eq(user.getGender().name()), 
            eq(user.getRole().name()), 
            eq(user.getDepartment().name()), 
            eq(user.getPhoneNo()), 
            eq(user.getAddress()), 
            eq(user.getAvgRating()), 
            eq(user.getNoOfReview()), 
            eq(user.getExperience()), 
            eq(user.getDob()), 
            eq(user.getCreatedAt()), 
            eq(user.getUpdatedAt()), 
            eq(user.getUuid())
        );
    }

    private User createUserStub() {
        User user = new User();
        user.setUsername("john_doe");
        user.setSalt("salt");
        user.setPassword("password");
        user.setEmail("john@example.com");
        user.setGender(Gender.MALE);
        user.setRole(Role.DOCTOR);
        user.setDepartment(Department.CARDIOLOGY);
        user.setPhoneNo("1234567890");
        user.setAddress("123 Main St");
        user.setAvgRating(new BigDecimal("4.5"));
        user.setNoOfReview(new BigDecimal("10"));
        user.setExperience(new BigDecimal("5"));
        user.setDob(LocalDate.of(1990, 1, 1));
        user.setCreatedAt(LocalDateTime.of(2020, 1, 1, 12, 0));
        user.setUpdatedAt(LocalDateTime.of(2021, 1, 1, 12, 0));
        return user;
    }
	@Test
	public void testDeleteUser() {
		String userId = "123";

		when(userDAO.executeDelete(anyString(), anyString())).thenReturn(true);

		boolean result = userDAO.deleteUser(userId);

		assertTrue(result, "Should delete user successfully");
		verify(userDAO).executeDelete("DELETE FROM user WHERE uuid = ?", userId);
	}

	@Test
	public void testFindUsersByRole() {
		Role role = Role.DOCTOR;
		List<User> users = new ArrayList<>();

		when(userDAO.executeGetAllQuery(anyString(), anyString())).thenReturn(users);

		List<User> result = userDAO.findUsersByRole(role);

		assertEquals(users, result, "Should return users by role");
		verify(userDAO).executeGetAllQuery("SELECT * FROM user WHERE role = ?", role.name());
	}

	@Test
	public void testFindByEmail() {
		String email = "test@example.com";
		User user = new User();

		when(userDAO.executeGetQuery(anyString(), anyString())).thenReturn(user);

		User result = userDAO.findByEmail(email);

		assertEquals(user, result, "Should return user by email");
		verify(userDAO).executeGetQuery("SELECT * FROM user WHERE email = ?", email);
	}

	@Test
	public void testFindUsersByDepartment() {
		Department department = Department.CARDIOLOGY;
		List<User> users = new ArrayList<>();

		when(userDAO.executeGetAllQuery(anyString(), anyString())).thenReturn(users);

		List<User> result = userDAO.findUsersByDepartment(department);

		assertEquals(users, result, "Should return users by department");
		verify(userDAO).executeGetAllQuery("SELECT * FROM user WHERE department = ?", department.name());
	}
}
