package com.wg.dabms.a.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.wg.dabms.model.User;
import com.wg.dabms.model.enums.Department;
import com.wg.dabms.model.enums.Gender;
import com.wg.dabms.model.enums.Role;

public class UserDAO extends GenericDAO<User> {

	@Override
	protected User mapResultSetToEntity(ResultSet resultSet) throws SQLException {
		User user = new User();
		user.setUuid(resultSet.getString("uuid"));
		user.setUsername(resultSet.getString("username"));
		user.setSalt(resultSet.getString("salt"));
		user.setPassword(resultSet.getString("password"));
		user.setEmail(resultSet.getString("email"));
		user.setGender(Gender.valueOf(resultSet.getString("gender")));
		user.setRole(Role.valueOf(resultSet.getString("role")));
		user.setDepartment(Department.valueOf(resultSet.getString("department")));
		user.setPhoneNo(resultSet.getString("phone_no"));
		user.setAddress(resultSet.getString("address"));
		user.setAvgRating(resultSet.getBigDecimal("avg_rating"));
		user.setNoOfReview(resultSet.getBigDecimal("no_of_review"));
		user.setExperience(resultSet.getBigDecimal("experience"));
		user.setDob(resultSet.getDate("dob").toLocalDate());
		user.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
		user.setUpdatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime());
		return user;
	}

	public List<User> findUsersByUsernameAndRole(String username, Role role) {
		String query = "SELECT * FROM user WHERE username LIKE ? AND role = ?";
		return executeGetAllQuery(query, "%" + username + "%", role.name());
	}

	public User getUserById(String userId) {
		String query = "SELECT * FROM user WHERE uuid = ?";
		return executeGetQuery(query, userId);
	}

	public List<User> findUsersByUsername(String username) {
		String query = "SELECT * FROM user WHERE username = ?";
		return executeGetAllQuery(query, username);
	}

	public List<User> getAllUsers() {
		String query = "SELECT * FROM user";
		return executeGetAllQuery(query);
	}

	public boolean createUser(User user) {
		String query = "INSERT INTO user (uuid, username, salt, password, email, gender, role, department, phone_no, address, avg_rating, no_of_review, experience, dob, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		return executeUpdate(query, user.getUuid(), user.getUsername(), user.getSalt(), user.getPassword(),
				user.getEmail(), user.getGender().name(), user.getRole().name(), user.getDepartment().name(),
				user.getPhoneNo(), user.getAddress(), user.getAvgRating(), user.getNoOfReview(), user.getExperience(),
				user.getDob(), user.getCreatedAt(), user.getUpdatedAt());
	}

	public boolean updateUser(User user) {
		String query = "UPDATE user SET username = ?, salt = ?, password = ?, email = ?, gender = ?, role = ?, department = ?, phone_no = ?, address = ?, avg_rating = ?, no_of_review = ?, experience = ?, dob = ?, created_at = ?, updated_at = ? WHERE uuid = ?";
		return executeUpdate(query, user.getUsername(), user.getSalt(), user.getPassword(), user.getEmail(),
				user.getGender().name(), user.getRole().name(), user.getDepartment().name(), user.getPhoneNo(),
				user.getAddress(), user.getAvgRating(), user.getNoOfReview(), user.getExperience(), user.getDob(),
				user.getCreatedAt(), user.getUpdatedAt(), user.getUuid());
	}

	public boolean deleteUser(String uuid) {
		String query = "DELETE FROM user WHERE uuid = ?";
		return executeDelete(query, uuid);
	}

	public List<User> findUsersByRole(Role role) {
		String query = "SELECT * FROM user WHERE role = ?";
		return executeGetAllQuery(query, role.name());
	}

	public User findByEmail(String email) {
		String query = "SELECT * FROM user WHERE email = ?";
		return executeGetQuery(query, email);
	}

	public List<User> findUsersByDepartment(Department department) {
		String query = "SELECT * FROM user WHERE department = ?";
		return executeGetAllQuery(query, department.name());
	}

}
