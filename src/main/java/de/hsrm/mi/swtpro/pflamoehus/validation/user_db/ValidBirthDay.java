package de.hsrm.mi.swtpro.pflamoehus.validation.user_db;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

import javax.validation.Constraint;
import javax.validation.Payload;

/*
 * ValidBirthDay
 * Triggers a message when the given birhdate is not valid.
 * 
 * @author Ann-Cathrin Fabian
 * @version 1
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidBirthDayValidator.class)
@Documented
public @interface ValidBirthDay {

    /**
     * 
     * @return default message
     */
    String message() default "You have to be 18 or older.";
    
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
