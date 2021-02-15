package de.hsrm.mi.swtpro.pflamoehus.validation.user_db;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/*
 * ValidEmail
 * Triggers a message when the given email is not valid.
 * 
 * @author Svenja Schenk, Ann-Cathrin Fabian
 * @version 3
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidEmailValidator.class)
@Documented
public @interface ValidEmail {

    /**
     * 
     * @return default message
     */
    String message() default "Die angegebene Email-Adresse ist ungültig.";
    
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
