package com.wg.dabms.a.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.wg.dabms.a.service.NotificationService;
import com.wg.dabms.a.service.UserService;
import com.wg.dabms.input.choice.ChoiceInputHandler;
import com.wg.dabms.input.handler.NotificationInputHandler;
import com.wg.dabms.input.handler.UserInputHandler;
import com.wg.dabms.model.Notification;
import com.wg.dabms.model.User;
import com.wg.dabms.model.enums.Role;

public class NotificationController {

	private static NotificationService notificationService = new NotificationService();
	private UserService userService = new UserService();

	public void viewNotifications(User currentUser) {
		if (currentUser.getRole() == Role.PATIENT || currentUser.getRole() == Role.DOCTOR) {
			List<Notification> notifications = notificationService.getNotificationsByReceiverId(currentUser.getUuid());
			printNotificationTable(notifications);
		} else if (currentUser.getRole() == Role.ADMIN || currentUser.getRole() == Role.RECEPTIONIST) {
			System.out.println("Choose the type of notifications to view:");
			System.out.println("1. All notifications");
			System.out.println("2. Notifications I have received");
			System.out.println("3. Notifications I have sent");
			System.out.println("4. Notifications by generator username");
			System.out.println("5. Notifications by receiver username");

			int choice = ChoiceInputHandler.getIntChoice("Enter your choice (1-5): ", 1, 5);

			List<Notification> notifications = null;
			switch (choice) {
			case 1:
				notifications = notificationService.getAllNotifications();
				break;
			case 2:
				notifications = notificationService.getNotificationsByReceiverId(currentUser.getUuid());
				break;
			case 3:
				notifications = notificationService.getNotificationsByGeneratorId(currentUser.getUuid());
				break;
			case 4:
				String generatorUsername = UserInputHandler.getValidatedUsername("Enter generator username: ");
				List<User> generatorUsers = userService.findUsersByUsername(generatorUsername);
				if (generatorUsers.isEmpty()) {
					System.out.println("No users found with the username: " + generatorUsername);
					return;
				}
				printUserTable(generatorUsers);
				int generatorChoice = ChoiceInputHandler.getIntChoice("Select a generator user by index: ", 1,
						generatorUsers.size()) - 1;
				String generatorId = generatorUsers.get(generatorChoice).getUuid();
				notifications = notificationService.getNotificationsByGeneratorId(generatorId);
				break;
			case 5:
				String receiverUsername = UserInputHandler.getValidatedUsername("Enter receiver username: ");
				List<User> receiverUsers = userService.findUsersByUsername(receiverUsername);
				if (receiverUsers.isEmpty()) {
					System.out.println("No users found with the username: " + receiverUsername);
					return;
				}
				printUserTable(receiverUsers);
				int receiverChoice = ChoiceInputHandler.getIntChoice("Select a receiver user by index: ", 1,
						receiverUsers.size()) - 1;
				String receiverId = receiverUsers.get(receiverChoice).getUuid();
				notifications = notificationService.getNotificationsByReceiverId(receiverId);
				break;
			default:
				System.out.println("Invalid choice.");
				return;
			}

			printNotificationTable(notifications);
		} else {
			System.out.println("Access denied. You do not have permission to view notifications.");
		}
	}

	private void printUserTable(List<User> users) {
		System.out.printf("%-5s %-20s %-30s %-15s %-15s %-15s%n", "Index", "Username", "Role", "Department", "DOB",
				"Mobile No");
		for (int i = 0; i < users.size(); i++) {
			User user = users.get(i);
			System.out.printf("%-5d %-20s %-30s %-15s %-15s %-15s%n", i + 1, user.getUsername(), user.getRole(),
					user.getDepartment() != null ? user.getDepartment().name() : "N/A",
					user.getDob() != null ? user.getDob() : "N/A",
					user.getPhoneNo() != null ? user.getPhoneNo() : "N/A");
		}
	}

	private void printNotificationTable(List<Notification> notifications) {
		System.out.printf("%-5s %-20s %-20s %-20s %-20s %-20s %-20s%n", "Index", "Generated At", "Updated At",
				"Generator", "Receiver", "Title", "Description");
		for (int i = 0; i < notifications.size(); i++) {
			Notification notification = notifications.get(i);
			User receiver = userService.getUserById(notification.getReceiverId());
			User generator = userService.getUserById(notification.getGeneratorId());

			String receiverName = (receiver != null) ? receiver.getUsername() : "Unknown";
			String generatorName = (generator != null) ? generator.getUsername() : "Unknown";

			System.out.printf("%-5d %-20s %-20s %-20s %-20s %-20s %-20s%n", i + 1, notification.getGeneratedAt(),
					notification.getUpdatedAt(), generatorName, receiverName, notification.getTitle(),
					notification.getDescription());
		}
	}

