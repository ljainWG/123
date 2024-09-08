package com.wg.dabms.a.controller;

import java.time.LocalDate;
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
import com.wg.dabms.model.enums.Role;
import com.wg.dabms.model.enums.TimeSlot;

public class AppointmentController {

    private final UserService userService;
    private final AppointmentService appointmentService;

    public AppointmentController(UserService userService, AppointmentService appointmentService) {
        this.userService = userService;
        this.appointmentService = appointmentService;
    }

    public void bookAppointment(User currentUser) {
        if (currentUser.getRole() == Role.PATIENT) {
            bookAppointmentForPatient(currentUser);
        } else if (currentUser.getRole() == Role.RECEPTIONIST) {
            bookAppointmentForReceptionist();
        } else {
            System.out.println("You do not have permission to book an appointment.");
        }
    }

    private void bookAppointmentForPatient(User patient) {
        // Get available doctors
        List<User> doctors = userService.getUsersByRole(Role.DOCTOR);
        if (doctors.isEmpty()) {
            System.out.println("No doctors available for booking.");
            return;
        }

        // Print doctor list and select doctor
        printUserTable(doctors);
        int doctorIndex = ChoiceInputHandler.getIntChoice("Select the doctor by index:", 0, doctors.size() - 1);
        User selectedDoctor = doctors.get(doctorIndex);

        // Get available time slots and date
        LocalDate date = AppointmentInputHandler.getInputDate("Enter the appointment date (YYYY-MM-DD):");
        TimeSlot slot = AppointmentInputHandler.getInputTimeSlot("Enter the time slot (MORNING/AFTERNOON/EVENING):");

        // Create appointment
        Appointment appointment = new Appointment();
        appointment.setUuid(UUID.randomUUID().toString());
        appointment.setDoctorId(selectedDoctor.getUuid());
        appointment.setPatientId(patient.getUuid());
        appointment.setScheduledDate(date);
        appointment.setSlotNo(slot);
        appointment.setStatus(AppointmentStatus.SCHEDULED);
        appointment.setBookingDate(java.time.LocalDateTime.now());

        boolean isBooked = appointmentService.createAppointment(appointment);
        if (isBooked) {
            System.out.println("Appointment booked successfully.");
        } else {
            System.out.println("Failed to book appointment.");
        }
    }

    private void bookAppointmentForReceptionist() {
        // Get patient details
        String username = UserInputHandler.getValidatedUsername("Enter the username of the patient:");
        List<User> patients = userService.findUsersByUsername(username);
        if (patients.isEmpty()) {
            System.out.println("No patients found with the given username.");
            return;
        }

        // Print patient list and select patient
        printUserTable(patients);
        int patientIndex = ChoiceInputHandler.getIntChoice("Select the patient by index:", 0, patients.size() - 1);
        User selectedPatient = patients.get(patientIndex);

        // Get available doctors
        List<User> doctors = userService.getUsersByRole(Role.DOCTOR);
        if (doctors.isEmpty()) {
            System.out.println("No doctors available for booking.");
            return;
        }

        // Print doctor list and select doctor
        printUserTable(doctors);
        int doctorIndex = ChoiceInputHandler.getIntChoice("Select the doctor by index:", 0, doctors.size() - 1);
        User selectedDoctor = doctors.get(doctorIndex);

        // Get available time slots and date
        LocalDate date = AppointmentInputHandler.getInputDate("Enter the appointment date (YYYY-MM-DD):");
        TimeSlot slot = AppointmentInputHandler.getInputTimeSlot("Enter your preferred slot");

        // Create appointment
        Appointment appointment = new Appointment();
        appointment.setUuid(UUID.randomUUID().toString());
        appointment.setDoctorId(selectedDoctor.getUuid());
        appointment.setPatientId(selectedPatient.getUuid());
        appointment.setScheduledDate(date);
        appointment.setSlotNo(slot);
        appointment.setStatus(AppointmentStatus.SCHEDULED);
        appointment.setBookingDate(java.time.LocalDateTime.now());

        boolean isBooked = appointmentService.createAppointment(appointment);
        if (isBooked) {
            System.out.println("Appointment booked successfully.");
        } else {
            System.out.println("Failed to book appointment.");
        }
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
}
