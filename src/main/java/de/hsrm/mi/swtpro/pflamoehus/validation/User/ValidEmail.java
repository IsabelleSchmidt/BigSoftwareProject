package de.hsrm.mi.swtpro.pflamoehus.validation.User;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidEmailValidator.class)
@Documented
public @interface ValidEmail {
    String message() default "The Email Adress is invalid. ";

    Class<? extends Payload>[] payload() default{ };
    Class<?>[] groups() default{ };
}
