package de.hsrm.mi.swtpro.pflamoehus.validation.product_db;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import de.hsrm.mi.swtpro.pflamoehus.product.ProductType;

/*
 * ValidProductTypeValidator
 * The given producttype has to be the same as one value in the producttype enum.
 * 
 * @author Svenja Schenk, Ann-Cathrin Fabian
 * @version 2
 */
public class ValidProductTypeValidator implements ConstraintValidator<ValidProductType, String> {

    /**
     * @param value given producttype
     * @param context contextual data
     * @return valid or not
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        for (ProductType type : ProductType.values()) {
            if (type.toString().equals(value)) {
                return true;
            }
        }
        return false;
    }

}
