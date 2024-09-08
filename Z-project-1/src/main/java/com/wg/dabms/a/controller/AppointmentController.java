package com.wg.dabms.a.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.UUID;

import com.wg.dabms.a.service.AppointmentService;
import com.wg.dabms.a.service.UserService;
import com.wg.dabms.input.choice.ChoiceInputHandler;
import com.wg.dabms.input.handler.AppointmentInputHandler;
import com.wg.dabms.input.handler.UserInputHandler;
import com.wg.dabms.model.Appointment;
import com.wg.dabms.model.User;
import com.wg.dabms.model.enums.AppointmentStatus;
import com.wg.dabms.model.enums.Department;
import com.wg.dabms.model.enums.Role;
import com.wg.dabms.model.enums.TimeSlot;

public class AppointmentController {

	private final UserService userService;
	private final AppointmentService appointmentService;

	public AppointmentController() {
		this.userService = new UserService();
		this.appointmentService = new AppointmentService();
	}

	public void bookAppointment(User currentUser) {
		// Step 1: Ensure the user is either a patient, receptionist, or admin
		if (currentUser.getRole() != Role.PATIENT && currentUser.getRole() != Role.RECEPTIONIST
				&& currentUser.getRole() != Role.ADMIN) {
			System.out.println("Only patients, receptionists, and admins can book appointments.");
			return;
		}

		// Step 2: Get department and list of doctors in that department
		Department department = UserInputHandler.getInputDepartment("Enter the department you want to search for");
		List<User> doctors = userService.getUsersByDepartment(department);

		if (doctors.isEmpty()) {
			System.out.println("No doctors found in this department.");
			return;
		}

		// Step 3: Print the list of doctors and select one
		printDoctorTable(doctors);
		int doctorIndex = ChoiceInputHandler.getIntChoice("Select a doctor by index:", 1, doctors.size());
		User selectedDoctor = doctors.get(doctorIndex - 1);

		// Step 4: Get the patient UUID (Receptionist/Admin can book for another
		// patient)
		String patientUuid;
		if (currentUser.getRole() == Role.PATIENT) {
			patientUuid = currentUser.getUuid();
		} else {
			// Receptionist/Admin needs to choose a patient by username
			String username = UserInputHandler.getValidatedUsername("Enter the username of the patient:");
			List<User> patients = userService.findUsersByUsername(username);
			if (patients.isEmpty()) {
				System.out.println("No patients found with that username.");
				return;
			}
			printUserTable(patients);
			int patientIndex = ChoiceInputHandler.getIntChoice("Select a patient by index:", 1, patients.size());
			patientUuid = patients.get(patientIndex - 1).getUuid();
		}

		// Step 5: Get appointment details (Date and TimeSlot)
		LocalDate scheduledDate = null;
		try {
			scheduledDate = AppointmentInputHandler.getInputDate("Enter the appointment date (YYYY-MM-DD):");
		} catch (DateTimeParseException e) {
			System.out.println("Invalid date format. Appointment not booked.");
			return;
		}

		TimeSlot timeSlot = AppointmentInputHandler.getInputTimeSlot("Enter your preferred slot");

		// Step 6: Create the appointment object
		Appointment appointment = new Appointment();
		appointment.setUuid(UUID.randomUUID().toString());
		appointment.setDoctorId(selectedDoctor.getUuid());
		appointment.setPatientId(patientUuid);
		appointment.setScheduledDate(scheduledDate);
		appointment.setSlotNo(timeSlot);
		appointment.setBookingDate(LocalDateTime.now());
		appointment.setStatus(AppointmentStatus.SCHEDULED);
		appointment.setStatusUpdationDate(LocalDateTime.now());

		// Step 7: Book the appointment using the AppointmentService
		boolean isBooked = appointmentService.createAppointment(appointment);

		if (isBooked) {
			System.out.println("Appointment booked successfully.");
		} else {
			System.out.println("Failed to book the appointment.");
		}
	}

	private void printDoctorTable(List<User> doctors) {
		System.out.printf("%-5s %-20s %-20s %-10s %-15s %-15s%n", "Index", "Doctor Name", "Department", "Role",
				"Experience", "Avg Rating");
		for (int i = 0; i < doctors.size(); i++) {
			User doctor = doctors.get(i);
			System.out.printf("%-5d %-20s %-20s %-10s %-15s %-15s%n", i + 1, doctor.getUsername(),
					doctor.getDepartment(), doctor.getRole(),
					doctor.getExperience() != null ? doctor.getExperience() : "N/A",
					doctor.getAvgRating() != null ? doctor.getAvgRating() : "N/A");
		}
	}

	private void printUserTable(List<User> users) {
		System.out.printf("%-5s %-20s %-15s %-15s %-15s %-15s%n", "Index", "Username", "Role", "DOB", "Phone No",
				"Department");
		for (int i = 0; i < users.size(); i++) {
			User user = users.get(i);
			System.out.printf("%-5d %-20s %-15s %-15s %-15s %-15s%n", i + 1, user.getUsername(), user.getRole(),
					user.getDob(), user.getPhoneNo(), user.getDepartment());
		}
	}

