package de.hsrm.mi.swtpro.pflamoehus.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidProductTypeValidator implements ConstraintValidator<ValidProductType, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // TODO Auto-generated method stub
        return false;
    }
    
}
