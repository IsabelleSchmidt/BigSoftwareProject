package de.hsrm.mi.swtpro.pflamoehus.validation.product_db;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/*
 * ValidRoomType
 * Triggers a message when the given roomtype is not valid.
 * 
 * @author Ann-Cathrin Fabian
 * @version 2
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidRoomTypeValidator.class)
@Documented
public @interface ValidRoomType {

    /**
     * 
     * @return default message
     */
    String message() default "This is not an existing room type. The following types are available: Wohnzimmer, Küche/Wohnküche, Kinderzimmer, Arbeitszimmer";

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
