package com.wg.dabms.input.handler;

import java.math.BigDecimal;
import java.util.Scanner;

import com.wg.dabms.input.validator.ReviewValidator;

public class ReviewInputHandler {
    private static Scanner scanner = new Scanner(System.in);
    private static ReviewValidator validator = new ReviewValidator();

    public static String getValidatedDescription(String prompt) {
        String description;
        do {
            System.out.print(prompt);
            description = scanner.nextLine();
            if (!validator.validateDescription(description)) {
                System.out.println(
                    "Invalid description. Please enter a non-empty description (1-255 characters) with valid characters.");
            }
        } while (!validator.validateDescription(description));
        return description;
    }

    public static BigDecimal getInputBigDecimal(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                BigDecimal value = new BigDecimal(scanner.nextLine());
                if (value.compareTo(BigDecimal.ZERO) < 0 || value.compareTo(BigDecimal.valueOf(5)) > 0) {
                    System.out.println("Invalid rating. Please enter a number between 0 and 5.");
                } else {
                    return value;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }
}
