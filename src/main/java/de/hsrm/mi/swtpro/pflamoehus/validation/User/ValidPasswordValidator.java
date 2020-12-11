package de.hsrm.mi.swtpro.pflamoehus.validation.user;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;



public class ValidPasswordValidator implements ConstraintValidator<ValidPassword, String> {

    
    /** 
     * @param value
     * @param context
     * @return boolean
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        

        Pattern passwordPattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*\\p{Punct}).{8,32}$");
        Matcher matcher = passwordPattern.matcher(value);
        

        return matcher.matches();
    }

}
