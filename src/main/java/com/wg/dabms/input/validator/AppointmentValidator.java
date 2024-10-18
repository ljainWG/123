package com.wg.dabms.input.validator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class AppointmentValidator {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    public boolean validateScheduledDate(String scheduledDateStr) {
        try {
            LocalDate scheduledDate = LocalDate.parse(scheduledDateStr, DATE_FORMATTER);
            return !scheduledDate.isBefore(LocalDate.now());
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public boolean validateBookingDate(String bookingDateStr) {
        try {
            LocalDateTime bookingDate = LocalDateTime.parse(bookingDateStr, DATE_TIME_FORMATTER);
            return !bookingDate.isAfter(LocalDateTime.now());
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public boolean validateStatusUpdationDate(String statusUpdationDateStr) {
        try {
            LocalDateTime statusUpdationDate = LocalDateTime.parse(statusUpdationDateStr, DATE_TIME_FORMATTER);
            return !statusUpdationDate.isAfter(LocalDateTime.now());
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