	public void createNotification(User currentUser) {
		if (currentUser.getRole() != Role.ADMIN && currentUser.getRole() != Role.RECEPTIONIST) {
			System.out.println("Access denied. You do not have permission to create notifications.");
			return;
		}

		String title = NotificationInputHandler.getValidatedTitle("Enter the title of the notification: ");
		String description = NotificationInputHandler.getValidatedDescription("Enter the description: ");
		String receiverUsername = UserInputHandler.getValidatedUsername("Enter the receiver's username: ");

		List<User> users = userService.findUsersByUsername(receiverUsername);
		if (users.isEmpty()) {
			System.out.println("No users found with the provided username.");
			return;
		}

		printUserTable(users);
		int choice = ChoiceInputHandler.getIntChoice("Enter the number of the user to receive the notification: ", 1,
				users.size());
		User selectedUser = users.get(choice - 1);

		String receiverId = selectedUser.getUuid();
		String generatorId = currentUser.getUuid();
		LocalDateTime now = LocalDateTime.now();

		Notification notification = new Notification();
		notification.setUuid(UUID.randomUUID().toString());
		notification.setGeneratorId(generatorId);
		notification.setReceiverId(receiverId);
		notification.setTitle(title);
		notification.setDescription(description);
		notification.setGeneratedAt(now);
		notification.setUpdatedAt(now);

		boolean success = notificationService.createNotification(notification);
		if (success) {
			System.out.println("Notification created successfully.");
		} else {
			System.out.println("Failed to create notification.");
		}
	}

	public void updateNotification(User currentUser) {
		List<Notification> notifications = null;

		if (currentUser.getRole() == Role.ADMIN || currentUser.getRole() == Role.RECEPTIONIST) {
			System.out.println("Choose the type of notifications to update:");
			System.out.println("1. All notifications");
			System.out.println("2. Notifications I have generated");
			System.out.println("3. Notifications by generator username");
			System.out.println("4. Notifications by receiver username");

			int choice = ChoiceInputHandler.getIntChoice("Enter your choice (1-4): ", 1, 4);

			switch (choice) {
			case 1:
				notifications = notificationService.getAllNotifications();
				break;
			case 2:
				notifications = notificationService.getNotificationsByGeneratorId(currentUser.getUuid());
				break;
			case 3:
				String generatorUsername = UserInputHandler.getValidatedUsername("Enter generator username: ");
				List<User> generatorUsers = userService.findUsersByUsername(generatorUsername);
				if (generatorUsers.isEmpty()) {
					System.out.println("No users found with the username: " + generatorUsername);
					return;
				}
				printUserTable(generatorUsers);
				int generatorChoice = ChoiceInputHandler.getIntChoice("Select a generator user by index: ", 1,
						generatorUsers.size()) - 1;
				String generatorId = generatorUsers.get(generatorChoice).getUuid();
				notifications = notificationService.getNotificationsByGeneratorId(generatorId);
				break;
			case 4:
				String receiverUsername = UserInputHandler.getValidatedUsername("Enter receiver username: ");
				List<User> receiverUsers = userService.findUsersByUsername(receiverUsername);
				if (receiverUsers.isEmpty()) {
					System.out.println("No users found with the username: " + receiverUsername);
					return;
				}
				printUserTable(receiverUsers);
				int receiverChoice = ChoiceInputHandler.getIntChoice("Select a receiver user by index: ", 1,
						receiverUsers.size()) - 1;
				String receiverId = receiverUsers.get(receiverChoice).getUuid();
				notifications = notificationService.getNotificationsByReceiverId(receiverId);
				break;

			}

			if (notifications == null || notifications.isEmpty()) {
				System.out.println("No notifications found.");
				return;
			}

			printNotificationTable(notifications);
			int index = ChoiceInputHandler.getIntChoice("Enter the index of the notification to update: ", 1,
					notifications.size());
			Notification selectedNotification = notifications.get(index - 1);

			if (currentUser.getRole() == Role.RECEPTIONIST) {
				User generator = userService.getUserById(selectedNotification.getGeneratorId());
				if (generator != null && generator.getRole() == Role.ADMIN) {
					System.out.println("You cannot update notifications generated by an admin.");
					return;
				}
			}

			if (selectedNotification.getReceiverId().equals(currentUser.getUuid())) {
				System.out.println("You cannot update notifications that you have received.");
				return;
			}

			if (currentUser.getRole() == Role.ADMIN) {
				User generator = userService.getUserById(selectedNotification.getGeneratorId());
				if (generator != null && generator.getRole() == Role.ADMIN
						&& !generator.getUuid().equals(currentUser.getUuid())) {
					System.out.println("You cannot update notifications generated by another admin.");
					return;
				}
			}
			String newTitle = NotificationInputHandler.getValidatedTitle("Enter new title: ");
			String newDescription = NotificationInputHandler.getValidatedDescription("Enter new description: ");

			selectedNotification.setTitle(newTitle);
			selectedNotification.setDescription(newDescription);
			selectedNotification.setUpdatedAt(LocalDateTime.now());

			boolean updated = notificationService.updateNotification(selectedNotification);

			if (updated) {
				System.out.println("Notification updated successfully.");
			} else {
				System.out.println("Failed to update the notification.");
			}

		} else {
			System.out.println("Access denied. You do not have permission to update notifications.");
		}
	}
	