	public void updateAppointmentStatus(User currentUser) {
		List<Appointment> appointments;

		// If user is a patient or doctor, only show appointments they are involved in.
		if (currentUser.getRole() == Role.PATIENT) {
			appointments = appointmentService.getAppointmentsByPatientId(currentUser.getUuid());
		} else if (currentUser.getRole() == Role.DOCTOR) {
			appointments = appointmentService.getAppointmentsByDoctorId(currentUser.getUuid());
		} else {
			// If user is receptionist or admin, prompt to search by patient or doctor
			System.out.println("Would you like to search by:");
			System.out.println("1. Patient name");
			System.out.println("2. Doctor name");
			int searchChoice = ChoiceInputHandler.getIntChoice("Enter your choice:", 1, 2);

			if (searchChoice == 1) {
				String patientUsername = UserInputHandler.getValidatedUsername("Enter the patient's username: ");
				List<User> patients = userService.findUsersByUsername(patientUsername);
				if (patients.isEmpty()) {
					System.out.println("No patients found with that username.");
					return;
				}
				printUserTable(patients);
				int patientIndex = ChoiceInputHandler.getIntChoice("Select the patient by index:", 1, patients.size());
				String patientId = patients.get(patientIndex - 1).getUuid();
				appointments = appointmentService.getAppointmentsByPatientId(patientId);
			} else {
				String doctorUsername = UserInputHandler.getValidatedUsername("Enter the doctor's username: ");
				List<User> doctors = userService.findUsersByUsername(doctorUsername);
				if (doctors.isEmpty()) {
					System.out.println("No doctors found with that username.");
					return;
				}
				printUserTable(doctors);
				int doctorIndex = ChoiceInputHandler.getIntChoice("Select the doctor by index:", 1, doctors.size());
				String doctorId = doctors.get(doctorIndex - 1).getUuid();
				appointments = appointmentService.getAppointmentsByDoctorId(doctorId);
			}
		}

		if (appointments.isEmpty()) {
			System.out.println("No appointments found.");
			return;
		}

		// Print available appointments
		printAppointmentTable(appointments);
		int appointmentIndex = ChoiceInputHandler.getIntChoice("Select the appointment to update status:", 1,
				appointments.size());
		Appointment selectedAppointment = appointments.get(appointmentIndex - 1);

		// Select new status
		System.out.println("Select the new status:");
		System.out.println("1. COMPLETED");
		System.out.println("2. CANCELED");
		System.out.println("3. NO_SHOW_PATIENT");
		System.out.println("4. NO_SHOW_DOCTOR");
		int statusChoice = ChoiceInputHandler.getIntChoice("Enter your choice:", 1, 4);
		AppointmentStatus newStatus = null;

		switch (statusChoice) {
		case 1:
			newStatus = AppointmentStatus.COMPLETED;
			break;
		case 2:
			newStatus = AppointmentStatus.CANCELED;
			break;
		case 3:
			newStatus = AppointmentStatus.NO_SHOW_PATIENT;
			break;
		case 4:
			newStatus = AppointmentStatus.NO_SHOW_DOCTOR;
			break;
		}

		boolean isUpdated = appointmentService.updateAppointmentStatus(selectedAppointment.getUuid(), newStatus);

		if (isUpdated) {
			System.out.println("Appointment status updated successfully.");
		} else {
			System.out.println("Failed to update appointment status.");
		}
	}

	private void printAppointmentTable(List<Appointment> appointments) {
		System.out.printf("%-5s %-20s %-20s %-12s %-10s %-20s %-20s%n", "Index", "Doctor", "Patient", "Date", "Slot",
				"Created At", "Updated At");
		for (int i = 0; i < appointments.size(); i++) {
			Appointment appointment = appointments.get(i);
			String doctorName = userService.getUserById(appointment.getDoctorId()).getUsername();
			String patientName = userService.getUserById(appointment.getPatientId()).getUsername();

			System.out.printf("%-5d %-20s %-20s %-12s %-10s %-20s %-20s%n", i + 1, doctorName, patientName,
					appointment.getScheduledDate(), appointment.getSlotNo(), appointment.getBookingDate(),
					appointment.getStatusUpdationDate());
		}

	}

	public void deleteAppointment(User currentUser) {
		if (currentUser.getRole() != Role.ADMIN) {
			System.out.println("Only admins can delete appointments.");
			return;
		}

		String searchType = ChoiceInputHandler.getStringChoice("Search appointments by", "Doctor Name", "Patient Name");
		String searchName = UserInputHandler.getValidatedUsername("Enter the name to search for: ");

		List<User> users = searchUsers(searchType, searchName);

		if (users.isEmpty()) {
			System.out.println("No users found with the provided name.");
			return;
		}

		// Print the list of users for selection
		printUserTable(users);
		int userIndex = ChoiceInputHandler.getIntChoice("Select the user by index:", 0, users.size() - 1);
		String selectedUserId = users.get(userIndex).getUuid();

		List<Appointment> appointments = getAppointments(searchType, selectedUserId);

		if (appointments.isEmpty()) {
			System.out.println("No appointments found for the selected user.");
			return;
		}

		// Print appointments for selection
		printAppointmentTable(appointments);
		int appointmentIndex = ChoiceInputHandler.getIntChoice("Select the appointment by index:", 0,
				appointments.size() - 1);
		String appointmentId = appointments.get(appointmentIndex).getUuid();

		// Confirm deletion
		boolean confirm = ChoiceInputHandler
				.getBooleanChoice("Are you sure you want to delete this appointment? (yes/no): ");

		if (confirm) {
			boolean isDeleted = appointmentService.deleteAppointment(appointmentId);
			System.out.println(isDeleted ? "Appointment deleted successfully." : "Failed to delete appointment.");
		} else {
			System.out.println("Appointment deletion canceled.");
		}
	}

