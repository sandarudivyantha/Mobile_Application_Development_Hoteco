package com.example.hoteco.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    public static final Pattern VALID_NIC_ADDRESS_REGEX =
            Pattern.compile("^(\\d{9}[Vv]|\\d{12}[Vv]?)$", Pattern.CASE_INSENSITIVE);
    public static final Pattern VALID_MOBILE_NO_REGEX =
            Pattern.compile("^\\d{10}$", Pattern.CASE_INSENSITIVE);

    public static boolean isValidEmail(String email) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
    }

    public static boolean isValidNIC(String nic) {
        Matcher matcher = VALID_NIC_ADDRESS_REGEX.matcher(nic);
        return matcher.find();
    }

    public static boolean isValidMobile(String mobile) {
        Matcher matcher = VALID_MOBILE_NO_REGEX.matcher(mobile);
        return matcher.find();
    }

    public static boolean isValidPassword(String password) {
        if(password.length() >= 8) return true;
        return false;
    }
    public static boolean isValidConfirmPassword(String password) {
        if(password.length() >= 8) return true;
        return false;
    }
}
