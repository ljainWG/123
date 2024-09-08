package com.wg.dabms.a.controller;

import java.util.List;
import java.util.UUID;

import com.wg.dabms.a.service.AppointmentService;
import com.wg.dabms.a.service.PrescriptionService;
import com.wg.dabms.a.service.UserService;
import com.wg.dabms.input.choice.ChoiceInputHandler;
import com.wg.dabms.input.handler.PrescriptionInputHandler;
import com.wg.dabms.input.handler.UserInputHandler;
import com.wg.dabms.model.Appointment;
import com.wg.dabms.model.Prescription;
import com.wg.dabms.model.User;
import com.wg.dabms.model.enums.Role;

public class PrescriptionController {

	private final UserService userService;
    private final AppointmentService appointmentService;
    private final PrescriptionService prescriptionService;

    public PrescriptionController(UserService userService, AppointmentService appointmentService, PrescriptionService prescriptionService) {
        this.userService = userService;
        this.appointmentService = appointmentService;
        this.prescriptionService = prescriptionService;
    }

    public void listPrescriptions(User currentUser) {
        List<Prescription> prescriptions;
        if (currentUser.getRole() == Role.PATIENT || currentUser.getRole() == Role.DOCTOR) {
            prescriptions = prescriptionService.getPrescriptionsByUser(currentUser.getUuid());
        } else {
            String username = UserInputHandler.getValidatedUsername("Enter the username of the user whose prescriptions you want to view: ");
            List<User> users = prescriptionService.findUsersByUsername(username);
            printUserTable(users);
            int index = ChoiceInputHandler.getIntChoice("Select the user by index", 1, users.size());
            String userId = users.get(index-1).getUuid();
            prescriptions = prescriptionService.getPrescriptionsByUser(userId);
        }

        printPrescriptionList(prescriptions);
    }

    private void printUserTable(List<User> users) {
        System.out.printf("%-5s %-20s %-30s %-15s %-15s %-15s%n", "Index", "Username", "Role", "Department", "DOB", "Mobile No");
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            System.out.printf("%-5d %-20s %-30s %-15s %-15s %-15s%n",
                i+1,
                user.getUsername(),
                user.getRole(),
                user.getDepartment() != null ? user.getDepartment() : "N/A",
                user.getDob() != null ? user.getDob() : "N/A",
                user.getPhoneNo() != null ? user.getPhoneNo() : "N/A"
            );
        }
    }

    private void printPrescriptionList(List<Prescription> prescriptions) {
        System.out.printf("%-5s %-20s %-20s %-15s %-10s %-20s %-20s%n", "Index", "Doctor", "Patient", "Appointment Date", "Slot", "Creation Date", "Updation Date");
        for (int i = 0; i < prescriptions.size(); i++) {
            Prescription prescription = prescriptions.get(i);
            Appointment appointment = appointmentService.getAppointmentById(prescription.getAppointmentId());
            String doctorName = prescriptionService.getUserNameById(appointment.getDoctorId());
            String patientName = prescriptionService.getUserNameById(appointment.getPatientId());

            System.out.printf("%-5d %-20s %-20s %-15s %-10s %-20s %-20s%n",
                i+1,
                doctorName,
                patientName,
                appointment.getScheduledDate(),
                appointment.getSlotNo(),
                prescription.getCreatedAt(),
                prescription.getUpdatedAt()
            );
        }
    }
    public void createPrescription(User currentUser) {
        if (currentUser.getRole() != Role.DOCTOR) {
            System.out.println("Only doctors can create prescriptions.");
            return;
        }

        // Doctor searches for appointments by their own UUID
        String doctorUuid = currentUser.getUuid();

        List<Appointment> appointments = appointmentService.getAppointmentsByDoctorId(doctorUuid);

        if (appointments.isEmpty()) {
            System.out.println("No appointments found for your UUID.");
            return;
        }

        // Print appointments for selection
        printAppointmentTable(appointments);
        int appointmentIndex = ChoiceInputHandler.getIntChoice("Select the appointment by index:", 1, appointments.size());
        Appointment selectedAppointment = appointments.get(appointmentIndex-1);

        // Create prescription
        Prescription prescription = new Prescription();
        prescription.setUuid(UUID.randomUUID().toString());
        prescription.setAppointmentId(selectedAppointment.getUuid());
        prescription.setDescription(PrescriptionInputHandler.getValidatedDescription("Enter prescription description:"));
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
        System.out.printf("%-5s %-20s %-20s %-12s %-10s %-20s %-20s%n", "Index", "Doctor", "Patient", "Date", "Slot", "Created At", "Updated At");
        for (int i = 0; i < appointments.size(); i++) {
            Appointment appointment = appointments.get(i);
            String doctorName = userService.getUserById(appointment.getDoctorId()).getUsername();
            String patientName = userService.getUserById(appointment.getPatientId()).getUsername();

            System.out.printf("%-5d %-20s %-20s %-12s %-10s %-20s %-20s%n",
                    i + 1,
                    doctorName,
                    patientName,
                    appointment.getScheduledDate(),
                    appointment.getSlotNo(),
                    appointment.getBookingDate(),
                    appointment.getStatusUpdationDate());
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
            String username = UserInputHandler.getValidatedUsername("Enter the username of the user whose prescriptions you want to update: ");
            List<User> users = prescriptionService.findUsersByUsername(username);
            printUserTable(users);
            int index = ChoiceInputHandler.getIntChoice("Select the user by index", 1, users.size());
            String userId = users.get(index-1).getUuid();
            prescriptions = prescriptionService.getPrescriptionsByUser(userId);
        }

        if (prescriptions.isEmpty()) {
            System.out.println("No prescriptions found for the selected user.");
            return;
        }

        // Print prescriptions for selection
        printPrescriptionList(prescriptions);
        int prescriptionIndex = ChoiceInputHandler.getIntChoice("Select the prescription by index:", 1, prescriptions.size());
        Prescription selectedPrescription = prescriptions.get(prescriptionIndex-1);

        // Update prescription
        selectedPrescription.setDescription(PrescriptionInputHandler.getValidatedDescription("Enter new prescription description:"));
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

        // Retrieve prescriptions
        List<Prescription> prescriptions = prescriptionService.getAllPrescriptions();

        if (prescriptions.isEmpty()) {
            System.out.println("No prescriptions found.");
            return;
        }

        // Print prescriptions for selection
        printPrescriptionList(prescriptions);
        int prescriptionIndex = ChoiceInputHandler.getIntChoice("Select the prescription to delete by index:", 1, prescriptions.size());
        Prescription selectedPrescription = prescriptions.get(prescriptionIndex-1);

        // Delete prescription
        boolean isDeleted = prescriptionService.deletePrescription(selectedPrescription.getUuid());

        if (isDeleted) {
            System.out.println("Prescription deleted successfully.");
        } else {
            System.out.println("Failed to delete prescription.");
        }
    }
}