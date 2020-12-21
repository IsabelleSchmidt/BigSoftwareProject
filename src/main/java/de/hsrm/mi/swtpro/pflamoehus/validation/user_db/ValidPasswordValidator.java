package de.hsrm.mi.swtpro.pflamoehus.validation.user_db;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/*
 * ValidPasswordValidator
 * The given password has to match the given pattern.
 * 
 * @author Svenja Schenk
 * @version 2
 */
 public class ValidPasswordValidator implements ConstraintValidator<ValidPassword, String> {

    /**
     * @param value given password
     * @param context contextual data
     * @return valid or not
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        Pattern passwordPattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*\\p{Punct}).{8,}$");
        Matcher matcher = passwordPattern.matcher(value);

        return matcher.matches();
    }

}
