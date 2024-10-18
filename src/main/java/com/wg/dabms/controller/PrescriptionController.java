package com.wg.dabms.controller;

import java.util.List;
import java.util.UUID;

import com.wg.dabms.input.choice.ChoiceInputHandler;
import com.wg.dabms.input.handler.PrescriptionInputHandler;
import com.wg.dabms.input.handler.UserInputHandler;
import com.wg.dabms.model.Appointment;
import com.wg.dabms.model.Prescription;
import com.wg.dabms.model.User;
import com.wg.dabms.model.enums.Role;
import com.wg.dabms.service.AppointmentService;
import com.wg.dabms.service.PrescriptionService;
import com.wg.dabms.service.UserService;

public class PrescriptionController {

	private UserService userService = new UserService();
	private AppointmentService appointmentService = new AppointmentService();
	private PrescriptionService prescriptionService = new PrescriptionService();

	public PrescriptionController() {
	}

	public void listPrescriptions(User currentUser) {
		List<Prescription> prescriptions;
		if (currentUser.getRole() == Role.PATIENT || currentUser.getRole() == Role.DOCTOR) {
			prescriptions = prescriptionService.getPrescriptionsByUser(currentUser.getUuid());
		} else { 
			String username = UserInputHandler
					.getValidatedUsername("Enter the username of the user whose prescriptions you want to view: ");
			List<User> users = prescriptionService.findUsersByUsername(username);
			printUserTable(users);
			int index = ChoiceInputHandler.getIntChoice("Select the user by index", 1, users.size());
			String userId = users.get(index - 1).getUuid();
			prescriptions = prescriptionService.getPrescriptionsByUser(userId);
		}
		if (prescriptions.isEmpty() || prescriptions == null) {
			System.out.println("No prescription Found");
			return;
		}

		printPrescriptionList(prescriptions);
	}

	private void printUserTable(List<User> users) {
		System.out.printf("%-5s %-20s %-30s %-15s %-15s %-15s%n", "Index", "Username", "Role", "Department", "DOB",
				"Mobile No");
		for (int i = 0; i < users.size(); i++) {
			User user = users.get(i);
			System.out.printf("%-5d %-20s %-30s %-15s %-15s %-15s%n", i + 1, user.getUsername(), user.getRole(),
					user.getDepartment() != null ? user.getDepartment() : "N/A",
					user.getDob() != null ? user.getDob() : "N/A",
					user.getPhoneNo() != null ? user.getPhoneNo() : "N/A");
		}
	}

	private void printPrescriptionList(List<Prescription> prescriptions) {
		System.out.printf("%-5s %-20s %-20s %-15s %-10s %-20s %-20s %-30s%n", "Index", "Doctor", "Patient",
				"Appointment Date", "Slot", "Creation Date", "Updation Date", "Description");

		for (int i = 0; i < prescriptions.size(); i++) {
			Prescription prescription = prescriptions.get(i);

			Appointment appointment = appointmentService.getAppointmentById(prescription.getAppointmentId());

			if (appointment == null) {
				System.out.printf("%-5d %-20s %-20s %-15s %-10s %-20s %-20s %-30s%n", i + 1, "Unknown", "Unknown",
						"N/A", "N/A", prescription.getCreatedAt(), prescription.getUpdatedAt(),
						prescription.getDescription() != null ? prescription.getDescription() : "N/A");
				continue;
			}

			String doctorName = prescriptionService.getUserNameById(appointment.getDoctorId());
			doctorName = (doctorName != null) ? doctorName : "Unknown";

			String patientName = prescriptionService.getUserNameById(appointment.getPatientId());
			patientName = (patientName != null) ? patientName : "Unknown";

			// Print the prescription details
			System.out.printf("%-5d %-20s %-20s %-15s %-10s %-20s %-20s %-30s%n", i + 1, doctorName, patientName,
					appointment.getScheduledDate() != null ? appointment.getScheduledDate() : "N/A",
					appointment.getSlotNo() != null ? appointment.getSlotNo().name() : "N/A",
					prescription.getCreatedAt() != null ? prescription.getCreatedAt() : "N/A",
					prescription.getUpdatedAt() != null ? prescription.getUpdatedAt() : "N/A",
					prescription.getDescription() != null ? prescription.getDescription() : "N/A");
		}
	}

