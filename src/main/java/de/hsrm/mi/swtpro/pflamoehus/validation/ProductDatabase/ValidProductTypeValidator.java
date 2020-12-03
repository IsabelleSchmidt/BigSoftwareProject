package de.hsrm.mi.swtpro.pflamoehus.validation.ProductDatabase;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidProductTypeValidator implements ConstraintValidator<ValidProductType, String> {

    
    /** 
     * @param value passed value
     * @param context Provides contextual data and operation
     * @return boolean if the entered ProductType exists in the database 
     */
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
