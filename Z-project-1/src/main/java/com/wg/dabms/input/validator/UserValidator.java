package com.wg.dabms.input.validator;

import java.math.BigDecimal;

public class UserValidator {
    private static final String USERNAME_REGEX = "^[a-zA-Z\\s]{1,50}$"; // Restrict length to a reasonable range
    private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[@$*])[A-Za-z@$*]{8,}$"; // Fixed regex to be more precise
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@(gmail\\.com|watchguard\\.com|outlook\\.com)$";
    private static final String PHONE_REGEX = "^\\+?\\d{10,15}$"; // Support optional + and 10-15 digits
    private static final String ADDRESS_REGEX = "^[a-zA-Z0-9\\s,.'-]{5,255}$"; // Allow more characters and better length check

    public boolean validateUsername(String username) {
        return username != null && username.matches(USERNAME_REGEX);
    }

    public boolean validatePassword(String password) {
        return password != null && password.matches(PASSWORD_REGEX);
    }

    public boolean validateEmail(String email) {
        return email != null && email.matches(EMAIL_REGEX);
    }

    public boolean validatePhoneNo(String phoneNo) {
        return phoneNo != null && phoneNo.matches(PHONE_REGEX);
    }

    public boolean isValidPhone(String phone) {
        return validatePhoneNo(phone); // Reuse validation method
    }

    public boolean isValidAddress(String address) {
        return address != null && address.matches(ADDRESS_REGEX);
    }

    public boolean isValidExperience(BigDecimal experience) {
        return experience != null && experience.compareTo(BigDecimal.ZERO) > 0;
    }
}
