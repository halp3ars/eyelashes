package com.bot.eyelashes.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneNumberValidation {


    private static final String PATTERN_PHONE = "^(\\+7|8)?[\\s\\-]?\\(?[489][0-9]{2}\\)?[\\s\\-]?[0-9]{3}[\\s\\-]?[0-9]{2}[\\s\\-]?[0-9]{2}$";

    public static boolean isValidPhone(String phone) {
        Pattern pattern = Pattern.compile(PATTERN_PHONE);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

}
