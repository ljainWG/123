package com.wg.dabms.a.service;

import java.util.List;

import com.wg.dabms.a.dao.UserDAO;
import com.wg.dabms.model.User;
import com.wg.dabms.model.enums.Department;
import com.wg.dabms.model.enums.Role;

public class UserService {

	private final UserDAO userDAO;

	public UserService() {
		this.userDAO = new UserDAO();
	}

	public User getUserById(String uuid) {
		return userDAO.getUserById(uuid);
	}

	public List<User> getAllUsers() {
		return userDAO.getAllUsers();
	}

	public boolean createUser(User user) {
		return userDAO.createUser(user);
	}

	public boolean updateUser(User user) {
		return userDAO.updateUser(user);
	}

	public boolean deleteUser(String uuid) {
		return userDAO.deleteUser(uuid);
	}

	public List<User> getUsersByRole(Role role) {
		return userDAO.findUsersByRole(role);
	}

	public List<User> findUsersByUsername(String username) {
		return userDAO.findUsersByUsername(username);
	}

	public User findByEmail(String email) {
		return userDAO.findByEmail(email);
	}
	
	public List<User> getUsersByDepartment(Department department) {
        return userDAO.findUsersByDepartment(department);
    }
	public List<User> findUsersByUsernameAndRole(String username, Role role) {
        return userDAO.findUsersByUsernameAndRole(username, role);
    }
}
