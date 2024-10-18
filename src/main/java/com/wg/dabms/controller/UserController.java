package com.wg.dabms.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.wg.dabms.input.choice.ChoiceInputHandler;
import com.wg.dabms.input.handler.UserInputHandler;
import com.wg.dabms.menu.AdminLauncher;
import com.wg.dabms.menu.DoctorLauncher;
import com.wg.dabms.menu.PatientLauncher;
import com.wg.dabms.menu.ReceptionistLauncher;
import com.wg.dabms.model.User;
import com.wg.dabms.model.enums.Department;
import com.wg.dabms.model.enums.Gender;
import com.wg.dabms.model.enums.Role;
import com.wg.dabms.service.UserService;
import com.wg.dabms.util.password.PasswordUtil;

public class UserController {

	public static UserService userService = new UserService();

	public UserController() {
	}

	public static void LogIn() {

		String email = UserInputHandler.getValidatedEmail("Enter email");
		String password = UserInputHandler.getValidatedPassword("Enter password");

		User user = userService.findByEmail(email);

		if (user != null && PasswordUtil.verifyPassword(password, user.getSalt(), user.getPassword())) {

			switch (user.getRole()) {
			case ADMIN:
				AdminLauncher.launchMenu(user); 
				break;
			case PATIENT:
				PatientLauncher.launchMenu(user);
				break;
			case DOCTOR:
				DoctorLauncher.launchMenu(user);
				break;
			case RECEPTIONIST:
					ReceptionistLauncher.launchMenu(user);
				break;
			}

		} else {
			System.out.println("Enter the valid credentials");
		}
	}

	public static void registerUser(User currentUser) { 

		String name = UserInputHandler.getValidatedUsername("Enter your name");
		String salt = PasswordUtil.generateSalt();
		String hashedPassword = PasswordUtil.hashPassword(UserInputHandler.getValidatedPassword("Enter password"),
				salt);
		String email;
		while (true) {
			email = UserInputHandler.getValidatedEmail("Enter email");
			if (userService.emailExists(email)) {
				System.out.println("Email already exists. Please enter a different email.");
			} else {
				break;
			}
		}

		LocalDate dob = UserInputHandler.getInputDate("Enter dob in format(yyyy-MM-dd)");

		Gender gender = UserInputHandler.getInputGender("Enter gender");
		Role role = Role.PATIENT;
		if (currentUser != null && currentUser.getRole() == Role.ADMIN) {
			role = UserInputHandler.getInputRole("Enter the role for new user");
		}
		Department deptt = role.equals(Role.DOCTOR) ? UserInputHandler.getInputDepartment("Enter the department")
				: (role.equals(Role.RECEPTIONIST) ? Department.HOSPITALITY
						: (role.equals(Role.ADMIN) ? Department.ADMINISTRATION : Department.NONE));
		String phoneNo = UserInputHandler.getInputPhone("Enter the phone number");
		String Address = UserInputHandler.getInputAddress("Enter the address");
		BigDecimal experience = null;
		BigDecimal noOfreviews = null;
		if (role.equals(Role.DOCTOR)) {
			experience = UserInputHandler.getInputExperience("Enter experience");
			noOfreviews = BigDecimal.ZERO;
		}

		User newUser = new User();
		newUser.setUuid(UUID.randomUUID().toString());
		newUser.setUsername(name);
		newUser.setSalt(salt);
		newUser.setPassword(hashedPassword);
		newUser.setEmail(email);
		newUser.setDob(dob);
		newUser.setGender(gender);
		newUser.setRole(role);
		newUser.setDepartment(deptt);
		newUser.setPhoneNo(phoneNo);
		newUser.setAddress(Address);
		newUser.setExperience(experience);
		newUser.setNoOfReview(noOfreviews);
		LocalDateTime time = LocalDateTime.now();
		newUser.setCreatedAt(time);
		newUser.setUpdatedAt(time);

		userService.createUser(newUser);

	}

