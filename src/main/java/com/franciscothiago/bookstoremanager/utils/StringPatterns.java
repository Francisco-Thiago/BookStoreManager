package com.franciscothiago.bookstoremanager.utils;

import com.franciscothiago.bookstoremanager.exception.InvalidStringException;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class StringPatterns {

    public String normalize(String value) throws InvalidStringException {
        String noSpace = value.replaceAll("[ ]+", " ").trim();
        String noUnnecessaryAccents = noSpace
                .replaceAll("[.]+", ".")
                .replaceAll("[;]+", ";")
                .replaceAll("[,]+", ",")
                .replaceAll("[,;]|[;,]|[.;]|[;,]|[.;]|[;.]", "");
        String[] separatedValue = noUnnecessaryAccents.split(" ");
        separatedValue[separatedValue.length-1] = separatedValue[separatedValue.length-1].replaceAll(",", "").replaceAll(";", "");

        if(join(separatedValue).trim().equals("")) {
            throw new InvalidStringException("Invalid String. Please avoid empty spaces or special characters");
        }
        return join(separatedValue);
    }

    public void onlyStringsValidator(String value) {
        Pattern special = Pattern.compile("[0-9Á-ü]");
        Matcher matchSpecial = special.matcher(value);
        if(matchSpecial.find()) {
            throw new InvalidStringException("Please enter all capital letters without accents or special characters.");
        }
    }

    public String join(String[] value) {
        StringBuilder currentValue = new StringBuilder();
        for (String element : value) {
            currentValue.append(" ").append(element);
        }
        return currentValue.toString().trim();
    }
}
