package de.hsrm.mi.swtpro.pflamoehus.validation.user_db;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/*
 * ValidPassword
 * Triggers a message when the given password is not valid.
 * 
 * @author Svenja Schenk
 * @version 2
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidPasswordValidator.class)
@Documented
public @interface ValidPassword {
    /**
     * 
     * @return default message
     */
    String message() default "The password needs to contain at least one upper case, one lower case letter, a sign and a number. Furthermore, the password needs to be at least 8 and at maximum 32 characters long.";

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