package de.hsrm.mi.swtpro.pflamoehus.validation.user;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidGenderValidator implements ConstraintValidator<ValidGender, String> {

    private enum GENDER{
            MALE("MALE"), FEMALE("FEMALE"), DIVERSE("DIVERSE");
            private String gender;
            GENDER(String gender){
                this.gender = gender;
            }
    
            public String toString(){
                return gender;
            }
        }


    
    /** 
     * @param value
     * @param context
     * @return boolean
     */

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        
        for(GENDER gender: GENDER.values()){
            if(gender.toString().equals(value)){
                return true;
            }
        }
        return false;
    }
    
}
