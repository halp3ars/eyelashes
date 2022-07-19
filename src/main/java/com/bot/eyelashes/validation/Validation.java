package com.bot.eyelashes.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {


    private static final String REGEX_LETTERS = "^(?![\\d+_@.-]+$)[a-zA-Z а-яёА-ЯЁ+_@.-]*$";

    public static boolean isValidText(String text) {
        Pattern pattern = Pattern.compile(REGEX_LETTERS);
        Matcher matcher = pattern.matcher(text);
        return matcher.matches();
    }

}
