package com.wg.dabms.menu;

import com.wg.dabms.constant.Constant;
import com.wg.dabms.controller.AppointmentController;
import com.wg.dabms.controller.NotificationController;
import com.wg.dabms.controller.PrescriptionController;
import com.wg.dabms.controller.ReviewController;
import com.wg.dabms.controller.UserController;
import com.wg.dabms.input.choice.ChoiceInputHandler;
import com.wg.dabms.model.User;

public class AdminLauncher {
	private static UserController userController = new UserController();
	private static NotificationController notificationController = new NotificationController();
	private static AppointmentController appointmentController = new AppointmentController();
	private static PrescriptionController prescriptionController = new PrescriptionController();
	private static ReviewController reviewController = new ReviewController();

	AdminLauncher() {

	}

	public static void launchMenu(User user) { 
		boolean condition = true;
		while (condition) {
			int choice = ChoiceInputHandler.getIntChoice(Constant.adminMenu, 1, Constant.adminMenuSize);
			switch (choice) {
			case 1:
				appointmentController.bookAppointment(user);
				break;
			case 2:
				appointmentController.updateAppointmentStatus(user);
				break;
			case 3:
				appointmentController.viewAppointments(user);
				break;
			case 4:
				appointmentController.deleteAppointment(user);
				break;
			case 5:
				prescriptionController.updatePrescription(user);
				break;
			case 6:
				prescriptionController.listPrescriptions(user);
				break;
			case 7:
				prescriptionController.deletePrescription(user);
				break;
			case 8:
				notificationController.createNotification(user);
				break;
			case 9:
				notificationController.updateNotification(user);
				break;
			case 10:
				notificationController.viewNotifications(user);
				break;
			case 11:
				notificationController.deleteNotification(user);
				break;
			case 12:
				userController.registerUser(user);
				break;
			case 13:
				userController.updateUserProfile(user);
				break;
			case 14:
				userController.listUsers(user);
				break;
			case 15:
				userController.deleteUser(user);
				break;
			case 16:
				userController.viewProfile(user);
				break;
			case 17:
				condition = false;
				break;
			case 18:
				System.exit(0);
				break;
			}
		}
	}

}
