package com.wg.dabms.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.wg.dabms.model.enums.AppointmentStatus;
import com.wg.dabms.model.enums.TimeSlot;

public class Appointment {

	String uuid;
	String doctorId;
	String patientId;
	AppointmentStatus status;
	LocalDate scheduledDate;
	TimeSlot slotNo;
	LocalDateTime bookingDate;
	LocalDateTime statusUpdationDate;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(String doctorId) {
		this.doctorId = doctorId;
	}

	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	public AppointmentStatus getStatus() {
		return status;
	}

	public void setStatus(AppointmentStatus status) {
		this.status = status;
	}

	public LocalDate getScheduledDate() {
		return scheduledDate;
	}

	public void setScheduledDate(LocalDate scheduledDate) {
		this.scheduledDate = scheduledDate;
	}

	public TimeSlot getSlotNo() {
		return slotNo;
	}

	public void setSlotNo(TimeSlot slotNo) {
		this.slotNo = slotNo;
	}

	public LocalDateTime getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(LocalDateTime bookingDate) {
		this.bookingDate = bookingDate;
	}

	public LocalDateTime getStatusUpdationDate() {
		return statusUpdationDate;
	}

	public void setStatusUpdationDate(LocalDateTime statusUpdationDate) {
		this.statusUpdationDate = statusUpdationDate;
	}

}