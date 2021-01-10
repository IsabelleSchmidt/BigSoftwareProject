package de.hsrm.mi.swtpro.pflamoehus.validation.order_db;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/*
 * ValidStatus
 * Triggers a message when the given status is not valid.
 * 
 * @author Svenja Schenk
 * @version 1
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidStatusValidator.class)
@Documented
public @interface ValidStatus {
    /**
     * 
     * @return default message
     */
    String message() default "This Status is invalid.";

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
