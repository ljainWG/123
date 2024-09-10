package com.wg.dabms.menu;

import com.wg.dabms.Constant.Constant;
import com.wg.dabms.a.controller.AppointmentController;
import com.wg.dabms.a.controller.NotificationController;
import com.wg.dabms.a.controller.PrescriptionController;
import com.wg.dabms.a.controller.ReviewController;
import com.wg.dabms.a.controller.UserController;
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
				
				break;
			case 2:

				break;
			case 3:

				break;
			case 4:

				break;
			case 5:

				break;
			case 6:

				break;
			case 7:

				break;
			case 8:

				break;
			case 9:

				break;
			case 10:

				break;
			case 11:

				break;
			case 12:

				break;
			case 13:

				break;
			}
		}
	}

}
