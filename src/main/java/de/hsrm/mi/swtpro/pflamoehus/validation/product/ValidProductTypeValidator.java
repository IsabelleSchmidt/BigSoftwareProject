package de.hsrm.mi.swtpro.pflamoehus.validation.product;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import de.hsrm.mi.swtpro.pflamoehus.product.ProductType;

public class ValidProductTypeValidator implements ConstraintValidator<ValidProductType, String> {

    
    /** 
     * @param value passed value
     * @param context Provides contextual data and operation
     * @return boolean if the entered ProductType exists in the database 
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        for(ProductType type: ProductType.values()){
            if(type.toString().equals(value)){
                return true;
            }
        }
        return false;
    }
    
}
