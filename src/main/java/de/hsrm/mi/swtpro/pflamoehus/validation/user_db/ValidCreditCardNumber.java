package de.hsrm.mi.swtpro.pflamoehus.validation.user_db;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.ElementType;

/*
 * ValidCreditCardNumber
 * Triggers a message when the given creditcard number is not valid.
 * 
 * @author Ann-Cathrin Fabian
 * @version 2
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidCreditCardNumberValidator.class)
@Documented
public @interface ValidCreditCardNumber {

    /**
     * @return default message
     */
    String message() default "Die angegebene Kreditkartennummer ist ungültig.";

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