	private List<User> searchUsers(String searchType, String searchName) {
		if (searchType.equals("Doctor Name")) {
			return userService.findUsersByUsernameAndRole(searchName, Role.DOCTOR);
		} else {
			return userService.findUsersByUsernameAndRole(searchName, Role.PATIENT);
		}
	}

	private List<Appointment> getAppointments(String searchType, String userId) {
		if (searchType.equals("Doctor Name")) {
			return appointmentService.getAppointmentsByDoctorId(userId);
		} else {
			return appointmentService.getAppointmentsByPatientId(userId);
		}
	}
	

    public void viewAppointments(User currentUser) {
        List<Appointment> appointments;

        if (currentUser.getRole() == Role.PATIENT || currentUser.getRole() == Role.DOCTOR) {
            // For Patient and Doctor: Show only appointments where the user is either patient or doctor
            appointments = getAppointmentsByRoleAndStatus(currentUser);
        } else {
            // For Receptionist and Admin: Show appointments based on various criteria
            String searchOption = ChoiceInputHandler.getStringChoice("Choose search criteria", "By Status", "By User", "All");

            if (searchOption.equalsIgnoreCase("By Status")) {
                appointments = getAppointmentsByStatus();
            } else if (searchOption.equalsIgnoreCase("By User")) {
                appointments = getAppointmentsByUser();
            } else {
                appointments = appointmentService.getAllAppointments(); // Method to get all appointments
            }
        }

        if (appointments.isEmpty()) {
            System.out.println("No appointments found.");
        } else {
            printAppointmentTable(appointments);
        }
    }

    private List<Appointment> getAppointmentsByRoleAndStatus(User currentUser) {
        String statusInput = ChoiceInputHandler.getStringChoice("Select appointment status", "Scheduled", "Cancelled", "Completed", "All");
        AppointmentStatus status = statusInput.equalsIgnoreCase("All") ? null : AppointmentStatus.valueOf(statusInput.toUpperCase());

        if (currentUser.getRole() == Role.PATIENT) {
            return status == null ? appointmentService.getAppointmentsByPatientIdAndStatus(currentUser.getUuid(), null) : appointmentService.getAppointmentsByPatientIdAndStatus(currentUser.getUuid(), status);
        } else {
            return status == null ? appointmentService.getAppointmentsByDoctorIdAndStatus(currentUser.getUuid(), null) : appointmentService.getAppointmentsByDoctorIdAndStatus(currentUser.getUuid(), status);
        }
    }

    private List<Appointment> getAppointmentsByStatus() {
        String statusInput = ChoiceInputHandler.getStringChoice("Select appointment status", "Scheduled", "Cancelled", "Completed", "All");
        AppointmentStatus status = statusInput.equalsIgnoreCase("All") ? null : AppointmentStatus.valueOf(statusInput.toUpperCase());

        return status == null ? appointmentService.getAllAppointments() : appointmentService.getAppointmentsByStatus(status);
    }

    private List<Appointment> getAppointmentsByUser() {
        String searchType = ChoiceInputHandler.getStringChoice("Search appointments by", "Doctor Name", "Patient Name");
        String searchName = UserInputHandler.getValidatedUsername("Enter the name to search for: ");
        List<User> users = userService.findUsersByUsernameAndRole(searchName, searchType.equalsIgnoreCase("Doctor Name") ? Role.DOCTOR : Role.PATIENT);

        if (users.isEmpty()) {
            System.out.println("No users found with the provided name.");
            return List.of();
        }

        printUserTable(users);
        int userIndex = ChoiceInputHandler.getIntChoice("Select the user by index:", 0, users.size() - 1);
        String selectedUserId = users.get(userIndex).getUuid();
        String statusInput = ChoiceInputHandler.getStringChoice("Select appointment status", "Scheduled", "Cancelled", "Completed", "All");
        AppointmentStatus status = statusInput.equalsIgnoreCase("All") ? null : AppointmentStatus.valueOf(statusInput.toUpperCase());

        return searchType.equalsIgnoreCase("Doctor Name") ? (status == null ? appointmentService.getAppointmentsByDoctorIdAndStatus(selectedUserId, null) : appointmentService.getAppointmentsByDoctorIdAndStatus(selectedUserId, status)) : (status == null ? appointmentService.getAppointmentsByPatientIdAndStatus(selectedUserId, null) : appointmentService.getAppointmentsByPatientIdAndStatus(selectedUserId, status));
    }}