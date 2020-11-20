package de.hsrm.mi.swtpro.pflamoehus.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidPriceValidation implements ConstraintValidator<ValidPrice, Double> {

    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {
        
        if(value > 0){
            return true;
        }else{
            return false;
        }
        
    }
    
}
