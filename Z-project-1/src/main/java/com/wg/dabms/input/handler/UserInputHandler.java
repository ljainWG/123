package com.wg.dabms.input.handler;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import com.wg.dabms.input.validator.UserValidator;
import com.wg.dabms.model.enums.Department;
import com.wg.dabms.model.enums.Gender;
import com.wg.dabms.model.enums.Role;

public class UserInputHandler {
    private static Scanner scanner = new Scanner(System.in);
    private static UserValidator validator = new UserValidator();

    public static String getValidatedUsername(String prompt) {
        return getValidatedInput(prompt, validator::validateUsername, "Invalid username. Please enter a valid username.");
    }

    public static String getValidatedPassword(String prompt) {
        return getValidatedInput(prompt, validator::validatePassword, "Invalid password. Please enter a valid password.");
    }

    public static String getValidatedEmail(String prompt) {
        return getValidatedInput(prompt, validator::validateEmail, "Invalid email. Please enter a valid email address.");
    }

    public static BigDecimal getInputBigDecimal(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return new BigDecimal(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    public static LocalDate getInputDate(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return LocalDate.parse(scanner.nextLine().trim());
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please enter in the format YYYY-MM-DD.");
            }
        }
    }

    public static Gender getInputGender(String prompt) {
        return getEnumInput(prompt + " (MALE, FEMALE, OTHER): ", Gender.class);
    }

    public static Role getInputRole(String prompt) {
        return getEnumInput(prompt + " (DOCTOR, RECEPTIONIST, ADMIN, PATIENT): ", Role.class);
    }

    public static Department getInputDepartment(String prompt) {
        return getEnumInput(prompt + " (CARDIOLOGY, NEUROLOGY, ORTHOPEDICS, PEDIATRICS, GENERAL_MEDICINE, DERMATOLOGY, GYNECOLOGY, ADMINISTRATION, HOSPITALITY, NONE): ", Department.class);
    }

    public static String getInputPhone(String prompt) {
        return getValidatedInput(prompt, validator::isValidPhone, "Invalid phone number format. Please enter in the format (123) 456-7890:");
    }

    public static String getInputAddress(String prompt) {
        return getValidatedInput(prompt, validator::isValidAddress, "Invalid address. Please try again:");
    }

    public static BigDecimal getInputExperience(String prompt) {
        System.out.println(prompt);
        while (true) {
            if (scanner.hasNextBigDecimal()) {
                BigDecimal experience = scanner.nextBigDecimal().abs(); // Ensure positive value
                if (validator.isValidExperience(experience)) {
                    return experience;
                } else {
                    System.out.println("Experience must be a positive number. Please try again:");
                }
            } else {
                System.out.println("Invalid input. Please enter a numeric value for experience:");
                scanner.next(); // Clear the invalid input
            }
        }
    }

    // Helper method for enum inputs
    private static <T extends Enum<T>> T getEnumInput(String prompt, Class<T> enumClass) {
        while (true) {
            System.out.print(prompt);
            try {
                return Enum.valueOf(enumClass, scanner.nextLine().trim().toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input. Please enter a valid option.");
            }
        }
    }

    // Generic input validation method
    private static String getValidatedInput(String prompt, java.util.function.Predicate<String> validator, String errorMessage) {
        String input;
        do {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
            if (!validator.test(input)) {
                System.out.println(errorMessage);
            }
        } while (!validator.test(input));
        return input;
    }
}
