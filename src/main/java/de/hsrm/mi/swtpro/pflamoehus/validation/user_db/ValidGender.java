package de.hsrm.mi.swtpro.pflamoehus.validation.user_db;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

import javax.validation.Constraint;
import javax.validation.Payload;

/*
 * ValidGender
 * Triggers a message when the given gender is not valid.
 * 
 * @author Ann-Cathrin Fabian
 * @version 2
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidGenderValidator.class)
@Documented
public @interface ValidGender {

    /**
     * 
     * @return default message
     */
    String message() default "Ungültige Eingabe.";

    /**
     * @return class
     */
    Class<? extends Payload>[] payload() default {};

    /**
     * 
     * @return class
     */
    Class<?>[] groups() default {};

}
