package de.hsrm.mi.swtpro.pflamoehus.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidRoomTypeValidator implements ConstraintValidator<ValidRoomType, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        String [] roomTypes = {"Bad", "Schlafzimmer", "Wohnzimmer", "Küche/Wohnküche", "Kinderzimmer", "Arbeitszimmer"};

        for(String room: roomTypes){
            if(room.equals(value)){
                return true;
            }
        }
        return false;
    }
    
}
