package com.wg.dabms.service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.wg.dabms.dao.AppointmentDAO;
import com.wg.dabms.model.Appointment;
import com.wg.dabms.model.enums.AppointmentStatus;

public class AppointmentService {

    private static final Logger LOGGER = Logger.getLogger(AppointmentService.class.getName());
    private AppointmentDAO appointmentDAO = new AppointmentDAO();

    public AppointmentService() {
        LOGGER.info("AppointmentService instantiated.");
    }

    public Appointment getAppointmentById(String uuid) {
        LOGGER.log(Level.INFO, "Fetching appointment by ID: {0}", uuid);
        return appointmentDAO.getAppointmentById(uuid);
    }

    public List<Appointment> getAllAppointments() {
        LOGGER.info("Fetching all appointments.");
        return appointmentDAO.getAllAppointments();
    }

    public boolean createAppointment(Appointment appointment) {
        LOGGER.log(Level.INFO, "Creating appointment: {0}", appointment);
        boolean result = appointmentDAO.createAppointment(appointment);
        LOGGER.log(Level.INFO, "Appointment creation successful: {0}", result);
        return result;
    }

    public boolean updateAppointment(Appointment appointment) {
        LOGGER.log(Level.INFO, "Updating appointment: {0}", appointment);
        boolean result = appointmentDAO.updateAppointment(appointment);
        LOGGER.log(Level.INFO, "Appointment update successful: {0}", result);
        return result;
    }

    public boolean deleteAppointment(String uuid) {
        LOGGER.log(Level.INFO, "Deleting appointment with ID: {0}", uuid);
        boolean result = appointmentDAO.deleteAppointment(uuid);
        LOGGER.log(Level.INFO, "Appointment deletion successful: {0}", result);
        return result;
    }

    public List<Appointment> getAppointmentsByPatientId(String patientId) {
        LOGGER.log(Level.INFO, "Fetching appointments by patient ID: {0}", patientId);
        return appointmentDAO.findAppointmentsByPatientId(patientId);
    }

    public List<Appointment> getAppointmentsByDoctorId(String doctorId) {
        LOGGER.log(Level.INFO, "Fetching appointments by doctor ID: {0}", doctorId);
        return appointmentDAO.findAppointmentsByDoctorId(doctorId);
    }

    public boolean updateAppointmentStatus(String appointmentId, AppointmentStatus newStatus) {
        LOGGER.log(Level.INFO, "Updating appointment status for ID: {0} to status: {1}", new Object[]{appointmentId, newStatus});
        boolean result = appointmentDAO.updateAppointmentStatus(appointmentId, newStatus);
        LOGGER.log(Level.INFO, "Appointment status update successful: {0}", result);
        return result;
    }

    public List<Appointment> getAppointmentsByDoctorIdAndStatus(String doctorId, AppointmentStatus status) {
        LOGGER.log(Level.INFO, "Fetching appointments by doctor ID: {0} and status: {1}", new Object[]{doctorId, status});
        return appointmentDAO.getAppointmentsByDoctorIdAndStatus(doctorId, status);
    }

    public List<Appointment> getAppointmentsByPatientIdAndStatus(String patientId, AppointmentStatus status) {
        LOGGER.log(Level.INFO, "Fetching appointments by patient ID: {0} and status: {1}", new Object[]{patientId, status});
        return appointmentDAO.getAppointmentsByPatientIdAndStatus(patientId, status);
    }

    public List<Appointment> getAppointmentsByStatus(AppointmentStatus status) {
        LOGGER.log(Level.INFO, "Fetching appointments by status: {0}", status);
        return appointmentDAO.getAppointmentsByStatus(status);
    }
}
