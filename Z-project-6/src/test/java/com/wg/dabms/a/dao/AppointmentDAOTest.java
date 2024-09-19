package com.wg.dabms.a.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.wg.dabms.dao.AppointmentDAO;
import com.wg.dabms.model.Appointment;
import com.wg.dabms.model.enums.AppointmentStatus;
import com.wg.dabms.model.enums.TimeSlot;

public class AppointmentDAOTest {

    @InjectMocks
    private AppointmentDAO appointmentDAO;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    @BeforeEach
    public void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
    }

    @Test
    public void testCreateAppointment() throws SQLException {
        Appointment appointment = new Appointment();
        appointment.setUuid("1");
        appointment.setDoctorId("doctor1");
        appointment.setPatientId("patient1");
        appointment.setStatus(AppointmentStatus.SCHEDULED);
        appointment.setScheduledDate(LocalDate.now());
        appointment.setSlotNo(TimeSlot.SLOT_1030AM_11AM);
        appointment.setBookingDate(LocalDateTime.now());
        appointment.setStatusUpdationDate(LocalDateTime.now());

        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean result = appointmentDAO.createAppointment(appointment);

        assertTrue(result);
        verify(preparedStatement).executeUpdate();
    }

    @Test
    public void testGetAppointmentById() throws SQLException {
        // Create an Appointment instance with expected values
        Appointment appointment = new Appointment();
        appointment.setUuid("1");
        appointment.setDoctorId("doctor1");
        appointment.setPatientId("patient1");
        appointment.setStatus(AppointmentStatus.SCHEDULED); // Expected status
        appointment.setScheduledDate(LocalDate.now());
        appointment.setSlotNo(TimeSlot.SLOT_1030AM_11AM);
        appointment.setBookingDate(LocalDateTime.now());
        appointment.setStatusUpdationDate(LocalDateTime.now());

        // Mock the ResultSet behavior
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getString("uuid")).thenReturn("1");
        when(resultSet.getString("doctor_id")).thenReturn("doctor1");
        when(resultSet.getString("patient_id")).thenReturn("patient1");
        when(resultSet.getString("status")).thenReturn("SCHEDULED"); // Return the expected status
        when(resultSet.getDate("scheduled_date")).thenReturn(java.sql.Date.valueOf(LocalDate.now()));
        when(resultSet.getString("slot_no")).thenReturn("SLOT_1030AM_11AM"); // Ensure this matches TimeSlot enum
        when(resultSet.getTimestamp("booking_date")).thenReturn(java.sql.Timestamp.valueOf(LocalDateTime.now()));
        when(resultSet.getTimestamp("status_updation_date")).thenReturn(java.sql.Timestamp.valueOf(LocalDateTime.now()));

        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        // Execute the method under test
        Appointment result = appointmentDAO.getAppointmentById("1");

        // Validate the results
        assertNotNull(result);
        assertEquals("1", result.getUuid());
        assertEquals("doctor1", result.getDoctorId());
        assertEquals("patient1", result.getPatientId());
        assertEquals(AppointmentStatus.SCHEDULED, result.getStatus()); 
        assertEquals(LocalDate.now(), result.getScheduledDate());
        assertEquals(TimeSlot.SLOT_1030AM_11AM, result.getSlotNo()); 
        assertNotNull(result.getBookingDate());
        assertNotNull(result.getStatusUpdationDate());
    }


    @Test
    public void testUpdateAppointment() throws SQLException {
        Appointment appointment = new Appointment();
        appointment.setUuid("1");
        appointment.setDoctorId("doctor1");
        appointment.setPatientId("patient1");
        appointment.setStatus(AppointmentStatus.COMPLETED);
        appointment.setScheduledDate(LocalDate.now());
        appointment.setSlotNo(TimeSlot.SLOT_1030AM_11AM);
        appointment.setBookingDate(LocalDateTime.now());
        appointment.setStatusUpdationDate(LocalDateTime.now());

        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean result = appointmentDAO.updateAppointment(appointment);

        assertTrue(result);
        verify(preparedStatement).executeUpdate();
    }

    @Test
    public void testDeleteAppointment() throws SQLException {
        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean result = appointmentDAO.deleteAppointment("1");

        assertTrue(result);
        verify(preparedStatement).executeUpdate();
    }

    @Test
    public void testGetAllAppointments() throws SQLException {
        Appointment appointment1 = new Appointment();
        appointment1.setUuid("1");
        appointment1.setDoctorId("doctor1");

        Appointment appointment2 = new Appointment();
        appointment2.setUuid("2");
        appointment2.setDoctorId("doctor2");

        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(resultSet.getString("uuid")).thenReturn("1").thenReturn("2").thenReturn(null);
        when(resultSet.getString("doctor_id")).thenReturn("doctor1").thenReturn("doctor2").thenReturn(null);

        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        List<Appointment> result = appointmentDAO.getAllAppointments();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("1", result.get(0).getUuid());
        assertEquals("2", result.get(1).getUuid());
    }

    @Test
    public void testFindAppointmentsByPatientId() throws SQLException {
        Appointment appointment = new Appointment();
        appointment.setUuid("1");
        appointment.setDoctorId("doctor1");
        appointment.setPatientId("patient1");

        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getString("uuid")).thenReturn("1");
        when(resultSet.getString("doctor_id")).thenReturn("doctor1");
        when(resultSet.getString("patient_id")).thenReturn("patient1");

        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        List<Appointment> result = appointmentDAO.findAppointmentsByPatientId("patient1");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("1", result.get(0).getUuid());
    }

    @Test
    public void testFindAppointmentsByDoctorId() throws SQLException {
        Appointment appointment = new Appointment();
        appointment.setUuid("1");
        appointment.setDoctorId("doctor1");

        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getString("uuid")).thenReturn("1");
        when(resultSet.getString("doctor_id")).thenReturn("doctor1");

        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        List<Appointment> result = appointmentDAO.findAppointmentsByDoctorId("doctor1");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("1", result.get(0).getUuid());
    }

    @Test
    public void testGetAppointmentsByDoctorIdAndStatus() throws SQLException {
        Appointment appointment = new Appointment();
        appointment.setUuid("1");
        appointment.setDoctorId("doctor1");
        appointment.setStatus(AppointmentStatus.SCHEDULED);

        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getString("uuid")).thenReturn("1");
        when(resultSet.getString("doctor_id")).thenReturn("doctor1");
        when(resultSet.getString("status")).thenReturn("BOOKED");

        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        List<Appointment> result = appointmentDAO.getAppointmentsByDoctorIdAndStatus("doctor1", AppointmentStatus.SCHEDULED);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("1", result.get(0).getUuid());
    }

    @Test
    public void testGetAppointmentsByPatientIdAndStatus() throws SQLException {
        Appointment appointment = new Appointment();
        appointment.setUuid("1");
        appointment.setPatientId("patient1");
        appointment.setStatus(AppointmentStatus.SCHEDULED);

        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getString("uuid")).thenReturn("1");
        when(resultSet.getString("patient_id")).thenReturn("patient1");
        when(resultSet.getString("status")).thenReturn("BOOKED");

        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        List<Appointment> result = appointmentDAO.getAppointmentsByPatientIdAndStatus("patient1", AppointmentStatus.SCHEDULED);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("1", result.get(0).getUuid());
    }

    @Test
    public void testGetAppointmentsByStatus() throws SQLException {
        Appointment appointment = new Appointment();
        appointment.setUuid("1");
        appointment.setStatus(AppointmentStatus.SCHEDULED);

        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getString("uuid")).thenReturn("1");
        when(resultSet.getString("status")).thenReturn("BOOKED");

        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        List<Appointment> result = appointmentDAO.getAppointmentsByStatus(AppointmentStatus.SCHEDULED);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("1", result.get(0).getUuid());
    }

}
