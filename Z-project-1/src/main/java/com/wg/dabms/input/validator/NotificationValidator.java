package com.wg.dabms.input.validator;

import java.util.regex.Pattern;

public class NotificationValidator {
    private static final String TITLE_REGEX = "^[\\w\\s,.?!]{1,100}$"; // Allows word characters, spaces, and punctuation (1-100 characters)
    private static final String DESCRIPTION_REGEX = "^[\\w\\s,.?!]{1,255}$"; // Allows word characters, spaces, and punctuation (1-255 characters)

    // Validates that the title is non-empty and matches the TITLE_REGEX pattern
    public boolean validateTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            return false; // Ensures title is not empty
        }
        return Pattern.matches(TITLE_REGEX, title);
    }

    // Validates that the description is non-empty and matches the DESCRIPTION_REGEX pattern
    public boolean validateDescription(String description) {
        if (description == null || description.trim().isEmpty()) {
            return false; // Ensures description is not empty
        }
        return Pattern.matches(DESCRIPTION_REGEX, description);
    }
}