	public void deleteNotification(User currentUser) {
	    if (currentUser.getRole() != Role.ADMIN) {
	        System.out.println("Access denied. Only admins can delete notifications.");
	        return;
	    }

	    List<Notification> notifications = null;

	    System.out.println("Choose the type of notifications to delete:");
	    System.out.println("1. All notifications");
	    System.out.println("2. Notifications I have generated");
	    System.out.println("3. Notifications by generator username");
	    System.out.println("4. Notifications by receiver username");

	    int choice = ChoiceInputHandler.getIntChoice("Enter your choice (1-4): ", 1, 4);

	    switch (choice) {
	        case 1:
	            notifications = notificationService.getAllNotifications();
	            break;
	        case 2:
	            notifications = notificationService.getNotificationsByGeneratorId(currentUser.getUuid());
	            break;
	        case 3:
	            String generatorUsername = UserInputHandler.getValidatedUsername("Enter generator username: ");
	            List<User> generatorUsers = userService.findUsersByUsername(generatorUsername);
	            if (generatorUsers.isEmpty()) {
	                System.out.println("No users found with the username: " + generatorUsername);
	                return;
	            }
	            printUserTable(generatorUsers);
	            int generatorChoice = ChoiceInputHandler.getIntChoice("Select a generator user by index: ", 1,
	                    generatorUsers.size()) - 1;
	            String generatorId = generatorUsers.get(generatorChoice).getUuid();
	            notifications = notificationService.getNotificationsByGeneratorId(generatorId);
	            break;
	        case 4:
	            String receiverUsername = UserInputHandler.getValidatedUsername("Enter receiver username: ");
	            List<User> receiverUsers = userService.findUsersByUsername(receiverUsername);
	            if (receiverUsers.isEmpty()) {
	                System.out.println("No users found with the username: " + receiverUsername);
	                return;
	            }
	            printUserTable(receiverUsers);
	            int receiverChoice = ChoiceInputHandler.getIntChoice("Select a receiver user by index: ", 1,
	                    receiverUsers.size()) - 1;
	            String receiverId = receiverUsers.get(receiverChoice).getUuid();
	            notifications = notificationService.getNotificationsByReceiverId(receiverId);
	            break;
	    }

	    if (notifications == null || notifications.isEmpty()) {
	        System.out.println("No notifications found.");
	        return;
	    }

	    printNotificationTable(notifications);
	    int index = ChoiceInputHandler.getIntChoice("Enter the index of the notification to delete: ", 1,
	            notifications.size());
	    Notification selectedNotification = notifications.get(index - 1);

	    User generator = userService.getUserById(selectedNotification.getGeneratorId());
	    if (generator != null && generator.getRole() == Role.ADMIN 
	            && !generator.getUuid().equals(currentUser.getUuid())) {
	        System.out.println("You cannot delete notifications generated by another admin.");
	        return;
	    }

	    boolean deleted = notificationService.deleteNotification(selectedNotification.getUuid());

	    if (deleted) {
	        System.out.println("Notification deleted successfully.");
	    } else {
	        System.out.println("Failed to delete the notification.");
	    }
	}


}