	public void createPrescription(User currentUser) {
		if (currentUser.getRole() != Role.DOCTOR) {
			System.out.println("Only doctors can create prescriptions.");
			return;
		}

		String doctorUuid = currentUser.getUuid();

		List<Appointment> appointments = appointmentService.getAppointmentsByDoctorId(doctorUuid);

		if (appointments.isEmpty()) {
			System.out.println("No appointments found for your UUID.");
			return;
		}

		printAppointmentTable(appointments);
		int appointmentIndex = ChoiceInputHandler.getIntChoice("Select the appointment by index:", 1,
				appointments.size());
		Appointment selectedAppointment = appointments.get(appointmentIndex - 1);

		Prescription prescription = new Prescription();
		prescription.setUuid(UUID.randomUUID().toString());
		prescription.setAppointmentId(selectedAppointment.getUuid());
		prescription
				.setDescription(PrescriptionInputHandler.getValidatedDescription("Enter prescription description:"));
		prescription.setCreatedAt(java.time.LocalDateTime.now());
		prescription.setUpdatedAt(java.time.LocalDateTime.now());

		boolean isCreated = prescriptionService.createPrescription(prescription);

		if (isCreated) {
			System.out.println("Prescription created successfully.");
		} else {
			System.out.println("Failed to create prescription.");
		}
	}

	private void printAppointmentTable(List<Appointment> appointments) {
		System.out.printf("%-5s %-20s %-20s %-12s %-10s %-20s %-20s %-15s%n", "Index", "Doctor", "Patient", "Date",
				"Slot", "Created At", "Updated At", "Status");

		for (int i = 0; i < appointments.size(); i++) {
			Appointment appointment = appointments.get(i);

			User doctor = userService.getUserById(appointment.getDoctorId());
			User patient = userService.getUserById(appointment.getPatientId());

			String doctorName = (doctor != null) ? doctor.getUsername() : "Unknown Doctor";
			String patientName = (patient != null) ? patient.getUsername() : "Unknown Patient";

			String status = (appointment.getStatus() != null) ? appointment.getStatus().name() : "Unknown";
			System.out.printf("%-5d %-20s %-20s %-12s %-10s %-20s %-20s %-15s%n", i + 1, doctorName, patientName,
					appointment.getScheduledDate() != null ? appointment.getScheduledDate() : "N/A",
					appointment.getSlotNo() != null ? appointment.getSlotNo().name() : "N/A",
					appointment.getBookingDate() != null ? appointment.getBookingDate() : "N/A",
					appointment.getStatusUpdationDate() != null ? appointment.getStatusUpdationDate() : "N/A", status);
		}
	}

	public void updatePrescription(User currentUser) {
		if (currentUser.getRole() != Role.DOCTOR && currentUser.getRole() != Role.ADMIN) {
			System.out.println("Only doctors and admins can update prescriptions.");
			return;
		}
		List<Prescription> prescriptions;
		if (currentUser.getRole() == Role.DOCTOR) {
			prescriptions = prescriptionService.getPrescriptionsByUser(currentUser.getUuid());
		} else {
			String username = UserInputHandler
					.getValidatedUsername("Enter the username of the user whose prescriptions you want to update: ");
			List<User> users = prescriptionService.findUsersByUsername(username);
			printUserTable(users);
			int index = ChoiceInputHandler.getIntChoice("Select the user by index", 1, users.size());
			String userId = users.get(index - 1).getUuid();
			prescriptions = prescriptionService.getPrescriptionsByUser(userId);
		}

		if (prescriptions.isEmpty()) {
			System.out.println("No prescriptions found for the selected user.");
			return;
		}

		printPrescriptionList(prescriptions);
		int prescriptionIndex = ChoiceInputHandler.getIntChoice("Select the prescription by index:", 1,
				prescriptions.size());
		Prescription selectedPrescription = prescriptions.get(prescriptionIndex - 1);
		selectedPrescription.setDescription(
				PrescriptionInputHandler.getValidatedDescription("Enter new prescription description:"));
		selectedPrescription.setUpdatedAt(java.time.LocalDateTime.now());

		boolean isUpdated = prescriptionService.updatePrescription(selectedPrescription);

		if (isUpdated) {
			System.out.println("Prescription updated successfully.");
		} else {
			System.out.println("Failed to update prescription.");
		}
	}

	public void deletePrescription(User currentUser) {
		if (currentUser.getRole() != Role.ADMIN) {
			System.out.println("Only admins can delete prescriptions.");
			return;
		}
		List<Prescription> prescriptions = prescriptionService.getAllPrescriptions();

		if (prescriptions.isEmpty()) {
			System.out.println("No prescriptions found.");
			return;
		}

		printPrescriptionList(prescriptions);
		int prescriptionIndex = ChoiceInputHandler.getIntChoice("Select the prescription to delete by index:", 1,
				prescriptions.size());
		Prescription selectedPrescription = prescriptions.get(prescriptionIndex - 1);

		boolean isDeleted = prescriptionService.deletePrescription(selectedPrescription.getUuid());

		if (isDeleted) {
			System.out.println("Prescription deleted successfully.");
		} else {
			System.out.println("Failed to delete prescription.");
		}
	}
}