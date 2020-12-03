package de.hsrm.mi.swtpro.pflamoehus.validation.ProductDatabase;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidRoomTypeValidator implements ConstraintValidator<ValidRoomType, String> {

    
    /** 
     * @param value passed value
     * @param context Provides contextual data and operation
     * @return boolean if the entered RoomType exists in the database
     */
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
