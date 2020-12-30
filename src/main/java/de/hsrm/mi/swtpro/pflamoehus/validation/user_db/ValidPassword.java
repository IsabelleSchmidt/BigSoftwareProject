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
 * @author Svenja Schenk, Ann-Cathrin Fabian
 * @version 3
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
    String message() default "Das Passwort muss mindestens einen Großbuchstaben, einen Kleinbuchstaben, ein Zeichen und eine Nummer enthalten. Desweiteren muss das Passwort mindestens 8 Zeichen und darf höchstens 32 Zeichen lang sein.";

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