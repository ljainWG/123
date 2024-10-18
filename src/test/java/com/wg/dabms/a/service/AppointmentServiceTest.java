package com.wg.dabms.a.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.wg.dabms.dao.AppointmentDAO;
import com.wg.dabms.model.Appointment;
import com.wg.dabms.model.enums.AppointmentStatus;
import com.wg.dabms.service.AppointmentService;

public class AppointmentServiceTest {

    @Mock
    private AppointmentDAO appointmentDAO;

    @InjectMocks
    private AppointmentService appointmentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAppointmentById() {
        String uuid = "test-uuid";
        Appointment appointment = new Appointment();
        when(appointmentDAO.getAppointmentById(uuid)).thenReturn(appointment);

        Appointment result = appointmentService.getAppointmentById(uuid);
        assertEquals(appointment, result, "Should return the appointment by UUID");
        verify(appointmentDAO).getAppointmentById(uuid);
    }

    @Test
    public void testGetAllAppointments() {
        List<Appointment> appointments = new ArrayList<>();
        when(appointmentDAO.getAllAppointments()).thenReturn(appointments);

        List<Appointment> result = appointmentService.getAllAppointments();
        assertEquals(appointments, result, "Should return the list of all appointments");
        verify(appointmentDAO).getAllAppointments();
    }

    @Test
    public void testCreateAppointment() {
        Appointment appointment = new Appointment();
        when(appointmentDAO.createAppointment(appointment)).thenReturn(true);

        boolean result = appointmentService.createAppointment(appointment);
        assertTrue(result, "Should return true when appointment is created");
        verify(appointmentDAO).createAppointment(appointment);
    }

    

    @Test
    public void testUpdateAppointment() {
        Appointment appointment = new Appointment();
        when(appointmentDAO.updateAppointment(appointment)).thenReturn(true);

        boolean result = appointmentService.updateAppointment(appointment);
        assertTrue(result, "Should return true when appointment is updated");
        verify(appointmentDAO).updateAppointment(appointment);
    }

    @Test
    public void testDeleteAppointment() {
        String uuid = "test-uuid";
        when(appointmentDAO.deleteAppointment(uuid)).thenReturn(true);

        boolean result = appointmentService.deleteAppointment(uuid);
        assertTrue(result, "Should return true when appointment is deleted");
        verify(appointmentDAO).deleteAppointment(uuid);
    }

    @Test
    public void testGetAppointmentsByPatientId() {
        String patientId = "patient-1";
        List<Appointment> appointments = new ArrayList<>();
        when(appointmentDAO.findAppointmentsByPatientId(patientId)).thenReturn(appointments);

        List<Appointment> result = appointmentService.getAppointmentsByPatientId(patientId);
        assertEquals(appointments, result, "Should return appointments by patient ID");
        verify(appointmentDAO).findAppointmentsByPatientId(patientId);
    }

    @Test
    public void testGetAppointmentsByDoctorId() {
        String doctorId = "doctor-1";
        List<Appointment> appointments = new ArrayList<>();
        when(appointmentDAO.findAppointmentsByDoctorId(doctorId)).thenReturn(appointments);

        List<Appointment> result = appointmentService.getAppointmentsByDoctorId(doctorId);
        assertEquals(appointments, result, "Should return appointments by doctor ID");
        verify(appointmentDAO).findAppointmentsByDoctorId(doctorId);
    }

    @Test
    public void testUpdateAppointmentStatus() {
        String appointmentId = "test-appointment-id";
        AppointmentStatus status = AppointmentStatus.CANCELED;
        when(appointmentDAO.updateAppointmentStatus(appointmentId, status)).thenReturn(true);

        boolean result = appointmentService.updateAppointmentStatus(appointmentId, status);
        assertTrue(result, "Should return true when appointment status is updated");
        verify(appointmentDAO).updateAppointmentStatus(appointmentId, status);
    }

    @Test
    public void testGetAppointmentsByDoctorIdAndStatus() {
        String doctorId = "doctor-1";
        AppointmentStatus status = AppointmentStatus.SCHEDULED;
        List<Appointment> appointments = new ArrayList<>();
        when(appointmentDAO.getAppointmentsByDoctorIdAndStatus(doctorId, status)).thenReturn(appointments);

        List<Appointment> result = appointmentService.getAppointmentsByDoctorIdAndStatus(doctorId, status);
        assertEquals(appointments, result, "Should return appointments by doctor ID and status");
        verify(appointmentDAO).getAppointmentsByDoctorIdAndStatus(doctorId, status);
    }

    @Test
    public void testGetAppointmentsByPatientIdAndStatus() {
        String patientId = "patient-1";
        AppointmentStatus status = AppointmentStatus.SCHEDULED;
        List<Appointment> appointments = new ArrayList<>();
        when(appointmentDAO.getAppointmentsByPatientIdAndStatus(patientId, status)).thenReturn(appointments);

        List<Appointment> result = appointmentService.getAppointmentsByPatientIdAndStatus(patientId, status);
        assertEquals(appointments, result, "Should return appointments by patient ID and status");
        verify(appointmentDAO).getAppointmentsByPatientIdAndStatus(patientId, status);
    }

    @Test
    public void testGetAppointmentsByStatus() {
        AppointmentStatus status = AppointmentStatus.SCHEDULED;
        List<Appointment> appointments = new ArrayList<>();
        when(appointmentDAO.getAppointmentsByStatus(status)).thenReturn(appointments);

        List<Appointment> result = appointmentService.getAppointmentsByStatus(status);
        assertEquals(appointments, result, "Should return appointments by status");
        verify(appointmentDAO).getAppointmentsByStatus(status);
    }
}
