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
	private static UserController userController = new UserController();
	private static NotificationController notificationController = new NotificationController();
	private static AppointmentController appointmentController = new AppointmentController();
	private static PrescriptionController prescriptionController = new PrescriptionController();
	private static ReviewController reviewController = new ReviewController();

	PatientLauncher() {
	}

	public static void launchMenu(User user) {
		boolean condition = true;
		while (condition) {
			int choice = ChoiceInputHandler.getIntChoice(Constant.patientMenu, 1, Constant.patientMenuSize);
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
				prescriptionController.listPrescriptions(user);
				break;
			case 5:
				userController.deleteUser(user);
				
			case 6:condition= false;
				break;
			case 8:
				System.exit(0);
				break;
			}
		}
	}

}
