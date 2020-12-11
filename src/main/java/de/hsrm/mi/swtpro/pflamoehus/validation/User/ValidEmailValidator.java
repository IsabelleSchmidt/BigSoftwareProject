package de.hsrm.mi.swtpro.pflamoehus.validation.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidEmailValidator implements ConstraintValidator<ValidEmail, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
       
        Pattern mailPattern = Pattern.compile("^\\w{2,}[\\w\\p{Punct}\\d]{2,}@\\w{2,}[\\w\\p{Punct}\\d]*\\w{2,}((.de)|(.com))$");
        Matcher matcher = mailPattern.matcher(value);
        

        return matcher.matches();
    }
    
}