	public void deleteUser(User currentUser) {
		if (currentUser.getRole() == Role.PATIENT) {
			if (confirmDeletion("Are you sure you want to delete your account? (yes/no): ")) {
				boolean isDeleted = userService.deleteUser(currentUser.getUuid());
				System.out.println(
						isDeleted ? "Your account has been deleted successfully." : "Failed to delete your account.");
			} else {
				System.out.println("Account deletion canceled.");
			}
		} else if (currentUser.getRole() == Role.ADMIN) {
			String usernameToDelete = UserInputHandler
					.getValidatedUsername("Enter the username of the user to delete: ").trim();
			List<User> usersToDelete = userService.findUsersByUsername(usernameToDelete);

			if (usersToDelete.isEmpty()) {
				System.out.println("No users found with the provided username.");
				return;
			}

			printUserTable(usersToDelete);
			int userIndex = ChoiceInputHandler.getIntChoice("Select the user to delete by index:", 0,
					usersToDelete.size() - 1);
			User userToDelete = usersToDelete.get(userIndex);

			if (userToDelete.getRole() == Role.ADMIN) {
				System.out.println("Admins cannot delete other admins.");
				return;
			}

			if (confirmDeletion("Are you sure you want to delete this user? (yes/no): ")) {
				boolean isDeleted = userService.deleteUser(userToDelete.getUuid());
				System.out.println(isDeleted ? "User has been deleted successfully." : "Failed to delete user.");
			} else {
				System.out.println("User deletion canceled.");
			}
		} else {
			System.out.println("You are not authorized to delete accounts.");
		}
	}

	private boolean confirmDeletion(String message) {
		return ChoiceInputHandler.getBooleanChoice(message);
	}

	public void viewProfile(User user) {
		System.out.println("Username        : " + user.getUsername());
		System.out.println("Email           : " + user.getEmail());
		System.out.println("Gender          : " + user.getGender());
		System.out.println("Date of Birth   : " + (user.getDob() != null ? user.getDob() : "N/A"));
		System.out.println("Phone No        : " + (user.getPhoneNo() != null ? user.getPhoneNo() : "N/A"));
		System.out.println("Address         : " + (user.getAddress() != null ? user.getAddress() : "N/A"));
		System.out.println("Created At      : " + (user.getCreatedAt() != null ? user.getCreatedAt() : "N/A"));
		System.out.println("Updated At      : " + (user.getUpdatedAt() != null ? user.getUpdatedAt() : "N/A"));

		if (user.getRole() == Role.RECEPTIONIST || user.getRole() == Role.ADMIN) {
			System.out.println("Department      : " + (user.getDepartment() != null ? user.getDepartment() : "N/A"));
		} else if (user.getRole() == Role.DOCTOR) {
			System.out.println("Department      : " + (user.getDepartment() != null ? user.getDepartment() : "N/A"));
			System.out.println("Experience      : " + (user.getExperience() != null ? user.getExperience() : "N/A"));
			System.out.println("Average Rating  : " + (user.getAvgRating() != null ? user.getAvgRating() : "N/A"));
			System.out.println("Number of Reviews: " + (user.getNoOfReview() != null ? user.getNoOfReview() : "N/A"));
		}
	}

	public void updateUserProfile(User currentUser) {
		if (currentUser.getRole() == Role.PATIENT || currentUser.getRole() == Role.DOCTOR
				|| currentUser.getRole() == Role.RECEPTIONIST) {
			updateOwnProfile(currentUser);
		} else if (currentUser.getRole() == Role.ADMIN) {
			updateAnyUserProfile(currentUser);
		} else {
			System.out.println("Unauthorized role.");
		}
	}

