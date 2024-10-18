package com.wg.dabms.input.choice;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ChoiceInputHandler {
    private static final Scanner scanner = new Scanner(System.in);

    public static int getIntChoice(String prompt, int start, int end) {
        System.out.println(prompt);
        System.out.printf("Enter your choice (%d to %d):%n", start, end);
        
        while (true) {
            int choice;
            try {
                choice = scanner.nextInt();
                if (choice >= start && choice <= end) {
                    return choice;
                } else {
                    System.out.printf("Choice must be between %d and %d. Please try again.%n", start, end);
                }
            } catch (InputMismatchException e) {
                System.out.printf("Invalid input. Please enter an integer between %d and %d.%n", start, end);
                scanner.next(); 
            }
        }
    }
    
    public static String getStringChoice(String prompt, String... options) {
        System.out.println(prompt);
        for (int i = 0; i < options.length; i++) {
            System.out.printf("%d. %s%n", i + 1, options[i]);
        }

        while (true) {
            String input = scanner.next().trim().toLowerCase();
            for (int i = 0; i < options.length; i++) {
                if (input.equals(options[i].toLowerCase())) {
                    return options[i];
                }
            }
            System.out.printf("Invalid choice. Please enter one of the following: %s%n", String.join(", ", options));
        }
    }

    public static boolean getBooleanChoice(String prompt) {
        System.out.println(prompt + " (yes/no)");
        while (true) {
            String input = scanner.next().trim().toLowerCase();
            if (input.equals("yes")) {
                return true;
            } else if (input.equals("no")) {
                return false;
            } else {
                System.out.println("Invalid input. Please enter 'yes' or 'no'.");
            }
        }
    }
}
