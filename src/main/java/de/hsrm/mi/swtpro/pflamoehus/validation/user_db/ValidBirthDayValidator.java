package de.hsrm.mi.swtpro.pflamoehus.validation.user_db;

import java.time.LocalDate;
import java.time.Period;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/*
 * ValidBirthDayValidator
 * The user has to be at least 18. Otherwise he can't purchase products from the shop.
 * 
 * @author Ann-Cathrin Fabian
 * @version 1
 */
 public class ValidBirthDayValidator implements ConstraintValidator<ValidBirthDay, LocalDate> {

    /**
     * @param value given birthdate
     * @param context contextual data
     * @return valid or not
     */
    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        LocalDate currentDate = LocalDate.now();

        return Period.between(value, currentDate).getYears() >= 18;
    }

}
