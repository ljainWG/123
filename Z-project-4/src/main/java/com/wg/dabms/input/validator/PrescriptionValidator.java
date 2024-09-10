package com.wg.dabms.input.validator;

import java.util.regex.Pattern;

public class PrescriptionValidator {
    private static final String DESCRIPTION_REGEX = "^[a-zA-Z0-9 ,.]{1,255}$";

    public boolean validateDescription(String description) {
        if (description == null || description.trim().isEmpty()) {
            return false;
        }
        return Pattern.matches(DESCRIPTION_REGEX, description);
    }
}
