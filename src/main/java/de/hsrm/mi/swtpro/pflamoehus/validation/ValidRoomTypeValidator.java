package de.hsrm.mi.swtpro.pflamoehus.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidRoomTypeValidator implements ConstraintValidator<ValidRoomType, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        String [] product = {"Bad", "Schlafzimmer", "Wohnzimmer", "Küche/Wohnküche", "Kinderzimmer", "Arbeitszimmer"};

        for(String s: product){
            if(s.equals(value)){
                return true;
            }
        }
        return false;
    }
    
}