	private void updateOwnProfile(User currentUser) {
		System.out.println("Select the field you want to update:");
		System.out.println("1. Username");
		System.out.println("2. Email");
		System.out.println("3. Phone No");
		System.out.println("4. Address");
		System.out.println("5. Password");
		System.out.println("6. Gender");
		System.out.println("7. Date of Birth");
		if (currentUser.getRole() == Role.DOCTOR) {
			System.out.println("8. Experience");
		}
		int choice = ChoiceInputHandler.getIntChoice("Enter your choice:", 1, 8);

		switch (choice) {
		case 1:
			String newUsername = UserInputHandler.getValidatedUsername("Enter new username: ");
			currentUser.setUsername(newUsername);
			break;
		case 2:
			String newEmail = UserInputHandler.getValidatedEmail("Enter new email: ");
			currentUser.setEmail(newEmail);
			break;
		case 3:
			String newPhoneNo = UserInputHandler.getInputPhone("Enter new phone number: ");
			currentUser.setPhoneNo(newPhoneNo);
			break;
		case 4:
			String newAddress = UserInputHandler.getInputAddress("Enter new address: ");
			currentUser.setAddress(newAddress);
			break;
		case 5:
			String newPassword = UserInputHandler.getValidatedPassword("Enter new password: ");
			currentUser.setPassword(newPassword); // Encrypt or hash the password before saving
			break;
		case 6:
			Gender newGender = UserInputHandler.getInputGender("Select new gender:");
			currentUser.setGender(newGender);
			break;
		case 7:
			LocalDate newDob = UserInputHandler.getInputDate("Enter new date of birth (yyyy-mm-dd): ");
			currentUser.setDob(newDob);
			break;
		case 8:
			if (currentUser.getRole() == Role.DOCTOR) {
				BigDecimal newExperience = UserInputHandler.getInputExperience("Enter new experience: ");
				currentUser.setExperience(newExperience);
			} else {
				System.out.println("Invalid choice.");
			}
			break;
		default:
			System.out.println("Invalid choice.");
			break;
		}

		boolean isUpdated = userService.updateUserProfile(currentUser);
		System.out.println(isUpdated ? "Profile updated successfully." : "Failed to update profile.");
	}

	private void updateAnyUserProfile(User currentUser) {
		String usernameToUpdate = UserInputHandler.getValidatedUsername("Enter the username of the user to update: ");
		List<User> users = userService.findUsersByUsername(usernameToUpdate);

		if (users.isEmpty()) {
			System.out.println("No user found with that username.");
			return;
		}

		printUserTable(users);
		int userIndex = ChoiceInputHandler.getIntChoice("Select the user by index:", 1, users.size());
		User userToUpdate = users.get(userIndex - 1);

		System.out.println("Select the field you want to update:");
		System.out.println("1. Username");
		System.out.println("2. Email");
		System.out.println("3. Phone No");
		System.out.println("4. Address");
		System.out.println("5. Password");
		System.out.println("6. Role");
		System.out.println("7. Department");
		System.out.println("8. Experience");
		System.out.println("9. No of Reviews");
		System.out.println("10. Avg Rating");
		System.out.println("11. Gender");
		System.out.println("12. Date of Birth");
		int choice = ChoiceInputHandler.getIntChoice("Enter your choice:", 1, 12);

		switch (choice) {
		case 1:
			String newUsername = UserInputHandler.getValidatedUsername("Enter new username: ");
			userToUpdate.setUsername(newUsername);
			break;
		case 2:
			String newEmail = UserInputHandler.getValidatedEmail("Enter new email: ");
			userToUpdate.setEmail(newEmail);
			break;
		case 3:
			String newPhoneNo = UserInputHandler.getInputPhone("Enter new phone number: ");
			userToUpdate.setPhoneNo(newPhoneNo);
			break;
		case 4:
			String newAddress = UserInputHandler.getInputAddress("Enter new address: ");
			userToUpdate.setAddress(newAddress);
			break;
		case 5:
			String newPassword = UserInputHandler.getValidatedPassword("Enter new password: ");
			userToUpdate.setPassword(newPassword); // Encrypt or hash the password before saving
			break;
		case 6:
			Role newRole = UserInputHandler.getInputRole("Select new role:");
			userToUpdate.setRole(newRole);
			break;
		case 7:
			Department newDepartment = UserInputHandler.getInputDepartment("Select new department:");
			userToUpdate.setDepartment(newDepartment);
			break;
		case 8:
			BigDecimal newExperience = UserInputHandler.getInputExperience("Enter new experience: ");
			userToUpdate.setExperience(newExperience);
			break;
		case 9:
			BigDecimal newNoOfReviews = UserInputHandler.getValidatedNoOfReviews("Enter new number of reviews: ");
			userToUpdate.setNoOfReview(newNoOfReviews);
			break;
		case 10:
			BigDecimal newAvgRating = UserInputHandler.getValidatedAvgRating("Enter new average rating: ");
			userToUpdate.setAvgRating(newAvgRating);
			break;
		case 11:
			Gender newGender = UserInputHandler.getInputGender("Select new gender:");
			userToUpdate.setGender(newGender);
			break;
		case 12:
			LocalDate newDob = UserInputHandler.getInputDate("Enter new date of birth (yyyy-mm-dd): ");
			userToUpdate.setDob(newDob);
			break;
		default:
			System.out.println("Invalid choice.");
			break;
		}
		boolean isUpdated = userService.updateUserProfile(userToUpdate);
		System.out.println(isUpdated ? "Profile updated successfully." : "Failed to update profile.");
	}

