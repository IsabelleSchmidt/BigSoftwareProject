package de.hsrm.mi.swtpro.pflamoehus.validation.user_db;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.ElementType;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidCreditCardNumberValidator.class)
@Documented
public @interface ValidCreditCardNumber {
    String message() default "You have to be 18 or older.";

    Class<? extends Payload>[] payload() default {};

    Class<?>[] groups() default {};
}
