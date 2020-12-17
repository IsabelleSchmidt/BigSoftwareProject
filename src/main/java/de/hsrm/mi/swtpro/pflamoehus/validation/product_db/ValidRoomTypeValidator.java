package de.hsrm.mi.swtpro.pflamoehus.validation.product_db;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import de.hsrm.mi.swtpro.pflamoehus.product.RoomType;

/*
 * ValidRoomTypeValidator
 * The given producttype has to be the same as one value in the producttype enum.
 * 
 * @author Svenja Schenk, Ann-Cathrin Fabian
 * @version 2
 */
 public class ValidRoomTypeValidator implements ConstraintValidator<ValidRoomType, String> {

    /**
     * @param value given roomtype
     * @param context contextual data
     * @return valid or not
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null){
            return true;
        }
        for (RoomType room : RoomType.values()) {
            if (room.toString().equals(value)) {
                return true;
            }
        }
        return false;
    }

}
