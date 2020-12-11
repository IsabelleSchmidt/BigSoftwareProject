package de.hsrm.mi.swtpro.pflamoehus.validation.user;

import java.time.LocalDate;
import java.time.Period;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class ValidBirthDayValidator implements ConstraintValidator<ValidBirthDay, LocalDate> {

    
    /** 
     * @param value
     * @param context
     * @return boolean
     */
    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        LocalDate currentDate = LocalDate.now();
        if(Period.between(value, currentDate).getYears() >= 18){
            return true;
        }
        return false;
    }
    
}
