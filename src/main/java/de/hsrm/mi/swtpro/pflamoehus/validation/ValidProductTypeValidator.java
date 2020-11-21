package de.hsrm.mi.swtpro.pflamoehus.validation;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidProductTypeValidator implements ConstraintValidator<ValidProductType, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        String [] productType = {"Stuhl", "Pflanze", "Tisch", "Bett", "Dekoration", "Schrank/Kommode", "Sofa/Couch"};

        for(String type: productType){
            if(type.equals(value)){
                return true;
            }
        }
        return false;
    }
    
}
