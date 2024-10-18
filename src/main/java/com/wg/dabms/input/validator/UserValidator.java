package com.wg.dabms.input.validator;

import java.math.BigDecimal;

public class UserValidator {
    private static final String USERNAME_REGEX = "^[a-zA-Z\\s]{1,50}$"; 
    private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[@$*]).{8,}$"; 
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@(gmail\\.com|watchguard\\.com|outlook\\.com)$";
    private static final String PHONE_REGEX = "^\\+?\\d{10,15}$"; 
    private static final String ADDRESS_REGEX = "^[a-zA-Z0-9\\s,.'-]{5,255}$"; 

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
        return validatePhoneNo(phone); 
    }

    public boolean isValidAddress(String address) {
        return address != null && address.matches(ADDRESS_REGEX);
    }

    public boolean isValidExperience(BigDecimal experience) {
        return experience != null && experience.compareTo(BigDecimal.ZERO) > 0;
    }
}
