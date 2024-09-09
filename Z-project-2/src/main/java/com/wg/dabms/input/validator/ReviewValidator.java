package com.wg.dabms.input.validator;

import java.math.BigDecimal;
import java.util.regex.Pattern;

public class ReviewValidator {
    private static final String DESCRIPTION_REGEX = "^[a-zA-Z0-9 ,.]{1,255}$";

    // Validate review description
    public boolean validateDescription(String description) {
        if (description == null || description.trim().isEmpty()) {
            return false; // Ensure description is not null or empty
        }
        return Pattern.matches(DESCRIPTION_REGEX, description);
    }

    // Validate review rating
    public boolean validateRating(BigDecimal rating) {
        if (rating == null) {
            return false; // Ensure rating is not null
        }
        BigDecimal minRating = new BigDecimal("1.0");
        BigDecimal maxRating = new BigDecimal("5.0");
        return rating.compareTo(minRating) >= 0 && rating.compareTo(maxRating) <= 0;
    }
}
