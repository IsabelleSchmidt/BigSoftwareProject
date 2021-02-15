package de.hsrm.mi.swtpro.pflamoehus.validation.product_db;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/*
 * ValidProductType
 * Triggers a message when the given producttype is not valid.
 * 
 * @author Ann-Cathrin Fabian
 * @version 1
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidProductTypeValidator.class)
@Documented
public @interface ValidProductType {
    
    /**
     * 
     * @return default message
     */
    String message() default "This is not an existing type of product. The following types are available: PLANT, TABLE, BED, CHAIR, DECORATION, CLOSET, COUCH";

    /**
     * 
     * @return class
     */
    Class<? extends Payload>[] payload() default {};

    /**
     * 
     * @return class
     */
    Class<?>[] groups() default {};

}
