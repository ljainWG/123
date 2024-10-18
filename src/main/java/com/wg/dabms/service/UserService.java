package com.wg.dabms.service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.wg.dabms.dao.UserDAO;
import com.wg.dabms.model.User;
import com.wg.dabms.model.enums.Department;
import com.wg.dabms.model.enums.Role;

public class UserService {

    private static final Logger LOGGER = Logger.getLogger(UserService.class.getName());
    private UserDAO userDAO = new UserDAO();

    public UserService() {
        LOGGER.info("UserService instantiated.");
    }

    public boolean updateUserProfile(User updatedUser) {
        LOGGER.log(Level.INFO, "Updating user profile for user: {0}", updatedUser.getUuid());
        boolean result = userDAO.updateUser(updatedUser);
        LOGGER.log(Level.INFO, "User profile updated successfully: {0}", result);
        return result;
    }

    public User getUserById(String uuid) {
        LOGGER.log(Level.INFO, "Fetching user by ID: {0}", uuid);
        return userDAO.getUserById(uuid);
    }

    public List<User> getAllUsers() {
        LOGGER.info("Fetching all users.");
        return userDAO.getAllUsers();
    }

    public boolean createUser(User user) {
        LOGGER.log(Level.INFO, "Creating user: {0}", user);
        boolean result = userDAO.createUser(user);
        LOGGER.log(Level.INFO, "User creation successful: {0}", result);
        return result;
    }

    public boolean updateUser(User user) {
        LOGGER.log(Level.INFO, "Updating user: {0}", user.getUuid());
        boolean result = userDAO.updateUser(user);
        LOGGER.log(Level.INFO, "User update successful: {0}", result);
        return result;
    }

    public boolean deleteUser(String uuid) {
        LOGGER.log(Level.INFO, "Deleting user with ID: {0}", uuid);
        boolean result = userDAO.deleteUser(uuid);
        LOGGER.log(Level.INFO, "User deletion successful: {0}", result);
        return result;
    }

    public List<User> getUsersByRole(Role role) {
        LOGGER.log(Level.INFO, "Fetching users by role: {0}", role);
        return userDAO.findUsersByRole(role);
    }

    public List<User> findUsersByUsername(String username) {
        LOGGER.log(Level.INFO, "Finding users by username: {0}", username);
        return userDAO.findUsersByUsername(username);
    }

    public User findByEmail(String email) {
        LOGGER.log(Level.INFO, "Finding user by email: {0}", email);
        return userDAO.findByEmail(email);
    }
    
    public List<User> getUsersByDepartment(Department department) {
        LOGGER.log(Level.INFO, "Fetching users by department: {0}", department);
        return userDAO.findUsersByDepartment(department);
    }

    public List<User> findUsersByUsernameAndRole(String username, Role role) {
        LOGGER.log(Level.INFO, "Finding users by username: {0} and role: {1}", new Object[]{username, role});
        return userDAO.findUsersByUsernameAndRole(username, role);
    }

    public boolean emailExists(String email) {
        LOGGER.log(Level.INFO, "Checking if email exists: {0}", email);
        boolean exists = userDAO.findByEmail(email) != null;
        LOGGER.log(Level.INFO, "Email exists: {0}", exists);
        return exists;
    }
}