	public void listUsers(User currentUser) {
		if (currentUser.getRole() == Role.ADMIN || currentUser.getRole() == Role.RECEPTIONIST) {
			System.out.println("Choose the type of users to list:");
			System.out.println("1. All users");
			System.out.println("2. Users by role");
			System.out.println("3. Users by department");
			System.out.println("4. Users by username");

			int choice = ChoiceInputHandler.getIntChoice("Enter your choice (1-4): ", 1, 4);

			List<User> users = null;
			switch (choice) {
			case 1:
				users = userService.getAllUsers();
				break;
			case 2:
				Role role = UserInputHandler.getInputRole("Enter role: ");
				users = userService.getUsersByRole(role);
				break;
			case 3:
				Department department = UserInputHandler.getInputDepartment("Enter department: ");
				users = userService.getUsersByDepartment(department);
				break;
			case 4:
				String username = UserInputHandler.getValidatedUsername("Enter username: ");
				List<User> matchedUsers = userService.findUsersByUsername(username);
				if (matchedUsers.isEmpty()) {
					System.out.println("No users found with the username: " + username);
					return;
				}
				// Print the matched users in tabular form
				printUserTable(matchedUsers);
				int userChoice = ChoiceInputHandler.getIntChoice("Select a user by index: ", 1, matchedUsers.size())
						- 1;
				if (userChoice < 0 || userChoice >= matchedUsers.size()) {
					System.out.println("Invalid index.");
					return;
				}
				users = List.of(matchedUsers.get(userChoice)); // Only the selected user
				break;
			default:
				System.out.println("Invalid choice.");
				return;
			}

			if (users == null || users.isEmpty()) {
				System.out.println("No users found.");
				return;
			}

			// Print the list of users based on the selected option
			printUserTable(users);
		} else {
			System.out.println("Access denied. You do not have permission to list users.");
		}
	}


	private void printUserTable(List<User> users) {
		System.out.printf("%-5s %-20s %-20s %-30s %-15s %-15s %-15s%n", "Index", "Username", "Email", "Role",
				"Department", "DOB", "Mobile No");

		for (int i = 0; i < users.size(); i++) {
			User user = users.get(i);

			System.out.printf("%-5d %-20s %-30s %-15s %-15s %-15s%n", i + 1,
					user.getUsername() != null ? user.getUsername() : "N/A",
					user.getEmail() != null ? user.getEmail() : "N/A",
					user.getRole() != null ? user.getRole().name() : "N/A",
					user.getDepartment() != null ? user.getDepartment().name() : "N/A",
					user.getDob() != null ? user.getDob() : "N/A",
					user.getPhoneNo() != null ? user.getPhoneNo() : "N/A");
		}
	}

}