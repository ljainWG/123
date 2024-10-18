package com.wg.dabms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.wg.dabms.model.User;
import com.wg.dabms.model.enums.Department;
import com.wg.dabms.model.enums.Gender;
import com.wg.dabms.model.enums.Role;

public class UserDAO extends GenericDAO<User> {
    
    private static final Logger LOGGER = Logger.getLogger(UserDAO.class.getName());

    @Override
    protected User mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        User user = new User();
        
        user.setUuid(resultSet.getString("uuid"));
        user.setUsername(resultSet.getString("username"));
        user.setSalt(resultSet.getString("salt"));
        user.setPassword(resultSet.getString("password"));
        user.setEmail(resultSet.getString("email"));

        // Handle potential null values for enums
        String genderStr = resultSet.getString("gender");
        user.setGender(genderStr != null ? Gender.valueOf(genderStr) : null);
        
        String roleStr = resultSet.getString("role");
        user.setRole(roleStr != null ? Role.valueOf(roleStr) : null);
        
        String departmentStr = resultSet.getString("department");
        user.setDepartment(departmentStr != null ? Department.valueOf(departmentStr) : null);

        user.setPhoneNo(resultSet.getString("phone_no"));
        user.setAddress(resultSet.getString("address"));
        user.setAvgRating(resultSet.getBigDecimal("avg_rating"));
        user.setNoOfReview(resultSet.getBigDecimal("no_of_review"));
        user.setExperience(resultSet.getBigDecimal("experience"));
        user.setDob(resultSet.getDate("dob") != null ? resultSet.getDate("dob").toLocalDate() : null);
        user.setCreatedAt(resultSet.getTimestamp("created_at") != null ? resultSet.getTimestamp("created_at").toLocalDateTime() : null);
        user.setUpdatedAt(resultSet.getTimestamp("updated_at") != null ? resultSet.getTimestamp("updated_at").toLocalDateTime() : null);
        
        LOGGER.log(Level.INFO, "Mapped ResultSet to User entity: {0}", user);
        return user;
    }

    public List<User> findUsersByUsernameAndRole(String username, Role role) {
        LOGGER.log(Level.INFO, "Finding users by username: {0} and role: {1}", new Object[]{username, role});
        String query = "SELECT * FROM user WHERE username LIKE ? AND role = ?";
        return executeGetAllQuery(query, "%" + username + "%", role.name());
    }

    public User getUserById(String userId) {
        LOGGER.log(Level.INFO, "Getting user by ID: {0}", userId);
        String query = "SELECT * FROM user WHERE uuid = ?";
        return executeGetQuery(query, userId);
    }

    public List<User> findUsersByUsername(String username) {
        LOGGER.log(Level.INFO, "Finding users by username: {0}", username);
        String query = "SELECT * FROM user WHERE username = ?";
        return executeGetAllQuery(query, username);
    }

    public List<User> getAllUsers() {
        LOGGER.info("Getting all users");
        String query = "SELECT * FROM user";
        return executeGetAllQuery(query);
    }

    public boolean createUser(User user) {
        LOGGER.log(Level.INFO, "Creating user: {0}", user);
        String query = "INSERT INTO user (uuid, username, salt, password, email, gender, role, department, phone_no, address, avg_rating, no_of_review, experience, dob, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        boolean result = executeUpdate(query, user.getUuid(), user.getUsername(), user.getSalt(), user.getPassword(),
                user.getEmail(), user.getGender().name(), user.getRole().name(), user.getDepartment().name(),
                user.getPhoneNo(), user.getAddress(), user.getAvgRating(), user.getNoOfReview(), user.getExperience(),
                user.getDob(), user.getCreatedAt(), user.getUpdatedAt());
        LOGGER.log(Level.INFO, "User creation successful: {0}", result);
        return result;
    }

    public boolean updateUser(User user) {
        LOGGER.log(Level.INFO, "Updating user: {0}", user);
        String query = "UPDATE user SET username = ?, salt = ?, password = ?, email = ?, gender = ?, role = ?, department = ?, phone_no = ?, address = ?, avg_rating = ?, no_of_review = ?, experience = ?, dob = ?, created_at = ?, updated_at = ? WHERE uuid = ?";
        
        String gender = user.getGender() != null ? user.getGender().name() : null;
        String role = user.getRole() != null ? user.getRole().name() : null;
        String department = user.getDepartment() != null ? user.getDepartment().name() : null;
        
        boolean result = executeUpdate(query, user.getUsername(), user.getSalt(), user.getPassword(), user.getEmail(),
                gender, role, department, user.getPhoneNo(), user.getAddress(), user.getAvgRating(),
                user.getNoOfReview(), user.getExperience(), user.getDob(), user.getCreatedAt(), user.getUpdatedAt(), user.getUuid());
        LOGGER.log(Level.INFO, "User update successful: {0}", result);
        return result;
    }

    public boolean deleteUser(String uuid) {
        LOGGER.log(Level.INFO, "Deleting user with ID: {0}", uuid);
        String query = "DELETE FROM user WHERE uuid = ?";
        boolean result = executeDelete(query, uuid);
        LOGGER.log(Level.INFO, "User deletion successful: {0}", result);
        return result;
    }

    public List<User> findUsersByRole(Role role) {
        LOGGER.log(Level.INFO, "Finding users by role: {0}", role);
        String query = "SELECT * FROM user WHERE role = ?";
        return executeGetAllQuery(query, role.name());
    }

    public User findByEmail(String email) {
        LOGGER.log(Level.INFO, "Finding user by email: {0}", email);
        String query = "SELECT * FROM user WHERE email = ?";
        return executeGetQuery(query, email);
    }

    public List<User> findUsersByDepartment(Department department) {
        LOGGER.log(Level.INFO, "Finding users by department: {0}", department);
        String query = "SELECT * FROM user WHERE department = ?";
        return executeGetAllQuery(query, department.name());
    }
}
