package de.hsrm.mi.swtpro.pflamoehus.validation.productDatabase;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import de.hsrm.mi.swtpro.pflamoehus.product.RoomType;

public class ValidRoomTypeValidator implements ConstraintValidator<ValidRoomType, String> {

    
    /** 
     * @param value passed value
     * @param context Provides contextual data and operation
     * @return boolean if the entered RoomType exists in the database
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
      

        for(RoomType room: RoomType.values()){
            if(room.toString().equals(value)){
                return true;
            }
        }
        return false;
    }
    
}
