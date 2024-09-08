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
                scanner.next(); // Clear the invalid input
            }
        }
    }
}
