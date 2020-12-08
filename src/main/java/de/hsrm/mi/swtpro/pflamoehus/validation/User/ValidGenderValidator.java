package de.hsrm.mi.swtpro.pflamoehus.validation.User;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidGenderValidator implements ConstraintValidator<ValidGender, String> {

    
    /** 
     * @param value
     * @param context
     * @return boolean
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        
        String [] genderTypes = {"weiblich", "männlich", "divers"};

        for(String room: genderTypes){
            if(room.equals(value)){
                return true;
            }
        }
        return false;
    }
    
}
