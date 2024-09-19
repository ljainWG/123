package com.wg.dabms.input.handler;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import com.wg.dabms.model.enums.AppointmentStatus;
import com.wg.dabms.model.enums.TimeSlot;

public class AppointmentInputHandler {
    private static Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static LocalDate getInputDate(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                LocalDate inputDate = LocalDate.parse(input, DATE_FORMATTER);
                if (inputDate.isBefore(LocalDate.now())) {
                    System.out.println("Date cannot be in the past. Please enter today or a future date.");
                } else {
                    return inputDate;
                }
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please enter the date in the format YYYY-MM-DD.");
            }
        }
    }

    public static AppointmentStatus getInputAppointmentStatus(String prompt) {
        while (true) {
            System.out.print(prompt + " (SCHEDULED, COMPLETED, CANCELED, NO_SHOW_PATIENT, NO_SHOW_DOCTOR): ");
            String input = scanner.nextLine().toUpperCase();
            try {
                return AppointmentStatus.valueOf(input);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid status. Please enter one of the following: SCHEDULED, COMPLETED, CANCELED, NO_SHOW_PATIENT, NO_SHOW_DOCTOR.");
            }
        }
    }

    public static TimeSlot getInputTimeSlot(String prompt) {
    	System.out.print(prompt + " (SLOT_8AM_830AM, SLOT_830AM_9AM, SLOT_9AM_930AM, SLOT_930AM_10AM, SLOT_10AM_1030AM,\n" +
                "SLOT_1030AM_11AM, SLOT_11AM_1130AM, SLOT_1130AM_12PM, SLOT_12PM_1230PM, SLOT_1230PM_1PM,\n" +
                "SLOT_2PM_230PM, SLOT_230PM_3PM, SLOT_3PM_330PM, SLOT_330PM_4PM, SLOT_4PM_430PM,\n" +
                "SLOT_430PM_5PM, SLOT_5PM_530PM, SLOT_530PM_6PM, SLOT_6PM_630PM, SLOT_630PM_7PM): ");
        while (true) {            
            String input = scanner.nextLine().toUpperCase();
            try {
                return TimeSlot.valueOf(input);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid time slot. Please enter one of the following: SLOT_8AM_830AM, SLOT_830AM_9AM, SLOT_9AM_930AM, SLOT_930AM_10AM, SLOT_10AM_1030AM,\n" +
                        "SLOT_1030AM_11AM, SLOT_11AM_1130AM, SLOT_1130AM_12PM, SLOT_12PM_1230PM, SLOT_1230PM_1PM,\n" +
                        "SLOT_2PM_230PM, SLOT_230PM_3PM, SLOT_3PM_330PM, SLOT_330PM_4PM, SLOT_4PM_430PM,\n" +
                        "SLOT_430PM_5PM, SLOT_5PM_530PM, SLOT_530PM_6PM, SLOT_6PM_630PM, SLOT_630PM_7PM.");
            }
        }
    }
}
