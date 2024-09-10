package com.wg.dabms.a.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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
		appointment.setStatus(AppointmentStatus.valueOf(resultSet.getString("status")));
		appointment.setScheduledDate(resultSet.getDate("scheduled_date").toLocalDate());
		appointment.setSlotNo(TimeSlot.valueOf(resultSet.getString("slot_no")));
		appointment.setBookingDate(resultSet.getTimestamp("booking_date").toLocalDateTime());
		appointment.setStatusUpdationDate(resultSet.getTimestamp("status_updation_date").toLocalDateTime());
		return appointment;
	}

	public Appointment getAppointmentById(String uuid) {
		String query = "SELECT * FROM appointment WHERE uuid = ?";
		return executeGetQuery(query, uuid);
	}

	public List<Appointment> getAllAppointments() {
		String query = "SELECT * FROM appointment";
		return executeGetAllQuery(query);
	}

	public boolean createAppointment(Appointment appointment) {
		String query = "INSERT INTO appointment (uuid, doctor_id, patient_id, status, scheduled_date, slot_no, booking_date, status_updation_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		return executeUpdate(query, appointment.getUuid(), appointment.getDoctorId(), appointment.getPatientId(),
				appointment.getStatus().name(), appointment.getScheduledDate(), appointment.getSlotNo().name(),
				appointment.getBookingDate(), appointment.getStatusUpdationDate());
	}

	public boolean updateAppointment(Appointment appointment) {
		String query = "UPDATE appointment SET doctor_id = ?, patient_id = ?, status = ?, scheduled_date = ?, slot_no = ?, booking_date = ?, status_updation_date = ? WHERE uuid = ?";
		return executeUpdate(query, appointment.getDoctorId(), appointment.getPatientId(),
				appointment.getStatus().name(), appointment.getScheduledDate(), appointment.getSlotNo().name(),
				appointment.getBookingDate(), appointment.getStatusUpdationDate(), appointment.getUuid());
	}

	public boolean deleteAppointment(String uuid) {
		String query = "DELETE FROM appointment WHERE uuid = ?";
		return executeDelete(query, uuid);
	}

	public List<Appointment> findAppointmentsByPatientId(String patientId) {
		String query = "SELECT * FROM appointment WHERE patient_id = ?";
		return executeGetAllQuery(query, patientId);
	}

	public List<Appointment> findAppointmentsByDoctorId(String doctorId) {
		String query = "SELECT * FROM appointment WHERE doctor_id = ?";
		return executeGetAllQuery(query, doctorId);
	}

	public boolean updateAppointmentStatus(String appointmentId, AppointmentStatus newStatus) {
		String query = "UPDATE appointment SET status = ? WHERE uuid = ?";
		return executeUpdate(query, newStatus.name(), appointmentId);
	}

	public boolean isSlotAvailable(String doctorId, LocalDate date, TimeSlot slot) {
		String query = "SELECT COUNT(*) FROM appointment WHERE doctorId = ? AND scheduledDate = ? AND slotNo = ? AND status = ?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setString(1, doctorId);
			preparedStatement.setDate(2, java.sql.Date.valueOf(date));
			preparedStatement.setString(3, slot.name());
			preparedStatement.setString(4, AppointmentStatus.SCHEDULED.name());
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return resultSet.getInt(1) == 0;
			}
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "SQL error occurred while checking slot availability", e);
		}
		return false;
	}
	
    public List<Appointment> getAppointmentsByDoctorIdAndStatus(String doctorId, AppointmentStatus status) {
        String query = "SELECT * FROM appointment WHERE doctor_id = ?" + 
                       (status != null ? " AND status = ?" : "");
        return executeGetAllQuery(query, status != null ? new Object[]{doctorId, status.name()} : new Object[]{doctorId});
    }

    public List<Appointment> getAppointmentsByPatientIdAndStatus(String patientId, AppointmentStatus status) {
        String query = "SELECT * FROM appointment WHERE patient_id = ?" + 
                       (status != null ? " AND status = ?" : "");
        return executeGetAllQuery(query, status != null ? new Object[]{patientId, status.name()} : new Object[]{patientId});
    }

    public List<Appointment> getAppointmentsByStatus(AppointmentStatus status) {
        String query = "SELECT * FROM appointment" + 
                       (status != null ? " WHERE status = ?" : "");
        return executeGetAllQuery(query, status != null ? new Object[]{status.name()} : new Object[]{});
    }

}
