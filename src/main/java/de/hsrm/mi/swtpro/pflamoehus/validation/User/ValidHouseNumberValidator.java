package de.hsrm.mi.swtpro.pflamoehus.validation.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidHouseNumberValidator implements ConstraintValidator<ValidHouseNumber, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Pattern houseNrPattern = Pattern.compile("\\d+ ?[a-zA-Z]?$");
        Matcher matcher = houseNrPattern.matcher(value);
        return matcher.matches();
    }
    
}
