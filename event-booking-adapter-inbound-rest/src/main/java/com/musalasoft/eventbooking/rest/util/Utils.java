package com.musalasoft.eventbooking.rest.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    private Utils() {}
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    public static boolean isInvalidEmail(String email) {
        return !isValidEmail(email);
    }

    private static boolean isValidEmail(String email) {
        if (email == null)
            return false;

        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
