package de.hsrm.mi.swtpro.pflamoehus.validation.product_db;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/*
 * ValidPicture
 * Triggers a message when the given picture is not valid.
 * 
 * 
 * @author Svenja Schenk
 * @version 2
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidPictureValidator.class)
@Documented
public @interface ValidPicture {

    /**
     * 
     * @return default message
     */
    String message() default "Make sure your File is a .jpg/.jpeg/.png File";

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
