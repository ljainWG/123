package com.wg.dabms.a.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.wg.dabms.a.service.UserService;
import com.wg.dabms.input.choice.ChoiceInputHandler;
import com.wg.dabms.input.handler.UserInputHandler;
import com.wg.dabms.menu.PatientLauncher;
import com.wg.dabms.model.User;
import com.wg.dabms.model.enums.Department;
import com.wg.dabms.model.enums.Gender;
import com.wg.dabms.model.enums.Role;
import com.wg.dabms.util.password.PasswordUtil;

public class UserController {

	public static UserService userService;
	public UserController(){
		this.userService = new UserService();
	}

	public static void LogIn() {

		String email = UserInputHandler.getValidatedEmail("Enter email");
		String password = UserInputHandler.getValidatedPassword("Enter password");

		User user = userService.findByEmail(email);

		if (user != null && PasswordUtil.verifyPassword(password, user.getSalt(), user.getPassword())) {		

				switch (user.getRole()) {
				case ADMIN:
//					AdminLauncher.launchMenu(user);
					break;
				case PATIENT:
					PatientLauncher.launchMenu(user);
					break;
				case DOCTOR:
//					DoctorLauncher.launchMenu(user);
					break;
				case RECEPTIONIST:
//					ReceptionistLauncher.launchMenu(user);
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

		String email = UserInputHandler.getValidatedEmail("Enter email");

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
	            // Patient can delete their own account
	            if (confirmDeletion("Are you sure you want to delete your account? (yes/no): ")) {
	                boolean isDeleted = userService.deleteUser(currentUser.getUuid());
	                System.out.println(isDeleted ? "Your account has been deleted successfully." : "Failed to delete your account.");
	            } else {
	                System.out.println("Account deletion canceled.");
	            }
	        } else if (currentUser.getRole() == Role.ADMIN) {
	            // Admin can delete any user except other admins
	            String usernameToDelete = UserInputHandler.getValidatedUsername("Enter the username of the user to delete: ").trim();
	            List<User> usersToDelete = userService.findUsersByUsername(usernameToDelete);

	            if (usersToDelete.isEmpty()) {
	                System.out.println("No users found with the provided username.");
	                return;
	            }

	            // Print the list of users for selection
	            printUserTable(usersToDelete);
	            int userIndex = ChoiceInputHandler.getIntChoice("Select the user to delete by index:", 0, usersToDelete.size() - 1);
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
	            // Doctor and Receptionist cannot delete their own accounts
	            System.out.println("You are not authorized to delete accounts.");
	        }
	    }

	    private boolean confirmDeletion(String message) {
	        return ChoiceInputHandler.getBooleanChoice(message);
	    }
	    private void printUserTable(List<User> users) {
	        System.out.printf("%-5s %-20s %-30s %-15s %-15s %-15s%n", "Index", "Username", "Role", "Department", "DOB", "Mobile No");
	        for (int i = 0; i < users.size(); i++) {
	            User user = users.get(i);
	            System.out.printf("%-5d %-20s %-30s %-15s %-15s %-15s%n",
	                i,
	                user.getUsername(),
	                user.getRole(),
	                user.getDepartment() != null ? user.getDepartment() : "N/A",
	                user.getDob() != null ? user.getDob() : "N/A",
	                user.getPhoneNo() != null ? user.getPhoneNo() : "N/A"
	            );
	        }
	    }
}