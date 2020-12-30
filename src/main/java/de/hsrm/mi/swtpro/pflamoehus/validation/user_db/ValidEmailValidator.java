package de.hsrm.mi.swtpro.pflamoehus.validation.user_db;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/*
 * ValidEmailValidator
 * The given email has to match the given pattern.
 * 
 * @author Svenja Schenk
 * @version 2
 */
public class ValidEmailValidator implements ConstraintValidator<ValidEmail, String> {

    /**
     * @param value given email
     * @param context contextual data
     * @return valid or not
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        Pattern mailPattern = Pattern
                .compile("^\\w{2,}[\\w\\p{Punct}\\d]{2,}@\\w{2,}([\\w\\p{Punct}\\d]*\\w{2,})*((.de)|(.com)|(.net))$");
        Matcher matcher = mailPattern.matcher(value);

        return matcher.matches();
    }

}
