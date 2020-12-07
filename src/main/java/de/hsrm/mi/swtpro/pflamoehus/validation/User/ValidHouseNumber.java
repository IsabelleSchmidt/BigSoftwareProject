package de.hsrm.mi.swtpro.pflamoehus.validation.User;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Triggers a message if an wrong Picture is entered.
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidHouseNumberValidator.class)
@Documented
public @interface ValidHouseNumber {
    String message() default "The house number must contain at least one digit and can contain a letter afterwards";

    Class<? extends Payload>[] payload() default{ };
    Class<?>[] groups() default{ };
}
