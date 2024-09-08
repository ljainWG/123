package com.wg.dabms.a.service;

import java.time.LocalDate;
import java.util.List;

import com.wg.dabms.a.dao.AppointmentDAO;
import com.wg.dabms.model.Appointment;
import com.wg.dabms.model.enums.TimeSlot;

public class AppointmentService {

	private final AppointmentDAO appointmentDAO;

    public AppointmentService(AppointmentDAO appointmentDAO) {
        this.appointmentDAO = appointmentDAO;
    }

    public Appointment getAppointmentById(String uuid) {
        return appointmentDAO.getAppointmentById(uuid);
    }

    public List<Appointment> getAllAppointments() {
        return appointmentDAO.getAllAppointments();
    }

    public boolean createAppointment(Appointment appointment) {
        return appointmentDAO.createAppointment(appointment);
    }
    public boolean isSlotAvailable(String doctorId, LocalDate date, TimeSlot slot) {
        return appointmentDAO.isSlotAvailable(doctorId, date, slot);
    }

    public boolean updateAppointment(Appointment appointment) {
        return appointmentDAO.updateAppointment(appointment);
    }

    public boolean deleteAppointment(String uuid) {
        return appointmentDAO.deleteAppointment(uuid);
    }
    public List<Appointment> getAppointmentsByDoctorId(String doctorId) {
        return appointmentDAO.findAppointmentsByDoctorId(doctorId);
}
}