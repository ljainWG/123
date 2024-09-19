package com.wg.dabms.input.validator;

import java.util.regex.Pattern;

public class NotificationValidator {
    private static final String TITLE_REGEX = "^[\\w\\s,.?!]{1,100}$"; 
    private static final String DESCRIPTION_REGEX = "^[\\w\\s,.?!]{1,255}$";

    public boolean validateTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            return false;
        }
        return Pattern.matches(TITLE_REGEX, title);
    }

    public boolean validateDescription(String description) {
        if (description == null || description.trim().isEmpty()) {
            return false; 
        }
        return Pattern.matches(DESCRIPTION_REGEX, description);
    }
}
