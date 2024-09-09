package com.wg.dabms.input.validator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class AppointmentValidator {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    // Validates that the scheduled date is today or in the future
    public boolean validateScheduledDate(String scheduledDateStr) {
        try {
            LocalDate scheduledDate = LocalDate.parse(scheduledDateStr, DATE_FORMATTER);
            // Check if the scheduled date is not before today
            return !scheduledDate.isBefore(LocalDate.now());
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    // Validates that the booking date is not in the future
    public boolean validateBookingDate(String bookingDateStr) {
        try {
            LocalDateTime bookingDate = LocalDateTime.parse(bookingDateStr, DATE_TIME_FORMATTER);
            // Check if the booking date is not in the future
            return !bookingDate.isAfter(LocalDateTime.now());
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    // Validates that the status updation date is not in the future
    public boolean validateStatusUpdationDate(String statusUpdationDateStr) {
        try {
            LocalDateTime statusUpdationDate = LocalDateTime.parse(statusUpdationDateStr, DATE_TIME_FORMATTER);
            // Check if the status updation date is not in the future
            return !statusUpdationDate.isAfter(LocalDateTime.now());
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
