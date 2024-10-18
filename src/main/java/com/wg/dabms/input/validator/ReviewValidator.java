package com.wg.dabms.input.validator;

import java.math.BigDecimal;
import java.util.regex.Pattern;

public class ReviewValidator {
    private static final String DESCRIPTION_REGEX = "^[a-zA-Z0-9 ,.]{1,255}$";

    public boolean validateDescription(String description) {
        if (description == null || description.trim().isEmpty()) {
            return false; 
        }
        return Pattern.matches(DESCRIPTION_REGEX, description);
    }

    public boolean validateRating(BigDecimal rating) {
        if (rating == null) {
            return false;
        }
        BigDecimal minRating = new BigDecimal("1.0");
        BigDecimal maxRating = new BigDecimal("5.0");
        return rating.compareTo(minRating) >= 0 && rating.compareTo(maxRating) <= 0;
    }
}
