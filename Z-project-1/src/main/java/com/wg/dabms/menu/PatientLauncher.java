package com.wg.dabms.menu;

import com.wg.dabms.Constant.Constant;
import com.wg.dabms.a.controller.AppointmentController;
import com.wg.dabms.a.controller.NotificationController;
import com.wg.dabms.a.controller.PrescriptionController;
import com.wg.dabms.a.controller.ReviewController;
import com.wg.dabms.a.controller.UserController;
import com.wg.dabms.input.choice.ChoiceInputHandler;
import com.wg.dabms.model.User;

public class PatientLauncher {
	private static UserController userController;
	private static NotificationController notificationController;
	private static AppointmentController appointmentController;
	private static PrescriptionController prescriptionController;
	private static ReviewController reviewController;

	PatientLauncher() {
		userController = new UserController();
		notificationController = new NotificationController();
		appointmentController = new AppointmentController();
		prescriptionController = new PrescriptionController();
		reviewController = new ReviewController();
	}

	public static void launchMenu(User user) {
		boolean condition = true;
		while (condition) {
			int choice = ChoiceInputHandler.getIntChoice(Constant.patientMenu, 1, Constant.patientMenuSize);
			switch (choice) {
			case 1:
				System.out.println("view my notifications");
				break;
			case 2:

				break;
			case 3:
				prescriptionController.listPrescriptions(user);
				break;
			case 4:

				break;
			case 5:

				break;
			case 6:
				System.out.println("");
			case 8:
				System.exit(0);
				break;
			}
		}
	}

}
