package de.hsrm.mi.swtpro.pflamoehus.validation.user_db;

import java.time.LocalDate;
import java.time.Period;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidBirthDayValidator implements ConstraintValidator<ValidBirthDay, LocalDate> {

    /**
     * @param value
     * @param context
     * @return boolean
     * 
     * Valid, if the user is at least 18 years old
     */
    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        LocalDate currentDate = LocalDate.now();
        if (Period.between(value, currentDate).getYears() >= 18) {
            return true;
        }
        return false;
    }

}
