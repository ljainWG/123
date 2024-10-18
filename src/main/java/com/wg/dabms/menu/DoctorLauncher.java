package com.wg.dabms.menu;

import com.wg.dabms.constant.Constant;
import com.wg.dabms.controller.AppointmentController;
import com.wg.dabms.controller.NotificationController;
import com.wg.dabms.controller.PrescriptionController;
import com.wg.dabms.controller.ReviewController;
import com.wg.dabms.controller.UserController;
import com.wg.dabms.input.choice.ChoiceInputHandler;
import com.wg.dabms.model.User;

public class DoctorLauncher {
	private static UserController userController = new UserController();
	private static NotificationController notificationController = new NotificationController();
	private static AppointmentController appointmentController = new AppointmentController();
	private static PrescriptionController prescriptionController = new PrescriptionController();
	private static ReviewController reviewController = new ReviewController();

	DoctorLauncher() {

	}

	public static void launchMenu(User user) {
		boolean condition = true;
		while (condition) {
			int choice = ChoiceInputHandler.getIntChoice(Constant.doctorMenu, 1, Constant.doctorMenuSize);
			switch (choice) {
			case 1:
				appointmentController.viewAppointments(user);
				break;
			case 2:
				appointmentController.updateAppointmentStatus(user);
				break;
			case 3:
				appointmentController.viewAppointments(user);
				break;
			case 4:
				prescriptionController.createPrescription(user);
				break;
			case 5:
				prescriptionController.updatePrescription(user);
				break;
			case 6:
				prescriptionController.listPrescriptions(user);
				break;
			case 7:
				notificationController.viewNotifications(user);
				break;
			case 8:
				userController.viewProfile(user);
				break;
			case 9:
				userController.updateUserProfile(user);
				break;
			case 10:
				condition = false;
				break;
			case 11:
				System.exit(0);
				break;
			}
		}
	}

}
