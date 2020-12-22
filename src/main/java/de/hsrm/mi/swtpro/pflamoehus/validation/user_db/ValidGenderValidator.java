package de.hsrm.mi.swtpro.pflamoehus.validation.user_db;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/*
 * ValidGenderValidator
 * The given gender has to be the same as one value in the gender enum.
 * 
 * @author Svenja Schenk, Ann-Cathrin Fabian
 * @version 2
 */
public class ValidGenderValidator implements ConstraintValidator<ValidGender, String> {

    /**
     * Gender enum, which defines the valid types of gender.
     */
    private enum GENDER {
        /**
         * type 'männlich'
         */
        MALE("MALE"),
        /**
         * type 'weiblich'
         */
        FEMALE("FEMALE"),
        /**
         * type 'diverse'
         */
        DIVERSE("DIVERSE");

        private String value;

        GENDER(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }

    /**
     * @param value given gender
     * @param context contextual data
     * @return valid or not
     */

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        for (GENDER gender : GENDER.values()) {
            if (gender.toString().equals(value)) {
                return true;
            }
        }
        return false;
    }

}
