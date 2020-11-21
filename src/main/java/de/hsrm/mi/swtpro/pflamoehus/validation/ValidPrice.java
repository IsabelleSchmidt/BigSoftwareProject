package de.hsrm.mi.swtpro.pflamoehus.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidPriceValidation.class)
@Documented
public @interface ValidPrice {
    String message() default "Der Preis darf nicht negativ sein!";

    Class<? extends Payload>[] payload() default{ };
    Class<?>[] groups() default{ };
    
}
