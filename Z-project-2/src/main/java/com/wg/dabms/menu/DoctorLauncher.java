package com.wg.dabms.menu;

import com.wg.dabms.Constant.Constant;
import com.wg.dabms.a.controller.AppointmentController;
import com.wg.dabms.a.controller.NotificationController;
import com.wg.dabms.a.controller.PrescriptionController;
import com.wg.dabms.a.controller.ReviewController;
import com.wg.dabms.a.controller.UserController;
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
				prescriptionController.createPrescription(user);
				break;
			case 4:
				prescriptionController.listPrescriptions(user);
				break;
			case 5:
				prescriptionController.updatePrescription(user);
				break;
			case 6:

				break;
			case 7:

				break;
			case 8:
				condition = false;
				break;
			case 9:
				System.exit(0);
				break;
			}
		}
	}

}
