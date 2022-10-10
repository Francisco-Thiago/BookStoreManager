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
            throw new InvalidStringException("Por favor, evite campos vazios ou caracteres especiais.");
        }
        return join(separatedValue);
    }

    public void onlyStringsValidator(String value) {
        Pattern special = Pattern.compile("[0-9]");
        Matcher matchSpecial = special.matcher(value);
        if(matchSpecial.find()) {
            throw new InvalidStringException("Números ou caracteres especiais não são permitidos!");
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
