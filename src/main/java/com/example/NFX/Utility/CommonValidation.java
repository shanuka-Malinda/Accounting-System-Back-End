package com.example.NFX.Utility;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class CommonValidation {

    // ========================================
    // REGEX PATTERNS - CONSTANTS
    // ========================================

    // Basic patterns
    private static final String LETTERS_ONLY_PATTERN = "^[a-zA-Z\\s]+$";
    private static final String LETTERS_WITH_SPACES_PATTERN = "^[a-zA-Z\\s'-]+$";
    private static final String ALPHANUMERIC_PATTERN = "^[a-zA-Z0-9]+$";
    private static final String NUMERIC_ONLY_PATTERN = "^[0-9]+$";

    // Sri Lankan specific patterns
    private static final String NIC_OLD_PATTERN = "^[0-9]{9}[VXvx]$";
    private static final String NIC_NEW_PATTERN = "^[0-9]{12}$";

    // Contact patterns
    private static final String EMAIL_PATTERN =
            "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final String PHONE_LOCAL_PATTERN = "^0[0-9]{9}$";
    private static final String PHONE_INTERNATIONAL_PATTERN = "^\\+94[0-9]{9}$";
    private static final String MOBILE_PATTERN = "^0[7][0-9]{8}$";

    // Security patterns
    private static final String USERNAME_PATTERN = "^[a-zA-Z0-9._-]{3,20}$";
    private static final String STRONG_PASSWORD_PATTERN =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";

    // Business patterns
    private static final String CURRENCY_PATTERN = "^[0-9]+\\.?[0-9]{0,2}$";
    private static final String PERCENTAGE_PATTERN = "^(100(\\.00?)?|[1-9]?[0-9](\\.[0-9]{1,2})?)$";
    private static final String POSTAL_CODE_PATTERN = "^[0-9]{5}$";

    // ========================================
    // COMPILED PATTERNS - PERFORMANCE OPTIMIZATION
    // ========================================

    private static final Pattern LETTERS_ONLY_COMPILED = Pattern.compile(LETTERS_ONLY_PATTERN);
    private static final Pattern LETTERS_WITH_SPACES_COMPILED = Pattern.compile(LETTERS_WITH_SPACES_PATTERN);
    private static final Pattern ALPHANUMERIC_COMPILED = Pattern.compile(ALPHANUMERIC_PATTERN);
    private static final Pattern NUMERIC_ONLY_COMPILED = Pattern.compile(NUMERIC_ONLY_PATTERN);
    private static final Pattern NIC_OLD_COMPILED = Pattern.compile(NIC_OLD_PATTERN);
    private static final Pattern NIC_NEW_COMPILED = Pattern.compile(NIC_NEW_PATTERN);
    private static final Pattern EMAIL_COMPILED = Pattern.compile(EMAIL_PATTERN);
    private static final Pattern PHONE_LOCAL_COMPILED = Pattern.compile(PHONE_LOCAL_PATTERN);
    private static final Pattern PHONE_INTERNATIONAL_COMPILED = Pattern.compile(PHONE_INTERNATIONAL_PATTERN);
    private static final Pattern MOBILE_COMPILED = Pattern.compile(MOBILE_PATTERN);
    private static final Pattern USERNAME_COMPILED = Pattern.compile(USERNAME_PATTERN);
    private static final Pattern STRONG_PASSWORD_COMPILED = Pattern.compile(STRONG_PASSWORD_PATTERN);
    private static final Pattern CURRENCY_COMPILED = Pattern.compile(CURRENCY_PATTERN);
    private static final Pattern PERCENTAGE_COMPILED = Pattern.compile(PERCENTAGE_PATTERN);
    private static final Pattern POSTAL_CODE_COMPILED = Pattern.compile(POSTAL_CODE_PATTERN);

    // ========================================
    // DATE FORMATTERS
    // ========================================

    private static final DateTimeFormatter[] COMMON_DATE_FORMATS = {
            DateTimeFormatter.ofPattern("yyyy-MM-dd"),
            DateTimeFormatter.ofPattern("dd/MM/yyyy"),
            DateTimeFormatter.ofPattern("MM/dd/yyyy"),
            DateTimeFormatter.ofPattern("dd-MM-yyyy"),
            DateTimeFormatter.ofPattern("yyyy/MM/dd")
    };

    // ========================================
    // NULL AND EMPTY VALIDATIONS
    // ========================================

    /**
     * Validates if a string is null or empty (including whitespace only)
     */
    public static boolean isNullOrEmpty(String input) {
        return input == null || input.trim().isEmpty();
    }

    /**
     * Validates if a string is not null and not empty
     */
    public static boolean isNotNullOrEmpty(String input) {
        return !isNullOrEmpty(input);
    }

    /**
     * Validates if a collection is null or empty
     */
    public static boolean isNullOrEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * Validates if a map is null or empty
     */
    public static boolean isNullOrEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    /**
     * Validates if a list contains any null or empty strings
     */
    public boolean hasNullOrEmptyElements(List<String> list) {
        if (isNullOrEmpty(list)) {
            return true;
        }
        return list.stream().anyMatch(CommonValidation::isNullOrEmpty);
    }

    // ========================================
    // BASIC STRING VALIDATIONS
    // ========================================

    /**
     * Validates if input contains only letters
     */
    public static boolean isLettersOnly(String input) {
        return isNotNullOrEmpty(input) && LETTERS_ONLY_COMPILED.matcher(input.trim()).matches();
    }

    /**
     * Validates if input contains only letters and spaces (for names with spaces)
     */
    public static boolean isValidName(String name) {
        if (isNullOrEmpty(name)) return false;
        String trimmed = name.trim();
        return trimmed.length() >= 2 &&
                trimmed.length() <= 50 &&
                LETTERS_WITH_SPACES_COMPILED.matcher(trimmed).matches();
    }

    /**
     * Validates if input is alphanumeric
     */
    public static boolean isAlphanumeric(String input) {
        return isNotNullOrEmpty(input) && ALPHANUMERIC_COMPILED.matcher(input.trim()).matches();
    }

    /**
     * Validates if input contains only numbers
     */
    public static boolean isNumericOnly(String input) {
        return isNotNullOrEmpty(input) && NUMERIC_ONLY_COMPILED.matcher(input.trim()).matches();
    }

    /**
     * Validates string length within specified range
     */
    public static boolean isValidLength(String input, int minLength, int maxLength) {
        if (isNullOrEmpty(input)) return false;
        int length = input.trim().length();
        return length >= minLength && length <= maxLength;
    }

    // ========================================
    // IDENTIFICATION VALIDATIONS
    // ========================================

    /**
     * Validates Sri Lankan NIC (both old and new formats)
     */
    public static boolean isValidNIC(String nic) {
        if (isNullOrEmpty(nic)) return false;
        String trimmed = nic.trim().toUpperCase();
        return NIC_OLD_COMPILED.matcher(trimmed).matches() ||
                NIC_NEW_COMPILED.matcher(trimmed).matches();
    }

    /**
     * Validates passport number (basic format)
     */
    public static boolean isValidPassport(String passport) {
        if (isNullOrEmpty(passport)) return false;
        String trimmed = passport.trim().toUpperCase();
        return trimmed.length() >= 6 && trimmed.length() <= 12 &&
                trimmed.matches("^[A-Z0-9]+$");
    }

    // ========================================
    // CONTACT VALIDATIONS
    // ========================================

    /**
     * Validates email address format
     */
    public static boolean isValidEmail(String email) {
        if (isNullOrEmpty(email)) return false;
        String trimmed = email.trim().toLowerCase();
        return trimmed.length() <= 254 && EMAIL_COMPILED.matcher(trimmed).matches();
    }

    /**
     * Validates phone number (local and international formats)
     */
    public static boolean isValidPhoneNumber(String phoneNumber) {
        if (isNullOrEmpty(phoneNumber)) return false;
        String trimmed = phoneNumber.trim();
        return PHONE_LOCAL_COMPILED.matcher(trimmed).matches() ||
                PHONE_INTERNATIONAL_COMPILED.matcher(trimmed).matches();
    }

    /**
     * Validates Sri Lankan mobile number
     */
    public static boolean isValidMobileNumber(String mobileNumber) {
        if (isNullOrEmpty(mobileNumber)) return false;
        return MOBILE_COMPILED.matcher(mobileNumber.trim()).matches();
    }

    // ========================================
    // AUTHENTICATION VALIDATIONS
    // ========================================

    /**
     * Validates username format
     */
    public static boolean isValidUsername(String username) {
        if (isNullOrEmpty(username)) return false;
        return USERNAME_COMPILED.matcher(username.trim()).matches();
    }

    /**
     * Validates strong password (at least 8 chars, contains upper, lower, digit, special char)
     */
    public static boolean isValidPassword(String password) {
        if (isNullOrEmpty(password)) return false;
        return STRONG_PASSWORD_COMPILED.matcher(password).matches();
    }

    /**
     * Validates password strength level
     */
    public static PasswordStrength getPasswordStrength(String password) {
        if (isNullOrEmpty(password)) return PasswordStrength.INVALID;

        int score = 0;
        if (password.length() >= 8) score++;
        if (password.matches(".*[a-z].*")) score++;
        if (password.matches(".*[A-Z].*")) score++;
        if (password.matches(".*[0-9].*")) score++;
        if (password.matches(".*[@#$%^&+=!].*")) score++;
        if (password.length() >= 12) score++;

        if (score < 3) return PasswordStrength.WEAK;
        if (score < 5) return PasswordStrength.MEDIUM;
        return PasswordStrength.STRONG;
    }

    public enum PasswordStrength {
        INVALID, WEAK, MEDIUM, STRONG
    }

    // ========================================
    // DATE AND TIME VALIDATIONS
    // ========================================

    /**
     * Validates date string with multiple format support
     */
    public static boolean isValidDate(String dateString) {
        if (isNullOrEmpty(dateString)) return false;

        for (DateTimeFormatter formatter : COMMON_DATE_FORMATS) {
            try {
                LocalDate.parse(dateString.trim(), formatter);
                return true;
            } catch (DateTimeParseException e) {
                // Continue to next format
            }
        }
        return false;
    }

    /**
     * Validates if date is not in the future
     */
    public static boolean isNotFutureDate(String dateString) {
        if (!isValidDate(dateString)) return false;

        for (DateTimeFormatter formatter : COMMON_DATE_FORMATS) {
            try {
                LocalDate date = LocalDate.parse(dateString.trim(), formatter);
                return !date.isAfter(LocalDate.now());
            } catch (DateTimeParseException e) {
                // Continue to next format
            }
        }
        return false;
    }

    /**
     * Validates if date is within acceptable age range (18-100 years)
     */
    public static boolean isValidBirthDate(String dateString) {
        if (!isValidDate(dateString)) return false;

        for (DateTimeFormatter formatter : COMMON_DATE_FORMATS) {
            try {
                LocalDate birthDate = LocalDate.parse(dateString.trim(), formatter);
                LocalDate now = LocalDate.now();
                int age = now.getYear() - birthDate.getYear();

                if (birthDate.isAfter(now.minusYears(age))) {
                    age--;
                }

                return age >= 18 && age <= 100;
            } catch (DateTimeParseException e) {
                // Continue to next format
            }
        }
        return false;
    }

    /**
     * Validates LocalDateTime is not null
     */
    public static boolean isValidDateTime(LocalDateTime dateTime) {
        return dateTime != null;
    }

    // ========================================
    // BUSINESS VALIDATIONS
    // ========================================

    /**
     * Validates currency amount format
     */
    public static boolean isValidCurrency(String amount) {
        if (isNullOrEmpty(amount)) return false;
        return CURRENCY_COMPILED.matcher(amount.trim()).matches();
    }

    /**
     * Validates currency amount is positive
     */
    public static boolean isPositiveCurrency(String amount) {
        if (!isValidCurrency(amount)) return false;
        try {
            double value = Double.parseDouble(amount.trim());
            return value > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Validates percentage value (0-100)
     */
    public static boolean isValidPercentage(String percentage) {
        if (isNullOrEmpty(percentage)) return false;
        return PERCENTAGE_COMPILED.matcher(percentage.trim()).matches();
    }

    /**
     * Validates quantity is positive integer
     */
    public static boolean isValidQuantity(String quantity) {
        if (isNullOrEmpty(quantity)) return false;
        try {
            int qty = Integer.parseInt(quantity.trim());
            return qty > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Validates postal code format
     */
    public static boolean isValidPostalCode(String postalCode) {
        if (isNullOrEmpty(postalCode)) return false;
        return POSTAL_CODE_COMPILED.matcher(postalCode.trim()).matches();
    }

    // ========================================
    // ADVANCED VALIDATIONS
    // ========================================

    /**
     * Validates input against custom regex pattern
     */
    public static boolean matchesPattern(String input, String pattern) {
        if (isNullOrEmpty(input) || isNullOrEmpty(pattern)) return false;
        try {
            return Pattern.compile(pattern).matcher(input.trim()).matches();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Validates file extension
     */
    public static boolean isValidFileExtension(String filename, String... allowedExtensions) {
        if (isNullOrEmpty(filename) || allowedExtensions == null) return false;

        String extension = "";
        int lastDot = filename.lastIndexOf('.');
        if (lastDot > 0) {
            extension = filename.substring(lastDot + 1).toLowerCase();
        }

        for (String allowed : allowedExtensions) {
            if (extension.equals(allowed.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Validates URL format
     */
    public static boolean isValidURL(String url) {
        if (isNullOrEmpty(url)) return false;
        try {
            new java.net.URL(url.trim());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Validation result wrapper class
     */
    public static class ValidationResult {
        private final boolean valid;
        private final String message;

        public ValidationResult(boolean valid, String message) {
            this.valid = valid;
            this.message = message;
        }

        public boolean isValid() { return valid; }
        public String getMessage() { return message; }

        public static ValidationResult success() {
            return new ValidationResult(true, "Validation passed");
        }

        public static ValidationResult failure(String message) {
            return new ValidationResult(false, message);
        }
    }

    /**
     * Comprehensive validation for user registration
     */
    public static ValidationResult validateUserRegistration(String firstName, String lastName,
                                                            String email, String phone,
                                                            String username, String password) {
        if (!isValidName(firstName)) {
            return ValidationResult.failure("Invalid first name");
        }
        if (!isValidName(lastName)) {
            return ValidationResult.failure("Invalid last name");
        }
        if (!isValidEmail(email)) {
            return ValidationResult.failure("Invalid email address");
        }
        if (!isValidPhoneNumber(phone)) {
            return ValidationResult.failure("Invalid phone number");
        }
        if (!isValidUsername(username)) {
            return ValidationResult.failure("Invalid username format");
        }
        if (!isValidPassword(password)) {
            return ValidationResult.failure("Password does not meet security requirements");
        }

        return ValidationResult.success();
    }
}
