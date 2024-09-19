package com.wg.dabms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.wg.dabms.model.Appointment;
import com.wg.dabms.model.enums.AppointmentStatus;
import com.wg.dabms.model.enums.TimeSlot;

public class AppointmentDAO extends GenericDAO<Appointment> {

    private static final Logger LOGGER = Logger.getLogger(AppointmentDAO.class.getName());

    @Override
    protected Appointment mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        Appointment appointment = new Appointment();
        appointment.setUuid(resultSet.getString("uuid"));
        appointment.setDoctorId(resultSet.getString("doctor_id"));
        appointment.setPatientId(resultSet.getString("patient_id"));

        String statusString = resultSet.getString("status");
        if (statusString != null && !statusString.isEmpty()) {
            try {
                appointment.setStatus(AppointmentStatus.valueOf(statusString));
            } catch (IllegalArgumentException e) {
                appointment.setStatus(null);
                LOGGER.log(Level.WARNING, "Invalid status value: {0}", statusString);
            }
        } else {
            appointment.setStatus(null);
        }

        java.sql.Date scheduledDate = resultSet.getDate("scheduled_date");
        appointment.setScheduledDate(scheduledDate != null ? scheduledDate.toLocalDate() : null);

        String slotString = resultSet.getString("slot_no");
        if (slotString != null && !slotString.isEmpty()) {
            try {
                appointment.setSlotNo(TimeSlot.valueOf(slotString));
            } catch (IllegalArgumentException e) {
                appointment.setSlotNo(null);
                LOGGER.log(Level.WARNING, "Invalid slot number value: {0}", slotString);
            }
        } else {
            appointment.setSlotNo(null);
        }

        // Handle Booking Date
        java.sql.Timestamp bookingDate = resultSet.getTimestamp("booking_date");
        appointment.setBookingDate(bookingDate != null ? bookingDate.toLocalDateTime() : null);

        // Handle Status Updation Date
        java.sql.Timestamp statusUpdationDate = resultSet.getTimestamp("status_updation_date");
        appointment.setStatusUpdationDate(statusUpdationDate != null ? statusUpdationDate.toLocalDateTime() : null);

        LOGGER.log(Level.INFO, "Mapped ResultSet to Appointment entity: {0}", appointment);
        return appointment;
    }

    public Appointment getAppointmentById(String uuid) {
        LOGGER.log(Level.INFO, "Getting appointment by ID: {0}", uuid);
        String query = "SELECT * FROM appointment WHERE uuid = ?";
        return executeGetQuery(query, uuid);
    }

    public List<Appointment> getAllAppointments() {
        LOGGER.info("Getting all appointments");
        String query = "SELECT * FROM appointment";
        return executeGetAllQuery(query);
    }

    public boolean createAppointment(Appointment appointment) {
        LOGGER.log(Level.INFO, "Creating appointment: {0}", appointment);
        String query = "INSERT INTO appointment (uuid, doctor_id, patient_id, status, scheduled_date, slot_no, booking_date, status_updation_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        boolean result = executeUpdate(query, appointment.getUuid(), appointment.getDoctorId(), appointment.getPatientId(),
                appointment.getStatus() != null ? appointment.getStatus().name() : null,
                appointment.getScheduledDate(), 
                appointment.getSlotNo() != null ? appointment.getSlotNo().name() : null,
                appointment.getBookingDate(), 
                appointment.getStatusUpdationDate());
        LOGGER.log(Level.INFO, "Appointment creation successful: {0}", result);
        return result;
    }

    public boolean updateAppointment(Appointment appointment) {
        LOGGER.log(Level.INFO, "Updating appointment: {0}", appointment);
        String query = "UPDATE appointment SET doctor_id = ?, patient_id = ?, status = ?, scheduled_date = ?, slot_no = ?, booking_date = ?, status_updation_date = ? WHERE uuid = ?";
        boolean result = executeUpdate(query, appointment.getDoctorId(), appointment.getPatientId(),
                appointment.getStatus() != null ? appointment.getStatus().name() : null,
                appointment.getScheduledDate(), 
                appointment.getSlotNo() != null ? appointment.getSlotNo().name() : null,
                appointment.getBookingDate(), 
                appointment.getStatusUpdationDate(), 
                appointment.getUuid());
        LOGGER.log(Level.INFO, "Appointment update successful: {0}", result);
        return result;
    }

    public boolean deleteAppointment(String uuid) {
        LOGGER.log(Level.INFO, "Deleting appointment with ID: {0}", uuid);
        String query = "DELETE FROM appointment WHERE uuid = ?";
        boolean result = executeDelete(query, uuid);
        LOGGER.log(Level.INFO, "Appointment deletion successful: {0}", result);
        return result;
    }

    public List<Appointment> findAppointmentsByPatientId(String patientId) {
        LOGGER.log(Level.INFO, "Finding appointments by patient ID: {0}", patientId);
        String query = "SELECT * FROM appointment WHERE patient_id = ?";
        return executeGetAllQuery(query, patientId);
    }

    public List<Appointment> findAppointmentsByDoctorId(String doctorId) {
        LOGGER.log(Level.INFO, "Finding appointments by doctor ID: {0}", doctorId);
        String query = "SELECT * FROM appointment WHERE doctor_id = ?";
        return executeGetAllQuery(query, doctorId);
    }

    public boolean updateAppointmentStatus(String appointmentId, AppointmentStatus newStatus) {
        LOGGER.log(Level.INFO, "Updating appointment status for ID: {0} to status: {1}", new Object[]{appointmentId, newStatus});
        String query = "UPDATE appointment SET status = ? WHERE uuid = ?";
        boolean result = executeUpdate(query, newStatus.name(), appointmentId);
        LOGGER.log(Level.INFO, "Appointment status update successful: {0}", result);
        return result;
    }

    public List<Appointment> getAppointmentsByDoctorIdAndStatus(String doctorId, AppointmentStatus status) {
        LOGGER.log(Level.INFO, "Getting appointments by doctor ID: {0} and status: {1}", new Object[]{doctorId, status});
        String query = "SELECT * FROM appointment WHERE doctor_id = ?" + 
                       (status != null ? " AND status = ?" : "");
        return executeGetAllQuery(query, status != null ? new Object[]{doctorId, status.name()} : new Object[]{doctorId});
    }

    public List<Appointment> getAppointmentsByPatientIdAndStatus(String patientId, AppointmentStatus status) {
        LOGGER.log(Level.INFO, "Getting appointments by patient ID: {0} and status: {1}", new Object[]{patientId, status});
        String query = "SELECT * FROM appointment WHERE patient_id = ?" + 
                       (status != null ? " AND status = ?" : "");
        return executeGetAllQuery(query, status != null ? new Object[]{patientId, status.name()} : new Object[]{patientId});
    }

    public List<Appointment> getAppointmentsByStatus(AppointmentStatus status) {
        LOGGER.log(Level.INFO, "Getting appointments by status: {0}", status);
        String query = "SELECT * FROM appointment" + 
                       (status != null ? " WHERE status = ?" : "");
        return executeGetAllQuery(query, status != null ? new Object[]{status.name()} : new Object[]{});
    }
}
