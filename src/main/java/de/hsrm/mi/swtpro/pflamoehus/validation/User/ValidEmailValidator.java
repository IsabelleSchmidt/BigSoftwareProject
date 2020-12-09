package de.hsrm.mi.swtpro.pflamoehus.validation.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidEmailValidator implements ConstraintValidator<ValidEmail, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        //TODO: korrektes Pattern
        Pattern passwordPattern = Pattern.compile("[\\w\\d\\p{Punct}]{2,}@\\w{2,}[\\w{2,}\\p{Punct}]+((.de)|(.com))");
        Matcher matcher = passwordPattern.matcher(value);
        

        return matcher.matches();
    }
    
}
