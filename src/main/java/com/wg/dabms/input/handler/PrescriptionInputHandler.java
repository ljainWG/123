package com.wg.dabms.input.handler;

import java.util.Scanner;

import com.wg.dabms.input.validator.PrescriptionValidator;

public class PrescriptionInputHandler {
    private static Scanner scanner = new Scanner(System.in);
    private static PrescriptionValidator validator = new PrescriptionValidator();

    public static String getValidatedInput(String prompt, StringValidator validator, String errorMessage) {
        String input;
        do {
            System.out.print(prompt);
            input = scanner.nextLine();
            if (!validator.validate(input)) {
                System.out.println(errorMessage);
            }
        } while (!validator.validate(input));
        return input;
    }

    public static String getValidatedDescription(String prompt) {
        return getValidatedInput(
                prompt,
                validator::validateDescription,
                "Invalid description. Please enter a non-empty description (1-255 characters) with valid characters."
        );
    }

    @FunctionalInterface
    interface StringValidator {
        boolean validate(String input);
    }
}
